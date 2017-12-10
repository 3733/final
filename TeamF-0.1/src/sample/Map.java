package sample;


import java.util.HashMap;
import java.util.Vector;

public class Map {


    // List of all the nodes of the map
    private Vector<Node> Nodes = new Vector<Node>();

    //List of all the edges of the map
    private Vector<Edge> Edges = new Vector<Edge>();

    // Disabled nodes of the map
    private Vector<Node> DisabledNodes = new Vector<Node>();


    //Constructor

    public Map(Vector<Node> Nodes, Vector<Edge> Edges){
        this.Nodes = Nodes;
        this.Edges = Edges;
    }



    //Getters and Setters
    public Vector<Node> getNodes() {
        return Nodes;
    }

    public void setNodes(Vector<Node> Nodes) {
        this.Nodes = Nodes;
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

        Nodes.add(N);
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

    public double MouseNodeDist(double x, double y,Node End){
        return (Math.sqrt((Math.abs(x - End.getxCoordinate())*Math.abs(x - End.getxCoordinate()) + Math.abs(y - End.getyCoordinate())*Math.abs(y - End.getyCoordinate()))));
    }

}
