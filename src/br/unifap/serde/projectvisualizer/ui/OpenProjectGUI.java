package br.unifap.serde.projectvisualizer.ui;

import br.unifap.serde.projectvisualizer.actions.JTreeActions;
import br.unifap.serde.projectvisualizer.util.CreateChildNodes;
import br.unifap.serde.projectvisualizer.entities.FileNode;
import br.unifap.serde.projectvisualizer.util.MyTreeCellRenderer;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class OpenProjectGUI extends JInternalFrame {

    private JScrollPane jSPLeft;
    private JScrollPane jSPRight;
    private JSplitPane jSplitPane1;
    private JTabbedPane jTabbedPane;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree jTree;
    private String projectFile = "Open Project";

    public OpenProjectGUI(File fileRoot) {
        projectFile = fileRoot.getAbsolutePath();

        initUI();

        openDirectory(fileRoot);
    }

    private void openDirectory(File fileRoot) {
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);
        jTree.setModel(treeModel);

        Object ccn = new CreateChildNodes(fileRoot, root);

        new Thread((Runnable) ccn).start();

    }

    private void initUI() {
        jSplitPane1 = new JSplitPane();
        jSPRight = new JScrollPane();
        jSPLeft = new JScrollPane();
        jTabbedPane = new JTabbedPane();

        treeModel = new DefaultTreeModel(null);

        jTree = new JTree(treeModel);
        jTree.setCellRenderer(new MyTreeCellRenderer());
        jTree.setShowsRootHandles(true);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        jTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                JTreeActions.jTreeMouseClicked(evt, jTree, jTabbedPane);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        jTree.addKeyListener(new JTreeActions(jTree, jTabbedPane));
                
        jSPRight.setViewportView(jTabbedPane);

        jSplitPane1.setRightComponent(jSPRight);

        jSPLeft.setPreferredSize(new Dimension(250, this.getHeight()));
        jSPLeft.setViewportView(jTree);

        jSplitPane1.setLeftComponent(jSPLeft);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSplitPane1));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jSplitPane1));

        pack();

        this.setSize(800, 600);
        this.setTitle(projectFile);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setVisible(true);
        
    }

}
