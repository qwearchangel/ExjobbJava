package com.exjobb.beans;

import com.exjobb.ejbs.EJBCollection;
import com.exjobb.models.Item;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Filip
 */
@ManagedBean
@ViewScoped
public class ItemBean {
    
    @EJB
    EJBCollection ejb;
    
    private String brandNumber = "";

    public ItemBean() {
    }
    
     public List<Item> getAllItemsByBrand() {
        return ejb.item().getAllItemsUnderProduct(brandNumber);
     }

    public String getBrandNumber() {
        return brandNumber;
    }

    public void setBrandNumber(String brandNumber) {
        System.out.println("brand set: " + brandNumber);
        this.brandNumber = brandNumber;
    }
}
