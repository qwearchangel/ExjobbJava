package com.exjobb.ejbs;

import com.exjobb.models.Category;
import com.exjobb.models.Product;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
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
    
    @EJB
    CategoryManager cm;

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
        em.clear();
        
    }
    
    @Transactional()
    public void remove(Product product) {
        em.remove(getProductById(product.getId()));
        em.flush();
        em.clear();
        
    }
    
    public List<Product> getAllProductUnderCategory(String categortName) {
        Category category = cm.getByName(categortName);
        if (category == null) {
            return null;
        }
        return category.getProductCollection();
    }
}
