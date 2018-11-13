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
import jpa.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
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

    public void create(Shop shop) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (shop.getProductList() == null) {
            shop.setProductList(new ArrayList<Product>());
        }
        List<String> illegalOrphanMessages = null;
        Shop shop1OrphanCheck = shop.getShop1();
        if (shop1OrphanCheck != null) {
            Shop oldShopOfShop1 = shop1OrphanCheck.getShop();
            if (oldShopOfShop1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Shop " + shop1OrphanCheck + " already has an item of type Shop whose shop1 column cannot be null. Please make another selection for the shop1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Shop shopRel = shop.getShop();
            if (shopRel != null) {
                shopRel = em.getReference(shopRel.getClass(), shopRel.getShopid());
                shop.setShop(shopRel);
            }
            Shop shop1 = shop.getShop1();
            if (shop1 != null) {
                shop1 = em.getReference(shop1.getClass(), shop1.getShopid());
                shop.setShop1(shop1);
            }
            List<Product> attachedProductList = new ArrayList<Product>();
            for (Product productListProductToAttach : shop.getProductList()) {
                productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getProductid());
                attachedProductList.add(productListProductToAttach);
            }
            shop.setProductList(attachedProductList);
            em.persist(shop);
            if (shopRel != null) {
                Shop oldShop1OfShopRel = shopRel.getShop1();
                if (oldShop1OfShopRel != null) {
                    oldShop1OfShopRel.setShop(null);
                    oldShop1OfShopRel = em.merge(oldShop1OfShopRel);
                }
                shopRel.setShop1(shop);
                shopRel = em.merge(shopRel);
            }
            if (shop1 != null) {
                shop1.setShop(shop);
                shop1 = em.merge(shop1);
            }
            for (Product productListProduct : shop.getProductList()) {
                Shop oldShopShopidOfProductListProduct = productListProduct.getShopShopid();
                productListProduct.setShopShopid(shop);
                productListProduct = em.merge(productListProduct);
                if (oldShopShopidOfProductListProduct != null) {
                    oldShopShopidOfProductListProduct.getProductList().remove(productListProduct);
                    oldShopShopidOfProductListProduct = em.merge(oldShopShopidOfProductListProduct);
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
            Shop shopRelOld = persistentShop.getShop();
            Shop shopRelNew = shop.getShop();
            Shop shop1Old = persistentShop.getShop1();
            Shop shop1New = shop.getShop1();
            List<Product> productListOld = persistentShop.getProductList();
            List<Product> productListNew = shop.getProductList();
            List<String> illegalOrphanMessages = null;
            if (shopRelOld != null && !shopRelOld.equals(shopRelNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Shop " + shopRelOld + " since its shop1 field is not nullable.");
            }
            if (shop1New != null && !shop1New.equals(shop1Old)) {
                Shop oldShopOfShop1 = shop1New.getShop();
                if (oldShopOfShop1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Shop " + shop1New + " already has an item of type Shop whose shop1 column cannot be null. Please make another selection for the shop1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (shopRelNew != null) {
                shopRelNew = em.getReference(shopRelNew.getClass(), shopRelNew.getShopid());
                shop.setShop(shopRelNew);
            }
            if (shop1New != null) {
                shop1New = em.getReference(shop1New.getClass(), shop1New.getShopid());
                shop.setShop1(shop1New);
            }
            List<Product> attachedProductListNew = new ArrayList<Product>();
            for (Product productListNewProductToAttach : productListNew) {
                productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getProductid());
                attachedProductListNew.add(productListNewProductToAttach);
            }
            productListNew = attachedProductListNew;
            shop.setProductList(productListNew);
            shop = em.merge(shop);
            if (shopRelNew != null && !shopRelNew.equals(shopRelOld)) {
                Shop oldShop1OfShopRel = shopRelNew.getShop1();
                if (oldShop1OfShopRel != null) {
                    oldShop1OfShopRel.setShop(null);
                    oldShop1OfShopRel = em.merge(oldShop1OfShopRel);
                }
                shopRelNew.setShop1(shop);
                shopRelNew = em.merge(shopRelNew);
            }
            if (shop1Old != null && !shop1Old.equals(shop1New)) {
                shop1Old.setShop(null);
                shop1Old = em.merge(shop1Old);
            }
            if (shop1New != null && !shop1New.equals(shop1Old)) {
                shop1New.setShop(shop);
                shop1New = em.merge(shop1New);
            }
            for (Product productListOldProduct : productListOld) {
                if (!productListNew.contains(productListOldProduct)) {
                    productListOldProduct.setShopShopid(null);
                    productListOldProduct = em.merge(productListOldProduct);
                }
            }
            for (Product productListNewProduct : productListNew) {
                if (!productListOld.contains(productListNewProduct)) {
                    Shop oldShopShopidOfProductListNewProduct = productListNewProduct.getShopShopid();
                    productListNewProduct.setShopShopid(shop);
                    productListNewProduct = em.merge(productListNewProduct);
                    if (oldShopShopidOfProductListNewProduct != null && !oldShopShopidOfProductListNewProduct.equals(shop)) {
                        oldShopShopidOfProductListNewProduct.getProductList().remove(productListNewProduct);
                        oldShopShopidOfProductListNewProduct = em.merge(oldShopShopidOfProductListNewProduct);
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
                Integer id = shop.getShopid();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            Shop shopOrphanCheck = shop.getShop();
            if (shopOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Shop (" + shop + ") cannot be destroyed since the Shop " + shopOrphanCheck + " in its shop field has a non-nullable shop1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Shop shop1 = shop.getShop1();
            if (shop1 != null) {
                shop1.setShop(null);
                shop1 = em.merge(shop1);
            }
            List<Product> productList = shop.getProductList();
            for (Product productListProduct : productList) {
                productListProduct.setShopShopid(null);
                productListProduct = em.merge(productListProduct);
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

    public Shop findShop(Integer id) {
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
