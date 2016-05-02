package com.exjobb.xmlreader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Filip
 */
public class XMLProductReader {

    public static String getProductNumber(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductId";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            if (list.getLength() == 0) {
                return null;
            }
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getProductName(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductName";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            if (list.getLength() == 0) {
                return result;
            }
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getProductCategory(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductCategory";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            if (list.getLength() == 0) {
                return result;
            }
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getProductDescription(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductDescription";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            if (list.getLength() == 0) {
                return result;
            }
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
        }
        return result;
    }
}
