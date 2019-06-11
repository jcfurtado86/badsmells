package br.unifap.serde.projectvisualizer.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 *
 * @author furtado
 */
public class MainGUI extends JPanel {

    private JDesktopPane jDesktopPane;
    private JMenu jMenuFile;
    private JMenu jMenuHelp;
    private JMenuBar jMenuBar;
    private JMenuItem jMIOpenProject, jMIOpenProjectFile;
    private JMenuItem jMIAbout;
    private JMenuItem jMIDocumentation;
    private int pos = 0;
    public static boolean open = false;

    public JToolBar toolBar;
    public String fonts[] = {"Serif", "SansSerif", "Monospaced", "Dialog", "DialogInput"};

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

        JFrame frame = new JFrame("Menu Example");

        //GroupLayout layout = new GroupLayout(frame.getContentPane());
        //frame.getContentPane().setLayout(layout);
        //layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));
        //layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jDesktopPane));
        frame.setJMenuBar(jMenuBar);

        toolBar = new JToolBar("Legenda");
        toolBar.setFloatable(false);

        //for(int i=0;i<140;i++){
        //    toolBar.addSeparator();
        //}
        
        toolBar.addSeparator();
        ImageIcon image = new ImageIcon("D:\\NetBeans\\badsmells\\src\\br\\unifap\\serde\\projectvisualizer\\ui\\red.jpg");
        JLabel imagelabel = new JLabel(image);
        toolBar.add(imagelabel);
        toolBar.addSeparator();
        
        JLabel label = new JLabel("Classe Longa");
        toolBar.add(label);
        toolBar.addSeparator();

        ImageIcon image2 = new ImageIcon("D:\\NetBeans\\badsmells\\src\\br\\unifap\\serde\\projectvisualizer\\ui\\gray.jpg");
        JLabel imagelabel2 = new JLabel(image2);
        toolBar.add(imagelabel2);
        toolBar.addSeparator();
        
        JLabel label2 = new JLabel("Método Longo");
        toolBar.add(label2);
        toolBar.addSeparator();

        ImageIcon image3 = new ImageIcon("D:\\NetBeans\\badsmells\\src\\br\\unifap\\serde\\projectvisualizer\\ui\\blue.jpg");
        JLabel imagelabel3 = new JLabel(image3);
        toolBar.add(imagelabel3);
        toolBar.addSeparator();
        
        JLabel label3 = new JLabel("Código Duplicado");
        toolBar.add(label3);
        toolBar.addSeparator();

        ImageIcon image4 = new ImageIcon("D:\\NetBeans\\badsmells\\src\\br\\unifap\\serde\\projectvisualizer\\ui\\green.jpg");
        JLabel imagelabel4 = new JLabel(image4);
        toolBar.add(imagelabel4);
        toolBar.addSeparator();
        
        JLabel label4 = new JLabel("Lista Longa de Parâmetros");
        toolBar.add(label4);

        //toolBar.addSeparator(); 
        //JComboBox combo = new JComboBox(fonts);
        ///toolBar.add(combo);
        frame.getContentPane().add(jDesktopPane);
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);

        frame.setSize(1200, 700);
        frame.setTitle("JSniffer");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
