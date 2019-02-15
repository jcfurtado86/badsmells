/*
 * TMFileModelDraw.java
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

package net.bouthier.treemapSwing.fileViewer;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.bouthier.treemapAWT.TMComputeDrawAdapter;
import reconhecedor.BadSmells;
import reconhecedor.Reconhecedor;


/**
 * The TMFileModelDraw class implements an example of a TMComputeDrawAdapter
 * for a TMFileModelNode.
 * It use the date of last modification as color,
 * and the name of the file as tooltip.
 * <P>
 * The color legend is :
 * <UL>
 *   <IL> white  for files less than a hour old
 *   <IL> green  for files less than a day old
 *   <IL> yellow for files less than a week old
 *   <IL> orange for files less than a month old
 *   <IL> red    for files less than a year old
 *   <IL> blue   for files more than a year old
 * </UL>
 *
 * @author Christophe Bouthier [bouthier@loria.fr]
 * @version 2.5
 */
public class TMFileModelDraw 
	extends TMComputeDrawAdapter {
    
    public static int cont = 0;
    public static Color cor = Color.darkGray, cor1, cor2, cor3, cor4;

    /* --- TMComputeSizeAdapter -- */

    public TMFileModelDraw() {
        
        //cor1 = new Color((int)(Math.random()*218), (int)(Math.random()*218), (int)(Math.random()*218));
        //cor2 = new Color((int)(Math.random()*218), (int)(Math.random()*218), (int)(Math.random()*218));
        //cor3 = new Color((int)(Math.random()*218), (int)(Math.random()*218), (int)(Math.random()*218));
        //cor4 = new Color((int)(Math.random()*218), (int)(Math.random()*218), (int)(Math.random()*218));
        
        cor1 = new Color(190, 15, 66);
        cor2 = new Color(154, 146, 157);
        cor3 = new Color(7, 61, 57);
        cor4 = new Color(70, 77, 151);
        
    }

    
    
    public boolean isCompatibleWithObject(Object node) {
        if (node instanceof File) {
            return true;
        } else {
            return false;
        }
    }

    public Paint getFillingOfObject(Object node) {
        if (node instanceof File) {
            String operacao = ((File) node).getName();
            
            if(operacao.equals("1-Large Class"))
                cor = cor1;
            if(operacao.equals("2-Long Method"))
                cor = cor2;
            if(operacao.equals("3-Long Parameter List"))
                cor = cor3;
            if(operacao.equals("4-Duplicated Code"))
                cor = cor4;
        }
        return cor;
    }

    public String getTooltipOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            String name = "";
            
            if(java(file.getName())){
                cont++;
                name = Reconhecedor.badsmells.get(cont-1).getDescricao();
                
//                BadSmells badsmell = Reconhecedor.badsmells.get(cont-1);
//                name = "Badsmell: "+badsmell.getTipo()+" ("+badsmell.getNome()+")";
            }

            long modTime = file.lastModified();
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
            String date = df.format(new Date(modTime));
            String time = tf.format(new Date(modTime));

            String tooltip = name;
            return tooltip;
        }
        return "";
    }
    
    public String getTitleOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            if(java(file.getName())){
                String retorno = (Reconhecedor.badsmells.get(cont-1).getTipo()+ ": " +Reconhecedor.badsmells.get(cont-1).getNome());
                
                return retorno;
            }   
        }
        
        return "";
    }

    public Paint getColorTitleOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            return Color.WHITE;
        }
        return Color.WHITE;
    }
    
    private boolean java(String file){
        final String regex = "(.*?).java|(.*?).txt";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(file);

        while (matcher.find()) {
            return true;
        }
        return false;
    }

}
