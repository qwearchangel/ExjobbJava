/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import com.exjobb.entities.models.Item;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Filip
 */
@Stateless
@LocalBean
public class ItemManager {
    @PersistenceContext(name = "ExjobbJavaPU") 
    private EntityManager em;

    public ItemManager() {
    }
    
    public List<Item> getAll() {
        return em.createNamedQuery("Item.findAll", Item.class).getResultList();
    }
    
    @Transactional
    public void add(Item item) {
        em.merge(item);
        em.flush();
    }
    
    public Item getById(int id) {
        return em.find(Item.class, id);
    }
    
    public void remove(Item item) {
        Item remove = item;
        em.remove(remove);
        em.flush();
    }
}
