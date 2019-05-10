package br.unifap.serde.projectvisualizer.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author furtado
 */
public class MainGUI extends JFrame {

    private JDesktopPane jDesktopPane;
    private JMenu jMenuFile;
    private JMenu jMenuHelp;
    private JMenuBar jMenuBar;
    private JMenuItem jMIOpenProject, jMIOpenProjectFile;
    private JMenuItem jMIAbout;
    private JMenuItem jMIDocumentation;
    private int pos = 0;
    public static boolean open = false;

    private final String reconhecerNomeRepositorio = "([A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ-]+).git";

    public MainGUI() {
        initUI();
    }

    private void jMenuItemActionPerformed(ActionEvent evt) {
        JFileChooser jFChooser = new JFileChooser();
        jFChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        String url = JOptionPane.showInputDialog(null, "URL REPOSITORIO.git");

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {

            if (reconhecedor.ClonarRepositorio.clonar(url, nomeRepositorio(url))) {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                jFChooser.setSelectedFile(new File(System.getProperty("user.dir") + "\\Repositorio\\" + nomeRepositorio(url)));
                OpenProjectGUI projectWindow = new OpenProjectGUI(jFChooser.getSelectedFile());

                projectWindow.setLocation(pos * 50, pos++ * 50);

                jDesktopPane.add(projectWindow);

//                projectWindow.requestFocus();
//                projectWindow.addKeyListener(new KeyListener() {
//                    @Override
//                    public void keyPressed(KeyEvent e) {
////                        if((e.getKeyCode() == KeyEvent.VK_0)){
////                            System.out.println("deu certo");
////                        }
//                        System.out.println("key pressed: "+e);
//                    }
//
//                    @Override
//                    public void keyTyped(KeyEvent ke) { System.out.println("key typed "+ ke);}
//
//                    @Override
//                    public void keyReleased(KeyEvent ke) {System.out.println("keu released: "+ke);}
//                });
                try {
                    projectWindow.setSelected(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

        } catch (NullPointerException e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    private void jMenuItemFileActionPerformed(ActionEvent evt) {
        JFileChooser jFChooser = new JFileChooser(System.getProperty("user.dir"));
        jFChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = jFChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            OpenProjectGUI projectWindow = new OpenProjectGUI(jFChooser.getSelectedFile());

            projectWindow.setLocation(pos * 50, pos++ * 50);

            jDesktopPane.add(projectWindow);

            try {
                projectWindow.setMaximum(true);
                projectWindow.setSelected(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void jMenuItemDocumentationActionPerformed(ActionEvent evt) {
        if (!open) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Legenda().setVisible(true);
                }
            });
            MainGUI.open = true;
        }

//        JFrame frame = new JFrame();
//        frame.setLayout(null);
//        frame.setTitle("Documentação JSniffer");
//        frame.setSize(600, 400);
//        frame.setLocation(200, 200);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.setResizable(false);
//
//        JLabel labelVermelho = new JLabel("My label");
//        labelVermelho.setBounds(10, 10, 2000, 20);
//        labelVermelho.setText("Vermelho: Classe Longa é Uma classe que faz coisa \n"
//                + "demais no sistema");
//
//        JLabel labelCinza = new JLabel("My label");
//        labelCinza.setBounds(10, 40, 2000, 20);
//        labelCinza.setText("Cinza: Método Longo são Métodos que centralizam a \n"
//                + "funcionalidade da classe");
//
//        JLabel labelVerde = new JLabel("My label");
//        labelVerde.setBounds(10, 70, 200, 20);
//        labelVerde.setText("Verde: Longa Lista de Parâmetros");
//
//        JLabel labelAzul = new JLabel("My label");
//        labelAzul.setBounds(10, 100, 200, 20);
//        labelAzul.setText("Azul: Código Duplicado");
//
//        frame.add(labelVermelho);
//        frame.add(labelCinza);
//        frame.add(labelVerde);
//        frame.add(labelAzul);
//        JInternalFrame internalFrame = new JInternalFrame();
//        internalFrame.setContentPane(frame.getContentPane());
//        internalFrame.pack();
//
//        JLabel label = new JLabel("My label");
//        label.setText("<html>This is a<br>multline label!<br> Try it yourself!</html>");
//
//        //internalFrame.setVisible(true);
//        internalFrame.setTitle("Welecome to JavaTutorial.net");
//        internalFrame.setSize(600, 400);
//        internalFrame.setLocation(200, 200);
//        internalFrame.setVisible(true);
//        internalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        internalFrame.setResizable(false);
//        internalFrame.add(label);
//
//        internalFrame.setIconifiable(true);
//        internalFrame.setClosable(true);
//        internalFrame.setMaximizable(true);
//
//        jDesktopPane.add(internalFrame);
    }

    private String nomeRepositorio(String url) throws NullPointerException {
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
        jMIOpenProjectFile = new JMenuItem();
        jMIDocumentation = new JMenuItem();
        jMIAbout = new JMenuItem();

        jMenuFile.setText("Arquivo");
        jMenuHelp.setText("Ajuda");

        jMIDocumentation.setText("Documentação");
        jMIAbout.setText("About");

        jMIOpenProject.setText("Abrir Repositório Remoto");
        jMIOpenProject.addActionListener((ActionEvent evt) -> {
            jMenuItemActionPerformed(evt);
        });
        jMIOpenProject.setAccelerator(KeyStroke.getKeyStroke("control R"));

        jMIOpenProjectFile.setText("Abrir Repositório Local");
        jMIOpenProjectFile.addActionListener((ActionEvent evt) -> {
            jMenuItemFileActionPerformed(evt);
        });
        jMIOpenProjectFile.setAccelerator(KeyStroke.getKeyStroke("control O"));

        jMIDocumentation.addActionListener((ActionEvent evt) -> {
            jMenuItemDocumentationActionPerformed(evt);
        });
        jMIDocumentation.setAccelerator(KeyStroke.getKeyStroke("control L"));

        jMenuFile.add(jMIOpenProject);
        jMenuFile.add(jMIOpenProjectFile);

        jMenuHelp.add(jMIDocumentation);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuHelp);

        this.setJMenuBar(jMenuBar);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));

        pack();

        this.setSize(1200, 700);
        this.setTitle("JSniffer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
