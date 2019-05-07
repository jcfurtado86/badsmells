/*
 * TMAlgorithmClassic.java
 * www.bouthier.net
 *
 * The MIT License :
 * -----------------
 * Copyright (c) 2001 Christophe Bouthier
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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Enumeration;
import reconhecedor.Reconhecedor;

/**
 * The TMAlgorithmClassic class implements a classic treemap drawing algorithm.
 *
 * @author Christophe Bouthier [bouthier@loria.fr]
 * @version 2.5
 */
public class TMAlgorithmClassic
        extends TMAlgorithm {


    /* --- Drawing --- */
    /**
     * Draws the children of a node, by setting their drawing area first,
     * dependant of the algorithm used.
     *
     * @param g the graphic context
     * @param node the node whose children should be drawn
     * @param axis the axis of separation
     * @param level the level of deep
     */
    protected void drawChildren(Graphics2D g,
            TMNodeModelComposite node,
            short axis,
            int level) {
        TMNodeModel child = null;
        float size = node.getSize();
        float proportion = 0.0f;
        float remaining = 0.0f;
        float newDf = 0.0f;
        int newDi = 0;
        Rectangle pArea = node.getArea();
        Rectangle childArea = null;

        if (size == 0.0f) {
            return;
        }

        int x = pArea.x;
        int y = pArea.y;
        int w = pArea.width;
        int h = pArea.height;

        if ((w > borderLimit) && (h > borderLimit)) {
            x += borderSize;
            y += borderSize;
            w -= borderSize * 2;
            h -= borderSize * 2;
        }

        int maxX = x + w - 1;
        int maxY = y + h - 1;

        for (Enumeration e = node.children(); e.hasMoreElements();) {
            child = (TMNodeModel) e.nextElement();
            childArea = child.getArea();
            childArea.x = x;
            childArea.y = y;
            proportion = (child.getSize()) / size;

            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension d = tk.getScreenSize();
            //System.out.println("Screen width = " + d.width + " - Screen height = " + d.height);
            //System.out.println("Screen height = " + d.height);

            int tamanhoLargeClass = 0, tamanhoLongMethod = 0,
                    tamanhoLongParameter = 0, tamanhoDuplicated = 0;

            for (int i = 0; i < Reconhecedor.badsmells.size(); i++) {
                if (Reconhecedor.badsmells.get(i).getTipo().equals("Large Class")) {
                    tamanhoLargeClass++;
                } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Method")) {
                    tamanhoLongMethod++;
                } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Parameter List")) {
                    tamanhoLongParameter++;
                } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Duplicated Code")) {
                    tamanhoDuplicated++;
                }
            }

            float horizontal = w * proportion;
            float vertical = h * proportion;
            int resto = 0;

            /*String tipo = child.getTitle().split(":")[0];
            int quantidade = Integer.parseInt(child.getTooltip().split("£")[1]);

            for (int i = 0; i < Reconhecedor.badsmells.size(); i++) {
                if (tipo.equals("Large Class")) {
                    horizontal = (d.width / tamanhoLargeClass) + (quantidade);
                } else if (tipo.equals("Long Method")) {
                    horizontal = (d.width / tamanhoLongMethod) + (quantidade);
                } else if (tipo.equals("Long Parameter List")) {
                    horizontal = (d.width / tamanhoLongParameter) + (quantidade * 10);
                } else if (tipo.equals("Duplicated Code")) {
                    horizontal = (d.width / tamanhoDuplicated) + (quantidade);
                }
            }*/

            if (e.hasMoreElements()) {
                if (axis == HORIZONTAL) {
                    newDf = horizontal; //horizontal;//400;//815;//Tamanho proporcional altura
                    newDi = Math.round(newDf);
                    remaining += newDf - newDi;
                    if (remaining >= 1) {
                        newDi += 1;
                        remaining -= 1;
                    } else if (remaining <= -1) {
                        newDi -= 1;
                        remaining += 1;
                    }
                    childArea.width = newDi;
                    childArea.height = h;
                    x += newDi;
                } else { // VERTICAL
                    newDf = vertical; //vertical;//450;//proportion * h;//Tamanho proporcional Largura
                    newDi = Math.round(newDf);
                    remaining += newDf - newDi;
                    if (remaining >= 1) {
                        newDi += 1;
                        remaining -= 1;
                    } else if (remaining <= -1) {
                        newDi -= 1;
                        remaining += 1;
                    }
                    childArea.width = w;
                    childArea.height = newDi;
                    y += newDi;
                }
            } else { // last element fills
                if (axis == HORIZONTAL) {
                    childArea.width = (maxX - x) + 1;
                    childArea.height = h;
                } else {
                    childArea.width = w;
                    childArea.height = (maxY - y) + 1;
                }
            }
            drawNodes(g, child, switchAxis(axis), (level + 1));
        }
    }

}
