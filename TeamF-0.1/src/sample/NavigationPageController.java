package sample;

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
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.sound.sampled.Line;

import java.awt.*;
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
    private JFXButton sendButton;
    @FXML
    private javafx.scene.canvas.Canvas pathCanvas;
    @FXML
    private JFXListView directionSteps;
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
    private VBox labelBox;

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
    private JFXButton loginButton;

    @FXML
    private ImageView threeArrow, twoArrow, oneArrow, lowerOneArrow, lowerTwoArrow, groundArrow;

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
    private Vector<String> floorsVisited = new Vector<>();


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization and Start

    //Purpose: Initialize all the UI components
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Data.data.gc = pathCanvas.getGraphicsContext2D();
        map.setImage(Data.data.firstFloor);

        //disables the bars and starts up the zoom function
        scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        zoom();

        //popluating list view -- three
        ObservableList<String> threeItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("3"));
        threeList.setItems(threeItems);

        // Second Floor
        ObservableList<String> twoItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("2"));
        twoList.setItems(twoItems);

        // First Floor
        ObservableList<String> oneItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("1"));
        oneList.setItems(oneItems);

        // Ground Floor
        ObservableList<String> groundItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("G"));
        groundList.setItems(groundItems);


        // Lower 1 Floor
        ObservableList<String> lowerOneItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("L1"));
        lowerOneList.setItems(lowerOneItems);

        // Lower 2 Floor
        ObservableList<String> lowerTwoItems = FXCollections.observableArrayList(testEmbeddedDB.getLongNamesByFloor("L2"));
        lowerTwoList.setItems(lowerTwoItems);

        // All entries
        allEntries = FXCollections.observableArrayList(testEmbeddedDB.getAllLongNames());
        searchList.setItems(allEntries);

        end.setSelected(true);
        map.setImage(Data.data.firstFloor);
        stairs.setSelected(true);
        elevator.setSelected(true);
        tabPane.getSelectionModel().select(floorOne);

        threeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });

        twoList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });
        oneList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });
        lowerOneList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });
        lowerTwoList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });
        groundList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });


        //switching admin privs
        SettingSingleton.getSettingSingleton().getauthPropertyProperty().addListener((ObservableValue<? extends AuthenticationInfo> a, AuthenticationInfo before, AuthenticationInfo after) -> {
            if (after.getPriv().equals(AuthenticationInfo.Privilege.ADMIN)) {
                adminBox.setVisible(true);
                loginButton.setText("Log Out");
            } else {
                adminBox.setVisible(false);
                loginButton.setText("Log In");
            }
        });

        //switching admin privs
        SettingSingleton.getSettingSingleton().getauthPropertyProperty().addListener((ObservableValue<? extends AuthenticationInfo> a, AuthenticationInfo before, AuthenticationInfo after) -> {
                    if (after.getPriv().equals(AuthenticationInfo.Privilege.ADMIN)) {
                        loginButton.setOnAction((event) -> {
                            try {
                                logout();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    }});

        threeArrow.setVisible(false);
        twoArrow.setVisible(false);
        oneArrow.setVisible(false);
        groundArrow.setVisible(false);
        lowerTwoArrow.setVisible(false);
        lowerOneArrow.setVisible(false);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setMainController(Main in){
        mainController = in;
    }

    public JFXListView getDirectionSteps(){
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
        searchList.setVisible(false);
        String destinationText = destination.getText();
        oneArrow.setVisible(false);
        twoArrow.setVisible(false);
        threeArrow.setVisible(false);
        groundArrow.setVisible(false);
        lowerOneArrow.setVisible(false);
        lowerTwoArrow.setVisible(false);
        if (points.getSelectedToggle() == start) {

            System.out.println("LABEL!!!!!");
            startLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            if(!destinationText.equals("")&&!startLabel.getText().equals("")) {
                go();
            }
        }
        else{

            System.out.println("LABEL!!!!!");
            endLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());

            System.out.println(endLabel.getText()+"<=============DESTINATION LABEL");
            destination.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            endLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            if(!destinationText.equals("")) {
                go();
            }
        }
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
    }

    @FXML
    public void changeFloorL2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L2Floor);
        testDrawDirections(Data.data.pathL2);
        Data.data.currentMap = "L2";
    }

    @FXML
    public void changeFloor1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.firstFloor);
        testDrawDirections(Data.data.pathFirst);
        Data.data.currentMap = "1";
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
    }

    @FXML
    public void changeFloorG() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,1000,1000);
        map.setImage(Data.data.GFloor);
        testDrawDirections(Data.data.pathG);
        Data.data.currentMap = "G";
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

    public void findPath(String Start, String End) throws IOException, InterruptedException {
        //Returns

        for(int i = 0;i<data.graph.getNodes().size();i++){
            if(data.graph.getNodes().get(i).getLongName().trim().equals(Start.trim())){
                data.kiosk = data.graph.getNodes().get(i);
                System.out.println(data.kiosk.getLongName());
            }
        }
        System.out.println("START SEARCH!!!!!!!!!!");
        Node StartNode = data.kiosk;

        System.out.println("END SEARCH!!!!!!!!!!!!");
        Node EndNode = SearchEngine.SearchPath(End,Data.data.graph,data.kiosk);



        switch (currentAlgo){
            case 1:
                PathAlgorithm pathFinder1 = new PathAlgorithm(new Astar());
                this.path = pathFinder1.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 2:
                PathAlgorithm pathFinder2 = new PathAlgorithm(new BFSearch());
                this.path = pathFinder2.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 3:
                PathAlgorithm pathFinder3 = new PathAlgorithm(new DFSearch());
                this.path = pathFinder3.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 4:
                PathAlgorithm pathFinder4 = new PathAlgorithm(new Dijkstras());
                this.path = pathFinder4.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 5:
                PathAlgorithm pathFinder5 = new PathAlgorithm(new BeamFirstSearch());
                this.path = pathFinder5.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 6:
                PathAlgorithm pathFinder6 = new PathAlgorithm(new BestFirstSearch());
                this.path = pathFinder6.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
        }


        MultiFloorPathDrawing(this.path);

        directionSteps.setVisible(true);
        sendLabel.setVisible(true);
        email.setVisible(true);
        sendButton.setVisible(true);
        int length = path.size();
        String lastFloor = path.get(length - 1).getFloor();
        setMap(lastFloor);
    }


    @FXML
    public void clearFields(){
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();
        threeArrow.setVisible(false);
        twoArrow.setVisible(false);
        oneArrow.setVisible(false);
        groundArrow.setVisible(false);
        lowerOneArrow.setVisible(false);
        lowerTwoArrow.setVisible(false);
        sendLabel.setVisible(false);
        email.setVisible(false);
        sendButton.setVisible(false);
        directionSteps.setVisible(false);
        endLabel.setText("");
        startLabel.setText("Lower Pike Hallway Exit Lobby");
        destination.setText("");
        directionSteps.getItems().clear();
        reset(map, width, height);
    }

    @FXML
    public void go() throws IOException,InterruptedException{
        clear();
        findPath(startLabel.getText(),endLabel.getText());
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
        setArrows(floorsVisited);
        searchList.setVisible(false);
        directionSteps.setVisible(true);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Drawing and Directions functions

    // Purpose: Print out directions for a path of nodes
    public static String directions(Vector<Node> in){
        String out = "";
        Node a, b, c;
        if(in.size()<2){
            out = out.concat("Path too short");
        }
        a = in.get(0);
        b = in.get(1);
        out = out.concat("Start at " + a.getLongName().trim()+"<br>");
        out = out.concat("Go towards " + b.getLongName().trim()+"<br>");

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

            out = out.concat("When you arrive at " + b.getLongName().trim() + " go " + turn + " towards " + c.getLongName().trim() + "<br>");
        }
        return out;
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

    // Purpose: Insert a path of nodes that are only on ONE floor, draws the path on that floor
    @FXML
    public void MultiFloorPathDrawing(Vector<Node> path) throws IOException, InterruptedException {
        ///HERE////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ObservableList<String> populateSteps = FXCollections.observableArrayList();
        //edit later
        String directions = directions(path);
        String[] directionParts = directions.split("<br>");
        for (int i = 0; i < directionParts.length; i++) {
            populateSteps.add(directionParts[i]);
        }
        populateSteps.add("You have arrived at your destination.");
        directionSteps.setItems(populateSteps);


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

    public void setArrows(Vector<String> floorsNeeded){
        for (int i = 0; i < floorsNeeded.size(); i++) {
            String floorAt = floorsNeeded.elementAt(i);
            switch (floorAt){
                case "L2":
                    lowerTwoArrow.setVisible(true);
                    break;
                case "L1":
                    lowerOneArrow.setVisible(true);
                    break;
                case "G":
                    groundArrow.setVisible(true);
                    break;
                case "1":
                    oneArrow.setVisible(true);
                    break;
                case "2":
                    twoArrow.setVisible(true);
                    break;
                case "3":
                    threeArrow.setVisible(true);
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
                        Data.data.gc.strokeLine(node.getxCoordinate() / data.divisionCst + data.offset, node.getyCoordinate() / data.divisionCst , node2.getxCoordinate() / data.divisionCst + data.offset, node2.getyCoordinate() / data.divisionCst);
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

        stackPane.setOnScroll(new EventHandler<ScrollEvent>() {
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
        scrollContent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lastMouseCoordinates.set(new Point2D(event.getX(), event.getY()));
            }
        });

        scrollContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
        Main.startScreen();
        loginButton.setOnAction((event) -> {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        int z = 0;
        for(int i = currentPath.size(); i > 0; i--){
            reversePath.add(currentPath.get(i-1));
        }
        this.path = reversePath;
        MultiFloorPathDrawing(this.path);
    }

}