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
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class NavigationPageController implements Initializable{

    //new components
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private javafx.scene.image.ImageView icon;
    @FXML
    private Label sendLabel;
    @FXML
    private JFXButton sendButton;
    @FXML
    private JFXListView directionSteps;
    @FXML
    private JFXListView threeList, twoList, oneList, lowerTwoList, lowerOneList, groundList;
    @FXML
    private Label floorLabel;
    @FXML
    private JFXButton downFloor, upFloor;
    @FXML
    private JFXListView searchList;
    @FXML
    private ScrollPane scrollMap;
    @FXML
    private JFXSlider zoom;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXButton upButton;
    @FXML
    private JFXButton downButton;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private VBox labelBox;
    @FXML private Tab floorThree, floorTwo, floorOne, floorLowerTwo, floorLowerOne, floorGround;
    String currentFloor = "First Floor";


    //to login from navigation screen
    @FXML
    public void login(){
        Main.loginScreen();
    }

    @FXML
    public void help(){Main.genErrorScreen();}

    // Contains the user email entry
    @FXML
    private JFXTextField email;

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


    private Main mainController;

    public void setMainController(Main in){
        mainController = in;
    }

    private Vector<Node> path = new Vector<Node>();

    private Map CurMap;

    private Node Kiosk;


    public void setMap(Map m) throws IOException{
        this.CurMap = m;
        Data.data.firstFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png"));
        Data.data.secondFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png"));
        Data.data.thirdFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/03_thethirdfloor.png"));
        Data.data.GFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thegroundfloor.png"));
        Data.data.L1Floor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png"));
        Data.data.L2Floor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png"));
        Data.data.currentMap = "1";
        //System.out.println("KSJHDFUZBXCGV"+CurMap.getNodes().size());
    }

    public void setKiosk(Node k){
        this.Kiosk = k;
    }

    @FXML
    public void changeFloor() {

    }

    @FXML
    public void changeFloorL1() {
        map.setImage(Data.data.L1Floor);
        Data.data.currentMap = "L1";
    }

    @FXML
    public void changeFloorL2() {
        map.setImage(Data.data.L2Floor);
        Data.data.currentMap = "L2";
    }

    @FXML
    public void changeFloor1() {
        map.setImage(Data.data.firstFloor);
        Data.data.currentMap = "1";
    }

    @FXML
    public void changeFloor2() {
        map.setImage(Data.data.secondFloor);
        Data.data.currentMap = "2";
    }

    @FXML
    public void changeFloor3() {
        map.setImage(Data.data.thirdFloor);
        Data.data.currentMap = "3";
    }

    @FXML
    public void changeFloorG() {
        map.setImage(Data.data.GFloor);
        Data.data.currentMap = "G";
    }


    public void setSearch(String s){
        this.destination.setText(s);
    }

    // The go button next to the destination text field, starts pathfinding algorithm, direction print, map drawing

    public void findPath(String Start, String End) throws IOException {
        //Returns
        long st = System.currentTimeMillis();

        Node EndNode = SearchEngine.SearchPath(End,CurMap,Kiosk);
        System.out.println(EndNode.getLongName()+"<=====END");

        Node StartNode = SearchEngine.SearchPath(Start,CurMap,Kiosk);
        System.out.println(StartNode.getLongName()+"<=====START");

        switch (currentAlgo){
            case 1:
                this.path = CurMap.AStar(StartNode,EndNode);
                System.out.println(currentAlgo+"<==ALGO USED");
                break;
            case 2:
                this.path = CurMap.BFSearch(StartNode,EndNode);
                System.out.println(currentAlgo+"<==ALGO USED");
                break;
            case 3:
                this.path = CurMap.DFSearch(StartNode,EndNode);
                System.out.println(currentAlgo+"<==ALGO USED");
                break;
            case 4:
                this.path = CurMap.Dijkstras(StartNode,EndNode);
                System.out.println(currentAlgo+"<==ALGO USED");
                break;
        }

        long et = System.currentTimeMillis();
        System.out.println(et-st+"<===ALGO");

        st = System.currentTimeMillis();
        MultiFloorPathDrawing(path);
        et = System.currentTimeMillis();


        directionSteps.setVisible(true);
        sendLabel.setVisible(true);
        email.setVisible(true);
        sendButton.setVisible(true);
    }

    @FXML
    public void go() throws IOException,InterruptedException{
        clear();
        for(int i = 0; i < Data.data.floorList.size() ; i++){
            Data.data.floorList.set(i,false);
        }
        findPath(startLabel.getText(),endLabel.getText());
        searchList.setVisible(false);
        directionSteps.setVisible(true);
        sendLabel.setVisible(true);
        email.setVisible(true);
        sendButton.setVisible(true);
    }

    // Method to clear the path on the map when the user presses clear map
    @FXML
    public void clear() throws FileNotFoundException{
        Data.data.firstFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png"));
        Data.data.secondFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png"));
        Data.data.thirdFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/03_thethirdfloor.png"));
        Data.data.GFloor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thegroundfloor.png"));
        Data.data.L1Floor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png"));
        Data.data.L2Floor = new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png"));
        map.setImage(selectMap(Data.data.currentMap));
        for(int i = 0; i < Data.data.floorList.size() ; i++){
            Data.data.floorList.set(i,false);
        }
    }

    // this function returns the proper image based on the current image string
    public Image selectMap(String currentMap) {
        System.out.println(currentMap);
        if (currentMap != null) {
            if (currentMap.equals("L2")) {
                return Data.data.L2Floor;
            } else if (currentMap.equals("L1")) {
                return Data.data.L1Floor;
            } else if (currentMap.equals("0G") || currentMap.equals("G")) {
                return Data.data.GFloor;
            } else if (currentMap.equals("01") || currentMap.equals("1")) {
                return Data.data.firstFloor;
            } else if (currentMap.equals("02") || currentMap.equals("2")) {
                return Data.data.secondFloor;
            } else if (currentMap.equals("03") || currentMap.equals("3")) {
                return Data.data.thirdFloor;
            }
        }
            System.out.println("ERROR: INVALID FLOOR ID");
            return Data.data.firstFloor;
    }
    //sets invalid email label when necessary for errorhandling
    @FXML
    public static void setInvalidEmail(){
        invalidEmailText.setVisible(true);
    }

    // User clicks send email button
    @FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", map);
        emailService.sendEmail(NavigationPageController.directions(Data.data.path), email.getText());
    }

    // Button to return to the welcome screen
    @FXML
    public void back(){
        Main.startScreen();
    }

    // Purpose: Print out directions for a path of nodes
    public static String directions(Vector<Node> in){
        String out = "";
        Node a, b, c;
        if(in.size()<2){
            out = out.concat("Path too short");
        }
        a = in.get(0);
        b = in.get(1);
        out = out.concat("Start at " + a.getLongName()+"<br>");
        out = out.concat("Go towards " + b.getLongName()+"<br>");

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

            out = out.concat("When you arrive at " + b.getLongName() + " go " + turn + " towards " + c.getLongName() + "<br>");
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
    public void MultiFloorPathDrawing(Vector<Node> path) throws IOException{
        for(Node i: path){
            System.out.println(i.getLongName()+ "\t"+i.getNodeType());
        }
        // Possible floors (in order): L2, L1, 0G, 01, 02, 03
        //System.out.println("Reached the multifloor path drawing function");
        //System.out.println("This is the first node floor: " + path.get(0).getFloor());
        Vector<Vector<Node>> paths = separator(path);
        //System.out.println("Size of the vector of paths " + paths.size());
        /*
        for (Vector<Node> i: paths) {
            for (Node j: i){
                System.out.println(j.getLongName());
            }

        }*/
        //Vector<Vector<Node>> paths = new Vector<Vector<Node>>();
        //paths.add(path);

        for(Vector<Node> floorPath: paths){
            if (floorPath.size() > 0) {
                System.out.println(Data.data.floorList);
                //System.out.println("This is the node floor: " + floorPath.elementAt(0).getFloor().replaceAll("\\s+", ""));
                String pathFloor = floorPath.elementAt(0).getFloor().replaceAll("\\s+", "");
                if (pathFloor.equals("L2")) {
                    Data.data.L2Floor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.L2Floor, null));
                    Data.data.currentMap = "L2";
                    Data.data.floorList.set(0,true);
                } else if (pathFloor.equals("L1")) {
                    Data.data.L1Floor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.L1Floor, null));
                    Data.data.currentMap = "L1";
                    Data.data.floorList.set(1,true);
                } else if (pathFloor.equals("0G") || pathFloor.equals("G")) {
                    Data.data.GFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.GFloor, null));
                    Data.data.currentMap = "G";
                    Data.data.floorList.set(2,true);
                } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                    Data.data.firstFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.firstFloor, null));
                    Data.data.currentMap = "1";
                    Data.data.floorList.set(3,true);
                } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                    Data.data.secondFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.secondFloor, null));
                    Data.data.currentMap = "2";
                    Data.data.floorList.set(4,true);
                } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                    Data.data.thirdFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.thirdFloor, null));
                    Data.data.currentMap = "3";
                    Data.data.floorList.set(5,true);
                }
            }
        }
        //String floor = path.get(0).getFloor();

    }

    // Purpose: Draw a path of nodes on the map
    @FXML
    public Image testDrawDirections(Vector<Node> path, BufferedImage floorImage) throws IOException {
        Graphics2D pathImage = floorImage.createGraphics();
        int length = path.size();
        String nameDest = path.get(length-1).getShortName();
        String nameDept = path.get(0).getShortName();

        //Data.data.emailMapFloor1 = "path" + nameDept + "-" + nameDest;

        // Setting up the proper color settings
        pathImage.setStroke(new BasicStroke(10)); // Controlling the width of the shapes drawn
        // Iterate through all the path nodes to draw the path
        for(int i = 0; i < length ; i++) {
            Node node = path.get(i);
            //System.out.println("This is node: " + node.getNodeID());
            if(i + 1 < length){
                Node node2 = path.get(i+1);
                //System.out.println("This is node + 1: " + node2.getNodeID() + "\n\n");
                // Lines are drawn offset,
                if(!(node2.getNodeID().equals("BLANK")) && !(node.getNodeID().equals("BLANK"))) {
                    pathImage.setColor(new java.awt.Color(0, 0, 0)); // This color is black
                    pathImage.drawLine(node.getxCoordinate(), node.getyCoordinate(), node2.getxCoordinate(), node2.getyCoordinate());
                }
                }
        }
        map.setImage(SwingFXUtils.toFXImage(floorImage,null));
        //System.out.println("Image set on map");
        return SwingFXUtils.toFXImage(floorImage,null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //disables the bars and starts up the zoom function
            scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            zoom();

            //popluating list view -- three
            ObservableList<String> threeItems =FXCollections.observableArrayList (
                    "Bridge to Dana-Farber Cancer Institute", "Brigham Circle Medical Associates", "Center for Infertility and Reproductive Surgery",
                    "Clinical Trials", "Conference Center","Dialysis,", "Dialysis Waiting Room", "Fetal Med & Genetics", "General Surgical Specialties Suite A",
                    "General Surgical Specialties Suite B", "Gynecology", "Gyencology Oncology MIGS", "Innovation Hub", "Maternal Fetal Practice",
                    "MICU 3B/C Waiting Room", "OB/GYN Blood Lab", "Obstetrics", "The Porch", "Reproductive Endocrine Labs", "Urology", "Watkins Clinic C");
            threeList.setItems(threeItems);

            //populating list view -- second
            ObservableList<String> twoItems = FXCollections.observableArrayList("Bridge to Children's", "Brigham Health", "Carrie M. Hall Conference Center",
                    "Chest Diseases", "Coffee Connection", "Comprehensive Breast Health", "Conference Center", "Duncan Reid Conference Room", "Ear, Nose, & Throat",
                    "Endoscopy", "Garden Cafe", "Gift Shop", "Jen Center for Primary Care", "Lee Bell Breast Center", "Louis Bornstein Family Amphitheater",
                    "Medical Surgical Specialties", "MRI Associates", "Oral Medicine and Dentistry", "Orthopedics and Rhematology", "Outpatient Specimen Collection",
                    "Pat's Place", "Patient Financial Services", "Plastic Surgery", "Thoracic Surgery Clinic", "Vascular Diagnostic Lab", "Watkins A", "Watkins B",
                    "Weiner Center for Preoperative Evaluation");
            twoList.setItems(twoItems);

            //populating list view -- first
            ObservableList<String> oneItems = FXCollections.observableArrayList("Ambulatory X-Ray", "Asthma Research Center", "Au Bon Pain",
                    "Bretholtz Center for Patients and Families", "CART Waiting", "Connor's Center Security Desk", "CPE Classroom", "International Patient Center",
                    "Kessler Library", "MS Waiting", "Multifaith Chapel", "Neuroscience Waiting Room", "Obstetrics Admitting", "Occupational Health", "Partner's Shuttle",
                    "Rehabilitation Services", "Shapiro Board Room", "Sharf Admitting Center", "Spiritual Care Office", "Wound Care Center Ambulatory Treatment Room",
                    "Zinner Breakout Room");
            oneList.setItems(oneItems);

            //populating list view -- lower two
            ObservableList<String> lowerTwoItems = FXCollections.observableArrayList("Cardiac Stress Test Lab", "Cardiovascular Imaging Center", "CVRR",
                    "Interpreter Services", "MRI/CT Scan Imaging", "Radiation Oncology", "Radiation Oncology Conference Room", "Radiation Oncology T/X Suite");
            lowerTwoList.setItems(lowerTwoItems);

            //populating list view -- lower one
            ObservableList<String> lowerOneItems = FXCollections.observableArrayList("Abrams Conference Room", "Anesthesia Conference Room", "CSIR MRI",
                    "Day Surgery Family Waiting", "Helen Hogan Conference Room", "Medical Records Conference Room", "Medical Records Film Library", "Nuclear Medicine",
                    "Outpatient Fluoroscopy", "Pre-OP PACU", "Ultrasound", "Volunteers");
            lowerOneList.setItems(lowerOneItems);

            //adding all possible entries
            allEntries = FXCollections.observableArrayList("Restroom; S elevator; 1st floor", "Restroom; BTM conference center; 3rd floor",
                    "Elevator S G", "Infusion Waiting Area", "BTM Security Desk", "Clinical Trials", "Schlagler Innovation Lobby",
                    "Elevator S 01", "Neuroscience Waiting Room", "Orthopedics and Rhemutalogy","CART Waiting", "Elevator S; Floor 3",
                    "Elevator S 02", "MRI/CT Scan Imaging", "Fenwood Road", "BTM Security Desk", "Elevator S L2", "Neuro Testing Waiting Area",
                    "Hallway to Elevator", "Innovation Hub", "Parking Garage L2", "MS Waiting", "Conference Room 1 Level 2",
                    "Comprehensive Breast Heath Level 2", "Oral Medicine and Denstistry Level 2", "Lee Bell Breast Center Level 2",
                    "Jet Center for Primary Care Level 2", "Ear Nose & Throat Level 2", "Medical Surgical Specialties Level 2",
                    "Plastic Surgery Level 2", "Outpatient Pharamacy Level 2", "Weiner Center for Pre-operational Evaluation Level 2",
                    "Information Desk 1 Level 2", "Key icon Level 2", "Vascular Diagnostic Lab Level 2", "Outpatient Speciman Collection Level 2",
                    "Restroom 1 Level 2", "Restroom 2 Level 2", "Restroom 3 Level 2", "Restroom 4 Level 2", "Restroom 5 Level 2", "Cafe 1 Level 2",
                    "Patient Financial Services Level 2", "Anesthesia Conf Floor L1", "Medical Records Conference Room Floor L1", "Abrams Conference Room",
                    "Day Surgery Family Waiting Floor L1", "Day Surgery Family Waiting Exit Floor L1", "Medical Records Film Library Floor L1",
                    "Outpatient Fluoroscopy Floor L1", "Radiation Oncology T/X Suite Floor L2", "Pre-Op PACU Floor L1", "Radiation Oncology Floor L2",
                    "Nuclear Medicine Floor L1", "Ultrasound Floor L1", "CSIR MRI Floor L1", "Restroom L Elevator Floor L1", "Restroom K Elevator Floor L2",
                    "Restroom M Elevator Floor L1", "Restroom L Elevator Floor L2", "Restroom K Elevator Floor L1", "Restroom H Elevator Floor L1",
                    "Vending Machine 1 L1", "Volunteers Floor L1", "Interpreter Services Floor L2", "Elevator A Floor 2", "Elevator B Floor 2",
                    "Elevator C Floor 2", "Elevator D Floor 2", "Restroom B elevator Floor 2", "Restroom C elevator Floor 2", "Restroom C-D elevator Floor 2",
                    "Restroom D elevator Floor 2", "15 Francis Security Desk Floor 2", "Security Desk Thorn Floor 2", "Chest Diseases Floor 2",
                    "Thoracic Surgery Clinic Floor 2", "Brigham Health Floor 2", "Waiting Room Floor 2", "MRI Associates Floor 2",
                    "Carrie M. Hall Conference Center Floor 2", "Pat's Place Floor 2", "15 Lobby Entrance Floor 2", "Ambulance Parking Exit Floor 1",
                    "Waiting Room 1 Floor 1", "Connor's Center Security Desk Floor 1", "Restroom G1 Floor 1", "Exit 2 Floor 1", "Asthma Research Floor 1",
                    "Wound and Ambulatory 1", "Ocupational Health Floor 1", "Restroom F1 Floor 1", "Restroom H1 Floor 1", "Ambulatory X-Ray Floor 1",
                    "Rehabilitation Services Floor 1", "Staircase H2 Floor 1", "Lower Pike Hallway Exit Lobby", "Lobby Shattuck Street",
                    "Shattuck Street Lobby 1", "Shattuck Street Lobby Exit", "Shattuck Street Lobby 2", "Lobby Vending Machine", "Shattuck Street Lobby 3",
                    "Shattuck Street Lobby ATM", "Tower Lobby Entrance 1", "Tower Elevator Entrance", "Tower Staff Entrance",
                    "Center for International Medicine", "Spiritual Care Office", "Tower Medical Cashier", "Multifaith Chapel",
                    "Bretholtz Center for Patients and Families", "Kessler Library", "Sharf Admitting Center", "Hallway Lobby Entrance", "Obstetrics Admitting",
                    "Lobby Escalator", "Emergency Department", "Lobby Entrance Hallway", "75 Francis Valet Drop-off", "75 Lobby", "75 Lobby Information Desk",
                    "75 Lobby Valet Cashier", "Au Bon Pain", "Bathroom 75 Lobby", "Emergency Department Entrance", "International Patient Center", "Emergency Hallway",
                    "Shapiro Board Room Node 20 Floor 1", "Zinner Breakout Room Node 19 Floor 1", "Elevator N Node 26 Floor 1", "Elevator Q Node 18 Floor 1",
                    "Francis Street Exit Node 1 Floor 1", "Bathroom Node 12 Floor 1", "Random Room Node 35 Floor 1", "ATM Node 23 Floor 1", "Waiting room? Node 7 Floor 2",
                    "Watkins A Node 24 Floor 2 Floor 2", "Watkins B Node 35 Floor 2", "Elevator N Node 25 Floor 2", "Elevator Q Node 31a Floor 2", "Info Node 19 Floor 2",
                    "Restroom Node 6 Floor 2", "Restroom Node 31 Floor 2", "Brigham Circle Medical Associates Node 4 Floor 3", "Watkins Clinic C Node 14 Floor 3",
                    "Elevator N Node 15 Floor 3", "Elevator Q Node 5 Floor 3", "Restroom Node 12 Floor 3", "The Porch Node 16 Floor 3", "Elevator Q Node 7 Floor L1",
                    "Fenwood Road Exit Node 1 Floor L1", "Elevator Q Node 6 Floor L2", "Cardiovascular Imaging Center Floor L2", "Cardiac Stress Test Lab Floor L2",
                    "CVRR Floor L2", "Restroom Node 4 Floor L2", "Garden Cafe", "Vending Machine Floor 2?", "Bathroom 1 Tower Floor 2", "Gift Shop Tower Floor 2",
                    "Stairwell 2 Tower Floor 2", "Escalator 1 Floor 2", "Endoscopy", "Stairwell 1 Floor 3", "Reproductive Endocrine Labs", "Dialysis Waiting Room",
                    "Nursing Room", "Bathroom 1 Tower Floor 3", "MICU 3B/C Waiting Room", "Bathroom 2 Tower Floor 3", "Restroom 1 - Family", "Restroom 2", "Restroom 3",
                    "Restroom 4 - M wheelchair", "Restroom 5 - F wheelchair", "Center for Infertility and Reproductive Surgery", "Gynecology Oncology MIGS",
                    "General Surgical Specialties Suite A", "General Surgical Specialties Suite B", "Urology", "Maternal Fetal Practice", "Obstetrics", "Fetal Med & Genetics",
                    "Gynecology");
            searchList.setItems(allEntries);

            //populating list -- ground
            ObservableList<String> groundItems = FXCollections.observableArrayList("Infusion", "Neuro Testing", "Outpatient Plebotomy");
            groundList.setItems(groundItems);
            end.setSelected(true);
            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));   
            tabPane.getSelectionModel().select(floorOne);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Purpose: This draws all the nodes and edges currently in the database
    //Used for debugging and admin
    @FXML
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
    }

    ObservableList<String> allEntries;
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
                System.out.println(newValue);
                destination.setText(newValue);
                searchList.setVisible(false);
            }
        });

    }

    public void autoClose(){
        searchList.setVisible(false);
    }

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
            double delta = e.getDeltaY();
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

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }



    //switches images based on floors
    public void floorUp() throws FileNotFoundException{
        switch (currentFloor){
            case "Ground Floor":
                currentFloor = "Lower Level One";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png")));
                tabPane.getSelectionModel().select(floorLowerOne);
                System.out.println(currentFloor);
                break;
            case "Lower Level One":
                currentFloor = "Lower Level Two";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png")));
                tabPane.getSelectionModel().select(floorLowerTwo);
                System.out.println(currentFloor);
                break;
            case "Lower Level Two":
                currentFloor = "First Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
                tabPane.getSelectionModel().select(floorOne);
                System.out.println(currentFloor);
                break;
            case "First Floor":
                currentFloor = "Second Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png")));
                tabPane.getSelectionModel().select(floorTwo);
                System.out.println(currentFloor);
                break;
            case "Second Floor":
                currentFloor = "Third Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(true);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/03_thethirdfloor.png")));
                tabPane.getSelectionModel().select(floorThree);
                System.out.println(currentFloor);
                break;
            default: break;
        }
    }

    public void floorDown() throws FileNotFoundException{
        switch (currentFloor){
            case "Lower Level One":
                currentFloor = "Ground Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(true);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thegroundfloor.png")));
                tabPane.getSelectionModel().select(floorGround);
                System.out.println(currentFloor);
                break;
            case "Lower Level Two":
                currentFloor = "Lower Level One";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png")));
                tabPane.getSelectionModel().select(floorLowerOne);
                System.out.println(currentFloor);
                break;
            case "First Floor":
                currentFloor = "Lower Level Two";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png")));
                tabPane.getSelectionModel().select(floorLowerTwo);
                System.out.println(currentFloor);
                break;
            case "Second Floor":
                currentFloor = "First Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
                tabPane.getSelectionModel().select(floorOne);
                System.out.println(currentFloor);
                break;
            case "Third Floor":
                currentFloor = "Second Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png")));
                tabPane.getSelectionModel().select(floorTwo);
                System.out.println(currentFloor);
                break;
            default: break;
        }
    }

    private int currentAlgo =1;

    public void setCurrentAlgo(int current){
        this.currentAlgo =  current;
        //System.out.println(this.currentAlgo+ "<=======sdfsdfgbsghxbgfgsh");
    }

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

    //setting start and end nodes
    @FXML
    public void settingFields() throws IOException, InterruptedException {
        if (points.getSelectedToggle() == start) {
            String destinationText = destination.getText();
            startLabel.setText(SearchEngine.SearchPath(destinationText,CurMap,Kiosk).getLongName().trim());
            System.out.println(SearchEngine.SearchPath(destinationText,CurMap,Kiosk).getLongName().trim());
        }
        else{
            String destinationText = destination.getText();
            endLabel.setText(SearchEngine.SearchPath(destinationText,CurMap,Kiosk).getLongName().trim());
            System.out.println(SearchEngine.SearchPath(destinationText,CurMap,Kiosk).getLongName().trim());
        }
        go();
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
}
