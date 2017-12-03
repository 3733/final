package sample;

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

    // Stores the x and y of the window
    public double XWindow;
    public double YWindow;
}
