/*
 * TMAppletAction.java
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

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import net.bouthier.treemapSwing.TMView;


/**
* TMAppletAction implements the action listener/doer of the applet.
 */
public class TMAppletAction 
    extends MouseAdapter {
    
    private Applet theApplet = null;
    private TMView theView   = null;
    
    
  /* --- Constructor --- */
    
    public TMAppletAction(Applet applet, TMView view) {
        this.theApplet = applet;
        this.theView = view;
    }
    
    
  /* --- MouseAdapter --- */
    
    /**
     * Called when a user clicked on the treemap.
     * Manage double-click to open the corresponding URL by the applet.
	 *
	 * @param e    the MouseEvent generated when clicking
	 */
	public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() >= 2) {
            
            TMXMLNode node = (TMXMLNode) theView.getNodeUnderTheMouse(e);
            if (node != null) {
                /* get the url to display */
                String nodeUrl = node.getURL();
                String base = theApplet.getDocumentBase().toString();
                int i = base.lastIndexOf("/");
                base = base.substring(0, i + 1);
                nodeUrl = base + nodeUrl;
                
                /* display the url */
                AppletContext context = theApplet.getAppletContext();
                try {
                    context.showDocument(new URL(nodeUrl), "_blank");
                } catch (Exception ex) {
                    System.out.println("Malformed URL : " + ex.getMessage());
                }
            }
        }
    }
    
}
