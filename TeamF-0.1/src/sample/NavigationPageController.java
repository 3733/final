package sample;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NavigationPageController implements Initializable, Data{

    //fxml components
    @FXML
    private JFXDrawer drawer;
    @FXML
    private javafx.scene.image.ImageView icon;
    @FXML
    private Label sendLabel;
    @FXML
    private JFXButton sendButton;

    /**
     * canvas pathCanvas
     */
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
    private JFXTextField email;

    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private VBox adminBox;
    @FXML
    private JFXButton loginButton;


    //other components
    private Main mainController;

    private Vector<Node> path = new Vector<Node>();

    private Map CurMap;

    private Node Kiosk;

    private String filePath = "/sample/UI/Icons/";

    ObservableList<String> allEntries;

    private int currentAlgo = 1;


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


        Data.data.firstFloorNodes = testEmbeddedDB.getNodesByFloor(3);
        Data.data.groundFloorNodes = testEmbeddedDB.getNodesByFloor(2);
        Data.data.lowerLevel01FloorNodes = testEmbeddedDB.getNodesByFloor(1);
        Data.data.lowerLevel02FloorNodes = testEmbeddedDB.getNodesByFloor(0);
        Data.data.secondFloorNodes = testEmbeddedDB.getNodesByFloor(4);
        Data.data.thirdFloorNodes = testEmbeddedDB.getNodesByFloor(5);

        try {
            clickNearestNodeSelected();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setMainController(Main in){
        mainController = in;
    }

    public int getCurrentAlgo(){
        return this.currentAlgo;
    }

    public void setKiosk(Node k){
        this.Kiosk = k;
    }

    public void setSearch(String s){
        this.destination.setText(s);
    }

    public void autoClose(){
        searchList.setVisible(false);
    }

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
        return this.Kiosk;
    }

    //setting start and end nodes
    @FXML
    public void settingFields() throws IOException, InterruptedException {
        String destinationText = destination.getText();
        if (points.getSelectedToggle() == start) {
            startLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,Kiosk).getLongName().trim());
        }
        else{
            endLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,Kiosk).getLongName().trim());
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Floor Methods
    @FXML
    public void changeFloorL1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L1Floor);
        testDrawDirections(Data.data.pathL1);
        //tabPane.getSelectionModel().select(floorLowerOne);
        drawNodesCircle(Data.data.lowerLevel01FloorNodes);
        Data.data.currentMap = "L1";
    }

    @FXML
    public void changeFloorL2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L2Floor);
        testDrawDirections(Data.data.pathL2);
        //tabPane.getSelectionModel().select(floorLowerTwo);
        drawNodesCircle(Data.data.lowerLevel02FloorNodes);
        Data.data.currentMap = "L2";
    }

    @FXML
    public void changeFloor1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.firstFloor);
        testDrawDirections(Data.data.pathFirst);
        drawNodesCircle(Data.data.firstFloorNodes);
        //tabPane.getSelectionModel().select(floorOne);
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
        //tabPane.getSelectionModel().select(floorTwo);
        drawNodesCircle(Data.data.secondFloorNodes);
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
        //tabPane.getSelectionModel().select(floorThree);
        drawNodesCircle(Data.data.thirdFloorNodes);
        Data.data.currentMap = "3";
    }

    @FXML
    public void changeFloorG() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,1000,1000);
        map.setImage(Data.data.GFloor);
        testDrawDirections(Data.data.pathG);
        //tabPane.getSelectionModel().select(floorGround);
        drawNodesCircle(Data.data.groundFloorNodes);
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
    public void help(){Main.genErrorScreen();}

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
        Node EndNode = SearchEngine.SearchPath(End,Data.data.graph,Kiosk);

        Node StartNode = SearchEngine.SearchPath(Start,Data.data.graph,Kiosk);

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
                PathAlgorithm pathFinder5 = new PathAlgorithm(new BestFirstSearch());
                this.path = pathFinder5.executeStrategy(StartNode,EndNode, Data.data.graph);
                break;
            case 6:
                PathAlgorithm pathFinder6 = new PathAlgorithm(new BeamFirstSearch());
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

        for(Vector<Node> floorPath: paths){
            if (floorPath.size() > 0) {
                String pathFloor = floorPath.elementAt(0).getFloor().replaceAll("\\s+", "");
                if (pathFloor.equals("L2")) {
                    Data.data.pathL2 = floorPath;
                } else if (pathFloor.equals("L1")) {
                    Data.data.pathL1 = floorPath;
                } else if (pathFloor.equals("0G") || pathFloor.equals("G")) {
                    Data.data.pathG = floorPath;
                } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                    Data.data.pathFirst = floorPath;
                } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                    Data.data.pathSecond = floorPath;
                } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                    Data.data.pathThird = floorPath;
                }
            }
        }
        setMap("1");
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
            Data.data.gc.setLineWidth(2);
            Data.data.gc.setStroke(javafx.scene.paint.Color.BLACK);
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
                        Data.data.gc.strokeLine(node.getxCoordinate() / 4.4 + 2, node.getyCoordinate() / 4.4 + 2, node2.getxCoordinate() / 4.4 + 2, node2.getyCoordinate() / 4.4 + 2);
                    }
                }
            }
            String floor = path.get(0).getFloor().replaceAll("\\s+","");
        }
    }


    /**
     * function to draw the nodes of the  floors
     * @param FloorNodes
     */
    public void drawNodesCircle(Vector<Node> FloorNodes)
    {
        if(FloorNodes != null) {
            int length = FloorNodes.size();

            Data.data.gc.setStroke(Color.BLUE);
            Data.data.gc.stroke();
            // Iterate through all the given nodes to draw the node
            for (int i = 0; i < length; i++)
            {
                Node nodesMap = FloorNodes.get(i);
                pathCanvas.getGraphicsContext2D().strokeOval(nodesMap.getxCoordinate()/4.4 +2.0 ,nodesMap.getyCoordinate()/4.4 +2.0, 4.0, 4.0);
                pathCanvas.getGraphicsContext2D().fillOval(nodesMap.getxCoordinate()/4.4 +2.0 ,nodesMap.getyCoordinate()/4.4 +2.0, 4.0, 4.0);

            }
            }
    }

    /**
     * mouse click location done!
     * @throws IOException
     */

    public void clickNearestNodeSelected() throws IOException {
        pathCanvas.setOnMouseClicked((javafx.scene.input.MouseEvent e) -> {

            if (e.getClickCount() == 1)
            {
                double scaleX = 5000d / 1143d;
                double scaleY = 3400d / 781d;

                double newX1 = (e.getSceneX());
                double newY1 = (e.getSceneY());

                Node node = null;

                if(floorLowerTwo.isSelected()) {
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.lowerLevel02FloorNodes);
                } else if(floorLowerOne.isSelected()) {
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.lowerLevel01FloorNodes);
                } else if(floorGround.isSelected()) {
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.groundFloorNodes);
                } else if(floorOne.isSelected()) {
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.firstFloorNodes);
                } else if(floorTwo.isSelected()) {
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.secondFloorNodes);
                } else if(floorThree.isSelected()){
                    node = mousePosition((newX1-2)*4.4,(newY1-2)*4.4,Data.data.thirdFloorNodes);
                }

                Data.data.gc.setStroke(Color.RED);
                Data.data.gc.stroke();

                pathCanvas.getGraphicsContext2D().strokeOval(node.getxCoordinate()/4.4 +2.0 ,node.getyCoordinate()/4.4 +2.0, 7.0, 7.0);
                pathCanvas.getGraphicsContext2D().fillOval(node.getxCoordinate()/4.4 +2.0 ,node.getyCoordinate()/4.4 +2.0, 7.0, 7.0);
                Main.editNodeScreen(sendButton, node);




                // prevx = newX1;
                // prevy = newY1;
//                Data.data.gc.setStroke(Color.YELLOW);
//                pathCanvas.getGraphicsContext2D().setFill(Color.RED);
//                pathCanvas.getGraphicsContext2D().strokeOval(newX1 - 2.5,newY1 -.5, 6.0, 6.0);
//                pathCanvas.getGraphicsContext2D().fillOval(newX1-2.5 ,newY1-.5, 6.0, 6.0);


            }
        });
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
        return GoodOne;

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Zooming Panning & Dragging functions

    // Purpose: Zoom the map when the user zooms
    @FXML
    private void zoom() {
        int MIN_PIXELS = 15;
        //zoom
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();

        map.setPreserveRatio(true);
        reset(map, width, height);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        map.setOnMousePressed(e -> {

            Point2D mousePress = imageViewToImage(map, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        map.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(map, new Point2D(e.getX(), e.getY()));
            shift(map, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(map, new Point2D(e.getX(), e.getY())));
        });

        map.setOnScroll(e -> {
            double delta = -e.getDeltaY();
            Rectangle2D viewport = map.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                    // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                    // don't scale so that we're bigger than image dimensions:
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewToImage(map, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            map.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        map.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(map, width, height);
            }
        });

        map.setPreserveRatio(true);
        map.fitWidthProperty().bind(scrollMap.widthProperty());
        map.fitHeightProperty().bind(scrollMap.heightProperty());
    }

    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }


    /**
     * willis
     * reverse function
     */
    public void reverseButton_Clicked(){
        String start =startLabel.getText();
        String end = endLabel.getText();
        if( !(start == null || start.equals("") || end == null ||end.equals("")) )
        {
            startLabel.setText(end);
            endLabel.setText(start);
            try {
                go();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
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
        Main.startScreen();
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
                searchList.setVisible(false);
            }
        });

    }

}