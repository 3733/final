package sample;

import java.util.*;

public class BFSearch implements PathFinder {

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
        for (int i =0; i<Path.size();i++){
            System.out.println(Path.get(i).getLongName()+"<======");
        }
        return Path;


/*

        Path p = new Path();
        p.buildPath(end);
        Node current = end;

        while(cameFrom.keySet().contains(current)) {
            current = (Node)cameFrom.get(current);
            p.buildPath(current);
        }

        return p;
*/



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

        System.out.println("BFSearch");


        //a FIFO open_set
        Vector<Node> openSet = new Vector<>();
        //an empty set to maintain visited nodes
        Vector<Node> closedSet = new Vector<>();
        //a dictionary to maintain meta information (used for path formation)
        HashMap<Node,Node> cameFrom = new HashMap<>();

        Vector<Node> All = new Vector<>();

        cameFrom.put(null,Start);

        openSet.add(Start);

        closedSet.add(Start);

        while(openSet.size()>0) {

            Node Current = openSet.remove(0);
            //All.add(Current);
            System.out.println(Current.getNodeID()+"--------");
            if (Current.equals(End)) {

                return reconstructPath(cameFrom, Current);
                //return All;
            }

            for (int i = 0; i < Current.getNeighbors().size(); i++) {



                if (!(closedSet.contains(Current.getNeighbors().get(i)))) {

                    cameFrom.put(Current.getNeighbors().get(i),Current);
                    openSet.add(Current.getNeighbors().get(i));

                }
            }
            closedSet.add(Current);

        }

        return null;

      /*

        ArrayList<Node> s = new ArrayList();
        LinkedList<Node> q = new LinkedList();
        HashMap<Node, Node> parents = new HashMap();
        s.add(Start);
        q.addLast(Start);

        while (!q.isEmpty()) {
            Node current = (Node) q.removeFirst();
            if (current.equals(End)) {
                return this.reconstructPath(parents, End);
            }

            Iterator var7 = current.getNeighbors().iterator();

            while (var7.hasNext()) {
                Node n = (Node) var7.next();
                if (!s.contains(n)) {
                    s.add(n);
                    parents.put(n, current);
                    q.addLast(n);
                }
            }
        }

        return null;

*/
    }
}