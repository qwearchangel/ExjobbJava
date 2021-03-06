package com.exjobb.xmlreader;

import com.exjobb.constants.Operation;
import com.exjobb.ejbs.EJBCollection;
import com.exjobb.models.Category;
import com.exjobb.models.Item;
import com.exjobb.models.Product;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Filip
 */
public class XMLImporter {

    private EJBCollection ejb;

    {
        try {
            InitialContext ctx = new InitialContext();
            ejb = (EJBCollection) ctx.lookup("java:global/Exjobb_-_Java/EJBCollection");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public void xmlAction(String importPath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(new File(importPath));

            XPath xPath = XPathFactory.newInstance().newXPath();

            String dataType = getDataType(xPath, xml);
            if (dataType.equals("Product")) {
                AddOrDeleteProduct(xPath, xml);
            }
            if (dataType.equals("Item")) {
                AddOrDeleteItem(xPath, xml);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.getStackTrace();
        }
    }

    private String getDataType(XPath xPath, Document xml) {
        String result = "";
        String expression = "/Data/DataType";
        try {
            NodeList nodelist = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            result = nodelist.item(0).getFirstChild().getNodeValue();
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLImporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private String getOperation(XPath xPath, Document xml) {
        String expression = "Data/Operation";
        String result = "";
        try {
            NodeList nodelist = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            result = nodelist.item(0).getFirstChild().getNodeValue();
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void AddOrDeleteProduct(XPath xPath, Document xml) {
        String operation = getOperation(xPath, xml);
        String productNumber = XMLProductReader.getProductNumber(xPath, xml);

        if (operation.equals(Operation.Delete)) {
            ejb.product().remove(ejb.product().getProductByNumber(productNumber));
            return;
        }

        if (operation.equals(Operation.Link)) {
            Product product = new Product();
            product.setName(XMLProductReader.getProductName(xPath, xml));
            product.setDescription(XMLProductReader.getProductDescription(xPath, xml));
            product.setNumber(productNumber);

            String categoryName = XMLProductReader.getProductCategory(xPath, xml);
            Category category = ejb.category().getByName(categoryName);
            if (category == null) {
                ejb.category().add(new Category(categoryName));
                category = ejb.category().getByName(categoryName);
            }
            product.setCategory(category);
            ejb.product().add(product);
            return;
        }

        if (operation.equals(Operation.Update)) {
            Product update = ejb.product().getProductByNumber(productNumber);

            String name = XMLProductReader.getProductName(xPath, xml);
            String description = XMLProductReader.getProductDescription(xPath, xml);

            if (!name.isEmpty()) {
                update.setName(name);
            }
            if (!description.isEmpty()) {
                update.setDescription(description);
            }

            String categoryName = XMLProductReader.getProductCategory(xPath, xml);
            Category category;
            if (!categoryName.isEmpty()) {

                category = ejb.category().getByName(categoryName);
                if (category == null) {
                    ejb.category().add(new Category(categoryName));
                    category = ejb.category().getByName(categoryName);
                }
                update.setCategory(category);
            }
            ejb.product().add(update);
        }
    }

    private void AddOrDeleteItem(XPath xPath, Document xml) {
        String operation = getOperation(xPath, xml);
        String itemNumber = XMLItemReader.getItemNumber(xPath, xml);

        if (operation.equals(Operation.Delete)) {
            ejb.item().remove(ejb.item().getByNumber(itemNumber));
            return;
        }

        if (operation.equals(Operation.Link)) {
            Item item = new Item();
            
            item.setName(XMLItemReader.getItemName(xPath, xml));
            item.setNumber(itemNumber);
            item.setColor(XMLItemReader.getItemColor(xPath, xml));
            item.setSize(XMLItemReader.getItemSize(xPath, xml));
            item.setFeetsize(XMLItemReader.getItemFeetSize(xPath, xml));
            item.setDescription(XMLItemReader.getItemDescription(xPath, xml));

            Product product = ejb.product().getProductByNumber(removeLastTwo(itemNumber));
            if (product == null) {
                Logger.getLogger(XMLImporter.class.getName()).log(Level.SEVERE, null, "There is no product for this item!");
            }
            item.setProduct(product);
            ejb.item().add(item);
            return;
        }

        if (operation.equals(Operation.Update)) {
            Item update = ejb.item().getByNumber(itemNumber);
            
            String name = XMLItemReader.getItemName(xPath, xml);
            String color = XMLItemReader.getItemColor(xPath, xml);
            String size = XMLItemReader.getItemSize(xPath, xml);
            String feetSize = XMLItemReader.getItemFeetSize(xPath, xml);
            String description = XMLItemReader.getItemDescription(xPath, xml);
            
            if (!name.isEmpty()) {
               update.setName(name); 
            }
            if (!color.isEmpty()) {
                update.setColor(color);
            }
            if (!size.isEmpty()) {
                update.setSize(size);
            }
            if (!feetSize.isEmpty()) {
                update.setFeetsize(feetSize);
            }
            if (!description.isEmpty()) {
                update.setDescription(description);
            }
            
            Product product = ejb.product().getProductByNumber(removeLastTwo(itemNumber));
            if (product == null) {
                Logger.getLogger(XMLImporter.class.getName()).log(Level.SEVERE, null, "There is no product for this item!");
            } else {
                update.setProduct(product);
            }

            ejb.item().add(update);
        }
    }

    private String removeLastTwo(String number) {
        String str = number;
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}
