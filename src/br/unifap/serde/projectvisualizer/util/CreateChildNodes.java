/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifap.serde.projectvisualizer.util;

import br.unifap.serde.projectvisualizer.entities.FileNode;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author furtado
 */
public class CreateChildNodes implements Runnable {

    private final DefaultMutableTreeNode root;

    private final File fileRoot;

    public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    public void run() {
        createChildren(fileRoot, root);
    }

    private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                createChildren(file, childNode);
            }
        }

        for (File file : files) {
            if (file.isFile()) {
                if(java(file.getPath())){
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileNode(file));
                    node.add(childNode);
                }
            }
        }
    }
    
    private boolean java(String file){
        final String regex = "(.*?).java";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(file);

        while (matcher.find()) {
            return true;
        }
        return false;
    }

}
