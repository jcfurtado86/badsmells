/*
 * TMApplet.java
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


import javax.swing.JApplet;

import net.bouthier.treemapSwing.TMView;
import net.bouthier.treemapSwing.TreeMap;


/**
 * TMApplet implements an applet showing a treemap read from an XML file.
 */
public class TMApplet
    extends JApplet {
            
    public void init() {
        /* get the url of the XML file */
        String xmlUrl = getParameter("urlMap");
        String xmlBase = getDocumentBase().toString();
        int i = xmlBase.lastIndexOf("/");
        xmlBase = xmlBase.substring(0, i + 1);
        
        /* build the tree and the treemap */
        TMXMLNode root = TMXMLReader.buildTree(xmlBase + xmlUrl);
        TreeMap treemap = new TreeMap(root);
        
        /* get the view */
        TMAppletSize aSize = new TMAppletSize();
        TMAppletDraw aDraw = new TMAppletDraw();
        TMView view = treemap.getView(aSize, aDraw);
        
        /* configure the view */
        view.setAlgorithm(TMView.SQUARIFIED);
        view.DrawTitles(true);
        view.getAlgorithm().setCushion(true);
        view.getAlgorithm().setH(0.4);
        view.getAlgorithm().setF(1);
        view.getAlgorithm().setIS(150);
        view.getAlgorithm().setBorderOnCushion(true);
        view.getAlgorithm().setBorderSize(12);
/*
        javax.swing.JFrame f = new javax.swing.JFrame("conf");
        f.getContentPane().add(view.getAlgorithm().getConfiguringView());
        f.pack();
        f.show();
*/        
        /* put it in the applet with an action listener */
        TMAppletAction action = new TMAppletAction(this, view);
        view.addMouseListener(action);
        setContentPane(view); 
    }
        
}
