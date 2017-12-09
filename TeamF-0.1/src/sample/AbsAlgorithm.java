package sample;

import java.util.HashMap;
import java.util.Vector;

public abstract class AbsAlgorithm implements IPathFinder {


    /**
     * This is the reconstructPath method
     * <p>
     *   Given a hashmap of nodes, that map to other nodes, and a current node it adds all the nodes to the final path
     * </p>
     * @param   cameFrom  Hash map with all the edges
     * @param   Current   desired location
     * @return  Path , a vector with all the nodes of the total path
     */

    protected Vector<Node> reconstructPath(HashMap<Node,Node> cameFrom, Node Current){

        Vector<Node> Path = new Vector<Node>();
        Path.add(Current);

        while (cameFrom.keySet().contains(Current)){
            Current = cameFrom.get(Current);
            Path.add(0,Current);
        }

        return Path;
    }


    /**
     * This is the Euclidean Distance function
     * </p>
     * @param   Start  starting point
     * @param   End   desired location
     * @return  Returns the direct distance between the two points.
     */

    public double HeuristicCost(Node Start, Node End){
        if(!Start.getFloor().equals(End.getFloor())){
            return (Math.sqrt((Math.abs(Start.getxCoordinate() - End.getxCoordinate())*Math.abs(Start.getxCoordinate() - End.getxCoordinate()) + Math.abs(Start.getyCoordinate() - End.getyCoordinate())*Math.abs(Start.getyCoordinate() - End.getyCoordinate()))))+100;
        }else {
            return (Math.sqrt((Math.abs(Start.getxCoordinate() - End.getxCoordinate())*Math.abs(Start.getxCoordinate() - End.getxCoordinate()) + Math.abs(Start.getyCoordinate() - End.getyCoordinate())*Math.abs(Start.getyCoordinate() - End.getyCoordinate()))));
        }

    }


    /**
     * This is the algorithm to find the path
     * <p>
     *   Dijkstras is designed so that it finds the most cost efficient path.
     * </p>
     * @param   Start  starting point
     * @param   End   desired location
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public abstract Vector<Node> findPath(Node Start, Node End,Map map);

}
