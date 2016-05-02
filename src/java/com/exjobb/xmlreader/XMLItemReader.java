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
public class XMLItemReader {
    
    public static String getItemNumber(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemId";
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
    
    public static String getItemName(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemName";
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
    
    public static String getItemColor(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemColor";
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
    
    public static String getItemDescription(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemDescritpion";
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
    
    public static String getItemSize(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemSize";
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
    public static String getItemFeetSize(XPath xPath, Document xml) {
        String expression = "Data/Fields/ItemFeetSize";
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
