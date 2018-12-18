/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author piyao
 */
@Embeddable
public class LineitemPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERID")
    private int orderid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PRODUCTID")
    private String productid;

    public LineitemPK() {
    }

    public LineitemPK(int orderid, String productid) {
        this.orderid = orderid;
        this.productid = productid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderid;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineitemPK)) {
            return false;
        }
        LineitemPK other = (LineitemPK) object;
        if (this.orderid != other.orderid) {
            return false;
        }
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.LineitemPK[ orderid=" + orderid + ", productid=" + productid + " ]";
    }
    
}
