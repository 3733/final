package sample;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

public class BestFirstSearch extends AbsAlgorithm {




    /**
     * This is the BestFirstSearch algorithm to find a path to a node
     * <p>
     * BestFirstSearch is designed so that it finds the ending node, without taking the fastest path.
     * </p>
     *
     * @param Start starting point
     * @param End   desired location
     * @return ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public Vector<Node> findPath(Node Start, Node End, Map map) {

        System.out.println("BestFirstSearch");

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
            if (Current.equals(End)) {

                return reconstructPath(cameFrom, Current);
                //return All;
            }

            Current.getNeighbors().sort((Node1, Node2) -> (int)(map.HeuristicCost(Node1,End) - map.HeuristicCost(Node2,End)));

            for (int i = 0; i < Current.getNeighbors().size(); i++) {

                if (!(closedSet.contains(Current.getNeighbors().get(i)))) {

                    cameFrom.put(Current.getNeighbors().get(i),Current);
                    openSet.add(Current.getNeighbors().get(i));
                }
            }
            closedSet.add(Current);

        }
        return null;
    }
}
