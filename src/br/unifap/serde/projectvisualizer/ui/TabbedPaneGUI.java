/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifap.serde.projectvisualizer.ui;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author furtado
 */
public class TabbedPaneGUI extends JPanel {

    public TabbedPaneGUI() {
    }

    public static JPanel createTab() {        
        JPanel tabPanel = new JPanel();
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jTextArea.setWrapStyleWord(true);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(jTextArea);
        GroupLayout jPanel1Layout = new GroupLayout(tabPanel);
        tabPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane));

        return tabPanel;
    }
    
}
