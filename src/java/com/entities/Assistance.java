/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "assistance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assistance.findAll", query = "SELECT a FROM Assistance a")
    , @NamedQuery(name = "Assistance.findById", query = "SELECT a FROM Assistance a WHERE a.id = :id")
    , @NamedQuery(name = "Assistance.findByCreatedAt", query = "SELECT a FROM Assistance a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "Assistance.findByUpdatedAt", query = "SELECT a FROM Assistance a WHERE a.updatedAt = :updatedAt")})
public class Assistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "fk_id_user_has_group", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserHasGroup fkIdUserHasGroup;

    public Assistance() {
    }

    public Assistance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserHasGroup getFkIdUserHasGroup() {
        return fkIdUserHasGroup;
    }

    public void setFkIdUserHasGroup(UserHasGroup fkIdUserHasGroup) {
        this.fkIdUserHasGroup = fkIdUserHasGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assistance)) {
            return false;
        }
        Assistance other = (Assistance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Assistance[ id=" + id + " ]";
    }
    
}
