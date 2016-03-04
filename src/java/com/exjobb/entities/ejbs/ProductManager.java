/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import com.exjobb.entities.models.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Filip
 */
@Stateless
public class ProductManager {
    @PersistenceContext(name = "Exjobb_-_JavaPU") 
    EntityManager em;
    
    public List<Product> getAllProducts() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }
    
    public Product getProductById(int id) {
        return em.find(Product.class, id);
    }
    
    public void add(Product product) {
        em.merge(product);
    }
    
    public void remove(Product product) {
        Product remove = product;
        em.remove(remove);
    }
}
