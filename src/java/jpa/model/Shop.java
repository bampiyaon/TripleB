/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author piyao
 */
@Entity
@Table(name = "SHOP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shop.findAll", query = "SELECT s FROM Shop s")
    , @NamedQuery(name = "Shop.findByShopid", query = "SELECT s FROM Shop s WHERE s.shopid = :shopid")
    , @NamedQuery(name = "Shop.findByShopname", query = "SELECT s FROM Shop s WHERE s.shopname = :shopname")})
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SHOPID")
    private Integer shopid;
    @Size(max = 20)
    @Column(name = "SHOPNAME")
    private String shopname;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "shop1")
    private Shop shop;
    @JoinColumn(name = "SHOPID", referencedColumnName = "SHOPID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Shop shop1;
    @OneToMany(mappedBy = "shopShopid")
    private List<Product> productList;

    public Shop() {
    }

    public Shop(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop1() {
        return shop1;
    }

    public void setShop1(Shop shop1) {
        this.shop1 = shop1;
    }

    @XmlTransient
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shopid != null ? shopid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shop)) {
            return false;
        }
        Shop other = (Shop) object;
        if ((this.shopid == null && other.shopid != null) || (this.shopid != null && !this.shopid.equals(other.shopid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Shop[ shopid=" + shopid + " ]";
    }
    
}
