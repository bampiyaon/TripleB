/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.model.Shop;
import jpa.model.Lineitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Product;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (product.getLineitemList() == null) {
            product.setLineitemList(new ArrayList<Lineitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Shop shopid = product.getShopid();
            if (shopid != null) {
                shopid = em.getReference(shopid.getClass(), shopid.getShopid());
                product.setShopid(shopid);
            }
            List<Lineitem> attachedLineitemList = new ArrayList<Lineitem>();
            for (Lineitem lineitemListLineitemToAttach : product.getLineitemList()) {
                lineitemListLineitemToAttach = em.getReference(lineitemListLineitemToAttach.getClass(), lineitemListLineitemToAttach.getLineitemPK());
                attachedLineitemList.add(lineitemListLineitemToAttach);
            }
            product.setLineitemList(attachedLineitemList);
            em.persist(product);
            if (shopid != null) {
                shopid.getProductList().add(product);
                shopid = em.merge(shopid);
            }
            for (Lineitem lineitemListLineitem : product.getLineitemList()) {
                Product oldProductOfLineitemListLineitem = lineitemListLineitem.getProduct();
                lineitemListLineitem.setProduct(product);
                lineitemListLineitem = em.merge(lineitemListLineitem);
                if (oldProductOfLineitemListLineitem != null) {
                    oldProductOfLineitemListLineitem.getLineitemList().remove(lineitemListLineitem);
                    oldProductOfLineitemListLineitem = em.merge(oldProductOfLineitemListLineitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProduct(product.getProductid()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductid());
            Shop shopidOld = persistentProduct.getShopid();
            Shop shopidNew = product.getShopid();
            List<Lineitem> lineitemListOld = persistentProduct.getLineitemList();
            List<Lineitem> lineitemListNew = product.getLineitemList();
            List<String> illegalOrphanMessages = null;
            for (Lineitem lineitemListOldLineitem : lineitemListOld) {
                if (!lineitemListNew.contains(lineitemListOldLineitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lineitem " + lineitemListOldLineitem + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (shopidNew != null) {
                shopidNew = em.getReference(shopidNew.getClass(), shopidNew.getShopid());
                product.setShopid(shopidNew);
            }
            List<Lineitem> attachedLineitemListNew = new ArrayList<Lineitem>();
            for (Lineitem lineitemListNewLineitemToAttach : lineitemListNew) {
                lineitemListNewLineitemToAttach = em.getReference(lineitemListNewLineitemToAttach.getClass(), lineitemListNewLineitemToAttach.getLineitemPK());
                attachedLineitemListNew.add(lineitemListNewLineitemToAttach);
            }
            lineitemListNew = attachedLineitemListNew;
            product.setLineitemList(lineitemListNew);
            product = em.merge(product);
            if (shopidOld != null && !shopidOld.equals(shopidNew)) {
                shopidOld.getProductList().remove(product);
                shopidOld = em.merge(shopidOld);
            }
            if (shopidNew != null && !shopidNew.equals(shopidOld)) {
                shopidNew.getProductList().add(product);
                shopidNew = em.merge(shopidNew);
            }
            for (Lineitem lineitemListNewLineitem : lineitemListNew) {
                if (!lineitemListOld.contains(lineitemListNewLineitem)) {
                    Product oldProductOfLineitemListNewLineitem = lineitemListNewLineitem.getProduct();
                    lineitemListNewLineitem.setProduct(product);
                    lineitemListNewLineitem = em.merge(lineitemListNewLineitem);
                    if (oldProductOfLineitemListNewLineitem != null && !oldProductOfLineitemListNewLineitem.equals(product)) {
                        oldProductOfLineitemListNewLineitem.getLineitemList().remove(lineitemListNewLineitem);
                        oldProductOfLineitemListNewLineitem = em.merge(oldProductOfLineitemListNewLineitem);
                    }
                }
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
                String id = product.getProductid();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lineitem> lineitemListOrphanCheck = product.getLineitemList();
            for (Lineitem lineitemListOrphanCheckLineitem : lineitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Lineitem " + lineitemListOrphanCheckLineitem + " in its lineitemList field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Shop shopid = product.getShopid();
            if (shopid != null) {
                shopid.getProductList().remove(product);
                shopid = em.merge(shopid);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
