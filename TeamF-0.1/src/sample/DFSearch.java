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



        /*System.out.println("DFSearch");


        //a FIFO open_set
        Vector<Node> openSet = new Vector<>();
        //an empty set to maintain visited nodes
        Vector<Node> closedSet = new Vector<>();
        //a dictionary to maintain meta information (used for path formation)
        HashMap<Node,Node> cameFrom = new HashMap<>();

        openSet.add(Start);
        closedSet.add(Start);
        cameFrom.put(null,Start);





        while(openSet.size()>0) {

            //Node Current = openSet.remove(0);
            Node Current = openSet.get(0);

            if (Current.equals(End)) {
                return reconstructPath(cameFrom, Current);

            }


            for (int i = 0; i < Current.getNeighbors().size(); i++) {

                if (closedSet.contains(Current.getNeighbors().get(i))) {

                    continue;
                }


                if (!closedSet.contains(Current.getNeighbors().get(i))) {



                    cameFrom.put(Current.getNeighbors().get(i),Current);

                    openSet.add(Current.getNeighbors().get(i));
                }else{

                    closedSet.add(Current);
                    openSet.remove(0);
                }
            }

        }


        return null;*/


        HashMap<Node, Boolean> marked = new HashMap();
        HashMap<Node, Node> edgeTo = new HashMap();
        Node s = Start;
        dfs(s,marked,edgeTo);
        if (!hasPathTo(End, marked)) {
            return null;
        } else {
            return reconstructPath(edgeTo, End);
        }
    }

    private void dfs(Node v,HashMap<Node, Boolean> marked, HashMap<Node, Node> edgeTo) {
        marked.put(v, true);
        Iterator var2 = v.getNeighbors().iterator();

        while(var2.hasNext()) {
            Node w = (Node)var2.next();
            if (!marked.containsKey(w)) {
                edgeTo.put(w, v);
                dfs(w,marked,edgeTo);
            }
        }

    }

    private boolean hasPathTo(Node v,HashMap<Node, Boolean> marked) {
        return marked.containsKey(v) && ((Boolean)marked.get(v)).booleanValue();
    }


}


