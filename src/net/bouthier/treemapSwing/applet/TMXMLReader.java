/*
 * TMXMLReader.java
 * www.bouthier.net
 *
 * The MIT License :
 * -----------------
 * Copyright (c) 2005 Christophe Bouthier
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package net.bouthier.treemapSwing.applet;

import java.net.URL;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



/**
 * TMXMLReader builds a tree of TMXMLNode from reading an XML file.
 */
public class TMXMLReader 
    extends DefaultHandler {

    TMXMLNode root      = null;
    Vector    ancestors = new Vector();

    
  /* --- DefaultHandler --- */
    
    /**
     * Build a new node.
     */
    public void startElement(String uri, 
                             String localName,
                             String qName,
                             Attributes atts)
        throws SAXException {
            
        TMXMLNode node = new TMXMLNode(atts.getValue("name"), 
                                       atts.getValue("type"),
                                       atts.getValue("url"));
            
        if (! ancestors.isEmpty()) {
            ((TMXMLNode) ancestors.lastElement()).addChild(node); 
        } else {
            root = node;
        }
        ancestors.add(node);
    }
    
    /**
     * Close the node.
     */
    public void endElement(String uri,
                           String localName,
                           String qName)
        throws SAXException {
            
        ancestors.remove(ancestors.size() - 1);
    }
    
    
  /* --- Build tree --- */
    
    /**
     * Build the tree from the given URL to a XML file.
     * Returns the root of the built tree.
     */
    public static TMXMLNode buildTree(String xmlUrl) {
        XMLReader    parser  = null;
        TMXMLReader  handler = null;
        
        try {
            handler = new TMXMLReader();
            parser = XMLReaderFactory.createXMLReader("com.bluecast.xml.Piccolo");
            parser.setContentHandler(handler);
            parser.parse(xmlUrl);
        } catch (Exception e) {
            System.out.println("There is a problem : " + e.getMessage());
            e.printStackTrace();
        }
        
        return handler.root;
    }
    
}
