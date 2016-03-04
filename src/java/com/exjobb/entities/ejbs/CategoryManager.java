/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import com.exjobb.entities.models.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Filip
 */
@Stateless
public class CategoryManager {
    @PersistenceContext(name = "Exjobb_-_JavaPU") 
    EntityManager em;
    
    public List<Category> getAll() {
        return em.createNamedQuery("Category.findAll", Category.class).getResultList();
    }
    
    public void add(Category category) {
        em.merge(category);
    }
    
    public Category getById(int id) {
        return em.find(Category.class, id);
    }
    
    public void remove(Category category) {
        Category remove = category;
        em.remove(remove);
    }
}


