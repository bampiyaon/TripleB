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
import jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Shop;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.PreexistingEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author piyao
 */
public class ShopJpaController implements Serializable {

    public ShopJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Shop shop) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (shop.getProductList() == null) {
            shop.setProductList(new ArrayList<Product>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : shop.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductid());
                attachedProductList.add(productListProductToAttach);
            }
            shop.setProductList(attachedProductList);
            em.persist(shop);
            for (Product productListProduct : shop.getProductList()) {
                Shop oldShopidOfProductListProduct = productListProduct.getShopid();
                productListProduct.setShopid(shop);
                productListProduct = em.merge(productListProduct);
                if (oldShopidOfProductListProduct != null) {
                    oldShopidOfProductListProduct.getProductList().remove(productListProduct);
                    oldShopidOfProductListProduct = em.merge(oldShopidOfProductListProduct);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findShop(shop.getShopid()) != null) {
                throw new PreexistingEntityException("Shop " + shop + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Shop shop) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Shop persistentShop = em.find(Shop.class, shop.getShopid());
            List<Product> productListOld = persistentShop.getProductList();
            List<Product> productListNew = shop.getProductList();
            List<String> illegalOrphanMessages = null;
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its shopid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getProductid());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            shop.setProductList(productListNew);
            shop = em.merge(shop);
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Shop oldShopidOfProductListNewProduct = productListNewProduct.getShopid();
                    productListNewProduct.setShopid(shop);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldShopidOfProductListNewProduct != null && !oldShopidOfProductListNewProduct.equals(shop)) {
                        oldShopidOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldShopidOfProductListNewProduct = em.merge(oldShopidOfProductListNewProduct);
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
                String id = shop.getShopid();
                if (findShop(id) == null) {
                    throw new NonexistentEntityException("The shop with id " + id + " no longer exists.");
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
            Shop shop;
            try {
                shop = em.getReference(Shop.class, id);
                shop.getShopid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The shop with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Product> productListOrphanCheck = shop.getProductList();
            for (Product productListOrphanCheckProduct : productListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Shop (" + shop + ") cannot be destroyed since the Product " + productListOrphanCheckProduct + " in its productList field has a non-nullable shopid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(shop);
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

    public List<Shop> findShopEntities() {
        return findShopEntities(true, -1, -1);
    }

    public List<Shop> findShopEntities(int maxResults, int firstResult) {
        return findShopEntities(false, maxResults, firstResult);
    }

    private List<Shop> findShopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Shop.class));
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

    public Shop findShop(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Shop.class, id);
        } finally {
            em.close();
        }
    }

    public int getShopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Shop> rt = cq.from(Shop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
