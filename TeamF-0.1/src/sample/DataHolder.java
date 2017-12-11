package sample;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class DataHolder {
    // Current map stored, old
    public javafx.scene.image.Image map;

    // This stores what map is currently displayed
    public String currentMap;

    //This holds if the states of the maps were updated, used for emails
    public ArrayList<Boolean> floorList = new ArrayList<Boolean>(Arrays.asList(false,false,false,false,false,false));

    //The current path on the screen
    public Vector<Node> path;

    // The javafx images on each of the maps
    public javafx.scene.image.Image firstFloor;
    public javafx.scene.image.Image secondFloor;
    public javafx.scene.image.Image thirdFloor;
    public javafx.scene.image.Image GFloor;
    public javafx.scene.image.Image L1Floor;
    public javafx.scene.image.Image L2Floor;


    //List of all Nodes and Edges, a Map
    public Map graph;

    // Store the path for each floor
    public Vector<Node> pathFirst;
    public Vector<Node> pathSecond;
    public Vector<Node> pathThird;
    public Vector<Node> pathL2;
    public Vector<Node> pathL1;
    public Vector<Node> pathG;

    public javafx.scene.canvas.GraphicsContext gc; // Used only in navigation
    public javafx.scene.canvas.GraphicsContext gc1; // Used only in Map editing
    public javafx.scene.canvas.GraphicsContext gc2; // Used only in Map editing
    // Stores the x and y of the window
    public double XWindow;
    public double YWindow;

    public Node kiosk;
    public Node destinationNode;

    /**
     * Creating the Vector for the floor nodes.
     *the following maybe has issues.
     */
    public Vector<Node>  groundFloorNodes;
    public Vector<Node>  lowerLevel01FloorNodes;
    public Vector<Node>  lowerLevel02FloorNodes;
    public Vector<Node>  firstFloorNodes;
    public Vector<Node>  secondFloorNodes;
    public Vector<Node>  thirdFloorNodes;

    public Vector<Edge> thirdFloorEdges;
    public Vector<Edge> secondFloorEdges;
    public Vector<Edge> firstFloorEdges;
    public Vector<Edge> groundFloorEdges;
    public Vector<Edge> lower1FloorEdges;
    public Vector<Edge> lower2FloorEdges;

    public double divisionCst = 3.87;
    public int offset = 2;

    public Vector<Node> nodeAlign = new Vector<Node>();

    public ObservableList<HBox> directions =  FXCollections.observableArrayList();
    public Staff loggedInGuy;
}
