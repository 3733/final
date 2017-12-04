package sample;

import java.util.*;

public class BeamFirstSearch extends AbsAlgorithm {


    /**
     * This is the Beam Search algorithm to find the most efficient path
     * <p>
     * BFS is designed so that it finds the path to the selected node.
     * </p>
     *
     * @param Start starting point
     * @param End   desired location
     * @return ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public Vector<Node> findPath(Node Start, Node End, Map map) {

        System.out.println("BeamFirstSearch");


        int BeamSize = 3;

        Vector<Node> openSet = new Vector<>();
        Vector<Node> closedSet = new Vector<>();
        HashMap<Node,Node> cameFrom = new HashMap<>();
        openSet.add(Start);

        while(!openSet.isEmpty()){

            Node Current = openSet.get(0);
            openSet.remove(Current);
            closedSet.add(Current);

            if(Current.equals(End)){
                return reconstructPath(cameFrom, Current);
            }

            Vector<Node> CurrentNeighbors = new Vector<>();

            for(int i =0;i<Current.getNeighbors().size();i++){
                if (closedSet.contains(Current.getNeighbors().get(i))){
                    continue;
                }else{
                    CurrentNeighbors.add(Current.getNeighbors().get(i));
                }
            }

            CurrentNeighbors.sort((Node1, Node2) -> (int)(map.HeuristicCost(Node1,End) - map.HeuristicCost(Node2,End)));

            if(CurrentNeighbors.size() < BeamSize){
                openSet.addAll(CurrentNeighbors);
                for (Node neighbor : CurrentNeighbors) {
                    cameFrom.put(neighbor, Current);
                }

            } else{
                openSet.addAll(CurrentNeighbors.subList(0, BeamSize));
                for(int i=0;i<BeamSize;i++){
                    cameFrom.put(CurrentNeighbors.get(i), Current);
                }
            }
        }
        return null;
    }
}