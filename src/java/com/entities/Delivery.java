/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author presa
 */
@Entity
@Table(name = "delivery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d")
    , @NamedQuery(name = "Delivery.findById", query = "SELECT d FROM Delivery d WHERE d.id = :id")
    , @NamedQuery(name = "Delivery.findByDescription", query = "SELECT d FROM Delivery d WHERE d.description = :description")
    , @NamedQuery(name = "Delivery.findByValue", query = "SELECT d FROM Delivery d WHERE d.value = :value")
    , @NamedQuery(name = "Delivery.findByCreatedAt", query = "SELECT d FROM Delivery d WHERE d.createdAt = :createdAt")})
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value")
    private BigDecimal value;
    @Lob
    @Size(max = 65535)
    @Column(name = "file_url")
    private String fileUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @JoinColumn(name = "fk_id_user_has_group", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserHasGroup fkIdUserHasGroup;
    @JoinColumn(name = "fk_id_work", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Work fkIdWork;

    public Delivery() {
    }

    public Delivery(Integer id) {
        this.id = id;
    }

    public Delivery(Integer id, String description, Date createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserHasGroup getFkIdUserHasGroup() {
        return fkIdUserHasGroup;
    }

    public void setFkIdUserHasGroup(UserHasGroup fkIdUserHasGroup) {
        this.fkIdUserHasGroup = fkIdUserHasGroup;
    }

    public Work getFkIdWork() {
        return fkIdWork;
    }

    public void setFkIdWork(Work fkIdWork) {
        this.fkIdWork = fkIdWork;
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
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Delivery[ id=" + id + " ]";
    }
    
}
