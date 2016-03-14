/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.ejbs;

import com.exjobb.models.Item;
import com.exjobb.models.Product;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
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
public class ItemManager implements Serializable {

    @PersistenceContext(name = "ExjobbJavaPU")
    private EntityManager em;
    
    @EJB
    ProductManager pm;

    public ItemManager() {
    }

    public List<Item> getAll() {
        return em.createNamedQuery("Item.findAll", Item.class).getResultList();
    }

    @Transactional
    public void add(Item item) {
        em.merge(item);
        em.flush();
        em.clear();
        
    }

    public Item getById(int id) {
        return em.find(Item.class, id);
    }

    public Item getByNumber(int number) {
        return em.createNamedQuery("Item.findByNumber", Item.class).setParameter("number", number).getSingleResult();
    }

    @Transactional()
    public void remove(Item item) {
        em.remove(getById(item.getId()));
        em.flush();
        em.clear();
        
    }
    
    public List<Item> getAllItemsUnderProduct(String brandNumber) {
        if (brandNumber.isEmpty()) {
            return null;
        }
        Product product = pm.getProductByNumber(Integer.parseInt(brandNumber));
        if (product == null) {
            return null;
        }
        return (product.getItemCollection());
    }
}
