package com.exjobb.entities.models;

import com.exjobb.entities.models.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-04T15:46:37")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, String> feetsize;
    public static volatile SingularAttribute<Item, String> color;
    public static volatile SingularAttribute<Item, String> size;
    public static volatile SingularAttribute<Item, String> imagepath;
    public static volatile SingularAttribute<Item, Product> productid;
    public static volatile SingularAttribute<Item, Integer> id;

}