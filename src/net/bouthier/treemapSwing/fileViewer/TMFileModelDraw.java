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

    /* --- TMComputeSizeAdapter -- */

    public boolean isCompatibleWithObject(Object node) {
        if (node instanceof File) {
            return true;
        } else {
            return false;
        }
    }

    public Paint getFillingOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            long time = file.lastModified();
            long diff = (new Date()).getTime() - time;
//            if (diff <= 3600000L) { // less than an hour
//                return Color.white;
//            } else if (diff <= 86400000L) { // less than a day
//                return Color.green;
//            } else if (diff <= 604800000L) { // less than a week
//                return Color.yellow;
//            } else if (diff <= 2592000000L) { // less than a month
//                return Color.orange;
//            } else if (diff <= 31536000000L) { // less than a year
//                return Color.red;
//            } else { // more than a year
//                return Color.blue;
//            }
            return Color.red;
        }
        return Color.black;
    }

    public String getTooltipOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            String name = "";
            
            System.out.println(file.getName());
            
            if(java(file.getName())){
                cont++;
                name = Reconhecedor.badsmells.get(cont-1).getDescricao().replaceAll("\\n", "<br>") + "<br>";
            }

            long modTime = file.lastModified();
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
            DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT);
            String date = df.format(new Date(modTime));
            String time = tf.format(new Date(modTime));

            String tooltip = "<html>" + name + "<p>" + date + " : " + time;
            return tooltip;
        }
        return "";
    }

    public String getTitleOfObject(Object node) {
        if (node instanceof File) {
            File file = (File) node;
            if(java(file.getName())){
                return Reconhecedor.badsmells.get(cont-1).getNome();
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
