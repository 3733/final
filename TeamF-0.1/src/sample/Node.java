package sample;

import java.util.Vector;

public class Node {

    //Fields
    private String nodeID;
    private int xCoordinate;
    private int yCoordinate;
    private String floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;
    private char teamAssigned;
    private Vector<Node> Neighbors;


    //Constructor
    public Node (String nodeID, int xCoordinate, int yCoordinate, String floor, String building, String nodeType, String longName, String shortName, char teamAssigned){

        this.nodeID = nodeID;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.teamAssigned = teamAssigned;
        this.Neighbors = new Vector<Node>();

    }

    // Getters and Setters

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public char getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(char teamAssigned) {
        this.teamAssigned = teamAssigned;
    }

    public Vector<Node> getNeighbors() {
        return this.Neighbors;
    }

    public void setNeighbors(Vector<Node> neighbors) {
        Neighbors = neighbors;
    }

    /**
     * This function adds a node to the neighbor's vector of this node
     * </p>
     * @param   neighbor  Node to be added in the Neighbors Vector
     * @return  void
     */

    public void addNeighbors(Node neighbor) {
        this.Neighbors.add(0,neighbor);
    }

    /**
     * This function removes a node from the neighbor's vector of this node
     * </p>
     * @param   ID  nodeID of the Node to be deleted from the Neighbor's Vector
     * @return  void
     */
    public void deleteNeighbor(String ID) {
        for(int i=0; i < this.Neighbors.size();i++ ){
            if (Neighbors.get(i).nodeID.equals(ID)){
                Neighbors.remove(i);
            }
        }
    }
}