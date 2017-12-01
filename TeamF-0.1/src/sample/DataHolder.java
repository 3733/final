package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.image.VolatileImage;
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
    public static javafx.scene.image.Image firstFloor;
    public static javafx.scene.image.Image secondFloor;
    public static javafx.scene.image.Image thirdFloor;
    public static javafx.scene.image.Image GFloor;
    public static javafx.scene.image.Image L1Floor;
    public static javafx.scene.image.Image L2Floor;

    // Store the path for each floor
    public Vector<Node> pathFirst;
    public Vector<Node> pathSecond;
    public Vector<Node> pathThird;
    public Vector<Node> pathL2;
    public Vector<Node> pathL1;
    public Vector<Node> pathG;

    public javafx.scene.canvas.GraphicsContext gc;
    // Stores the x and y of the window
    public double XWindow;
    public double YWindow;
}
