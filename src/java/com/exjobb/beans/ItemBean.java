package com.exjobb.beans;

import com.exjobb.ejbs.EJBCollection;
import com.exjobb.models.Item;
import com.exjobb.xmlreader.DirectoryMonitor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Filip
 */
@ManagedBean
@RequestScoped
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
    
    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        if (context.getRenderResponse()) {
            return new DefaultStreamedContent();
        }
        
        ServletContext servletContext = (ServletContext) extContext.getContext();
        String noImageAbsolutePath = servletContext.getRealPath("/resources/image/No_image.jpg");
        File file = new File(noImageAbsolutePath);
        
        String itemNumber = extContext.getRequestParameterMap().get("itemImage");
        
        ByteArrayInputStream stream = null;
        try {
            stream = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if (itemNumber.isEmpty()) {
            return new DefaultStreamedContent(stream, "image/jpeg");
        }
        
        Properties prop = new Properties();
        try {
            prop.load(DirectoryMonitor.class.getResourceAsStream("/config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        String importPath = prop.getProperty("resourcepath");
        ByteArrayInputStream stream2 = null;
        try {
            Path path = Paths.get(importPath + itemNumber + "01.jpg");
            stream2 = new ByteArrayInputStream(Files.readAllBytes(path));
        } catch (FileNotFoundException ex) {
            System.out.println("No resource for: " + itemNumber);
        } catch (IOException ex) {
            System.out.println("No resource for: " + itemNumber);
        }
        if (stream2 == null) {
          return new DefaultStreamedContent(stream, "image/jpg");  
        } else {
            return new DefaultStreamedContent(stream2, "image/jpg");  
        }
        
    }
}
