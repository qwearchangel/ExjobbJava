package com.exjobb.beans;

import com.exjobb.ejbs.EJBCollection;
import com.exjobb.models.Product;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Filip
 */
@ManagedBean
@ViewScoped
public class ProductBean {

    private String categoryName;
    
    @EJB
    private EJBCollection ejb;
    
    public ProductBean() {
    }
    
    public Collection<Product> getAllProductByCategoryName() {
        return ejb.product().getAllProductUnderCategory(categoryName);
    }

    public void setCategoryName(String categoryName) {
        System.out.println("category set: " + categoryName);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
