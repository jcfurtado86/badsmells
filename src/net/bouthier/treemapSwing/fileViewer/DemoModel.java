/*
 * DemoModel.java
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.bouthier.treemapAWT.TMView;
import net.bouthier.treemapAWT.TreeMap;
import reconhecedor.BadSmells;
import reconhecedor.Reconhecedor;

/**
 * The DemoModel class implements a demo for Treemap. It's the same than the
 * Demo class, but it use TMModelNode instead of TMNode to describe the user's
 * tree.
 *
 * @author Christophe Bouthier [bouthier@loria.fr]
 * @version 2.5
 */
public class DemoModel {

    private static int count = 1;    // to have unique view name

    private static TMFileModelNode model = null; // the model of the demo tree
    private static TreeMap treeMap = null; // the treemap builded
    private static String name = null; // name for this demo

    private static JFrame viewFrame = null, janela = null;

    /**
     * Display a demo TreeMap.
     */
    public static JPanel main() throws IOException {
        File file1 = new File(System.getProperty("user.dir") + "\\TESTE");
        file1.mkdir();
        String pathRoot = System.getProperty("user.dir") + "\\TESTE";

//        if (args.length > 0) {
//            pathRoot = args[0];
//        } else {
//            pathRoot = File.separator;
//        }
        File rootFile = new File(pathRoot);
        try {
            System.out.println(
                    "Starting the treemap from " + rootFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (!rootFile.exists()) {
            System.out.println(
                    "Can't start treemap : "
                    + rootFile.getName()
                    + " does not exist.");
            return null;
        }

        model = new TMFileModelNode(rootFile);
        if (model == null) {
            System.err.println(
                    "Error : can't start treemap from "
                    + rootFile.getAbsolutePath());
            return null;
        }

        TMFileModelDraw.cont = 0;

        File file2 = new File(pathRoot + "\\1-Large Class");
        file2.mkdir();
        File file3 = new File(pathRoot + "\\2-Long Method");
        file3.mkdir();
        File file4 = new File(pathRoot + "\\3-Long Parameter List");
        file4.mkdir();
        File file5 = new File(pathRoot + "\\4-Duplicated Code");
        file5.mkdir();

        File dir = new File(pathRoot);
        if (dir.isDirectory()) {
            File[] arqs = dir.listFiles();
            for (File nome : arqs) {
                File[] files = nome.listFiles();
                for (File java : files) {
                    java.delete();
                }
            }
        }

        /*
        //Ordenar ArrayList com os badsmells
        ArrayList<BadSmells> aux1 = new ArrayList<>(),
                aux2 = new ArrayList<>(),
                aux3 = new ArrayList<>(),
                aux4 = new ArrayList<>();

        //Inserir os badsmells nos respectivos arraylists
        for (int i = 0; i < Reconhecedor.badsmells.size(); i++) {

            if (Reconhecedor.badsmells.get(i).getTipo().equals("Large Class")) {
                aux1.add(Reconhecedor.badsmells.get(i));
            }

            if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Method")) {
                aux2.add(Reconhecedor.badsmells.get(i));
            }

            if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Parameter List")) {
                aux3.add(Reconhecedor.badsmells.get(i));
            }

            if (Reconhecedor.badsmells.get(i).getTipo().equals("Duplicated Code")) {
                aux4.add(Reconhecedor.badsmells.get(i));
            }

        }

        //Reiniciar badsmells e dps inserir na sequência
        Reconhecedor.badsmells = new ArrayList<>();

        for (int i = 0; i < aux1.size(); i++) {
            Reconhecedor.badsmells.add(aux1.get(i));
        }

        for (int i = 0; i < aux2.size(); i++) {
            Reconhecedor.badsmells.add(aux2.get(i));
        }

        for (int i = 0; i < aux3.size(); i++) {
            Reconhecedor.badsmells.add(aux3.get(i));
        }

        for (int i = 0; i < aux4.size(); i++) {
            Reconhecedor.badsmells.add(aux4.get(i));
        }*/
        
        
        
        
        if (Reconhecedor.badsmells.size() == 0) {
            System.out.println("Parabéns, nenhum BadSmell foi identificado.");
            Reconhecedor.badsmells.add(new BadSmells("Sucesso!", "", "Especial", 0));
        }
        
        
        

        Random random = new Random();
        for (int i = 0; i < (Reconhecedor.badsmells.size() - 1); i++) {

            //sorteia um índice
            int j = random.nextInt(Reconhecedor.badsmells.size());

            //troca o conteúdo dos índices i e j do vetor
            BadSmells temp = Reconhecedor.badsmells.get(i);

            //Reconhecedor.badsmells.get(i) = Reconhecedor.badsmells.get(j);
            Reconhecedor.badsmells.set(i, Reconhecedor.badsmells.get(j));

            //Reconhecedor.badsmells.get(j) = temp;
            Reconhecedor.badsmells.set(j, temp);
        }

        for (int i = 0; i < Reconhecedor.badsmells.size(); i++) {
            random = new Random();
            //sorteia um índice
            int j = random.nextInt(3);

            if (j == 0) {
                File file = new File(pathRoot + "\\1-Large Class\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else if (j == 1) {
                File file = new File(pathRoot + "\\2-Long Method\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else if (j == 2) {
                File file = new File(pathRoot + "\\3-Long Parameter List\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else {
                File file = new File(pathRoot + "\\4-Duplicated Code\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            }

            /*if (Reconhecedor.badsmells.get(i).getTipo().equals("Large Class")) {
                File file = new File(pathRoot + "\\1-Large Class\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Method")) {
                File file = new File(pathRoot + "\\2-Long Method\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Long Parameter List")) {
                File file = new File(pathRoot + "\\3-Long Parameter List\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            } else if (Reconhecedor.badsmells.get(i).getTipo().equals("Duplicated Code")) {
                File file = new File(pathRoot + "\\4-Duplicated Code\\" + Reconhecedor.badsmells.get(i).getTipo() + i + ".java");
                file.createNewFile();
            }*/
        }

        treeMap = new TreeMap(model);
        name = rootFile.getAbsolutePath();

        TMFileModelSize fSize = new TMFileModelSize();
        TMFileModelDraw fDraw = new TMFileModelDraw();
        TMView view = treeMap.getView(fSize, fDraw);

        JPanel panel = (JPanel) view;
        //panel.setPreferredSize(new Dimension(1600, maior(aux1.size(),aux2.size(),aux3.size(), aux4.size())*450));
        //panel.setPreferredSize(new Dimension(maior(aux1.size(), aux2.size(), aux3.size(), aux4.size()) * 815, 1900));

        dir = new File(pathRoot);
        if (dir.isDirectory()) {
            File[] arqs = dir.listFiles();
            for (File nome : arqs) {
                File[] files = nome.listFiles();
                for (File java : files) {
                    java.delete();
                }
            }
        }

        return panel;
    }

    public static int maior(int array1, int array2, int array3, int array4) {
        Integer[] valores = {array1, array2, array3, array4};
        int maior = valores[0];
        for (int i = 1; i < valores.length; i++) {
            if (valores[i] > maior) {
                maior = valores[i];
            }
        }

        return maior;
    }

    public static void fechar() {
        try {
            janela.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
