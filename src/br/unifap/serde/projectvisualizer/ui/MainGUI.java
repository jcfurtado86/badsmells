package br.unifap.serde.projectvisualizer.ui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
    
    private final String reconhecerNomeRepositorio = "([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]+).git";

    public MainGUI() {
        initUI();
    }

    private void jMenuItemActionPerformed(ActionEvent evt) {
        JFileChooser jFChooser = new JFileChooser();
        jFChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
             
        String url = JOptionPane.showInputDialog(null, "URL REPOSITORIO.git");
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try {
            
        
            if (reconhecedor.ClonarRepositorio.clonar(url,nomeRepositorio(url)) ){
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                jFChooser.setSelectedFile(new File(System.getProperty("user.dir")+"\\Repositorio\\"+nomeRepositorio(url)));
                OpenProjectGUI projectWindow = new OpenProjectGUI(jFChooser.getSelectedFile());

                projectWindow.setLocation(pos * 50, pos++ * 50);

                jDesktopPane.add(projectWindow);

                try {
                    projectWindow.setSelected(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        
        } catch (NullPointerException e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
    }
    
    private String nomeRepositorio(String url) throws NullPointerException{
        final Pattern pattern = Pattern.compile(reconhecerNomeRepositorio, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(url);

        while (matcher.find()) {
            return matcher.group(1);
        }
        
        return "NomeNãoEncontrado";
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
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }

}
