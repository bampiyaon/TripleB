/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    , @NamedQuery(name = "Lineitem.findByQuantity", query = "SELECT l FROM Lineitem l WHERE l.quantity = :quantity")
    , @NamedQuery(name = "Lineitem.findByUnitprice", query = "SELECT l FROM Lineitem l WHERE l.unitprice = :unitprice")
    , @NamedQuery(name = "Lineitem.findByOrderid", query = "SELECT l FROM Lineitem l WHERE l.lineitemPK.orderid = :orderid")
    , @NamedQuery(name = "Lineitem.findByProductid", query = "SELECT l FROM Lineitem l WHERE l.lineitemPK.productid = :productid")})
public class Lineitem implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LineitemPK lineitemPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UNITPRICE")
    private double unitprice;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Orders orders;
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public Lineitem() {
    }

    public Lineitem(Product p) {
        this.product = p;
        this.quantity = 1;
        this.unitprice = p.getPrice();
    }

    public Lineitem(Orders orders, Product product, int quantity, double unitprice) {
        this.orders = orders;
        this.product = product;
        this.quantity = quantity;
        this.unitprice = unitprice;
    }

    public Lineitem(int orderid, String productid) {
        this.lineitemPK = new LineitemPK(orderid, productid);
    }

    public LineitemPK getLineitemPK() {
        return lineitemPK;
    }

    public void setLineitemPK(LineitemPK lineitemPK) {
        this.lineitemPK = lineitemPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

        public double getTotalLinePrice() {
        double total = 0;
        total = this.quantity * this.unitprice;
        return total;
    }
        
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineitemPK != null ? lineitemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineitem)) {
            return false;
        }
        Lineitem other = (Lineitem) object;
        if ((this.lineitemPK == null && other.lineitemPK != null) || (this.lineitemPK != null && !this.lineitemPK.equals(other.lineitemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Lineitem[ lineitemPK=" + lineitemPK + " ]";
    }
    
}
