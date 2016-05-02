package com.exjobb.ejbs;

import com.exjobb.models.Category;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Filip
 */
@Stateless
public class CategoryManager implements Serializable {
    @PersistenceContext(name = "ExjobbJavaPU") 
    private EntityManager em;

    public CategoryManager() {
    }
    
    public List<Category> getAll() {
        return em.createNamedQuery("Category.findAll", Category.class).getResultList();
    }
    
    public Category getByName(String name) {
        List<Category> list = em.createNamedQuery("Category.findByName", Category.class).setParameter("name", name).getResultList();
        if (list.isEmpty()) {
            return null;
        }
        else {
            return list.get(0);
        }
    }
    
    public Category getById(int id) {
        return em.find(Category.class, id);
    }
    
    @Transactional()
    public void add(Category category) {
        em.merge(category);
        em.flush();
        em.clear();
        
    }
    
    @Transactional()
    public void remove(Category category) {
        em.remove(getById(category.getId()));
        em.flush();
        em.clear();
        
    }
}


