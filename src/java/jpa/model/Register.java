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
@Table(name = "REGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Register.findAll", query = "SELECT r FROM Register r")
    , @NamedQuery(name = "Register.findByUsername", query = "SELECT r FROM Register r WHERE r.username = :username")
    , @NamedQuery(name = "Register.findByCusfname", query = "SELECT r FROM Register r WHERE r.cusfname = :cusfname")
    , @NamedQuery(name = "Register.findByCuslname", query = "SELECT r FROM Register r WHERE r.cuslname = :cuslname")
    , @NamedQuery(name = "Register.findByPassword", query = "SELECT r FROM Register r WHERE r.password = :password")})
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 20)
    @Column(name = "CUSFNAME")
    private String cusfname;
    @Size(max = 30)
    @Column(name = "CUSLNAME")
    private String cuslname;
    @Size(max = 20)
    @Column(name = "PASSWORD")
    private String password;

    public Register() {
    }

    public Register(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCusfname() {
        return cusfname;
    }

    public void setCusfname(String cusfname) {
        this.cusfname = cusfname;
    }

    public String getCuslname() {
        return cuslname;
    }

    public void setCuslname(String cuslname) {
        this.cuslname = cuslname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Register)) {
            return false;
        }
        Register other = (Register) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Register[ username=" + username + " ]";
    }
    
}
