package jgraph;

import java.util.ArrayList;
import javax.swing.JFrame;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

/**
 * A foldable directed acyclic graph (DAG) where each child has only one parent. AKA a Tree.
 * 
 * @author some programmer
 *
 */

public class ChampsTree extends JFrame {
    private static final long serialVersionUID = -2707712944901661771L;
    static ArrayList<node> node = new ArrayList<>();
    
    public static void main(String[] args) {
        
        for(int i=0; i<5; i++){
            node n = new node(1,"nó "+i, new node(0,"",null));
            node.add(n);
            
            //Filhos
                node.add(new node(2,"nó "+i+".1", n));
                node.add(new node(2,"nó "+i+".2", n));
                node.add(new node(2,"nó "+i+".3", n));
                node.add(new node(2,"nó "+i+".4", n));
                node.add(new node(2,"nó "+i+".5", n));
            
        }
        
        ChampsTree frame = new ChampsTree();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public ChampsTree() {
        super("Árvore de resultados");

        FoldableTree graph = new FoldableTree();
        
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);         
        layout.setUseBoundingBox(false);
        layout.setEdgeRouting(false);
        layout.setLevelDistance(30);
        layout.setNodeDistance(20);

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        
        try {           
            Object root = graph.insertVertex(parent, "treeRoot", "Classe", 0, 0, 60, 40);
            Object n = null;
            
            for(int i = 0; i < node.size(); i++){
                if(node.get(i).nivel == 1)
                    n = graph.insertVertex(parent, "v1", node.get(i).valor, 0, 0, 60, 40);
                    graph.insertEdge(parent, null, "", root, n);
                
                    for (int j = 0; j < node.size(); j++) {
                        if(node.get(j).getPai() == node.get(i)){
                            graph.insertEdge(parent, null, "", n, graph.insertVertex(parent, "v1", node.get(j).valor, 0, 0, 60, 40));
                            
                        }
                    }
                    //graph.insertEdge(parent, null, "", node.get(i-(node.size()/2)), graph.insertVertex(parent, "v1", node.get(i).valor, 0, 0, 60, 40));
            }
            
            graph.setCellsSelectable(false);
            graph.setCellsEditable(false);
            graph.setAllowDanglingEdges(false);
            
            
            layout.execute(parent);         
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        graph.addListener(mxEvent.FOLD_CELLS,  new mxIEventListener() {

            @Override
            public void invoke(Object sender, mxEventObject evt) {
                layout.execute(graph.getDefaultParent());
            }
        });

        mxGraphComponent graphComponent = new mxGraphComponent(graph);

        getContentPane().add(graphComponent);
    }
    
}