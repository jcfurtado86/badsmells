/*
 * TMAction.java
 * www.bouthier.net
 *
 * The MIT License :
 * -----------------
 * Copyright (c) 2003 Christophe Bouthier
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
package net.bouthier.treemapAWT;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The TMAction class manages the action on the treemap.
 *
 * @author Christophe Bouthier [bouthier@loria.fr]
 * @version 2.5.2
 */
public class TMAction
        extends MouseAdapter {

    private TMView view = null; // the view managed
    private static boolean ultimo = false;


    /* --- Constructor --- */
    /**
     * Constructor.
     *
     * @param view the view managed
     */
    public TMAction(TMView view) {
        this.view = view;
    }


    /* --- MouseAdapter --- */
    /**
     * Called when a user clicked on the treemap. Used to zoom or unzoom.
     *
     * @param e the MouseEvent generated when clicking
     */
    public void mouseClicked(MouseEvent e) {
        if (e.isShiftDown()) {
            view.unzoom();
        } else {
            boolean finalNode = view.zoom(e.getX(), e.getY());

            boolean ultimoNo = false;
            if (TMAction.ultimo) {
                while (true) {
                    if (!ultimoNo) {
                        try {
                            view.unzoom();
                        } catch (Error e2) {
                            System.out.println(e2.getMessage());
                            TMAction.ultimo = false;
                            break;
                        };
                    } else {
                        TMAction.ultimo = false;
                        break;
                    }
                }
            } else {
                while (true) {
                    if (!finalNode) {
                        //JOptionPane.showMessageDialog(null,view.getToolTipText(e));
                        finalNode = view.zoom(e.getX(), e.getY());
                    } else {
                        TMAction.ultimo = true;
                        break;
                    }
                }
            }
        }
    }

}
