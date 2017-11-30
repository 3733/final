package sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class DFSearch implements PathFinder {

    /**
     * This is the reconstructPath method, it is a helper for A*
     * <p>
     * Given a hashmap of nodes, that map to other nodes, and a current node it adds all the nodes to the final path
     * </p>
     *
     * @param cameFrom Hash map with all the edges
     * @param Current  desired location
     * @return Path , a vector with all the nodes of the total path
     */

    private Vector<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node Current) {

        Vector<Node> Path = new Vector<Node>();
        Path.add(Current);

        while (cameFrom.keySet().contains(Current)) {
            Current = cameFrom.get(Current);
            Path.add(0, Current);
        }


        return Path;
    }

    //Checks if there is a path
    private boolean hasPathTo(Node v,HashMap<Node, Boolean> marked) {
        return marked.containsKey(v) && ((Boolean)marked.get(v)).booleanValue();
    }

    /**
     * This is the A* algorithm to find the most efficient path
     * <p>
     * A* is designed so that it finds the most cost efficient path. Now with multiple floors based
     * on elevators and stairs
     * </p>
     *
     * @param Start starting point for the A* algorithm
     * @param End   desired location
     * @return ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */


    public Vector<Node> findPath(Node Start, Node End, Map map) {



        System.out.println("DFSearch");



        //an empty set to maintain visited nodes
        HashMap<Node, Boolean> checked = new HashMap();
        // A hash map to keep the path
        HashMap<Node, Node> cameFrom = new HashMap();
        Node s = Start;
        Recurcion(s,checked,cameFrom);
        if (!hasPathTo(End, checked)) {
            return null;
        } else {
            return reconstructPath(cameFrom, End);
        }
    }

    private void Recurcion(Node v,HashMap<Node, Boolean> marked, HashMap<Node, Node> cameFrom) {
        marked.put(v, true);
        Iterator var2 = v.getNeighbors().iterator();

        while(var2.hasNext()) {
            Node w = (Node)var2.next();
            if (!marked.containsKey(w)) {
                cameFrom.put(w, v);
                Recurcion(w,marked,cameFrom);
            }
        }

    }




}


