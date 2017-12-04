package sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class DFSearch extends AbsAlgorithm {




    /**
     * This is the DFS algorithm to find a path to a node
     * <p>
     * DFS is designed so that it finds the ending node, without taking the fastest path.
     * </p>
     *
     * @param Start starting point
     * @param End   desired location
     * @return ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */


    public Vector<Node> findPath(Node Start, Node End, Map map) {

        //an empty set to maintain visited nodes
        HashMap<Node, Boolean> checked = new HashMap();
        // A hash map to keep the path
        HashMap<Node, Node> cameFrom = new HashMap();
        Node s = Start;
        Recursion(s,checked,cameFrom);
        if (!hasPathTo(End, checked)) {
            return null;
        } else {
            return reconstructPath(cameFrom, End);
        }
    }

    private void Recursion(Node v,HashMap<Node, Boolean> marked, HashMap<Node, Node> cameFrom) {
        marked.put(v, true);
        Iterator var2 = v.getNeighbors().iterator();

        while(var2.hasNext()) {
            Node w = (Node)var2.next();
            if (!marked.containsKey(w)) {
                cameFrom.put(w, v);
                Recursion(w,marked,cameFrom);
            }
        }

    }


    //Checks if there is a path
    private boolean hasPathTo(Node v,HashMap<Node, Boolean> marked) {
        return marked.containsKey(v) && ((Boolean)marked.get(v)).booleanValue();
    }




}


