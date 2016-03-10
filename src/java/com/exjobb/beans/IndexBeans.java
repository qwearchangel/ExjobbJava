package com.exjobb.beans;

import com.exjobb.ejbs.EJBCollection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author Filip
 */
@ManagedBean
@RequestScoped
public class IndexBeans {
    
    @EJB
    private EJBCollection ejb;
    
    
}
