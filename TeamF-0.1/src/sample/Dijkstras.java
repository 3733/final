package sample;

//import org.omg.CORBA.NO_IMPLEMENT;

import java.util.HashMap;
import java.util.Vector;

public class Dijkstras extends AbsAlgorithm{


    /**
     * This is the Dijkstras algorithm to find the most efficient path
     * <p>
     *   Dijkstras is designed so that it finds the most cost efficient path.
     * </p>
     * @param   Start  starting point for the A* algorithm
     * @param   End   desired location
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */


    public Vector<Node> findPath(Node Start, Node End,Map map){

        Vector<Node> Nodes = map.getNodes();

        // The set of nodes already evaluated
        Vector<Node> closedSet = new Vector<>();

        // The set of currently discovered nodes that are not evaluated yet
        Vector<Node> openSet = new Vector<>();

        // Initially, only the start node is known.
        openSet.add(Start);

        // For each node, which node it can most efficiently be reached from.
        // If a node can be reached from many nodes, cameFrom will eventually contain the
        // most efficient previous step.
        HashMap<Node,Node> cameFrom = new HashMap<>();

        // For each node, the cost of getting from the start node to that node.Map with default value of Infinity
        HashMap<Node,Double> gScore  = new HashMap<>();


        for (int i=1; i< Nodes.size();i++){

            gScore.put(Nodes.get(i),1000000.0);
        }


        // The cost of going from start to start is zero.
        gScore.put(Start,0.0);




        // The node in openSet having the lowest fScore value
        Node Current;
        while(openSet.size()>0){

            Current = openSet.get(0);

            if (Current.equals(End)){
                return reconstructPath(cameFrom, Current);
            }

            openSet.remove(Current);
            closedSet.add(Current);


            for( int i=0; i< Current.getNeighbors().size();i++){

                // Ignore the neighbor which is already evaluated
                if (closedSet.contains(Current.getNeighbors().get(i))){
                    continue;
                }

                // Discover a new node
                if (!openSet.contains(Current.getNeighbors().get(i))){
                    openSet.add(Current.getNeighbors().get(i));
                }


                // The distance from start to a neighbor
                double gScoreT = gScore.get(Current) + HeuristicCost(Current,Current.getNeighbors().get(i));

                if (gScoreT >= gScore.get(Current.getNeighbors().get(i))){
                    continue;		// This is not a better path
                }


                // This path is the best until now. Record it!
                cameFrom.put(Current.getNeighbors().get(i),Current);
                gScore.put(Current.getNeighbors().get(i),gScoreT);


            }
        }

        return null;
    }


}
