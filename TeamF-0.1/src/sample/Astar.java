package sample;

import java.util.HashMap;
import java.util.Vector;

public class Astar extends AbsAlgorithm {



    /**
     * This is the GetMin method, it is a helper for A*
     * <p>
     *   It finds the node from openSet the with minimum distance in the hashmap
     * </p>
     * @param   Score  starting point for the A* algorithm
     * @param   openSet   desired location
     * @return  minNode it returns the node from the open set with min distance in the hashmap
     */

    private Node GetMin(HashMap<Node,Double> Score,Vector<Node> openSet){

        double Min = 1000000.0;
        Node minNode = null;
        for (int i=0;i<openSet.size();i++){
            if (Score.get(openSet.get(i)) <Min){
                Min = Score.get(openSet.get(i));
                minNode = openSet.get(i);
            }
        }

        return minNode;
    }


    /**
     * This is the A* algorithm to find the most efficient path
     * <p>
     *   A* is designed so that it finds the most cost efficient path.
     * </p>
     * @param   Start  starting point for the A* algorithm
     * @param   End   desired location
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */
    @Override
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
        Vector<Node> All = new Vector<>();

        for (int i=1; i< Nodes.size();i++){

            gScore.put(Nodes.get(i),1000000.0);
        }


        // The cost of going from start to start is zero.
        gScore.put(Start,0.0);


        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        // Map with default value of Infinity

        HashMap<Node,Double> fScore  = new HashMap<>();

        for (int i=1; i< Nodes.size();i++){

            fScore.put(Nodes.get(i),1000000.0);
        }

        // For the first node, that value is completely heuristic.
        fScore.put(Start,HeuristicCost(Start,End));

        // The node in openSet having the lowest fScore value
        Node Current;
        while(openSet.size()>0){

            Current = GetMin(fScore,openSet);
            //All.add(Current);
            if (Current.equals(End)){
                return reconstructPath(cameFrom, Current);
                //return All;
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
                fScore.put(Current.getNeighbors().get(i),gScoreT+HeuristicCost(Current.getNeighbors().get(i),End));

            }
        }

        return null;
    }
}
