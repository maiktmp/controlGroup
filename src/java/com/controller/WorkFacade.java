/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.User;
import com.entities.Work;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author presa
 */
@Stateless
public class WorkFacade extends AbstractFacade<Work> {

    @PersistenceContext(unitName = "ControlGroupPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkFacade() {
        super(Work.class);
    }
    
    
    public List<Work> findByGroup(String groupId, int[] range) {
        Query q = this.getEntityManager().createNamedQuery("Work.findAllByGroup");
        q.setParameter("groupId", Integer.parseInt(groupId));
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
}
