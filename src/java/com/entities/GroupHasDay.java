/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "group_has_day")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupHasDay.findAll", query = "SELECT g FROM GroupHasDay g"),
    @NamedQuery(name = "GroupHasDay.findById", query = "SELECT g FROM GroupHasDay g WHERE g.id = :id")})
    

public class GroupHasDay implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "fk_id_day", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Day fkIdDay;
    @JoinColumn(name = "fk_id_group", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Group fkIdGroup;
    

    public GroupHasDay() {
    }

    public GroupHasDay(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Day getFkIdDay() {
        return fkIdDay;
    }

    public void setFkIdDay(Day fkIdDay) {
        this.fkIdDay = fkIdDay;
    }

    public Group getFkIdGroup() {
        return fkIdGroup;
    }

    public void setFkIdGroup(Group fkIdGroup) {
        this.fkIdGroup = fkIdGroup;
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
        if (!(object instanceof GroupHasDay)) {
            return false;
        }
        GroupHasDay other = (GroupHasDay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.GroupHasDay[ id=" + id + " ]";
    }
    
    
    
}
