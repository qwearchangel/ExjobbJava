/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.ejbs;

import com.exjobb.models.Product;
import java.io.Serializable;
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
public class ProductManager implements Serializable {
    @PersistenceContext(name = "ExjobbJavaPU") 
    private EntityManager em;

    public ProductManager() {
    }
    
    public List<Product> getAllProducts() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }
    
    public Product getProductByNumber(int number) {
        List<Product> list = em.createNamedQuery("Product.findByNumber", Product.class).setParameter("number", number).getResultList();
        if (list.isEmpty()) {
            return null;
        }
        else {
            return list.get(0);
        }
    }
    
    public Product getProductById(int id) {
        return em.find(Product.class, id);
    }
    
    @Transactional
    public void add(Product product) {
        em.merge(product);
        em.flush();
    }
    
    public void remove(Product product) {
        em.remove(getProductById(product.getId()));
        em.flush();
    }
}
