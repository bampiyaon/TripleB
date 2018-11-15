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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piyao
 */
@Entity
@Table(name = "LINEITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lineitem.findAll", query = "SELECT l FROM Lineitem l")
    , @NamedQuery(name = "Lineitem.findByLineitemid", query = "SELECT l FROM Lineitem l WHERE l.lineitemid = :lineitemid")
    , @NamedQuery(name = "Lineitem.findByPrice", query = "SELECT l FROM Lineitem l WHERE l.price = :price")
    , @NamedQuery(name = "Lineitem.findByQuantity", query = "SELECT l FROM Lineitem l WHERE l.quantity = :quantity")
    , @NamedQuery(name = "Lineitem.findByProductProductid", query = "SELECT l FROM Lineitem l WHERE l.productProductid = :productProductid")})
public class Lineitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LINEITEMID")
    private Integer lineitemid;
    @Column(name = "PRICE")
    private Integer price;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "PRODUCT_PRODUCTID")
    private Integer productProductid;
    @JoinColumn(name = "CART_CARTID", referencedColumnName = "CARTID")
    @ManyToOne
    private Cart cartCartid;

    public Lineitem() {
    }

    public Lineitem(Integer lineitemid) {
        this.lineitemid = lineitemid;
    }

    public Integer getLineitemid() {
        return lineitemid;
    }

    public void setLineitemid(Integer lineitemid) {
        this.lineitemid = lineitemid;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductProductid() {
        return productProductid;
    }

    public void setProductProductid(Integer productProductid) {
        this.productProductid = productProductid;
    }

    public Cart getCartCartid() {
        return cartCartid;
    }

    public void setCartCartid(Cart cartCartid) {
        this.cartCartid = cartCartid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineitemid != null ? lineitemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineitem)) {
            return false;
        }
        Lineitem other = (Lineitem) object;
        if ((this.lineitemid == null && other.lineitemid != null) || (this.lineitemid != null && !this.lineitemid.equals(other.lineitemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Lineitem[ lineitemid=" + lineitemid + " ]";
    }
    
}
