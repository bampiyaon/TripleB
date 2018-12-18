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
import jpa.model.Address;
import jpa.model.Orders;
import jpa.model.Shipping;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class ShippingJpaController implements Serializable {

    public ShippingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Shipping shipping) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Address addressid = shipping.getAddressid();
            if (addressid != null) {
                addressid = em.getReference(addressid.getClass(), addressid.getAddressid());
                shipping.setAddressid(addressid);
            }
            Orders orderid = shipping.getOrderid();
            if (orderid != null) {
                orderid = em.getReference(orderid.getClass(), orderid.getOrderid());
                shipping.setOrderid(orderid);
            }
            em.persist(shipping);
            if (addressid != null) {
                addressid.getShippingList().add(shipping);
                addressid = em.merge(addressid);
            }
            if (orderid != null) {
                orderid.getShippingList().add(shipping);
                orderid = em.merge(orderid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findShipping(shipping.getShippingid()) != null) {
                throw new PreexistingEntityException("Shipping " + shipping + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Shipping shipping) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Shipping persistentShipping = em.find(Shipping.class, shipping.getShippingid());
            Address addressidOld = persistentShipping.getAddressid();
            Address addressidNew = shipping.getAddressid();
            Orders orderidOld = persistentShipping.getOrderid();
            Orders orderidNew = shipping.getOrderid();
            if (addressidNew != null) {
                addressidNew = em.getReference(addressidNew.getClass(), addressidNew.getAddressid());
                shipping.setAddressid(addressidNew);
            }
            if (orderidNew != null) {
                orderidNew = em.getReference(orderidNew.getClass(), orderidNew.getOrderid());
                shipping.setOrderid(orderidNew);
            }
            shipping = em.merge(shipping);
            if (addressidOld != null && !addressidOld.equals(addressidNew)) {
                addressidOld.getShippingList().remove(shipping);
                addressidOld = em.merge(addressidOld);
            }
            if (addressidNew != null && !addressidNew.equals(addressidOld)) {
                addressidNew.getShippingList().add(shipping);
                addressidNew = em.merge(addressidNew);
            }
            if (orderidOld != null && !orderidOld.equals(orderidNew)) {
                orderidOld.getShippingList().remove(shipping);
                orderidOld = em.merge(orderidOld);
            }
            if (orderidNew != null && !orderidNew.equals(orderidOld)) {
                orderidNew.getShippingList().add(shipping);
                orderidNew = em.merge(orderidNew);
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
                Integer id = shipping.getShippingid();
                if (findShipping(id) == null) {
                    throw new NonexistentEntityException("The shipping with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Shipping shipping;
            try {
                shipping = em.getReference(Shipping.class, id);
                shipping.getShippingid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shipping with id " + id + " no longer exists.", enfe);
            }
            Address addressid = shipping.getAddressid();
            if (addressid != null) {
                addressid.getShippingList().remove(shipping);
                addressid = em.merge(addressid);
            }
            Orders orderid = shipping.getOrderid();
            if (orderid != null) {
                orderid.getShippingList().remove(shipping);
                orderid = em.merge(orderid);
            }
            em.remove(shipping);
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

    public List<Shipping> findShippingEntities() {
        return findShippingEntities(true, -1, -1);
    }

    public List<Shipping> findShippingEntities(int maxResults, int firstResult) {
        return findShippingEntities(false, maxResults, firstResult);
    }

    private List<Shipping> findShippingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Shipping.class));
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

    public Shipping findShipping(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Shipping.class, id);
        } finally {
            em.close();
        }
    }

    public int getShippingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Shipping> rt = cq.from(Shipping.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
