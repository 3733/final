package sample;

import sample.Node;

/** This is the Edge class
 * <p>
 *     THe Edge class represents the vector between any two given nodes, containing an edge ID, start node, and end node
 * </p>
 * @param String EdgeID, Node Start, Node End
 */

public class Edge {

    //Fields
    private String EdgeID;
    private Node Start;
    private Node End;

    //Constructor
    public Edge(String EdgeID,Node Start, Node End) {

        this.EdgeID = EdgeID;
        this.Start = Start;
        this.End = End;
    }

    // Getters and Setters
    public String getEdgeID() {
        return EdgeID;
    }

    public void setEdgeID(String edgeID) {
        this.EdgeID = edgeID;
    }

    public Node getStart() {
        return Start;
    }

    public void setStart(Node start) {
        this.Start = start;
    }

    public Node getEnd() {
        return End;
    }

    public void setEnd(Node end) {
        this.End = end;
    }


}
