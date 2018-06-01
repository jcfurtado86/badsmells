/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifap.serde.projectvisualizer.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author furtado
 */
public class MainGUI extends JFrame {

    private JDesktopPane jDesktopPane;
    private JMenu jMenuFile;
    private JMenu jMenuHelp;
    private JMenuBar jMenuBar;
    private JMenuItem jMIOpenProject;
    private JMenuItem jMIAbout;
    private JMenuItem jMIDocumentation;
    private int pos = 0;

    public MainGUI() {
        initUI();
    }

    private void jMenuItemActionPerformed(ActionEvent evt) {
        JFileChooser jFChooser = new JFileChooser(System.getProperty("user.dir"));
        jFChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = jFChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            OpenProjectGUI projectWindow = new OpenProjectGUI(jFChooser.getSelectedFile());
            
            projectWindow.setLocation(pos * 50, pos++ * 50);
            
            jDesktopPane.add(projectWindow);
         
            try {
                projectWindow.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void initUI() {
        jDesktopPane = new JDesktopPane();
        jMenuBar = new JMenuBar();
        jMenuFile = new JMenu();
        jMenuHelp = new JMenu();
        jMIOpenProject = new JMenuItem();
        jMIDocumentation = new JMenuItem();
        jMIAbout = new JMenuItem();

        jMenuFile.setText("File");
        jMenuHelp.setText("Help");

        jMIDocumentation.setText("Documentation");
        jMIAbout.setText("About");

        jMIOpenProject.setText("Open Project");
        jMIOpenProject.addActionListener((ActionEvent evt) -> {
            jMenuItemActionPerformed(evt);
        });

        jMenuFile.add(jMIOpenProject);

        jMenuHelp.add(jMIDocumentation);
        jMenuHelp.add(jMIAbout);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuHelp);

        this.setJMenuBar(jMenuBar);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));

        pack();

        this.setSize(1200, 700);
        this.setTitle("Project Visualizer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /*
        try{
            UIManager.setLookAndFeel (UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            System.out.print(e.getMessage());
        }*/
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }

}
