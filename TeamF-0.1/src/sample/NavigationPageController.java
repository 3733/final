package sample;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class NavigationPageController implements Initializable{

    //new components
    @FXML
    private JFXButton Floor3Button;
    @FXML
    private JFXButton Floor2Button;
    @FXML
    private JFXButton Floor1Button;
    @FXML
    private JFXButton Gbutton;
    @FXML
    private JFXButton L1button;
    @FXML
    private JFXButton L2button;
    @FXML
    private JFXComboBox floorBox;
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


    //to login from navigation screen
    @FXML
    public void login(){
        Main.loginScreen();
    }

    @FXML
    public void help(){Main.genErrorScreen();}

    @FXML
    public void mapClick() {
        System.out.println(MouseInfo.getPointerInfo().getLocation());
        double x = MouseInfo.getPointerInfo().getLocation().x - Data.data.XWindow - 267;
        double y = MouseInfo.getPointerInfo().getLocation().y - Data.data.YWindow - 67;

        System.out.println("This is x:" + x + " This is y: " + y);
        System.out.println("This is stored x:" + Data.data.XWindow + " This is stored y: " + Data.data.YWindow);

    }
    // Contains the user zoom setting
    @FXML
    private Slider zoom;

    // Contains the user email entry
    @FXML
    private JFXTextField email;

    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;

    // Contains the desired user destination
    @FXML
    private TextField destination;

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
    }

    @FXML
    public void changeFloorL2() {
        map.setImage(Data.data.L2Floor);
    }

    @FXML
    public void changeFloor1() {
        map.setImage(Data.data.firstFloor);
    }

    @FXML
    public void changeFloor2() {
        map.setImage(Data.data.secondFloor);
    }

    @FXML
    public void changeFloor3() {
        map.setImage(Data.data.thirdFloor);
    }

    @FXML
    public void changeFloorG() {
        map.setImage(Data.data.GFloor);
    }


    public void setSearch(String s){
        this.destination.setText(s);
    }

    @FXML
    public void dragMap(){
        //map.setX();
        //map.setY();
    }

    // Purpose: Zoom the map ImageView
    @FXML
    public void zoomMap(){
        System.out.println("Zooming ");
        double sliderSetting = zoom.getValue();
        map.setScaleX(sliderSetting * 0.1);
        map.setScaleY(sliderSetting * 0.1);
    }

    // The go button next to the destination text field, starts pathfinding algorithm, direction print, map drawing

    public void findPath(String in) throws IOException {
        //Returns
        long st = System.currentTimeMillis();
        this.path= SearchEngine.SearchPath(in,CurMap,Kiosk);
        long et = System.currentTimeMillis();
        System.out.println(et-st+"<===ALGO");

        st = System.currentTimeMillis();
        MultiFloorPathDrawing(path);
        et = System.currentTimeMillis();
        System.out.println(et-st+"<===DR");
    }
    @FXML
    public void go() throws IOException,InterruptedException{
        findPath(destination.getText());
    }

    // Method to clear the path on the map when the user presses clear map
    @FXML
    public void clear() throws FileNotFoundException{
        map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
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
        //go();
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

    public Vector<Vector<Node>> seperator(Vector<Node> path){
        Vector<Vector<Node>> paths = new Vector<Vector<Node>>();// make one path per floor
        //Vector<String> floors = new Vector<String>();
        Vector<Node> ogFloor = new Vector<Node>();//create the path for the current floor
        String floor = path.elementAt(0).getFloor();
        //floors.add(path.elementAt(0).getFloor());
        for (Node i : path) {
            //if the node is an elevator then we will switch floors
            if(i.getNodeType().equals("ELEV") || i.getNodeType().equals("STAI")){
                ogFloor.add(i);
                paths.add(ogFloor);
                ogFloor.clear();
            }else{
                ogFloor.add(i);
            }
        }
        if(ogFloor.size()>0){
            paths.add(ogFloor);
        }
        return paths;
    }
    // Purpose: Insert a path of nodes that are only on ONE floor, draws the path on that floor
    @FXML
    public void MultiFloorPathDrawing(Vector<Node> path) throws IOException{
        // Possible floors (in order): L2, L1, 0G, 01, 02, 03
        System.out.println("Reached the multifloor path drawing function");
        Vector<Vector<Node>> paths = seperator(path);
        /*
        for (Vector<Node> i: paths) {
            for (Node j: i){
                System.out.println(j.getLongName());
            }

        }*/
        //Vector<Vector<Node>> paths = new Vector<Vector<Node>>();
        //paths.add(path);

        for(Vector<Node> floor: paths){
            switch(floor.elementAt(0).getFloor()){
                case "L2":
                    BufferedImage lowerLevel2Floor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/00_thelowerlevel2_blank.png"));
                    Data.data.L2Floor = testDrawDirections(floor, lowerLevel2Floor);
                    break;
                case "L1":
                    BufferedImage lowerLevel1Floor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/00_thelowerlevel1_blank.png"));
                    Data.data.L1Floor = testDrawDirections(floor, lowerLevel1Floor);
                    break;
                case "0G":
                    BufferedImage groundFloor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/00_thegroundfloor_blank.png"));
                    Data.data.GFloor = testDrawDirections(floor, groundFloor);
                    break;
                case "1":
                case "01":
                    System.out.println("Reached case statement");
                    BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/01_thefirstfloor_blank.png"));
                    Data.data.firstFloor = testDrawDirections(floor, firstFloor);
                    break;
                case "2":
                case "02":
                    BufferedImage secondFloor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/02_thesecondfloor_blank.png"));
                    Data.data.secondFloor = testDrawDirections(floor, secondFloor);
                    break;
                case "3":
                case "03":
                    BufferedImage thirdFloor = ImageIO.read(getClass().getResource("/sample/Map_Pictures/02_thethirdfloor_blank.png"));
                    Data.data.thirdFloor = testDrawDirections(floor, thirdFloor);
                    break;
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

        Data.data.currentMap = "path" + nameDept + "-" + nameDest;
        // Setting up the proper color settings
        pathImage.setStroke(new BasicStroke(10)); // Controlling the width of the shapes drawn
        // Iterate through all the path nodes to draw the path
        for(int i = 0; i < length ; i++) {
            Node node = path.get(i);
            if(i + 1 < length){
                Node node2 = path.get(i+1);
                // Lines are drawn offset,
                pathImage.setColor( new java.awt.Color(0,0,0)); // This color is black
                pathImage.drawLine(node.getxCoordinate(), node.getyCoordinate(),node2.getxCoordinate() ,node2.getyCoordinate());
            }
        }
        map.setImage(SwingFXUtils.toFXImage(floorImage,null));
        System.out.println("Image set on map");
        return SwingFXUtils.toFXImage(floorImage,null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
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

}
