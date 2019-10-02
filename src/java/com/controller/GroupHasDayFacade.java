/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.GroupHasDay;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author presa
 */
@Stateless
public class GroupHasDayFacade extends AbstractFacade<GroupHasDay> {

    @PersistenceContext(unitName = "ControlGroupPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupHasDayFacade() {
        super(GroupHasDay.class);
    }
    
}
