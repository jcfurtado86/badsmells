/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifap.serde.projectvisualizer.util;

import br.unifap.serde.projectvisualizer.entities.FileNode;
import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author furtado
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

            if (node.getUserObject() instanceof FileNode) {
                FileNode file = (FileNode) node.getUserObject();
                if (file.getFile().isDirectory() && node.isLeaf()) {
                    setIcon(UIManager.getIcon("Tree.closedIcon"));
                }else if(!file.getFile().isDirectory()){
                    URL imageUrl = getClass().getResource("cafeVermelho.png");
                    setIcon(new ImageIcon(imageUrl));
                }
            }
        }
        return this;
    }
}
