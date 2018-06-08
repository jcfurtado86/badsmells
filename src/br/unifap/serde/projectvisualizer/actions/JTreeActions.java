/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.unifap.serde.projectvisualizer.actions;

import reconhecedor.Metodo;
import br.unifap.serde.projectvisualizer.util.ButtonTabComponent;
import br.unifap.serde.projectvisualizer.entities.FileNode;
import br.unifap.serde.projectvisualizer.ui.OpenProjectGUI;
import br.unifap.serde.projectvisualizer.ui.TabbedPaneGUI;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import reconhecedor.Reconhecedor;

/**
 *
 * @author furtado
 */
public class JTreeActions {

    public JTreeActions() {
    }

    public static void jTreeMouseClicked(MouseEvent evt, JTree jTree, JTabbedPane jTabbedPane) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
        JTree jTTemp = (JTree) evt.getComponent();
        if (evt.getClickCount() == 2 && jTTemp.getClosestPathForLocation(evt.getX(), evt.getY()).getLastPathComponent() == node) {
            if (node != null) {
                FileNode info = (FileNode) node.getUserObject();
                if (info.getFile().isFile()) {
                    int opened = jTabbedPane.indexOfTab(node.toString());
                    if (opened == -1) {
                        JPanel newTab = TabbedPaneGUI.createTab();
                        jTabbedPane.add(node.toString(), newTab);
                        jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, new ButtonTabComponent(jTabbedPane));
                        jTabbedPane.setSelectedComponent(newTab);
                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(info.getFile())));
                            JScrollPane tempJSCP = (JScrollPane) newTab.getComponent(0);
                            JTextArea tempJTA = (JTextArea) tempJSCP.getViewport().getComponent(0);
                            tempJTA.read(input, info.getFile().getName());
                            
                            Reconhecedor find = new Reconhecedor();
                            ArrayList<String> resultado = find.executar(tempJTA.getText());                           
                            String resultadoSaida = "";
                            for(int i=0;i<resultado.size();i++){
                                resultadoSaida = resultadoSaida + resultado.get(i);
                            }
                            
                            tempJTA.setText(resultadoSaida);

                        } catch (IOException ex) {
                            Logger.getLogger(OpenProjectGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        jTabbedPane.setSelectedIndex(opened);
                    }
                }
            }

        }
    }

}
