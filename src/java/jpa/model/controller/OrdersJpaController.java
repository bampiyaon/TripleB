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
import jpa.model.Lineitem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Orders;
import jpa.model.Shipping;
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
        if (orders.getShippingList() == null) {
            orders.setShippingList(new ArrayList<Shipping>());
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
            List<Lineitem> attachedLineitemList = new ArrayList<Lineitem>();
            for (Lineitem lineitemListLineitemToAttach : orders.getLineitemList()) {
                lineitemListLineitemToAttach = em.getReference(lineitemListLineitemToAttach.getClass(), lineitemListLineitemToAttach.getLineitemPK());
                attachedLineitemList.add(lineitemListLineitemToAttach);
            }
            orders.setLineitemList(attachedLineitemList);
            List<Shipping> attachedShippingList = new ArrayList<Shipping>();
            for (Shipping shippingListShippingToAttach : orders.getShippingList()) {
                shippingListShippingToAttach = em.getReference(shippingListShippingToAttach.getClass(), shippingListShippingToAttach.getShippingid());
                attachedShippingList.add(shippingListShippingToAttach);
            }
            orders.setShippingList(attachedShippingList);
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
            for (Lineitem lineitemListLineitem : orders.getLineitemList()) {
                Orders oldOrdersOfLineitemListLineitem = lineitemListLineitem.getOrders();
                lineitemListLineitem.setOrders(orders);
                lineitemListLineitem = em.merge(lineitemListLineitem);
                if (oldOrdersOfLineitemListLineitem != null) {
                    oldOrdersOfLineitemListLineitem.getLineitemList().remove(lineitemListLineitem);
                    oldOrdersOfLineitemListLineitem = em.merge(oldOrdersOfLineitemListLineitem);
                }
            }
            for (Shipping shippingListShipping : orders.getShippingList()) {
                Orders oldOrderidOfShippingListShipping = shippingListShipping.getOrderid();
                shippingListShipping.setOrderid(orders);
                shippingListShipping = em.merge(shippingListShipping);
                if (oldOrderidOfShippingListShipping != null) {
                    oldOrderidOfShippingListShipping.getShippingList().remove(shippingListShipping);
                    oldOrderidOfShippingListShipping = em.merge(oldOrderidOfShippingListShipping);
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
            List<Lineitem> lineitemListOld = persistentOrders.getLineitemList();
            List<Lineitem> lineitemListNew = orders.getLineitemList();
            List<Shipping> shippingListOld = persistentOrders.getShippingList();
            List<Shipping> shippingListNew = orders.getShippingList();
            List<String> illegalOrphanMessages = null;
            if (paymentOld != null && !paymentOld.equals(paymentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Payment " + paymentOld + " since its orderid field is not nullable.");
            }
            for (Lineitem lineitemListOldLineitem : lineitemListOld) {
                if (!lineitemListNew.contains(lineitemListOldLineitem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lineitem " + lineitemListOldLineitem + " since its orders field is not nullable.");
                }
            }
            for (Shipping shippingListOldShipping : shippingListOld) {
                if (!shippingListNew.contains(shippingListOldShipping)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Shipping " + shippingListOldShipping + " since its orderid field is not nullable.");
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
            List<Lineitem> attachedLineitemListNew = new ArrayList<Lineitem>();
            for (Lineitem lineitemListNewLineitemToAttach : lineitemListNew) {
                lineitemListNewLineitemToAttach = em.getReference(lineitemListNewLineitemToAttach.getClass(), lineitemListNewLineitemToAttach.getLineitemPK());
                attachedLineitemListNew.add(lineitemListNewLineitemToAttach);
            }
            lineitemListNew = attachedLineitemListNew;
            orders.setLineitemList(lineitemListNew);
            List<Shipping> attachedShippingListNew = new ArrayList<Shipping>();
            for (Shipping shippingListNewShippingToAttach : shippingListNew) {
                shippingListNewShippingToAttach = em.getReference(shippingListNewShippingToAttach.getClass(), shippingListNewShippingToAttach.getShippingid());
                attachedShippingListNew.add(shippingListNewShippingToAttach);
            }
            shippingListNew = attachedShippingListNew;
            orders.setShippingList(shippingListNew);
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
            for (Shipping shippingListNewShipping : shippingListNew) {
                if (!shippingListOld.contains(shippingListNewShipping)) {
                    Orders oldOrderidOfShippingListNewShipping = shippingListNewShipping.getOrderid();
                    shippingListNewShipping.setOrderid(orders);
                    shippingListNewShipping = em.merge(shippingListNewShipping);
                    if (oldOrderidOfShippingListNewShipping != null && !oldOrderidOfShippingListNewShipping.equals(orders)) {
                        oldOrderidOfShippingListNewShipping.getShippingList().remove(shippingListNewShipping);
                        oldOrderidOfShippingListNewShipping = em.merge(oldOrderidOfShippingListNewShipping);
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
            List<Lineitem> lineitemListOrphanCheck = orders.getLineitemList();
            for (Lineitem lineitemListOrphanCheckLineitem : lineitemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Lineitem " + lineitemListOrphanCheckLineitem + " in its lineitemList field has a non-nullable orders field.");
            }
            List<Shipping> shippingListOrphanCheck = orders.getShippingList();
            for (Shipping shippingListOrphanCheckShipping : shippingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orders (" + orders + ") cannot be destroyed since the Shipping " + shippingListOrphanCheckShipping + " in its shippingList field has a non-nullable orderid field.");
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
