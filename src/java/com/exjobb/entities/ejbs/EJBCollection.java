/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Filip
 */
@Stateless
public class EJBCollection {
    
    @EJB
    CategoryManager cm;
    @EJB
    ProductManager pm;
    @EJB
    ItemManager im;
    
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
