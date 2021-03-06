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
import jpa.model.Payment;
import jpa.model.Account;
import jpa.model.Shipping;
import jpa.model.Lineitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Orders;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class OrdersJpaController implements Serializable {

    public OrdersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orders orders) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (orders.getLineitemList() == null) {
            orders.setLineitemList(new ArrayList<Lineitem>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Payment payment = orders.getPayment();
            if (payment != null) {
                payment = em.getReference(payment.getClass(), payment.getPaymentid());
                orders.setPayment(payment);
            }
            Account username = orders.getUsername();
            if (username != null) {
                username = em.getReference(username.getClass(), username.getUsername());
                orders.setUsername(username);
            }
            Shipping shipping = orders.getShipping();
            if (shipping != null) {
                shipping = em.getReference(shipping.getClass(), shipping.getShippingid());
                orders.setShipping(shipping);
            }
            List<Lineitem> attachedLineitemList = new ArrayList<Lineitem>();
            for (Lineitem lineitemListLineitemToAttach : orders.getLineitemList()) {
                lineitemListLineitemToAttach = em.getReference(lineitemListLineitemToAttach.getClass(), lineitemListLineitemToAttach.getLineitemPK());
                attachedLineitemList.add(lineitemListLineitemToAttach);
            }
            orders.setLineitemList(attachedLineitemList);
            em.persist(orders);
            if (payment != null) {
                Orders oldOrderidOfPayment = payment.getOrderid();
                if (oldOrderidOfPayment != null) {
                    oldOrderidOfPayment.setPayment(null);
                    oldOrderidOfPayment = em.merge(oldOrderidOfPayment);
                }
                payment.setOrderid(orders);
                payment = em.merge(payment);
            }
            if (username != null) {
                username.getOrdersList().add(orders);
                username = em.merge(username);
            }
            if (shipping != null) {
                Orders oldOrderidOfShipping = shipping.getOrderid();
                if (oldOrderidOfShipping != null) {
                    oldOrderidOfShipping.setShipping(null);
                    oldOrderidOfShipping = em.merge(oldOrderidOfShipping);
                }
                shipping.setOrderid(orders);
                shipping = em.merge(shipping);
            }
            for (Lineitem lineitemListLineitem : orders.getLineitemList()) {
                Orders oldOrdersOfLineitemListLineitem = lineitemListLineitem.getOrders();
                lineitemListLineitem.setOrders(orders);
                lineitemListLineitem = em.merge(lineitemListLineitem);
                if (oldOrdersOfLineitemListLineitem != null) {
                    oldOrdersOfLineitemListLineitem.getLineitemList().remove(lineitemListLineitem);
                    oldOrdersOfLineitemListLineitem = em.merge(oldOrdersOfLineitemListLineitem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrders(orders.getOrderid()) != null) {
                throw new PreexistingEntityException("Orders " + orders + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orders orders) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orders persistentOrders = em.find(Orders.class, orders.getOrderid());
            Payment paymentOld = persistentOrders.getPayment();
            Payment paymentNew = orders.getPayment();
            Account usernameOld = persistentOrders.getUsername();
            Account usernameNew = orders.getUsername();
            Shipping shippingOld = persistentOrders.getShipping();
            Shipping shippingNew = orders.getShipping();
            List<Lineitem> lineitemListOld = persistentOrders.getLineitemList();
            List<Lineitem> lineitemListNew = orders.getLineitemList();
            List<String> illegalOrphanMessages = null;
            if (paymentOld != null && !paymentOld.equals(paymentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Payment " + paymentOld + " since its orderid field is not nullable.");
            }
            if (shippingOld != null && !shippingOld.equals(shippingNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Shipping " + shippingOld + " since its orderid field is not nullable.");
            }
            for (Lineitem lineitemListOldLineitem : lineitemListOld) {
                if (!lineitemListNew.contains(lineitemListOldLineitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lineitem " + lineitemListOldLineitem + " since its orders field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paymentNew != null) {
                paymentNew = em.getReference(paymentNew.getClass(), paymentNew.getPaymentid());
                orders.setPayment(paymentNew);
            }
            if (usernameNew != null) {
                usernameNew = em.getReference(usernameNew.getClass(), usernameNew.getUsername());
                orders.setUsername(usernameNew);
            }
            if (shippingNew != null) {
                shippingNew = em.getReference(shippingNew.getClass(), shippingNew.getShippingid());
                orders.setShipping(shippingNew);
            }
            List<Lineitem> attachedLineitemListNew = new ArrayList<Lineitem>();
            for (Lineitem lineitemListNewLineitemToAttach : lineitemListNew) {
                lineitemListNewLineitemToAttach = em.getReference(lineitemListNewLineitemToAttach.getClass(), lineitemListNewLineitemToAttach.getLineitemPK());
                attachedLineitemListNew.add(lineitemListNewLineitemToAttach);
            }
            lineitemListNew = attachedLineitemListNew;
            orders.setLineitemList(lineitemListNew);
            orders = em.merge(orders);
            if (paymentNew != null && !paymentNew.equals(paymentOld)) {
                Orders oldOrderidOfPayment = paymentNew.getOrderid();
                if (oldOrderidOfPayment != null) {
                    oldOrderidOfPayment.setPayment(null);
                    oldOrderidOfPayment = em.merge(oldOrderidOfPayment);
                }
                paymentNew.setOrderid(orders);
                paymentNew = em.merge(paymentNew);
            }
            if (usernameOld != null && !usernameOld.equals(usernameNew)) {
                usernameOld.getOrdersList().remove(orders);
                usernameOld = em.merge(usernameOld);
            }
            if (usernameNew != null && !usernameNew.equals(usernameOld)) {
                usernameNew.getOrdersList().add(orders);
                usernameNew = em.merge(usernameNew);
            }
            if (shippingNew != null && !shippingNew.equals(shippingOld)) {
                Orders oldOrderidOfShipping = shippingNew.getOrderid();
                if (oldOrderidOfShipping != null) {
                    oldOrderidOfShipping.setShipping(null);
                    oldOrderidOfShipping = em.merge(oldOrderidOfShipping);
                }
                shippingNew.setOrderid(orders);
                shippingNew = em.merge(shippingNew);
            }
            for (Lineitem lineitemListNewLineitem : lineitemListNew) {
                if (!lineitemListOld.contains(lineitemListNewLineitem)) {
                    Orders oldOrdersOfLineitemListNewLineitem = lineitemListNewLineitem.getOrders();
                    lineitemListNewLineitem.setOrders(orders);
                    lineitemListNewLineitem = em.merge(lineitemListNewLineitem);
                    if (oldOrdersOfLineitemListNewLineitem != null && !oldOrdersOfLineitemListNewLineitem.equals(orders)) {
                        oldOrdersOfLineitemListNewLineitem.getLineitemList().remove(lineitemListNewLineitem);
                        oldOrdersOfLineitemListNewLineitem = em.merge(oldOrdersOfLineitemListNewLineitem);
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
                Integer id = orders.getOrderid();
                if (findOrders(id) == null) {
                    throw new NonexistentEntityException("The orders with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orders orders;
            try {
                orders = em.getReference(Orders.class, id);
                orders.getOrderid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orders with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Payment paymentOrphanCheck = orders.getPayment();
            if (paymentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Payment " + paymentOrphanCheck + " in its payment field has a non-nullable orderid field.");
            }
            Shipping shippingOrphanCheck = orders.getShipping();
            if (shippingOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Shipping " + shippingOrphanCheck + " in its shipping field has a non-nullable orderid field.");
            }
            List<Lineitem> lineitemListOrphanCheck = orders.getLineitemList();
            for (Lineitem lineitemListOrphanCheckLineitem : lineitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Lineitem " + lineitemListOrphanCheckLineitem + " in its lineitemList field has a non-nullable orders field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account username = orders.getUsername();
            if (username != null) {
                username.getOrdersList().remove(orders);
                username = em.merge(username);
            }
            em.remove(orders);
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

    public List<Orders> findOrdersEntities() {
        return findOrdersEntities(true, -1, -1);
    }

    public List<Orders> findOrdersEntities(int maxResults, int firstResult) {
        return findOrdersEntities(false, maxResults, firstResult);
    }

    private List<Orders> findOrdersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orders.class));
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

    public Orders findOrders(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orders.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orders> rt = cq.from(Orders.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
