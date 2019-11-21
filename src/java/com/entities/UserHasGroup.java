/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "user_has_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserHasGroup.findAll", query = "SELECT u FROM UserHasGroup u")
    , @NamedQuery(name = "UserHasGroup.findById", query = "SELECT u FROM UserHasGroup u WHERE u.id = :id")
    , @NamedQuery(name = "UserHasGroup.findByUserGroup", query = "SELECT u FROM UserHasGroup u WHERE u.fkIdGroup.id = :groupId AND u.fkIdUser.id = :userId")
    , @NamedQuery(name = "UserHasGroup.findByCreatedAt", query = "SELECT u FROM UserHasGroup u WHERE u.createdAt = :createdAt")
    , @NamedQuery(name = "UserHasGroup.findByUpdatedAt", query = "SELECT u FROM UserHasGroup u WHERE u.updatedAt = :updatedAt")})
public class UserHasGroup implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdUserHasGroup")
    private Collection<Delivery> deliveryCollection;
    @JoinColumn(name = "fk_id_group", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Group fkIdGroup;
    @JoinColumn(name = "fk_id_permission", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Permission fkIdPermission;
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User fkIdUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdUserHasGroup")
    private Collection<Assistance> assistanceCollection;

    public UserHasGroup() {
    }

    public UserHasGroup(Integer id) {
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

    @XmlTransient
    public Collection<Delivery> getDeliveryCollection() {
        return deliveryCollection;
    }

    public void setDeliveryCollection(Collection<Delivery> deliveryCollection) {
        this.deliveryCollection = deliveryCollection;
    }

    public Group getFkIdGroup() {
        return fkIdGroup;
    }

    public void setFkIdGroup(Group fkIdGroup) {
        this.fkIdGroup = fkIdGroup;
    }

    public Permission getFkIdPermission() {
        return fkIdPermission;
    }

    public void setFkIdPermission(Permission fkIdPermission) {
        this.fkIdPermission = fkIdPermission;
    }

    public User getFkIdUser() {
        return fkIdUser;
    }

    public void setFkIdUser(User fkIdUser) {
        this.fkIdUser = fkIdUser;
    }

    @XmlTransient
    public Collection<Assistance> getAssistanceCollection() {
        return assistanceCollection;
    }

    public void setAssistanceCollection(Collection<Assistance> assistanceCollection) {
        this.assistanceCollection = assistanceCollection;
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
        if (!(object instanceof UserHasGroup)) {
            return false;
        }
        UserHasGroup other = (UserHasGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.UserHasGroup[ id=" + id + " ]";
    }
    
}
