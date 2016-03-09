/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exjobb.entities.xmlreader;

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

    public static int getProductNumber(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductId";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(result);
    }
    
    public static String getProductName(XPath xPath, Document xml) {
        String expression = "Data/Fields/ProductName";
        String result = "";
        try {
            NodeList list = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);
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
            result = list.item(0).getTextContent();
        } catch (XPathExpressionException | DOMException e) {
        }
        return result;
    }
}
