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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "`group`")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Group.findAll", query = "SELECT g FROM Group g")
    , @NamedQuery(name = "Group.findById", query = "SELECT g FROM Group g WHERE g.id = :id")
    , @NamedQuery(name = "Group.findByName", query = "SELECT g FROM Group g WHERE g.name = :name")
    , @NamedQuery(name = "Group.findByStartHour", query = "SELECT g FROM Group g WHERE g.startHour = :startHour")
    , @NamedQuery(name = "Group.findByEndHour", query = "SELECT g FROM Group g WHERE g.endHour = :endHour")
    , @NamedQuery(name = "Group.findByActive", query = "SELECT g FROM Group g WHERE g.active = :active")
    , @NamedQuery(name = "Group.findByCreatedAt", query = "SELECT g FROM Group g WHERE g.createdAt = :createdAt")
    , @NamedQuery(name = "Group.findByUpdatedAt", query = "SELECT g FROM Group g WHERE g.updatedAt = :updatedAt")})
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_hour")
    @Temporal(TemporalType.TIME)
    private Date startHour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_hour")
    @Temporal(TemporalType.TIME)
    private Date endHour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdGroup")
    private Collection<UserHasGroup> userHasGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdGroup")
    private Collection<GroupHasDay> groupHasDayCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdGroup")
    private Collection<WorkSpace> workSpaceCollection;

    public Group() {
    }

    public Group(Integer id) {
        this.id = id;
    }

    public Group(Integer id, String name, Date startHour, Date endHour, boolean active) {
        this.id = id;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartHour() {
        return startHour;
    }

    public void setStartHour(Date startHour) {
        this.startHour = startHour;
    }

    public Date getEndHour() {
        return endHour;
    }

    public void setEndHour(Date endHour) {
        this.endHour = endHour;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
    public Collection<UserHasGroup> getUserHasGroupCollection() {
        return userHasGroupCollection;
    }

    public void setUserHasGroupCollection(Collection<UserHasGroup> userHasGroupCollection) {
        this.userHasGroupCollection = userHasGroupCollection;
    }

    @XmlTransient
    public Collection<GroupHasDay> getGroupHasDayCollection() {
        return groupHasDayCollection;
    }

    public void setGroupHasDayCollection(Collection<GroupHasDay> groupHasDayCollection) {
        this.groupHasDayCollection = groupHasDayCollection;
    }

    @XmlTransient
    public Collection<WorkSpace> getWorkSpaceCollection() {
        return workSpaceCollection;
    }

    public void setWorkSpaceCollection(Collection<WorkSpace> workSpaceCollection) {
        this.workSpaceCollection = workSpaceCollection;
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
        if (!(object instanceof Group)) {
            return false;
        }
        Group other = (Group) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
