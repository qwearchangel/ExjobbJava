package com.exjobb.ejbs;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Filip
 */
@Stateless
public class EJBCollection implements Serializable{
    
    @EJB
    private CategoryManager cm;
    @EJB
    private ProductManager pm;
    @EJB
    private ItemManager im;

    public CategoryManager category() {
        return cm;
    }
    
    public ProductManager product() {
        return pm;
    }
    
    public ItemManager item() {
        return im;
    }
}
