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
import jpa.model.Account;
import jpa.model.Shipping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Address;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class AddressJpaController implements Serializable {

    public AddressJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (address.getShippingList() == null) {
            address.setShippingList(new ArrayList<Shipping>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account username = address.getUsername();
            if (username != null) {
                username = em.getReference(username.getClass(), username.getUsername());
                address.setUsername(username);
            }
            List<Shipping> attachedShippingList = new ArrayList<Shipping>();
            for (Shipping shippingListShippingToAttach : address.getShippingList()) {
                shippingListShippingToAttach = em.getReference(shippingListShippingToAttach.getClass(), shippingListShippingToAttach.getShippingid());
                attachedShippingList.add(shippingListShippingToAttach);
            }
            address.setShippingList(attachedShippingList);
            em.persist(address);
            if (username != null) {
                username.getAddressList().add(address);
                username = em.merge(username);
            }
            for (Shipping shippingListShipping : address.getShippingList()) {
                Address oldAddressidOfShippingListShipping = shippingListShipping.getAddressid();
                shippingListShipping.setAddressid(address);
                shippingListShipping = em.merge(shippingListShipping);
                if (oldAddressidOfShippingListShipping != null) {
                    oldAddressidOfShippingListShipping.getShippingList().remove(shippingListShipping);
                    oldAddressidOfShippingListShipping = em.merge(oldAddressidOfShippingListShipping);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAddress(address.getAddressid()) != null) {
                throw new PreexistingEntityException("Address " + address + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Address address) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Address persistentAddress = em.find(Address.class, address.getAddressid());
            Account usernameOld = persistentAddress.getUsername();
            Account usernameNew = address.getUsername();
            List<Shipping> shippingListOld = persistentAddress.getShippingList();
            List<Shipping> shippingListNew = address.getShippingList();
            List<String> illegalOrphanMessages = null;
            for (Shipping shippingListOldShipping : shippingListOld) {
                if (!shippingListNew.contains(shippingListOldShipping)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Shipping " + shippingListOldShipping + " since its addressid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usernameNew != null) {
                usernameNew = em.getReference(usernameNew.getClass(), usernameNew.getUsername());
                address.setUsername(usernameNew);
            }
            List<Shipping> attachedShippingListNew = new ArrayList<Shipping>();
            for (Shipping shippingListNewShippingToAttach : shippingListNew) {
                shippingListNewShippingToAttach = em.getReference(shippingListNewShippingToAttach.getClass(), shippingListNewShippingToAttach.getShippingid());
                attachedShippingListNew.add(shippingListNewShippingToAttach);
            }
            shippingListNew = attachedShippingListNew;
            address.setShippingList(shippingListNew);
            address = em.merge(address);
            if (usernameOld != null && !usernameOld.equals(usernameNew)) {
                usernameOld.getAddressList().remove(address);
                usernameOld = em.merge(usernameOld);
            }
            if (usernameNew != null && !usernameNew.equals(usernameOld)) {
                usernameNew.getAddressList().add(address);
                usernameNew = em.merge(usernameNew);
            }
            for (Shipping shippingListNewShipping : shippingListNew) {
                if (!shippingListOld.contains(shippingListNewShipping)) {
                    Address oldAddressidOfShippingListNewShipping = shippingListNewShipping.getAddressid();
                    shippingListNewShipping.setAddressid(address);
                    shippingListNewShipping = em.merge(shippingListNewShipping);
                    if (oldAddressidOfShippingListNewShipping != null && !oldAddressidOfShippingListNewShipping.equals(address)) {
                        oldAddressidOfShippingListNewShipping.getShippingList().remove(shippingListNewShipping);
                        oldAddressidOfShippingListNewShipping = em.merge(oldAddressidOfShippingListNewShipping);
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
                Integer id = address.getAddressid();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
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
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getAddressid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Shipping> shippingListOrphanCheck = address.getShippingList();
            for (Shipping shippingListOrphanCheckShipping : shippingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the Shipping " + shippingListOrphanCheckShipping + " in its shippingList field has a non-nullable addressid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account username = address.getUsername();
            if (username != null) {
                username.getAddressList().remove(address);
                username = em.merge(username);
            }
            em.remove(address);
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

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Address.class));
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

    public Address findAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Address> rt = cq.from(Address.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
