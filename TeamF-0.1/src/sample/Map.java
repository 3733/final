package sample;


import javax.swing.tree.DefaultTreeCellEditor;
import java.util.HashMap;
import java.util.Vector;

public class Map {


    // List of all the nodes of the map
    private Vector<Node> Map = new Vector<Node>();

    //List of all the edges of the map
    private Vector<Edge> Edges = new Vector<Edge>();

    // Disabled nodes of the map
    private Vector<Node> DisabledNodes = new Vector<Node>();


    //Constructor

    public Map(Vector<Node> Map, Vector<Edge> Edges){
        this.Map = Map;
        this.Edges = Edges;
    }



    //Getters and Setters
    public Vector<Node> getMap() {
        return Map;
    }

    public void setMap(Vector<Node> map) {
        Map = map;
    }

    public Vector<Edge> getEdges() {
        return Edges;
    }

    public void setEdges(Vector<Edge> edges) {
        Edges = edges;
    }

    public Vector<Node> getDisabledNodes() {
        return DisabledNodes;
    }

    public void setDisabledNodes(Vector<Node> disabledNodes) {
        DisabledNodes = disabledNodes;
    }




    //Testing Functions
    public void addToMap(Node N){

        Map.add(N);
    }

    public void addToEdges(Edge E){

        Edges.add(E);
    }


    /**
     * This is the Euclidean Distance function, it is a helper method for A*
     * </p>
     * @param   Path  list of nodes
     * @return  Returns the total
     */

    public double TotalDistance(Vector<Node> Path){

        double totalDist =0.0;

        for(int i =0; i<Path.size()-1; i++) {

            totalDist += HeuristicCost(Path.get(i), Path.get(i+1));
        }

        return  totalDist;

    }




    /**
     * This is the BuildMap Method
     * <p>
     *   BuildMap populates the neighbors lists on every node, by iterating through all the edges
     */

    public void BuildMap(){

        for (int i=0; i<Edges.size();i++ ){

            this.Edges.get(i).getStart().addNeighbors(this.Edges.get(i).getEnd());
            this.Edges.get(i).getEnd().addNeighbors(this.Edges.get(i).getStart());
        }
    }





    /**
     * This is the reconstructPath method, it is a helper for A*
     * <p>
     *   Given a hashmap of nodes, that map to other nodes, and a current node it adds all the nodes to the final path
     * </p>
     * @param   cameFrom  Hash map with all the edges
     * @param   Current   desired location
     * @return  Path , a vector with all the nodes of the total path
     */

    private Vector<Node> reconstructPath( HashMap<Node,Node> cameFrom,Node Current){

        Vector<Node> Path = new Vector<Node>();
        Path.add(Current);

        while (cameFrom.keySet().contains(Current)){
            Current = cameFrom.get(Current);
            Path.add(0,Current);
        }

        return Path;
    }





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
     * This is the Euclidean Distance function, it is a helper method for A*
     * </p>
     * @param   Start  starting point
     * @param   End   desired location
     * @return  Returns the direct distance between the two points.
     */

    public double HeuristicCost(Node Start, Node End){
        return (Math.sqrt((Math.abs(Start.getxCoordinate() - End.getxCoordinate())*Math.abs(Start.getxCoordinate() - End.getxCoordinate()) + Math.abs(Start.getyCoordinate() - End.getyCoordinate())*Math.abs(Start.getyCoordinate() - End.getyCoordinate()))));
    }






    /**
     * This is the A* algorithm to find the most efficient path
     * <p>
     *   A* is designed so that it finds the most cost efficient path. Now with multiple floors based
     *   on elevators and stairs
     * </p>
     * @param   Start  starting point for the A* algorithm
     * @param   End   desired location
     * @return  ListPoint it returns a vector that contains the nodes, that the minimum path from Start to End consists of.
     */

    public Vector<Node> AStar(Node Start, Node End){

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


        for (int i=1; i< Map.size();i++){

            gScore.put(Map.get(i),1000000.0);
        }


        // The cost of going from start to start is zero.
        gScore.put(Start,0.0);


        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        // Map with default value of Infinity

        HashMap<Node,Double> fScore  = new HashMap<>();

        for (int i=1; i< Map.size();i++){

            fScore.put(Map.get(i),1000000.0);
        }

        // For the first node, that value is completely heuristic.
        fScore.put(Start,HeuristicCost(Start,End));

        // The node in openSet having the lowest fScore value
        Node Current;
        while(openSet.size()>0){

            Current = GetMin(fScore,openSet);

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
                fScore.put(Current.getNeighbors().get(i),gScoreT+HeuristicCost(Current.getNeighbors().get(i),End));

            }
        }

        return null;
    }
}
