/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "work_space")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WorkSpace.findAll", query = "SELECT w FROM WorkSpace w")
    , @NamedQuery(name = "WorkSpace.findById", query = "SELECT w FROM WorkSpace w WHERE w.id = :id")
    , @NamedQuery(name = "WorkSpace.findByName", query = "SELECT w FROM WorkSpace w WHERE w.name = :name")})
public class WorkSpace implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdWorkSpace")
    private Collection<Work> workCollection;
    @JoinColumn(name = "fk_id_group", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Group fkIdGroup;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdParentWorkSpace")
    private Collection<WorkSpace> workSpaceCollection;
    @JoinColumn(name = "fk_id_parent_work_space", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private WorkSpace fkIdParentWorkSpace;

    @Transient
    private String listName;
    public WorkSpace() {
    }

    public String getListName() {
        return getName()+"|"+getFkIdGroup().getName();
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    
    public WorkSpace(Integer id) {
        this.id = id;
    }

    public WorkSpace(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @XmlTransient
    public Collection<Work> getWorkCollection() {
        return workCollection;
    }

    public void setWorkCollection(Collection<Work> workCollection) {
        this.workCollection = workCollection;
    }

    public Group getFkIdGroup() {
        return fkIdGroup;
    }

    public void setFkIdGroup(Group fkIdGroup) {
        this.fkIdGroup = fkIdGroup;
    }

    @XmlTransient
    public Collection<WorkSpace> getWorkSpaceCollection() {
        return workSpaceCollection;
    }

    public void setWorkSpaceCollection(Collection<WorkSpace> workSpaceCollection) {
        this.workSpaceCollection = workSpaceCollection;
    }

    public WorkSpace getFkIdParentWorkSpace() {
        return fkIdParentWorkSpace;
    }

    public void setFkIdParentWorkSpace(WorkSpace fkIdParentWorkSpace) {
        this.fkIdParentWorkSpace = fkIdParentWorkSpace;
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
        if (!(object instanceof WorkSpace)) {
            return false;
        }
        WorkSpace other = (WorkSpace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return fkIdGroup.getName() +" | "+name;
    }
    
}
