package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;

import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class NavigationPageController implements Initializable, Data{

    //FXML UI Components

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
    private AnchorPane mainPane;
    @FXML
    private JFXTabPane tabPane;

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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization and Start

    //Purpose: Initialize all the UI components
    @Override
    public void initialize(URL location, ResourceBundle resources){
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
///make a thing
/*                adminBox.setVisible(true);
                loginButton.setText("Log Out");
            } else {
                adminBox.setVisible(false);
                loginButton.setText("Log In");
            }
        });*/

                createServButton.setVisible(true);
                editMapButton.setVisible(true);
                editUsersButton.setVisible(true);
                existServButton.setVisible(true);

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
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setMainController(Main in){
        mainController = in;
    }

    public JFXListView<HBox> getDirectionSteps(){
        return this.directionSteps;
    }

    public Vector<String> getFloorsVisited(){
        return  this.floorsVisited;
    }

    public int getCurrentAlgo(){
        return this.currentAlgo;
    }

    public void setKiosk(Node k){
        data.kiosk = k;
    }

    public void setSearch(String s){
        this.destination.setText(s);
    }

    public void autoClose(){
        searchList.setVisible(false);
    }

    public JFXTextField getDestination(){return this.destination;}

    public Label getStartLabel(){return this.startLabel;}

    public Label getEndLabel(){return this.endLabel;}

    public JFXRadioButton getRadioStart(){return this.start;}

    public void setDestination(String s){destination.setText(s);}

    public JFXRadioButton getRadioEnd(){return this.end;}

    public void setCurrentAlgo(int current){
        this.currentAlgo =  current;
    }

    public VBox getAdminBox(){
        return this.adminBox;
    }

    public JFXButton getLoginButton() {
        return this.loginButton;
    }

    @FXML
    public void settingSearch(){
        if (points.getSelectedToggle() == start) {

            destination.setText(startLabel.getText());

        }
        else{
            destination.setText(endLabel.getText());
        }
    }

    @FXML
    public void setStart(String t){
        startLabel.setText(t);
    }

    public Node getKiosk(){
        return data.kiosk;
    }

    //setting start and end nodes
    @FXML
    public void settingFields() throws IOException, InterruptedException {
        /*searchList.setVisible(false);
        oneArrow.setVisible(false);
        twoArrow.setVisible(false);
        threeArrow.setVisible(false);
        groundArrow.setVisible(false);
        lowerOneArrow.setVisible(false);
        lowerTwoArrow.setVisible(false);*/

        Node currNode = SearchEngine.SearchClosestNode(destination.getText().trim());
        data.destinationNode = currNode;
/*
        if (points.getSelectedToggle() == start) {

            //System.out.println("LABEL!!!!!");

            data.kiosk = currNode;
            destination.setText(startLabel.getText().trim());
        }
        else if(points.getSelectedToggle() == end){

            //System.out.println("LABEL!!!!!");
            endLabel.setText(currNode.getLongName().trim());
            data.destinationNode = currNode;
            destination.setText(endLabel.getText().trim());
        }
*/
            go();

    }

    //sets invalid email label when necessary for errorhandling
    @FXML
    public static void setInvalidEmail(){
        invalidEmailText.setVisible(true);
    }

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
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L1Floor);
        testDrawDirections(Data.data.pathL1);
        Data.data.currentMap = "L1";
        hierarchicalText("L1");
    }

    @FXML
    public void changeFloorL2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L2Floor);
        testDrawDirections(Data.data.pathL2);
        Data.data.currentMap = "L2";
        hierarchicalText("L2");
    }

    @FXML
    public void changeFloor1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.firstFloor);
        testDrawDirections(Data.data.pathFirst);
        Data.data.currentMap = "1";
        hierarchicalText("1");
    }

    @FXML
    public void changeFloor2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        if(Data.data.gc != null){
            Data.data.gc.clearRect(0, 0, x, y);
        }
        map.setImage(Data.data.secondFloor);
        testDrawDirections(Data.data.pathSecond);
        Data.data.currentMap = "2";
        hierarchicalText("2");
    }

    @FXML
    public void changeFloor3() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        if(Data.data.gc != null) {
            Data.data.gc.clearRect(0, 0, x, y);
        }
        map.setImage(Data.data.thirdFloor);
        testDrawDirections(Data.data.pathThird);
        Data.data.currentMap = "3";
        hierarchicalText("3");
    }

    @FXML
    public void changeFloorG() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,1000,1000);
        map.setImage(Data.data.GFloor);
        testDrawDirections(Data.data.pathG);
        Data.data.currentMap = "G";
        hierarchicalText("G");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Screen Functions
    @FXML
    public void login() throws IOException{
        Main.loginScreen(loginButton);
        clearFields();
        clear();
    }

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    // Button to return to the welcome screen
    @FXML
    public void back() throws IOException{
        Main.startScreen();
        clearFields();
        clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Finding Functions

    public void findPath() throws IOException, InterruptedException {

        this.path = SearchEngine.NodeToNode(data.destinationNode,currentAlgo);

        MultiFloorPathDrawing(this.path);
/*
        directionSteps.setVisible(true);
        sendLabel.setVisible(true);
        email.setVisible(true);
        sendButton.setVisible(true);*/
        int length = path.size();
        String lastFloor = path.get(length - 1).getFloor();
        setMap(lastFloor);
    }



    @FXML
    public void clearFields(){
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();
        /*sendLabel.setVisible(false);
        email.setVisible(false);
        sendButton.setVisible(false);
        directionSteps.setVisible(false);
        endLabel.setText("");
        startLabel.setText("Lower Pike Hallway Exit Lobby");
        destination.setText("");
        directionSteps.getItems().clear();
        reset(map, width, height);*/
    }

    @FXML
    public void go() throws IOException,InterruptedException{
        clear();
        findPath();
        /*
        SettingSingleton.getSettingSingleton().getauthPropertyProperty().addListener((ObservableValue<? extends AuthenticationInfo> a, AuthenticationInfo before, AuthenticationInfo after) -> {
            if(after.getPriv().equals(AuthenticationInfo.Privilege.ADMIN)){
                sendLabel.setVisible(false);
                email.setVisible(false);
                sendButton.setVisible(false);
            }
            else{
                sendLabel.setVisible(true);
                email.setVisible(true);
                sendButton.setVisible(true);
            }
        });
        //setArrows(floorsVisited);
        searchList.setVisible(false);
        directionSteps.setVisible(true);*/
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Drawing and Directions functions

    // Purpose: Print out directions for a path of nodes
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

    Image startIMG = new Image(getClass().getResourceAsStream(filePath + "Start.png"));
    Image straightIMG = new Image(getClass().getResourceAsStream(filePath + "Straight.png"));
    Image rightIMG = new Image(getClass().getResourceAsStream(filePath + "RightTurn.png"));
    Image leftIMG = new Image(getClass().getResourceAsStream(filePath + "LeftTurn.png"));
    Image sharpRightIMG = new Image(getClass().getResourceAsStream(filePath + "SharpRight.png"));
    Image sharpLeftIMG = new Image(getClass().getResourceAsStream(filePath + "SharpLeft.png"));
    Image elevIMG = new Image(getClass().getResourceAsStream(filePath + "Elevator.png"));
    Image stairIMG = new Image(getClass().getResourceAsStream(filePath + "Stairs.png"));

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

    // Purpose: Insert a path of nodes that are only on ONE floor, draws the path on that floor
    @FXML
    public void MultiFloorPathDrawing(Vector<Node> path) throws IOException, InterruptedException {
        floorVisA.setVisible(false);
        floorVisB.setVisible(false);
        floorVisC.setVisible(false);
        floorVisD.setVisible(false);
        floorVisE.setVisible(false);
        floorVisF.setVisible(false);
        ///HERE////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sequence.clear();
        data.directions.clear();
        //ObservableList<HBox> populateSteps = FXCollections.observableArrayList();
        //edit later
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
        setMap("1");
    }

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

    public void setMap(String map) {
        map.replaceAll("\\s+","");
        if(map.equals("L2")) {
            changeFloorL2();
        } else if(map.equals("L1")) {
            changeFloorL1();
        }else if(map.equals("G")) {
            changeFloorG();
        } else if(map.equals("01") || map.equals("1")) {
            changeFloor1();
        } else if(map.equals("02") || map.equals("2")) {
            changeFloor2();
        } else if(map.equals("03") || map.equals("3")){
            changeFloor3();
        }
    }
    // Purpose: Draw a path of nodes on the map
    @FXML
    public void testDrawDirections(Vector<Node> path) {
        if(path != null) {
            int length = path.size();
            String nameDest = path.get(length - 1).getShortName();
            String nameDept = path.get(0).getShortName();
            // Setting up the proper color settings
            Data.data.gc.setLineWidth(3);
            Data.data.gc.setStroke(javafx.scene.paint.Color.rgb(26,71,154));
            Data.data.gc.stroke();
            // Iterate through all the path nodes to draw the path
            for (int i = 0; i < length; i++) {
                Node node = path.get(i);
                //System.out.println("This is node: " + node.getNodeID());
                if (i + 1 < length) {
                    Node node2 = path.get(i + 1);
                    //System.out.println("This is node + 1: " + node2.getNodeID() + "\n\n");
                    // Lines are drawn offset,
                    if (!(node2.getNodeID().equals("BLANK")) && !(node.getNodeID().equals("BLANK"))) {
                        Point2D calcPoint = convertFromImage(node.getxCoordinate(),node.getyCoordinate());
                        Point2D calcPoint2 = convertFromImage(node2.getxCoordinate(), node2.getyCoordinate());
                        Data.data.gc.strokeLine(calcPoint.getX(), calcPoint.getY(), calcPoint2.getX(), calcPoint2.getY());
                    }
                }
            }
            String floor = path.get(0).getFloor().replaceAll("\\s+","");
        }
    }

    //Purpose: This draws all the nodes and edges currently in the database
    //Used for debugging and admin
    /*@FXML
    public void drawAll() throws IOException{
        BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/UI/Icons/01_thefirstfloor.png"));
        Graphics2D pathImage = firstFloor.createGraphics();
        Vector<Edge> edges = testEmbeddedDB.getAllEdges();
        Vector<Node> nodes = testEmbeddedDB.getAllNodes();
        int edgeLength = edges.size();
        int nodeLength = nodes.size();
        pathImage.setStroke(new BasicStroke(10)); // Controlling the width of the shapes drawn
        for(int i = 0; i < edgeLength; i++ ) {
            Node nodeStart = edges.get(i).getStart();
            System.out.println("Start: " + nodeStart.getShortName());
            Node nodeEnd = edges.get(i).getEnd();
            System.out.println("Stop: " + nodeEnd.getShortName());
            pathImage.setColor( new java.awt.Color(0,0,0)); // This color is black
            pathImage.drawLine(nodeStart.getxCoordinate(), nodeStart.getyCoordinate(),nodeEnd.getxCoordinate() ,nodeEnd.getyCoordinate());
        }
        for(int i = 0; i < nodeLength; i++){
            Node node = nodes.get(i);
            pathImage.setColor( new java.awt.Color(236,4,4)); // This color is black
            pathImage.drawOval(node.getxCoordinate() - 10,node.getyCoordinate() - 10,15,15);
            pathImage.fillOval(node.getxCoordinate() - 10,node.getyCoordinate() - 10,15,15);
        }
        map.setImage(SwingFXUtils.toFXImage(firstFloor,null));
    } */

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

    public Point2D convertToImage(double x, double y){
        updateImageCoordinates();
        updateCanvasCoordinates();
        System.out.println("This is the image view: " + data.canvasX + ", " + data.canvasY);
        System.out.println("This is the map image: " + data.MapX + ", " + data.MapY);
        System.out.println("This is the input point: " + x + ", " + y);
        double returnX = x * ((data.imageViewX / data.canvasX) * (data.MapX/data.imageViewX));
        double returnY = (y) * ((data.imageViewY / data.canvasY) * (data.MapY/data.imageViewY));
        System.out.println("This is the point: " + returnX + " ," + returnY);
        return new Point2D(returnX,returnY);
    }

    public Point2D convertToCanvas(double x, double y) {
        double returnX = x * (data.MapX / data.canvasX);
        double returnY = y * (data.MapX / data.canvasY);
        //System.out.println("This is the point: " + returnX + " ," + returnY);
        return new Point2D(returnX,returnY);
    }

    public Point2D convertFromCanvas(double x, double y) {
        double returnX = x / (data.MapX / data.canvasX) ;
        double returnY = y / (data.MapX / data.canvasY);
        //System.out.println("This is the point: " + returnX + " ," + returnY);
        return new Point2D(returnX,returnY);
    }

    public void updateImageCoordinates() {
        data.imageViewX = map.getLayoutBounds().getWidth();
        data.imageViewY = map.getLayoutBounds().getHeight();
    }

    public void updateCanvasCoordinates() {
        data.canvasX = pathCanvas.getWidth();
        data.canvasY = pathCanvas.getHeight();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Zooming Panning & Dragging functions

    // Purpose: Zoom the map when the user zooms
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
                if (!(scaleFactor * stackPane.getScaleX() < 1)) {
                    stackPane.setScaleX(stackPane.getScaleX() * scaleFactor);
                    stackPane.setScaleY(stackPane.getScaleY() * scaleFactor);
                }
                repositionScroller(scrollContent, scrollMap, scaleFactor, scrollOffset);
            }
        });

        final ObjectProperty<Point2D> lastMouseCoordinates = new SimpleObjectProperty<Point2D>();
        pathCanvas.setOnMousePressed(new EventHandler<MouseEvent>() {
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
                    System.out.println("This is the selected node: " + selectedNode.getNodeID());

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

    private Point2D figureScrollOffset(Group scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

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

    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    private void resetStack(ScrollPane pane, double width, double height) {
        pane.setViewportBounds(new BoundingBox(0,0,width,height));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void shiftMap(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private void shiftStack(ScrollPane pane, Point2D delta) {
        Bounds viewport = pane.getViewportBounds();

        double width = pathCanvas.getWidth() ;
        double height = pathCanvas.getHeight();

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        pane.setViewportBounds(new BoundingBox(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();
        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Functions

    // Purpose: Method to clear the path on the map when the user presses clear map
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
    }

    @FXML
    public void emergencyButton() throws IOException, InterruptedException{
        destination.setText("Exit");
        settingFields();
    }

    @FXML
    public void logout() throws IOException, InterruptedException{
        AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
        SettingSingleton.getSettingSingleton().setAuthProperty(clearAuth);
        Main.logOutUser();
        Main.mapScreen();
        clearFields();
        clear();
    }

    //  EDIT LATER
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

   @FXML
    public void chat(){
/*        //Main.setHelpScreenServiceRequestScreen();
        try{
            messenger.API m = new messenger.API();
            m.run(6,6,600,600,
                    "/src/UI/style.css", "test", "test", "sip:HELP@130.215.213.204:6969");
        } catch (Exception e){
            System.out.println("API ERROR: " + e.getLocalizedMessage());
        }*/
    }

    @FXML
    public void setAlgorithm(){}


    // Purpose: Send an email when user clicks send email button
    @FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", map);
        emailService.sendEmail(NavigationPageController.directions(Data.data.path), email.getText());
    }

    // Purpose: Find the proper destination when a user types in the search bar
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

    @FXML
    public void initDrawer(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/UI/mainMenuDrawer.fxml"));
            VBox menuBox = loader.load();
            mainMenu.setSidePane(menuBox);
            if(mainMenu.visibleProperty().get()){
                mainMenu.setVisible(false);
                destination.setVisible(true);
                search.setVisible(true);
            }else{
                mainMenu.setVisible(true);
                destination.setVisible(false);
                search.setVisible(false);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function to calculate the nearest node depend on the mouse click location.
     * @param x
     * @param y
     * @param NodesOfTheFloor
     * @return
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

}