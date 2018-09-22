/*
 * TMAppletDraw.java
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

import java.awt.Color;
import java.awt.Paint;

import net.bouthier.treemapSwing.TMComputeDraw;
import net.bouthier.treemapSwing.TMExceptionBadTMNodeKind;
import net.bouthier.treemapSwing.TMNode;
import net.bouthier.treemapSwing.TMNodeAdapter;


public class TMAppletDraw 
    implements TMComputeDraw {

    /**
     * Test if this TMComputeDraw could be used
     * with the kind of TMNode passed in parameter.
     *
     * @param node    the TMNode to test the compatibility with
     * @return        <CODE>true</CODE> if this kind of node is compatible;
     *                <CODE>false</CODE> otherwise
     */
    public boolean isCompatibleWith(TMNode node) {
        if (node instanceof TMXMLNode) {
            return true;
        } else {
            return false;
        }        
    }
    
    /**
     * Returns the filling of the node.
     *
     * @param nodeAdapter               the node which we will draw
     * @return                          the filling of the node
     * @throws TMExceptionBadTMNodeKind If the kind of TMNode returned is 
     *                                  incompatible with this TMComputeDraw.
     */
    public Paint getFilling(TMNodeAdapter nodeAdapter)
        throws TMExceptionBadTMNodeKind {
        
        TMNode node = nodeAdapter.getNode();
        if (node instanceof TMXMLNode) {
            TMXMLNode fNode = (TMXMLNode) node;
            String type = fNode.getType();

            if (type == null) {
                return Color.lightGray;
            } else if (type.equals("done")) {
                return Color.white;
            } else if (type.equals("work")) {
                return Color.green;
            } else if (type.equals("todo")) {
                return Color.yellow;
            } else {
                return Color.red;
            }
        } else {
            throw new TMExceptionBadTMNodeKind(this, node);
        }
    }
    
    /**
        * Returns the tooltip of the node.
     *
     * @param nodeAdapter               the node for which we want the tooltip
     * @return                          the tooltip of the node
     * @throws TMExceptionBadTMNodeKind If the kind of TMNode returned is 
     *                                  incompatible with this TMComputeDraw.
     */
    public String getTooltip(TMNodeAdapter nodeAdapter)
        throws TMExceptionBadTMNodeKind {
        
        TMNode node = nodeAdapter.getNode();
        if (node instanceof TMXMLNode) {
            TMXMLNode fNode = (TMXMLNode) node;
                
            String name = fNode.getName();
            String type = fNode.getType();
            String fType = "";
            if (type.equals("done")) {
                fType = "Page done";
            } else if (type.equals("work")) {
                fType = "Page in progress";
            } else if (type.equals("todo")) {
                fType = "Page to do";
            }
            String url  = fNode.getURL();
            String fUrl = url.substring(6);
                                
            String tooltip = "<html>" + name + "<p>"
                                      + fType + "<p>"
                                      + fUrl + "</html>";
            return tooltip;
        } else {
            throw new TMExceptionBadTMNodeKind(this, node);
        }            
    }
    
    /**
     * Returns the title of the node.
     *
     * @param nodeAdapter               the node for which we want the title
     * @return                          the title of the node
     * @throws TMExceptionBadTMNodeKind if the kind of TMNode returned is 
     *                                  incompatible with this TMComputeDraw.
     */
    public String getTitle(TMNodeAdapter nodeAdapter)
        throws TMExceptionBadTMNodeKind {
        
        TMNode node = nodeAdapter.getNode();
        if (node instanceof TMXMLNode) {
            TMXMLNode fNode = (TMXMLNode) node;
            return fNode.getName();
        } else {
            throw new TMExceptionBadTMNodeKind(this, node);
        }
    }
    
    /**
     * Returns the color of the title of the node.
     *
     * @param nodeAdapter               the node for which we want the title
     * @return                          the title of the node
     * @throws TMExceptionBadTMNodeKind if the kind of TMNode returned is 
     *                                  incompatible with this TMComputeDraw.
     */
    public Paint getTitleColor(TMNodeAdapter nodeAdapter)
        throws TMExceptionBadTMNodeKind {
        
        return Color.black;
    }

}
