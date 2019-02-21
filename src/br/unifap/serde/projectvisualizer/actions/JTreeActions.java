/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifap.serde.projectvisualizer.actions;

import br.unifap.serde.projectvisualizer.util.ButtonTabComponent;
import br.unifap.serde.projectvisualizer.entities.FileNode;
import br.unifap.serde.projectvisualizer.ui.OpenProjectGUI;
import br.unifap.serde.projectvisualizer.ui.TabbedPaneGUI;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import net.bouthier.treemapSwing.fileViewer.DemoModel;
import reconhecedor.Reconhecedor;

/**
 *
 * @author furtado
 */
public class JTreeActions implements KeyListener {

    private JTree jTree;
    private JTabbedPane jTabbedPane;

    private static JScrollPane jScrollPane;

    public JTreeActions(JTree jTree, JTabbedPane jTabbedPane) {
        this.jTree = jTree;
        this.jTabbedPane = jTabbedPane;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            clickedKey(this.jTree, this.jTabbedPane);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void jTreeMouseClicked(MouseEvent evt, JTree jTree, JTabbedPane jTabbedPane) {
        clicked(evt, jTree, jTabbedPane);
    }

    public static void clicked(MouseEvent evt, JTree jTree, JTabbedPane jTabbedPane) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
        JTree jTTemp = (JTree) evt.getComponent();
        if (evt.getClickCount() == 2 && jTTemp.getClosestPathForLocation(evt.getX(), evt.getY()).getLastPathComponent() == node) {

            if (node != null) {
                FileNode info = (FileNode) node.getUserObject();
                if (info.getFile().isFile()) {
                    int opened = jTabbedPane.indexOfTab(node.toString());
                    if (opened == -1) {

                        JPanel newTab = TabbedPaneGUI.createTab();

                        try {
                            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(info.getFile())));
                            JScrollPane tempJSCP = (JScrollPane) newTab.getComponent(0);
                            JTextArea tempJTA = (JTextArea) tempJSCP.getViewport().getComponent(0);
                            tempJTA.read(input, info.getFile().getName());

                            Reconhecedor find = new Reconhecedor();
                            ArrayList<String> resultado = find.executar(tempJTA.getText());
                            String resultadoSaida = "";
                            for (int i = 0; i < resultado.size(); i++) {
                                resultadoSaida = resultadoSaida + resultado.get(i);
                            }

                            tempJTA.setText(resultadoSaida);

                            try {
                                newTab = DemoModel.main();

                                jScrollPane = new JScrollPane(newTab);

                                jTabbedPane.add(node.toString(), jScrollPane);
                                jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, new ButtonTabComponent(jTabbedPane));
                                jTabbedPane.setSelectedComponent(jScrollPane);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

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

    public static void clickedKey(JTree jTree, JTabbedPane jTabbedPane) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        if (node != null) {
            FileNode info = (FileNode) node.getUserObject();
            if (info.getFile().isFile()) {
                int opened = jTabbedPane.indexOfTab(node.toString());
                if (opened == -1) {
                    JPanel newTab = TabbedPaneGUI.createTab();

                    try {
                        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(info.getFile())));
                        JScrollPane tempJSCP = (JScrollPane) newTab.getComponent(0);
                        JTextArea tempJTA = (JTextArea) tempJSCP.getViewport().getComponent(0);
                        tempJTA.read(input, info.getFile().getName());

                        Reconhecedor find = new Reconhecedor();
                        ArrayList<String> resultado = find.executar(tempJTA.getText());
                        String resultadoSaida = "";
                        for (int i = 0; i < resultado.size(); i++) {
                            resultadoSaida = resultadoSaida + resultado.get(i);
                        }

                        tempJTA.setText(resultadoSaida);

                        try {
                            newTab = DemoModel.main();

                            jScrollPane = new JScrollPane(newTab);

                            jTabbedPane.add(node.toString(), jScrollPane);
                            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount() - 1, new ButtonTabComponent(jTabbedPane));
                            jTabbedPane.setSelectedComponent(jScrollPane);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(OpenProjectGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    jTabbedPane.setSelectedIndex(opened);
                }
            }

        }
    }

}
