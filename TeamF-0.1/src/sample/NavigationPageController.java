package sample;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//import Healthcare.HealthCareRun;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
//import controllers.API.APIApp;

import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static sample.Main.getLoggedInGuy;

public class NavigationPageController implements Initializable, Data, ITimed{


    private Timer atimer;

    //FXML UI Components
    @FXML
    private AnchorPane animationPane;
    @FXML
    private Group scrollContent;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private javafx.scene.image.ImageView icon;
    @FXML
    private Label sendLabel;
    @FXML
    private JFXButton menuButton, search;
    @FXML
    private JFXButton sendButton;
    @FXML
    private javafx.scene.canvas.Canvas pathCanvas;
    @FXML
    private JFXListView<HBox> directionSteps;
    @FXML
    private JFXListView threeList, twoList, oneList, lowerTwoList, lowerOneList, groundList;
    @FXML
    private JFXListView searchList;
    @FXML
    private ScrollPane scrollMap;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private AnchorPane buttonHolder;
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton floorVisA, floorVisB, floorVisC, floorVisD, floorVisE, floorVisF;

    @FXML
    private VBox labelBox;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Tab floorThree, floorTwo, floorOne, floorLowerTwo, floorLowerOne, floorGround;
    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;
    // Contains the desired user destination
    @FXML
    private JFXTextField destination;
    // Contains stairs option
    @FXML
    private JFXCheckBox stairs;
    // Contains the elevator option
    @FXML
    private JFXCheckBox elevator;
    // Contains the Invalid email error message
    @FXML
    private static Label invalidEmailText;
    @FXML
    private JFXRadioButton start, end;

    @FXML
    private JFXTextField startField, endField;

    @FXML
    private ToggleGroup points;

    @FXML
    private String defaultStart;

    @FXML
    private Label startLabel, endLabel;

    @FXML
    private JFXButton helpButton, aboutButton;

    // Email UI Components
    @FXML
    private JFXTextField email;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private VBox adminBox;

    @FXML
    private VBox mainMenuBox;

    @FXML
    private JFXDrawer mainMenu, adminMenu;

    @FXML
    private JFXButton loginButton;

    @FXML
    private ImageView threeArrow, twoArrow, oneArrow, lowerOneArrow, lowerTwoArrow, groundArrow;


    @FXML
    private JFXButton createServButton;

    @FXML
    private JFXButton existServButton;

    @FXML
    private JFXButton editMapButton;

    @FXML
    private JFXButton editUsersButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton timeoutButton;

    //other components
    @FXML
    private Main mainController;

    private Vector<Node> path = new Vector<Node>();

    private Map CurMap;

    private String filePath = "/sample/UI/Icons/";

    ObservableList<String> allEntries;

    private int currentAlgo = 1;

    double scaleValue = 0.7;

    private Node selectedNode;

    private Vector<String> floorsVisited = new Vector<>();

    private Vector<JFXButton> floorButtons = new Vector<>();

    private MenuDrawerController menuDrawerController;

    private Vector<ImageView> buttonPanes = new Vector<>();

    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction()//  throws IOException, InterruptedException
    {
        // AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
        // Staff guest = new Staff("2", "B", 999999, "9", "2", "User", "z@yorha.net");
        if (getLoggedInGuy().getEmployeeType().trim().equals("User"))
        {
            // do nothing
        }
        else
        {
            try
            {
                //timeoutController.doNavTimer();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Could not start timer.");
            }
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization and Start

    //Purpose: Initialize all the UI components
    @Override
    public void initialize(URL location, ResourceBundle resources){

        menuDrawerController = mainController.menuDrawerController;
        mainMenu.setVisible(false);
        Data.data.gc = pathCanvas.getGraphicsContext2D();
        map.setImage(Data.data.firstFloor);

        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburger);
        transition.setRate(-1);

        menuButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{
            transition.setRate(transition.getRate() * -1);
            mainMenu.toggle();
            if(mainMenu.isShown()&&!mainMenu.visibleProperty().getValue()){
                mainMenu.setVisible(true);
            }
            transition.play();
        });

        createServButton.setVisible(false);
        editMapButton.setVisible(false);
        editUsersButton.setVisible(false);
        existServButton.setVisible(false);
        timeoutButton.setVisible(false);


        //mainMenu.setOnDrawerClosed(mainMenu.setVisible(t););

        //disables the bars and starts up the zoom function
        scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoom();

        updateImageCoordinates();

        floorButtons.add(floorVisA);
        floorButtons.add(floorVisB);
        floorButtons.add(floorVisC);
        floorButtons.add(floorVisD);
        floorButtons.add(floorVisE);
        floorButtons.add(floorVisF);

        floorVisA.setVisible(false);
        floorVisB.setVisible(false);
        floorVisC.setVisible(false);
        floorVisD.setVisible(false);
        floorVisE.setVisible(false);
        floorVisF.setVisible(false);



        //popluating list view -- three
        ObservableList<String> threeItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("3"));

        // Second Floor
        ObservableList<String> twoItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("2"));

        // First Floor
        ObservableList<String> oneItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("1"));

        // Ground Floor
        ObservableList<String> groundItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("G"));

        // Lower 1 Floor
        ObservableList<String> lowerOneItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("L1"));

        // Lower 2 Floor
        ObservableList<String> lowerTwoItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("L2"));

        // All entries
        allEntries = FXCollections.observableArrayList(testEmbeddedDB.getAllLongNames());
        searchList.setItems(allEntries);

        end.setSelected(true);
        map.setImage(Data.data.firstFloor);
        stairs.setSelected(true);
        elevator.setSelected(true);
        tabPane.getSelectionModel().select(floorOne);

        //switching admin privs
        SettingSingleton.getSettingSingleton().getauthPropertyProperty().addListener((ObservableValue<? extends AuthenticationInfo> a, AuthenticationInfo before, AuthenticationInfo after) -> {
            if (after.getPriv().equals(AuthenticationInfo.Privilege.ADMIN)) {
                createServButton.setVisible(true);
                editMapButton.setVisible(true);
                editUsersButton.setVisible(true);
                existServButton.setVisible(true);
                timeoutButton.setVisible(true);

                loginButton.setOnAction((event) -> {
                    try {
                        logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            else if(after.getPriv().equals(AuthenticationInfo.Privilege.STAFF)){
                createServButton.setVisible(true);
                existServButton.setVisible(true);
                Image logoutPNG = new Image(getClass().getResourceAsStream("/sample/UI/Icons/f61b5f54.png"));
                ImageView logoutIMG = new ImageView(logoutPNG);
                logoutIMG.setFitHeight(25);
                logoutIMG.setFitWidth(25);
                loginButton.setGraphic(logoutIMG);
                loginButton.setOnAction((event) -> {
                    try {
                        logout();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            else{
                createServButton.setVisible(false);
                editMapButton.setVisible(false);
                editUsersButton.setVisible(false);
                existServButton.setVisible(false);
                loginButton.setOnAction((event) -> {
                    try {
                        login();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        startZoom();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    /**
     * Set the map controller
     * @param in The main screen controller
     */
    public void setMainController(Main in){
        mainController = in;
    }

    /**
     * Get the current direction steps
     * @return Return the JFX HBox direction steps
     */
    public JFXListView<HBox> getDirectionSteps(){
        return this.directionSteps;
    }

    /**
     * Return a vector with all the floors visited
     * @return Vector<String> contains all the floors visited
     */
    public Vector<String> getFloorsVisited(){
        return  this.floorsVisited;
    }

    /**
     * Get the currently selected algorithm
     * @return A number representing the current algorithm
     */
    public int getCurrentAlgo(){
        return this.currentAlgo;
    }

    /**
     * Set the current kiosk in the dataholder class
     * @param k Node representing the kiosk
     */
    public void setKiosk(Node k){
        data.kiosk = k;
    }

    /**
     * Set the destination text box string
     * @param s The string to put inside the textbox
     */
    public void setSearch(String s){
        this.destination.setText(s);
    }

    /**
     * Close the search list
     */
    public void autoClose(){
        searchList.setVisible(false);
    }

    public JFXTextField getDestination(){return this.destination;}

    public Label getStartLabel(){return this.startLabel;}

    public Label getEndLabel(){return this.endLabel;}

    public JFXRadioButton getRadioStart(){return this.start;}

    public void setDestination(String s){destination.setText(s);}

    public JFXRadioButton getRadioEnd(){return this.end;}

    /**
     * Set the current pathfinding algorithm
     * @param current An integer represent to represent the current pathfinding algorithm
     */
    public void setCurrentAlgo(int current){
        this.currentAlgo =  current;
    }

    public VBox getAdminBox(){
        return this.adminBox;
    }

    public JFXButton getLoginButton() {
        return this.loginButton;
    }


    final ChangeListener<Number> paneChanged = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            ReadOnlyDoubleProperty prop = (ReadOnlyDoubleProperty) observable;
            String name = prop.getName();
            Double val = prop.getValue();
            if(name == "width"){
                map.setFitWidth(val);
                pathCanvas.setWidth(val);
            }else if(name == "height"){
                map.setFitHeight(val);
                pathCanvas.setHeight(val);
            }
        }
    };


    /**
     * Set the search field text fields
     */
    @FXML
    public void settingSearch(){
        if (points.getSelectedToggle() == start) {

            destination.setText(startLabel.getText());

        }
        else{
            destination.setText(endLabel.getText());
        }
    }

    /**
     * Set the starting text field
     * @param t String representing the starting text field
     */
    @FXML
    public void setStart(String t){
        startLabel.setText(t);
    }

    public Node getKiosk(){
        return data.kiosk;
    }

    /**
     * Function that uses the search in order to properly gauge what destination node is meant by the user,
     * called by the search button, starts the pathfinding function with go()
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void settingFields() throws IOException, InterruptedException {
        Node currNode = SearchEngine.SearchClosestNode(destination.getText().trim());
        data.destinationNode = currNode;
        destination.setText(currNode.getLongName().trim());
        go();
    }

    /**
     * Display invalid email message
     */
    @FXML
    public static void setInvalidEmail(){
        invalidEmailText.setVisible(true);
    }

    /**
     * Set the current map of nodes
     * @param m Map to be stored
     * @throws IOException
     */
    public void setMap(Map m) throws IOException{
        this.CurMap = m;
        Data.data.currentMap = "1";
    }

    public Map getMap(){
        return this.CurMap;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Floor Methods
    @FXML
    public void changeFloorL1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L1Floor);
        onFloorChange();
        drawAnimation(data.pathL1);
        drawButtons(data.buttonNodes, "L1");
        Data.data.currentMap = "L1";
        hierarchicalText("L1");
        //AutoZoom(data.pathL1);
        update();
    }

    @FXML
    public void changeFloorL2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L2Floor);
        onFloorChange();
        drawAnimation(data.pathL2);
        drawButtons(data.buttonNodes,"L2");
        Data.data.currentMap = "L2";
        hierarchicalText("L2");
        //AutoZoom(data.pathL2);
        update();
    }

    @FXML
    public void changeFloor1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        data.gc.clearRect(0,0, x, y);
        map.setImage(Data.data.firstFloor);
        onFloorChange();
        drawAnimation(data.pathFirst);
        drawButtons(data.buttonNodes, "1");
        Data.data.currentMap = "1";
        hierarchicalText("1");
        //AutoZoom(data.pathFirst);
        update();
    }

    @FXML
    public void changeFloor2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        if(Data.data.gc != null){
            Data.data.gc.clearRect(0, 0, x, y);
        }
        map.setImage(Data.data.secondFloor);
        onFloorChange();
        drawAnimation(data.pathSecond);
        drawButtons(data.buttonNodes, "2");
        Data.data.currentMap = "2";
        hierarchicalText("2");
        //AutoZoom(data.pathSecond);
        update();
    }

    @FXML
    public void changeFloor3() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        if(Data.data.gc != null) {
            Data.data.gc.clearRect(0, 0, x, y);
        }
        map.setImage(Data.data.thirdFloor);
        onFloorChange();
        drawAnimation(data.pathThird);
        drawButtons(data.buttonNodes, "3");
        Data.data.currentMap = "3";
        hierarchicalText("3");
        //AutoZoom(data.pathThird);
        update();
    }

    @FXML
    public void changeFloorG() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.GFloor);
        onFloorChange();
        drawAnimation(data.pathG);
        drawButtons(data.buttonNodes, "G");
        Data.data.currentMap = "G";
        hierarchicalText("G");
        //AutoZoom(data.pathG);
        update();
    }

    /**
     * This calls all the functions necessary for a floor change
     */
    public void onFloorChange() {
        clearAnimations();
        clearButtons();
        drawStartFinish();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Screen Functions

    /**
     * Call the login screen, clear the navigationpage controller and its fields
     * @throws IOException
     */
    @FXML
    public void login() throws IOException{
        Main.loginScreen(loginButton);
        clearFields();
        clear();
    }

    /**
     * Call the help screen
     */
    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    /**
     * Return to the start screen and clear all the navigation fields and map
     * @throws IOException
     */
    @FXML
    public void back() throws IOException{
        Main.startScreen();
        clearFields();
        clear();
    }

    /**
     * Show the timeout settings window
     */
    @FXML
    public void changeTimeout(){
        Main.timeOutWindow(timeoutButton);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Finding Functions

    /**
     * Find a path, utilizes the nodes stored inside the dataholder class to find the shortest path
     * MultiFloor actually draws the path
     * @throws IOException
     * @throws InterruptedException
     */
    public void findPath() throws IOException, InterruptedException {
        clear();
        this.path = SearchEngine.NodeToNode(data.destinationNode,currentAlgo);

        data.buttonNodes = findFloorHyperLinks(this.path);

        MultiFloorPathDrawing(this.path);
        int length = path.size();
        String lastFloor = path.get(length - 1).getFloor();
        setMap(lastFloor.trim());
    }


    /**
     * clear the fields of the ImageView map object
     */
    @FXML
    public void clearFields(){
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();
    }

    /**
     * clear the screen and use findPath() to find the path
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void go() throws IOException,InterruptedException{
        clear();
        findPath();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Drawing and Directions functions

    /**
     * Find a string of directions corresponding to the path on the screen
     * @param in Pass in a path of nodes
     * @return String of directions
     */
    public static String directions(Vector<Node> in){
        String out = "";
        Node a, b, c;
        int count = 0;
        if(in.size()<2){
            out = out.concat("Path too short");
        }
        a = in.get(0);
        b = in.get(1);
        out = out.concat("Start at " + a.getLongName().trim().split("Node")[0].split("Floor")[0]+"<br>");
        floorSequence(a.getFloor().trim());
        out = out.concat("Go towards " + b.getLongName().trim().split("Node")[0].split("Floor")[0]+"<br>");
        floorSequence(b.getFloor().trim());

        for(int i = 2; i < in.size(); i++){
            a = in.get(i-2);
            b = in.get(i-1);
            c = in.get(i);
            String turn = "";
            double angle = NodeMath.findAngle(a.getxCoordinate(), a.getyCoordinate(), b.getxCoordinate(), b.getyCoordinate(), c.getxCoordinate(), c.getyCoordinate());
            if(angle<45){
                turn = "sharply right";
            }else if(angle < 135){
                turn = "right";
            }else if(angle < 225){
                turn = "straight";
            }else if(angle <315){
                turn = "left";
            }else{
                turn = "sharply left";
            }

            if(!b.getFloor().equals(c.getFloor()) && b.getNodeType().equals("ELEV")){
                if (count == 1){
                    out = out.concat("Go straight to " + b.getLongName().trim().split("Node")[0].split("Floor")[0] + "<br>");
                    floorSequence(b.getFloor().trim());
                    count --;
                }
                out = out.concat("Take the elevator to floor " + c.getFloor().trim() + "<br>");
                floorSequence(c.getFloor().trim());
            }
            else if(!b.getFloor().equals(c.getFloor()) && b.getNodeType().equals("STAI")){
                if (count == 1){
                    out = out.concat("Go straight to " + b.getLongName().trim().split("Node")[0].split("Floor")[0] + "<br>");
                    floorSequence(b.getFloor().trim());
                    count --;
                }
                out = out.concat("Take the stairs to floor " + c.getFloor().trim() + "<br>");
                floorSequence(c.getFloor().trim());
            }
            else if(turn.equals("straight") && count == 0) {
                count ++;
            }
            else if(!turn.equals("straight")){
                if (count == 1){

                    out = out.concat("Go straight to " + b.getLongName().trim().split("Node")[0].split("Floor")[0] + "<br>");
                    floorSequence(b.getFloor().trim());
                    count --;
                }
                out = out.concat("Turn " + turn + " towards " + c.getLongName().trim().split("Node")[0].split("Floor")[0] + "<br>");
                floorSequence(c.getFloor().trim());
            }
        }
        return out;
    }

    /**
     * Find all the proper images for the directions
     */
    Image startIMG = new Image(getClass().getResourceAsStream(filePath + "Start.png"));
    Image straightIMG = new Image(getClass().getResourceAsStream(filePath + "Straight.png"));
    Image rightIMG = new Image(getClass().getResourceAsStream(filePath + "RightTurn.png"));
    Image leftIMG = new Image(getClass().getResourceAsStream(filePath + "LeftTurn.png"));
    Image sharpRightIMG = new Image(getClass().getResourceAsStream(filePath + "SharpRight.png"));
    Image sharpLeftIMG = new Image(getClass().getResourceAsStream(filePath + "SharpLeft.png"));
    Image elevIMG = new Image(getClass().getResourceAsStream(filePath + "Elevator.png"));
    Image stairIMG = new Image(getClass().getResourceAsStream(filePath + "Stairs.png"));

    /**
     * Return a linked list of all the images for each of the directions
     * @param path A path of nodes
     * @return Linked list of all the images corresponding to each direction
     */
    public LinkedList<Image> directionArrows(String[] path){
        LinkedList<Image> images = new LinkedList<Image>();

        for(int i = 0; i < path.length; i++){
            if(path[i].contains("Turn sharply right")){
                images.add(i, sharpRightIMG);
            }
            else if(path[i].contains("Turn right")){
                images.add(i, rightIMG);
            }
            else if(path[i].contains("Turn sharply left")){
                images.add(i, sharpLeftIMG);
            }
            else if(path[i].contains("Turn left")){
                images.add(i, leftIMG);
            }
            else if(path[i].contains("straight") || path[i].contains("towards")){
                images.add(i, straightIMG);
            }
            else if(path[i].contains("elevator")){
                images.add(i, elevIMG);
            }
            else if(path[i].contains("stairs")){
                images.add(i, stairIMG);
            }
            else{
                images.add(i, startIMG);
            }
        }

        return images;
    }

    static LinkedList<String> sequence = new LinkedList<String>();
    public static void floorSequence(String floor){
        sequence.add(floor);
    }

    /**
     * Separates one path from the pathfinding algorithm into paths by floor
     * @param path A path of nodes from pathfinding algorithm
     * @return A vector of vectors of nodes representing paths for each floor
     */
    public Vector<Vector<Node>> separator(Vector<Node> path){
        Vector<Vector<Node>> paths = new Vector<Vector<Node>>();
        for(int i = 0; i <6; i++){
            paths.add(new Vector<Node>());
        }
        String prev = path.get(0).getFloor().replaceAll("\\s+","");
        Node blank = new Node("BLANK", -1,-1,"BL", "BLANK","BLANKTYPE","BLANK","BLANK", 'Z');
        int prevElt = 0;
        for(Node i: path){
            String pathFloor = i.getFloor().replaceAll("\\s+","");
            if(!(pathFloor.equals(prev))){
                paths.elementAt(prevElt).add(blank);
                prev = pathFloor;
            }
            if(pathFloor.equals("L2")){
                paths.elementAt(0).add(i);
                prevElt = 0;
            } else if (pathFloor.equals("L1")) {
                prevElt = 1;
                paths.elementAt(1).add(i);
            } else if (pathFloor.equals("0G") || pathFloor.equals("G")){
                prevElt = 2;
                paths.elementAt(2).add(i);
            } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                prevElt = 3;
                paths.elementAt(3).add(i);
            } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                prevElt = 4;
                paths.elementAt(4).add(i);
            } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                prevElt = 5;
                paths.elementAt(5).add(i);
            }
        }
        return paths;
    }

    /**
     * Create hierarchical text for every floor
     * @param floor The current floor
     */
    public void hierarchicalText(String floor){
        for(int z = 0; z < data.directions.size(); z++){
            if(sequence.get(z).equals(floor)){
                data.directions.get(z).setStyle("-fx-background-color: #d7eef2");
            }
            else{
                data.directions.get(z).setStyle("");
            }
        }
    }

    /**
     * Show all the floor buttons that have to be visible for the current floor
     */
    public void displayFloorButtons(){
        String a = sequence.get(0);
        int b = 0;
        floorVisA.setText(a);
        floorVisA.setVisible(true);
        floorVisA.setOnAction((event) -> {
            if(floorVisA.getText().equals("3")){
                changeFloor3();
            }
            if(floorVisA.getText().equals("2")){
                changeFloor2();
            }
            if(floorVisA.getText().equals("1")){
                changeFloor1();
            }
            if(floorVisA.getText().equals("G")){
                changeFloorG();
            }
            if(floorVisA.getText().equals("L1")){
                changeFloorL1();
            }
            if(floorVisA.getText().equals("L2")){
                changeFloorL2();
            }});

        for(int i = 1; i < sequence.size(); i++){
            if(!sequence.get(i).equals(a)){
                if(b == 0) {
                    floorVisB.setVisible(true);
                    floorVisB.setText(sequence.get(i));
                    floorVisB.setOnAction((event) -> {
                        if(floorVisB.getText().equals("3")){
                            changeFloor3();
                        }
                        if(floorVisB.getText().equals("2")){
                            changeFloor2();
                        }
                        if(floorVisB.getText().equals("1")){
                            changeFloor1();
                        }
                        if(floorVisB.getText().equals("G")){
                            changeFloorG();
                        }
                        if(floorVisB.getText().equals("L1")){
                            changeFloorL1();
                        }
                        if(floorVisB.getText().equals("L2")){
                            changeFloorL2();
                        }});
                }
                else if(b==1){
                    floorVisC.setVisible(true);
                    floorVisC.setText(sequence.get(i));
                    floorVisC.setOnAction((event) -> {
                        if(floorVisC.getText().equals("3")){
                            changeFloor3();
                        }
                        if(floorVisC.getText().equals("2")){
                            changeFloor2();
                        }
                        if(floorVisC.getText().equals("1")){
                            changeFloor1();
                        }
                        if(floorVisC.getText().equals("G")){
                            changeFloorG();
                        }
                        if(floorVisC.getText().equals("L1")){
                            changeFloorL1();
                        }
                        if(floorVisC.getText().equals("L2")){
                            changeFloorL2();
                        }});
                }
                else if(b==2){
                    floorVisD.setVisible(true);
                    floorVisD.setText(sequence.get(i));
                    floorVisD.setOnAction((event) -> {
                        if(floorVisD.getText().equals("3")){
                            changeFloor3();
                        }
                        if(floorVisD.getText().equals("2")){
                            changeFloor2();
                        }
                        if(floorVisD.getText().equals("1")){
                            changeFloor1();
                        }
                        if(floorVisD.getText().equals("G")){
                            changeFloorG();
                        }
                        if(floorVisD.getText().equals("L1")){
                            changeFloorL1();
                        }
                        if(floorVisD.getText().equals("L2")){
                            changeFloorL2();
                        }});
                }
                else if(b==3){
                    floorVisE.setVisible(true);
                    floorVisE.setText(sequence.get(i));
                    floorVisE.setOnAction((event) -> {
                        if(floorVisE.getText().equals("3")){
                            changeFloor3();
                        }
                        if(floorVisE.getText().equals("2")){
                            changeFloor2();
                        }
                        if(floorVisE.getText().equals("1")){
                            changeFloor1();
                        }
                        if(floorVisE.getText().equals("G")){
                            changeFloorG();
                        }
                        if(floorVisE.getText().equals("L1")){
                            changeFloorL1();
                        }
                        if(floorVisE.getText().equals("L2")){
                            changeFloorL2();
                        }});
                }
                else if(b==4){
                    floorVisF.setVisible(true);
                    floorVisF.setText(sequence.get(i));
                    floorVisF.setOnAction((event) -> {
                        if(floorVisF.getText().equals("3")){
                            changeFloor3();
                        }
                        if(floorVisF.getText().equals("2")){
                            changeFloor2();
                        }
                        if(floorVisF.getText().equals("1")){
                            changeFloor1();
                        }
                        if(floorVisF.getText().equals("G")){
                            changeFloorG();
                        }
                        if(floorVisF.getText().equals("L1")){
                            changeFloorL1();
                        }
                        if(floorVisF.getText().equals("L2")){
                            changeFloorL2();
                        }});
                }
                b++;
            }
            a = sequence.get(i);
        }

    }

    /**
     * Calls the necessary functions to store the path into data holder and which buttons to display
     * @param path A vector of nodes of the current path
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void MultiFloorPathDrawing(Vector<Node> path) throws IOException, InterruptedException {
        floorVisA.setVisible(false);
        floorVisB.setVisible(false);
        floorVisC.setVisible(false);
        floorVisD.setVisible(false);
        floorVisE.setVisible(false);
        floorVisF.setVisible(false);
        sequence.clear();
        data.directions.clear();
        String directions = directions(path);
        String[] directionParts = directions.split("<br>");


        LinkedList<Image> arrows = directionArrows(directionParts);
        ObservableList<javafx.scene.image.ImageView> images = FXCollections.observableArrayList();

        for(Image arrow : arrows){
            javafx.scene.image.ImageView directionPointers = new javafx.scene.image.ImageView();
            directionPointers.setImage(arrow);
            directionPointers.setFitHeight(25);
            directionPointers.setFitWidth(25);
            images.add(directionPointers);
        }

        for (int i = 0; i < directionParts.length; i++) {
            HBox entry = new HBox();
            Label label = new Label();
            label.setText(directionParts[i]);

            Label floorLabel = new Label();
            floorLabel.setText(sequence.get(i));
            floorLabel.setVisible(false);

            entry.getChildren().addAll(images.get(i), label, floorLabel);
            entry.setAlignment(Pos.CENTER_LEFT);

            data.directions.add(entry);
        }

        HBox entry = new HBox();
        String end = "You have arrived at your destination.";
        Label label = new Label();
        label.setText(end);

        floorSequence(sequence.get(sequence.size()-1));
        Label floorLabel = new Label();
        floorLabel.setText(sequence.get(sequence.size()-1));
        floorLabel.setVisible(false);

        Image endIMG = new Image(getClass().getResourceAsStream(filePath + "Finish.png"));
        javafx.scene.image.ImageView finish = new javafx.scene.image.ImageView();
        finish.setImage(endIMG);
        finish.setFitHeight(25);
        finish.setFitWidth(25);

        entry.getChildren().addAll(finish, label, floorLabel);
        data.directions.add(entry);
        Main.sendDirections();

        displayFloorButtons();

        // Possible floors (in order): L2, L1, 0G, 01, 02, 03
        Vector<Vector<Node>> paths = separator(path);

        floorsVisited.clear();
        for(Vector<Node> floorPath: paths){
            if (floorPath.size() > 0) {
                String pathFloor = floorPath.elementAt(0).getFloor().replaceAll("\\s+", "");
                if (pathFloor.equals("L2")) {
                    Data.data.pathL2 = floorPath;
                    floorsVisited.add("L2");
                } else if (pathFloor.equals("L1")) {
                    Data.data.pathL1 = floorPath;
                    floorsVisited.add("L1");
                } else if (pathFloor.equals("0G") || pathFloor.equals("G")) {
                    Data.data.pathG = floorPath;
                    floorsVisited.add("G");
                } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                    Data.data.pathFirst = floorPath;
                    floorsVisited.add("1");
                } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                    Data.data.pathSecond = floorPath;
                    floorsVisited.add("2");
                } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                    Data.data.pathThird = floorPath;
                    floorsVisited.add("3");
                }
            }
        }

        Vector<Node> startEnd = new Vector<Node>();
        startEnd.add(path.get(0));
        startEnd.add(path.get(path.size() - 1));
        data.startEndNodes = startEnd;
    }

    /**
     * Set the floor buttons for the current floor
     */
    public void setFloorButtons(){
        for (int i = 0; i < floorsVisited.size(); i++) {
            JFXButton currentButton = floorButtons.elementAt(i);
            currentButton.setVisible(true);
            String currentFloor = floorsVisited.elementAt(i);
            currentButton.setText(currentFloor);
            switch (currentFloor){
                case "L2":
                    currentButton.setOnAction((event) -> {
                        changeFloorL2();
                    });
                    break;
                case "L1":
                    currentButton.setOnAction((event) -> {
                        changeFloorL1();
                    });
                    break;
                case "G":
                    currentButton.setOnAction((event) -> {
                        changeFloorG();
                    });
                    break;
                case "1":
                    currentButton.setOnAction((event) -> {
                        changeFloor1();
                    });
                    break;
                case "2":
                    currentButton.setOnAction((event) -> {
                        changeFloor2();
                    });
                    break;
                case "3":
                    currentButton.setOnAction((event) -> {
                        changeFloor3();
                    });
                    break;
                default: break;
            }
        }
    }

    /**
     * Set the currently displayed map floor based on a string that is passed in
     * @param map String representing the floor to be changed to
     */
    public void setMap(String map) {
        if(tabPane.getSelectionModel().isSelected(1)){
            tabPane.getSelectionModel().select(2);
        } else {
            tabPane.getSelectionModel().select(1);
        }

        if(map.equals("L2")) {
            tabPane.getSelectionModel().select(5);
        } else if(map.equals("L1")) {
            tabPane.getSelectionModel().select(4);
        }else if(map.equals("G")) {
            tabPane.getSelectionModel().select(3);
        } else if(map.equals("01") || map.equals("1")) {
            tabPane.getSelectionModel().select(2);
        } else if(map.equals("02") || map.equals("2")) {
            tabPane.getSelectionModel().select(1);
        } else if(map.equals("03") || map.equals("3")){
            tabPane.getSelectionModel().select(0);
        }
    }


    /**
     * Draw the all the buttons of a current floor on the navigation screen
     * @param changeFloorNodes The vector of nodes that change to other floors for the current path
     * @param currentFloor String of the current floor that is being displayed
     */
    @FXML
    public void drawButtons(Vector<Node> changeFloorNodes, String currentFloor) {
        if(changeFloorNodes != null) {
            int length = changeFloorNodes.size();
            for (int i = 0; i < length; i += 2) {
                //System.out.println("This is the floor node: " + changeFloorNodes.get(i).getNodeID());
                //System.out.println("This is the other floor node: " + changeFloorNodes.get(i + 1).getNodeID());
                Node floor1Node = changeFloorNodes.get(i);
                Node floor2Node = changeFloorNodes.get(i);
                if(i + 1 < length) {
                    floor2Node = changeFloorNodes.get(i + 1);
                }
                int x = floor1Node.getxCoordinate();
                int y = floor1Node.getyCoordinate();
                //System.out.println("This is currentFloor: " + currentFloor + " This is the changefloor: " + floor1Node.getFloor());
                if (floor1Node.getFloor().trim().equals(currentFloor.trim())) {
                    //System.out.println("This happened");
                    Point2D point = convertFromImage(x,y);
                    createFloorChangeButton(point.getX(), point.getY(), floor2Node.getFloor());
                } else if (floor2Node.getFloor().trim().equals(currentFloor.trim())) {
                    //System.out.println("This happened too");
                    Point2D point = convertFromImage(floor2Node.getxCoordinate(), floor2Node.getyCoordinate());
                    createFloorChangeButton(point.getX(), point.getY(), floor1Node.getFloor());
                }
            }
        }
    }

    /**
     * Create a floor change button stored inside one big anchorpane in the navigation page controller
     * @param canvasX The canvas x location
     * @param canvasY The canvas y location
     * @param floorIn The floor that the node is going to
     */
    @FXML
    private void createFloorChangeButton(double canvasX, double canvasY, String floorIn) {
        ImageView floorIcon = new ImageView();

        int fromFloor = currentFloor();
        String floorTo = floorIn.trim();

        if(floorTo.equals("L2")) {
            floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevDownL2.png")));
        } else if (floorTo.equals("L1")) {
            if(fromFloor < 1){
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevUpL1.png")));
            } else {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevDownL2.png")));
            }
        } else if (floorTo.equals("G")) {
            if(fromFloor < 2) {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevUpG.png")));
            } else {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevDownG.png")));
            }
        } else if (floorTo.equals("1")) {
            if(fromFloor < 3) {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevUp1.png")));
            } else {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevDown1.png")));
            }
        } else if (floorTo.equals("2")) {
            if(fromFloor < 4) {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevUp2.png")));
            } else {
                floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevDown2.png")));
            }
        } else if (floorTo.equals("3")) {
            floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/elevUp3.png")));
        }

        //System.out.println("Printing a pane at: (" + canvasX + ", " + canvasY + ")");
        floorIcon.setFitHeight(20);
        floorIcon.setFitWidth(20);
        floorIcon.setX(canvasX - 10);
        floorIcon.setY(canvasY - 10);
        floorIcon.toFront();
        floorIcon.setPickOnBounds(true);
        floorIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println("TRYING TO CHANGE THE MAP TO: " + floorTo);
                setMap(floorTo.trim());
            }
        });
        buttonHolder.getChildren().add(floorIcon);
    }

    /**
     * @return int The current floor, from 0 = L2 to 5 = third floor
     */
    public int currentFloor() {
        int selected = tabPane.getSelectionModel().getSelectedIndex();
        switch (selected){
            case 5:
                return 0;
            case 4:
                return 1;
            case 3:
                return 2;
            case 2:
                return 3;
            case 1:
                return 4;
            case 0:
                return 5;
            default:
                return -1;
        }
    }

    /**
     * Clear all the current floor change buttons on the navigation page
     */
    public void clearButtons() {
        buttonHolder.getChildren().clear();
    }

    /**
     * Analyze the current path in order to determine where to put floor change buttons
     * @param path The current path drawn on the map
     * @return Vector<Node> Vector containing all the nodes which connect to another floor
     * for the current path
     */
    public Vector<Node> findFloorHyperLinks(Vector<Node> path) {
        Vector<Node> returnVector = new Vector<Node>();
        int length = path.size();
        String floor = path.get(0).getFloor();
        for(int i = 0; i < length; i++){
            Node n = path.get(i);
            if(!n.getFloor().equals(floor)) {
                if (i < length) {
                    //System.out.println("Added: " + path.get(i).getNodeID());
                    //System.out.println("Added: " + path.get(i-1).getNodeID());
                    returnVector.add(path.get(i-1));
                    returnVector.add(path.get(i));
                    floor = path.get(i).getFloor();
                }
            }
        }
        return returnVector;
    }

    /**
     * Draw the current path and animation, takes in a vector of nodes to draw the path and call the animation
     * @param path Current path of nodes
     */
    public void drawAnimation( Vector<Node> path) {
        if (path != null) {
            int length = path.size();
            Path animationPath = new Path();
            Vector<PathElement> holder = new Vector<>();
            int j = 0;
            int totalDistance = 0;
            for (int i = 0; i < length; i++) {
                Node node = path.get(i);

                if (i + 1 < length) {
                    Node node2 = path.get(i + 1);
                    System.out.println(node2.getNodeID());
                    if (!(node2.getNodeID().equals("BLANK")) && !(node.getNodeID().equals("BLANK"))) {
                        Point2D point = convertFromImage(node.getxCoordinate(), node.getyCoordinate());
                        Point2D point2 = convertFromImage(node2.getxCoordinate(), node2.getyCoordinate());
                        System.out.println("This is point x: " + point.getX() + ", " + point.getY());
                        MoveTo to = new MoveTo(point.getX(), point.getY());
                        LineTo toLine = new LineTo(point2.getX(), point2.getY());
                        int x1 = node.getxCoordinate();
                        int x2 = node2.getxCoordinate();
                        int y1 = node.getyCoordinate();
                        int y2 = node2.getyCoordinate();
                        totalDistance += Math.sqrt(((x2 * x2) - (x1*x1)) + ((y2 * y2) - (y1 * y1)));
                        holder.add(2 * j, to);
                        holder.add(2 * j + 1, toLine);
                        j++;
                    }
                }
            }
            int length2 = holder.size();
            PathElement[] pathEl = new PathElement[length2];
            for(int i = 0; i < length2; i++){
                pathEl[i] = holder.get(i);
            }

            animationPath.setStroke(javafx.scene.paint.Color.rgb(26,71,154));
            animationPath.setStrokeWidth(3);
            animationPath.getElements().addAll(pathEl);

            javafx.scene.shape.Circle rectangle = new Circle(3, 0,3);
            rectangle.setFill(javafx.scene.paint.Color.LIGHTBLUE);
            PathTransition animation = new PathTransition();
            animation.setNode(rectangle);
            animation.setPath(animationPath);
            animation.setInterpolator(Interpolator.LINEAR);
            animation.setDuration(new Duration(5000));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            data.animation = animationPane;
            animationPane.getChildren().addAll(animationPath,rectangle);
        }
    }

    /**
     * Clear all the currently displayed animations
     */
    public void clearAnimations() {
        animationPane.getChildren().clear();
    }

    /**
     * Convert from the map image (5000x3400) to the anchorpane drawing/ canvas pathcanvas
     * All the constants and window sizes are stored inside data holder
     * @param x X coordinate to be converted
     * @param y Y coordinate to be converted
     * @return A point representing the two converted values as a point
     */
    public Point2D convertFromImage(double x, double y){
        updateImageCoordinates();
        updateCanvasCoordinates();
        //System.out.println("This is the image view: " + data.imageViewX + ", " + data.imageViewY);
        //System.out.println("This is the map image: " + data.MapX + ", " + data.MapY);
        //System.out.println("This is the input point: " + x + ", " + y);
        double returnX = x / ((data.imageViewX / data.canvasX) * (data.MapX/data.imageViewX));
        double returnY = y / ((data.imageViewY / data.canvasY) * (data.MapY/data.imageViewY));
        //System.out.println("This is the point: " + returnX + " ," + returnY);
        return new Point2D(returnX,returnY);
    }

    /**
     * Convert an input point from the anchorpane / canvas to the image (5000x3400)
     * @param x Input from anchorpane or canvas
     * @param y Input from anchorpane or canvas
     * @return return a point representing the converted point
     */
    public Point2D convertToImage(double x, double y){
        updateImageCoordinates();
        updateCanvasCoordinates();
        //System.out.println("This is the image view: " + data.canvasX + ", " + data.canvasY);
        //System.out.println("This is the map image: " + data.MapX + ", " + data.MapY);
        //System.out.println("This is the input point: " + x + ", " + y);
        double returnX = x * ((data.imageViewX / data.canvasX) * (data.MapX/data.imageViewX));
        double returnY = (y) * ((data.imageViewY / data.canvasY) * (data.MapY/data.imageViewY));
        //System.out.println("This is the point: " + returnX + " ," + returnY);
        return new Point2D(returnX,returnY);
    }

    /**
     * Update the stored sizes for the image view object
     */
    public void updateImageCoordinates() {
        data.imageViewX = map.getLayoutBounds().getWidth();
        data.imageViewY = map.getLayoutBounds().getHeight();
    }

    /**
     * Update the stored sizes for the pathcanvs object
     */
    public void updateCanvasCoordinates() {
        data.canvasX = pathCanvas.getWidth();
        data.canvasY = pathCanvas.getHeight();
    }

    /**
     * This draws start and end icons if it is the right floor
     */
    public void drawStartFinish() {
        //System.out.println("Drawn the start or finish");
        int fromFloor = currentFloor();
        //System.out.println("This is the current floor int: " + currentFloor());
        if(data.startEndNodes != null) {
            Point2D point = convertFromImage(data.startEndNodes.get(0).getxCoordinate(), data.startEndNodes.get(0).getyCoordinate());
            if (data.startEndNodes.get(0).getFloor().trim().equals("L2")) {
                if (fromFloor == 0) {
                    drawStartIcon(point.getX(), point.getY());
                }
            } else if (data.startEndNodes.get(0).getFloor().trim().equals("L1")) {
                if (fromFloor == 1) {
                    drawStartIcon(point.getX(), point.getY());
                }
            } else if (data.startEndNodes.get(0).getFloor().trim().equals("G")) {
                if (fromFloor == 2) {
                    drawStartIcon(point.getX(), point.getY());
                }
            } else if (data.startEndNodes.get(0).getFloor().trim().equals("1")) {
                if (fromFloor == 3) {
                    drawStartIcon(point.getX(), point.getY());
                }
            } else if (data.startEndNodes.get(0).getFloor().trim().equals("2")) {
                if (fromFloor == 4) {
                    drawStartIcon(point.getX(), point.getY());
                }
            } else if (data.startEndNodes.get(0).getFloor().trim().equals("3")) {
                if (fromFloor == 5) {
                    drawStartIcon(point.getX(), point.getY());
                }
            }
        }

        if (data.startEndNodes != null) {
            Point2D point2 = convertFromImage(data.startEndNodes.get(1).getxCoordinate(),data.startEndNodes.get(1).getyCoordinate());
            if (data.startEndNodes.get(1).getFloor().trim().equals("L2")) {
                if (fromFloor == 0) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            } else if (data.startEndNodes.get(1).getFloor().trim().equals("L1")) {
                if (fromFloor == 1) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            } else if (data.startEndNodes.get(1).getFloor().trim().equals("G")) {
                if (fromFloor == 2) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            } else if (data.startEndNodes.get(1).getFloor().trim().equals("1")) {
                if (fromFloor == 3) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            } else if (data.startEndNodes.get(1).getFloor().trim().equals("2")) {
                if (fromFloor == 4) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            } else if (data.startEndNodes.get(1).getFloor().trim().equals("3")) {
                if (fromFloor == 5) {
                    drawFinishIcon(point2.getX(), point2.getY());
                }
            }
        }
    }

    /**
     * Draw a start icon ("you are here") onto the anchorpane buttonholder
     * @param canvasX the x position of the image
     * @param canvasY the y position of the image
     */
    public void drawStartIcon(double canvasX, double canvasY) {
        ImageView floorIcon = new ImageView();
        floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/you-are-here-icon.png")));
        //System.out.println("Printing a starting pane at: (" + canvasX + ", " + canvasY + ")");
        floorIcon.setFitHeight(20);
        floorIcon.setFitWidth(20);
        floorIcon.setX(canvasX - 10);
        floorIcon.setY(canvasY - 20);
        floorIcon.toFront();
        buttonHolder.getChildren().add(floorIcon);
        data.button = buttonHolder;
    }

    /**
     * Draw a destination icon onto the anchorpane button holder
     * @param canvasX the x position of the image
     * @param canvasY the y position of the image
     */
    public void drawFinishIcon(double canvasX, double canvasY) {
        ImageView floorIcon = new ImageView();
        floorIcon.setImage(new Image(getClass().getResourceAsStream("/sample/UI/Icons/Finish.png")));
        floorIcon.setFitHeight(20);
        floorIcon.setFitWidth(20);
        floorIcon.setX(canvasX);
        floorIcon.setY(canvasY - 20);
        floorIcon.toFront();
        buttonHolder.getChildren().add(floorIcon);
        data.button = buttonHolder;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Zooming Panning & Dragging functions

    /**
     * Zoom and pan the screen based on listeners for user input events, obtains all user input mouse information
     */
    @FXML
    public void zoom() {
        scrollMap.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                stackPane.setMinSize(newValue.getWidth(),newValue.getHeight());
            }
        });

        scrollContent.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                double scaleFactor = (event.getDeltaY() > 0) ? 1.03 : 1/1.03;
                Point2D scrollOffset = figureScrollOffset(scrollContent,scrollMap);
                if (!(scaleFactor * stackPane.getScaleX() < 1.0)) {
                    stackPane.setScaleX(stackPane.getScaleX() * scaleFactor);
                    stackPane.setScaleY(stackPane.getScaleY() * scaleFactor);
                } else {
                    System.out.println("This happened");
                }
                repositionScroller(scrollContent, scrollMap, scaleFactor, scrollOffset);
            }
        });

        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
        buttonHolder.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
                double divisionCst = 4.15;
                int offset = 2;

                double newX1 = (event.getX());
                double newY1 = (event.getY());

//                Data.data.gc.strokeOval(newX1, newY1, 7.0, 7.0);
//                Data.data.gc.fillOval(newX1, newY1, 7.0, 7.0);

                if (floorLowerTwo.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX() , calcPoint.getY(), Data.data.lowerLevel02FloorNodes);
                } else if (floorLowerOne.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX(), calcPoint.getY(), Data.data.lowerLevel01FloorNodes);
                } else if (floorGround.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX(), calcPoint.getY(), Data.data.groundFloorNodes);
                } else if (floorOne.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX(), calcPoint.getY(), Data.data.firstFloorNodes);
                } else if (floorTwo.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX(), calcPoint.getY(), Data.data.secondFloorNodes);
                } else if (floorThree.isSelected()) {
                    Point2D calcPoint = convertToImage(newX1, newY1);
                    selectedNode = mousePosition(calcPoint.getX(), calcPoint.getY(), Data.data.thirdFloorNodes);
                }

                if(event.getClickCount() == 2) {
                    Point2D calcPoint = convertToImage(selectedNode.getxCoordinate(),selectedNode.getyCoordinate());
                    Data.data.gc.strokeOval(calcPoint.getX(), calcPoint.getY(), 7.0, 7.0);
                    Data.data.gc.fillOval(calcPoint.getX(), calcPoint.getY(), 7.0, 7.0);
                    //System.out.println("This is the selected node: " + selectedNode.getNodeID());

                    if (points.getSelectedToggle() == start) {
                        startLabel.setText(selectedNode.getLongName().trim());
                        data.kiosk = selectedNode;
                    }
                    else if(points.getSelectedToggle() == end){
                        endLabel.setText(selectedNode.getLongName().trim());
                        data.destinationNode = selectedNode;
                        try {
                            findPath();
                        } catch (IOException e) {
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        });

        scrollMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getX() - lastMouseCoordinates.get().getX();
                double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollMap.getViewportBounds().getWidth();
                double deltaH = deltaX * ((scrollMap.getHmax() - scrollMap.getHmin()) / extraWidth);
                double desiredH = scrollMap.getHvalue() - deltaH;
                if(deltaX > 0) {
                    scrollMap.setHvalue(Math.max(0, Math.min(scrollMap.getHmax(), desiredH)));
                }
                double deltaY = event.getY() - lastMouseCoordinates.get().getY();
                double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollMap.getViewportBounds().getHeight();
                double deltaV = deltaY * ((scrollMap.getHmax() - scrollMap.getHmin()) / extraHeight);
                double desiredV = scrollMap.getVvalue() - deltaV;
                if ( deltaY > 0) {
                    scrollMap.setVvalue(Math.max(0, Math.min(scrollMap.getVmax(), desiredV)));
                }
            }
        });
    }

    /**
     * Set the navigation page controller in a certain zoom or location for clarity
     */
    @FXML
    public void startZoom() {
        scrollMap.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                stackPane.setMinSize(newValue.getWidth(),newValue.getHeight());
            }
        });

        double scaleFactor = (2 > 0) ? 2.03 : 1/1.03;
        Point2D scrollOffset = figureScrollOffset(scrollContent,scrollMap);
        if (!(scaleFactor * stackPane.getScaleX() < 1)) {
            stackPane.setScaleX(stackPane.getScaleX() * scaleFactor);
            stackPane.setScaleY(stackPane.getScaleY() * scaleFactor);
        }
        repositionScroller(scrollContent, scrollMap, scaleFactor, scrollOffset);


        double deltaX = data.kiosk.getxCoordinate() - 3000;
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scrollMap.getViewportBounds().getWidth();
        double deltaH = deltaX * ((scrollMap.getHmax() - scrollMap.getHmin()) / extraWidth);
        double desiredH = scrollMap.getHvalue() - deltaH;
        scrollMap.setHvalue(Math.max(0, Math.min(scrollMap.getHmax(), desiredH)));
        double deltaY = data.kiosk.getyCoordinate() - 1250;
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scrollMap.getViewportBounds().getHeight();
        double deltaV = deltaY * ((scrollMap.getHmax() - scrollMap.getHmin()) / extraHeight);
        double desiredV = scrollMap.getVvalue() - deltaV;
        scrollMap.setVvalue(Math.max(0, Math.min(scrollMap.getVmax(), desiredV)));

    }

    /**
     * Calculate the scroll ofsett after a zoom
     * @param scrollContent FXML scroll content group
     * @param scroller Scrollpane which holds the scroll content
     * @return A point representing the scroll offset
     */
    private Point2D figureScrollOffset(Group scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    /**
     * Reposition the scrollbars of the scroll window
     * @param scrollContent FXML Scroll content group
     * @param scroller FXML Scroll pane holding the scroll content
     * @param scaleFactor FXML Scale factor
     * @param scrollOffset The offset point as calculated by figure scroll offset
     */
    private void repositionScroller(Group scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2 ;
            double newScrollXOffset = (scaleFactor - 1) *  halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2 ;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Functions

    // Purpose: Method to clear the path on the map when the user presses clear map

    /**
     * Clear the current stored items for the path and buttons, clear the canvas
     * @throws FileNotFoundException
     */
    @FXML
    public void clear() throws FileNotFoundException{
        Data.data.gc.clearRect(0,0,pathCanvas.getWidth(),pathCanvas.getHeight());
        Data.data.pathThird = null;
        Data.data.pathSecond = null;
        Data.data.pathFirst = null;
        Data.data.pathL1 = null;
        Data.data.pathL2 = null;
        Data.data.pathG = null;

        for(int i = 0; i < Data.data.floorList.size() ; i++){
            Data.data.floorList.set(i,false);
        }
        int length = animationPane.getChildren().size();
        animationPane.getChildren().remove(0,length);
        data.buttonNodes = null;
        clearButtons();
    }

    /**
     * Emergency button for showing the nearest exit
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void emergencyButton() throws IOException, InterruptedException{
        destination.setText("Exit");
        settingFields();
    }

    /**
     * Logout the program
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void logout() throws IOException, InterruptedException{
        AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
        SettingSingleton.getSettingSingleton().setAuthProperty(clearAuth);
        Image loginPNG = new Image(getClass().getResourceAsStream("/sample/UI/Icons/user-login-icon-14.png"));
        ImageView loginIMG = new ImageView(loginPNG);
        loginIMG.setFitHeight(25);
        loginIMG.setFitWidth(25);
        loginButton.setGraphic(loginIMG);
        Main.logOutUser();
        Main.mapScreen();
        clearFields();
        clear();
    }

    /**
     * Show the map edit screen
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void edit() throws IOException, InterruptedException{
        Main.mapEditScreen();
        clearFields();
        clear();
    }

    @FXML
    public void serviceRequest() {Main.serviceScreen();}
    @FXML
    public void acceptRequest() {Main.acceptScreen();}

    @FXML
    public void editUsers(){Main.editUsersScreen();}

    @FXML
    public void about(){Main.aboutWindow(aboutButton);}

    /**
     * Use the chat API when the help button is pressed
     */
   @FXML
    public void chat(){
        //Main.setHelpScreenServiceRequestScreen();
        try{
           /* messenger.API m = new messenger.API();
            m.run(6,6,600,600,
                    "/src/UI/style.css", "test", "test", "sip:HELP@130.215.213.204:6969");
       */ } catch (Exception e){
            System.out.println("API ERROR: " + e.getLocalizedMessage());
        }
    }

    // Purpose: Send an email when user clicks send email button
    @FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", map);
        emailService.sendEmail(NavigationPageController.directions(Data.data.path), email.getText());
    }

    /**
     * Use the searchbar and the search engine to predict user searches
     */
    @FXML
    public void autoComplete(){
        searchList.setVisible(true);
        ObservableList<String> filteredEntries = FXCollections.observableArrayList("empty");
        //filtering
        destination.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                LinkedList<String> searchResult = new LinkedList<>();
                //Check if the entered Text is part of some entry
                String text = destination.getText();
                Pattern pattern;
                pattern = Pattern.compile(".*" + text + ".*",
                        Pattern.CASE_INSENSITIVE);

                for (String entry : allEntries) {
                    Matcher matcher = pattern.matcher(entry);
                    if (matcher.matches()) {
                        searchResult.add(entry);
                    }
                }

                if (allEntries.size() > 0) {
                    filteredEntries.clear();
                    filteredEntries.addAll(searchResult);
                }
                searchList.setItems(filteredEntries);
            }
        });

        searchList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
                try {
                    settingFields();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Reverse the nodes to path find
     * @throws IOException
     * @throws InterruptedException
     */
    public void reverseNodes() throws IOException, InterruptedException {
        Vector<Node> currentPath = this.path;
        Vector<Node> reversePath = new Vector<Node>();
        String begin = startLabel.getText();
        String dest = endLabel.getText();
        int z = 0;
        for(int i = currentPath.size(); i > 0; i--){
            reversePath.add(currentPath.get(i-1));
        }
        startLabel.setText(dest);
        endLabel.setText(begin);
        this.path = reversePath;
        MultiFloorPathDrawing(this.path);
    }

    /**
     * Draw the necessary FXML Components
     */
    @FXML
    public void initDrawer(){
        try{
            FXMLLoader menuLoader = new FXMLLoader();
            menuLoader.setLocation(getClass().getResource("/sample/UI/mainMenuDrawer.fxml"));
            VBox menuBox = menuLoader.load();


            mainMenu.setSidePane(menuBox);
            if(mainMenu.visibleProperty().get()){
                mainMenu.setVisible(false);
                //destination.setVisible(true);
                //search.setVisible(true);
            }else{
                mainMenu.setVisible(true);
                //destination.setVisible(false);
                //search.setVisible(false);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function to calculate the nearest node depend on the mouse click location.
     * @param x  X position of the mouse click
     * @param y Y Position of the mouse click
     * @param NodesOfTheFloor Nodes of the currenly displayed floor
     * @return Node closest to the given x and y point
     */
    public Node mousePosition (double x, double y,Vector<Node> NodesOfTheFloor) {
        double newMouseX = x;
        double newMouseY = y;
        Node GoodOne = null;
        double MinDist = 100000000.0;
        double dist;

        for (Node i: NodesOfTheFloor) {
            dist = data.graph.MouseNodeDist(newMouseX,newMouseY,i);
            if(dist < MinDist) {
                MinDist = dist;
                GoodOne = i;
            }
        }

        for(Node n: data.graph.getNodes()){
            if(n.getNodeID().trim().equals(GoodOne.getNodeID().trim())){
                return n;
            }
        }
        return null;
    }


    private void update(){
        FXMLLoader menuLoader = new FXMLLoader();
        menuLoader.setLocation(getClass().getResource("/sample/UI/mainMenuDrawer.fxml"));
        VBox menuBox = null;
        try {
            menuBox = menuLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(mainMenu!=null){
            mainMenu.setSidePane(menuBox);
        }

    }


    @FXML
    public void insurance(){
        //HealthCareRun health = new HealthCareRun();
        int i;
        try {
            //health.run(0,0,600,350,"view/stylesheets/default.css","","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void runVoice(){

    }

    public void transportRequest(){
        //APIApp api = new APIApp();
        try{
            //APIApp.run(300, 400,600,500,"","","");

        } catch (Exception e){
            e.printStackTrace();
        }

    }



   @FXML
   public void AutoZoom(Vector<Node> path){
        /*if(path==null){
            return;
        }

       Vector<Double> ZoomArea = AutoZoom.AutoZoom(path,200);


       double Xmove = ZoomArea.get(0)+1000;
       double Ymove = ZoomArea.get(1)+1000;
       double scale = ZoomArea.get(2);


       System.out.println(scale+"=================================================");
       System.out.println(Xmove+"=================================================");
       System.out.println(Ymove+"=================================================");

       System.out.println(stackPane.getHeight()+"    <==============HEIGHT");
       System.out.println(stackPane.getWidth() +"    <==============WIDTH");


       stackPane.setScaleX(stackPane.getScaleX() * scale);
       stackPane.setScaleY(stackPane.getScaleY() * scale);



       // ----------------- WORKS UNTIL HERE ALMOST ----------------------//

       //I DONT KNOW HOW TO PANE TO THE NEW LOCATIONS Xmove - Ymove
       stackPane.setLayoutX(Xmove);
       stackPane.setLayoutY(Ymove);
*/

   }


}