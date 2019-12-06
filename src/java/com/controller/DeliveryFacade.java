/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.Delivery;
import com.entities.Group;
import com.entities.User;
import com.entities.UserHasGroup;
import com.entities.Work;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

/**
 *
 * @author presa
 */
@Stateless
public class DeliveryFacade extends AbstractFacade<Delivery> {

    @PersistenceContext(unitName = "ControlGroupPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeliveryFacade() {
        super(Delivery.class);
    }

    public List<UserHasGroup> findByGroupUser() {
        User user = (User) User.getSession().getAttribute("USER");
        Query q = this.getEntityManager().createNamedQuery("UserHasGroup.findByUserGroup");
        q.setParameter("groupId", Integer.parseInt((String) User.getSession().getAttribute("GROUP_ID")));
        q.setParameter("userId", user.getId());
        return q.getResultList();
    }

    public List<Work> findById(String Id) {
        Query q = this.getEntityManager().createNamedQuery("Work.findById");
        q.setParameter("id", Integer.valueOf(Id));
        return q.getResultList();
    }

    public List<Delivery> listByUser(int[] range) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);

        Query q = this.getEntityManager().createNamedQuery("Delivery.findByUser");
        q.setParameter("userId", ((User) session.getAttribute("USER")).getId());
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

}
