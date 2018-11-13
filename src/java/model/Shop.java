/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
        return "model.Shop[ shopid=" + shopid + " ]";
    }
    
}
