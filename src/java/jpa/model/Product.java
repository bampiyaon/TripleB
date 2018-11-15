/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piyao
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductid", query = "SELECT p FROM Product p WHERE p.productid = :productid")
    , @NamedQuery(name = "Product.findByProductname", query = "SELECT p FROM Product p WHERE p.productname = :productname")
    , @NamedQuery(name = "Product.findByProductprince", query = "SELECT p FROM Product p WHERE p.productprince = :productprince")
    , @NamedQuery(name = "Product.findByProductdetail", query = "SELECT p FROM Product p WHERE p.productdetail = :productdetail")
    , @NamedQuery(name = "Product.findByShopShopid", query = "SELECT p FROM Product p WHERE p.shopShopid = :shopShopid")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PRODUCTID")
    private String productid;
    @Size(max = 50)
    @Column(name = "PRODUCTNAME")
    private String productname;
    @Column(name = "PRODUCTPRINCE")
    private Integer productprince;
    @Size(max = 100)
    @Column(name = "PRODUCTDETAIL")
    private String productdetail;
    @Column(name = "SHOP_SHOPID")
    private Integer shopShopid;

    public Product() {
    }

    public Product(String productid) {
        this.productid = productid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getProductprince() {
        return productprince;
    }

    public void setProductprince(Integer productprince) {
        this.productprince = productprince;
    }

    public String getProductdetail() {
        return productdetail;
    }

    public void setProductdetail(String productdetail) {
        this.productdetail = productdetail;
    }

    public Integer getShopShopid() {
        return shopShopid;
    }

    public void setShopShopid(Integer shopShopid) {
        this.shopShopid = shopShopid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Product[ productid=" + productid + " ]";
    }
    
}
