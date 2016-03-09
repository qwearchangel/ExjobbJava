/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.ejbs;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

/**
 *
 * @author Filip
 */
@Stateless(mappedName = "EJBCo")
@LocalBean
public class EJBCollection {
    
    @EJB
    private CategoryManager cm;
    @EJB
    private ProductManager pm;
    @EJB
    private ItemManager im;
    @EJB
    private FileSystemMonitor fsm;

    public EJBCollection() {
    }
    
    public CategoryManager category() {
        return cm;
    }
    
    public ProductManager product() {
        return pm;
    }
    
    public ItemManager item() {
        return im;
    }
    
    public FileSystemMonitor fileSystemMonitor() {
        return fsm;
    }
}
