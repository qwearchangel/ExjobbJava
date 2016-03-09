package com.exjobb.entities.xmlreader;

import com.exjobb.entities.ejbs.EJBCollection;
import com.exjobb.entities.models.Product;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB
    EJBCollection ejb;

    public static void Import(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(new File(filePath));

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

    private static String getDataType(XPath xPath, Document xml) {
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
    
    private static String getOperation(XPath xPath, Document xml) {
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

    private static void AddOrDeleteProduct(XPath xPath, Document xml) {
        Product product = new Product();
        String operation = getOperation(xPath, xml);
        
        product.setName(XMLProductReader.getProductName(xPath, xml));
        System.out.println(product.toString());
    }

    private static void AddOrDeleteItem(XPath xPath, Document xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
