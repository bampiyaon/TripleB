/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.model.Lineitem;
import jpa.model.LineitemPK;
import jpa.model.Orders;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class LineitemJpaController implements Serializable {

    public LineitemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lineitem lineitem) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (lineitem.getLineitemPK() == null) {
            lineitem.setLineitemPK(new LineitemPK());
        }
        lineitem.getLineitemPK().setProductid(lineitem.getProduct().getProductid());
        lineitem.getLineitemPK().setOrderid(lineitem.getOrders().getOrderid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orders orders = lineitem.getOrders();
            if (orders != null) {
                orders = em.getReference(orders.getClass(), orders.getOrderid());
                lineitem.setOrders(orders);
            }
            Product product = lineitem.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductid());
                lineitem.setProduct(product);
            }
            em.persist(lineitem);
            if (orders != null) {
                orders.getLineitemList().add(lineitem);
                orders = em.merge(orders);
            }
            if (product != null) {
                product.getLineitemList().add(lineitem);
                product = em.merge(product);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLineitem(lineitem.getLineitemPK()) != null) {
                throw new PreexistingEntityException("Lineitem " + lineitem + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lineitem lineitem) throws NonexistentEntityException, RollbackFailureException, Exception {
        lineitem.getLineitemPK().setProductid(lineitem.getProduct().getProductid());
        lineitem.getLineitemPK().setOrderid(lineitem.getOrders().getOrderid());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lineitem persistentLineitem = em.find(Lineitem.class, lineitem.getLineitemPK());
            Orders ordersOld = persistentLineitem.getOrders();
            Orders ordersNew = lineitem.getOrders();
            Product productOld = persistentLineitem.getProduct();
            Product productNew = lineitem.getProduct();
            if (ordersNew != null) {
                ordersNew = em.getReference(ordersNew.getClass(), ordersNew.getOrderid());
                lineitem.setOrders(ordersNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductid());
                lineitem.setProduct(productNew);
            }
            lineitem = em.merge(lineitem);
            if (ordersOld != null && !ordersOld.equals(ordersNew)) {
                ordersOld.getLineitemList().remove(lineitem);
                ordersOld = em.merge(ordersOld);
            }
            if (ordersNew != null && !ordersNew.equals(ordersOld)) {
                ordersNew.getLineitemList().add(lineitem);
                ordersNew = em.merge(ordersNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getLineitemList().remove(lineitem);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getLineitemList().add(lineitem);
                productNew = em.merge(productNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LineitemPK id = lineitem.getLineitemPK();
                if (findLineitem(id) == null) {
                    throw new NonexistentEntityException("The lineitem with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LineitemPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lineitem lineitem;
            try {
                lineitem = em.getReference(Lineitem.class, id);
                lineitem.getLineitemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineitem with id " + id + " no longer exists.", enfe);
            }
            Orders orders = lineitem.getOrders();
            if (orders != null) {
                orders.getLineitemList().remove(lineitem);
                orders = em.merge(orders);
            }
            Product product = lineitem.getProduct();
            if (product != null) {
                product.getLineitemList().remove(lineitem);
                product = em.merge(product);
            }
            em.remove(lineitem);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lineitem> findLineitemEntities() {
        return findLineitemEntities(true, -1, -1);
    }

    public List<Lineitem> findLineitemEntities(int maxResults, int firstResult) {
        return findLineitemEntities(false, maxResults, firstResult);
    }

    private List<Lineitem> findLineitemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lineitem.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Lineitem findLineitem(LineitemPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lineitem.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineitemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lineitem> rt = cq.from(Lineitem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
