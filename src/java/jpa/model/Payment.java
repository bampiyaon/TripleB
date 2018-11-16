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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piyao
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPaymentid", query = "SELECT p FROM Payment p WHERE p.paymentid = :paymentid")
    , @NamedQuery(name = "Payment.findByMethods", query = "SELECT p FROM Payment p WHERE p.methods = :methods")
    , @NamedQuery(name = "Payment.findByTotalprice", query = "SELECT p FROM Payment p WHERE p.totalprice = :totalprice")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PAYMENTID")
    private Integer paymentid;
    @Size(max = 30)
    @Column(name = "METHODS")
    private String methods;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALPRICE")
    private int totalprice;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @OneToOne(optional = false)
    private Orders orderid;

    public Payment() {
    }

    public Payment(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public Payment(Integer paymentid, int totalprice) {
        this.paymentid = paymentid;
        this.totalprice = totalprice;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public Orders getOrderid() {
        return orderid;
    }

    public void setOrderid(Orders orderid) {
        this.orderid = orderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentid != null ? paymentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentid == null && other.paymentid != null) || (this.paymentid != null && !this.paymentid.equals(other.paymentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Payment[ paymentid=" + paymentid + " ]";
    }
    
}
