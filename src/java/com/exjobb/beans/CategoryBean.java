package com.exjobb.beans;

import com.exjobb.ejbs.EJBCollection;
import com.exjobb.models.Category;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;


/**
 *
 * @author Filip
 */
@ManagedBean
@ViewScoped
public class CategoryBean {
    @EJB
    private EJBCollection ejb;
    

    public CategoryBean() {
    }
    
    
    public List<Category> getCategories() {
        return ejb.category().getAll();
    }

    public MenuModel getMenu() {
         MenuModel menu = new DefaultMenuModel();
        for(Category category : getCategories()) {
            DefaultMenuItem c = new DefaultMenuItem(category.getName());
            c.setCommand("#{productBean.setCategoryName(\'" + category.getName()+ "\')}");
            c.setUpdate(":#{p:component('itemDataTable')");
            c.setAjax(false);
            menu.addElement(c);
        }
        menu.generateUniqueIds();
        return menu;
    }
}
