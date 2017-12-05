package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;


public class AdminPageController implements Initializable{

    @FXML
    private SplitMenuButton algoMenu;
    @FXML
    private JFXButton upFloor, downFloor;
    @FXML
    private JFXTextField destination;
    @FXML
    private Label floorLabel;
    @FXML
    private ScrollPane scrollMap;

    @FXML
    private ImageView map;
    @FXML
    private JFXButton upButton, downButton;
    @FXML
    private String currentFloor = "First Floor";
    @FXML
    private SplitMenuButton quickFloor;
    @FXML
    private JFXListView threeList, twoList, oneList, groundList, lowerTwoList, lowerOneList;
    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab floorOne;
    @FXML
    private JFXCheckBox stairs, elevator;
    private Vector<Node> path = new Vector<Node>();
    private Map CurMap;
    private Node Kiosk;

    @FXML
    public void logout(){
        Main.startScreen();
    }

    @FXML
    public void edit(){
        Main.mapEditScreen();
    }

    @FXML
    public void serviceRequest() {Main.serviceScreen();}
    @FXML
    public void acceptRequest() {Main.acceptScreen();}

    public void setMap(Map m){
        this.CurMap = m;
        //System.out.println("KSJHDFUZBXCGV"+CurMap.getNodes().size());
    }

    public void setKiosk(Node k){
        this.Kiosk = k;
    }

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void editUsers(){Main.editUsersScreen();}
    @FXML
    public void back(){Main.startScreen();}
    @FXML
    public void help(){Main.genErrorScreen();}

    @FXML
    public void setAlgorithm(){}

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            zoom();
            scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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

            //populating list -- ground
            ObservableList<String> groundItems = FXCollections.observableArrayList("Infusion", "Neuro Testing", "Outpatient Plebotomy");
            groundList.setItems(groundItems);
            tabPane.getSelectionModel().select(floorOne);
            stairs.setSelected(true);
            elevator.setSelected(true);
            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public void findPath(String in) throws IOException {
        //Returns
        long st = System.currentTimeMillis();
        //this.path= SearchEngine.SearchPath(in,CurMap,Kiosk);
        long et = System.currentTimeMillis();


        st = System.currentTimeMillis();
        MultiFloorPathDrawing(path);
        et = System.currentTimeMillis();

    }
    @FXML
    public void go() throws IOException,InterruptedException{
        clear();
        findPath(destination.getText());
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
        Vector<Vector<Node>> paths = new Vector<Vector<Node>>();// make one path per floor
        Vector<Node> ogFloor = new Vector<Node>();//create the path for the current floor
        //String floor = path.elementAt(0).getFloor();
        for (Node i : path) {
            //if the node is an elevator then we will switch floors
            if((ogFloor.size() > 0) &&(i.getNodeType().equals("ELEV") || i.getNodeType().equals("STAI"))){
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
    public Vector<Vector<Node>> separator2(Vector<Node> path){
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

        }
        // Possible floors (in order): L2, L1, 0G, 01, 02, 03

        Vector<Vector<Node>> paths = separator2(path);

       
        //Vector<Vector<Node>> paths = new Vector<Vector<Node>>();
        //paths.add(path);

        for(Vector<Node> floorPath: paths){
            if (floorPath.size() > 0) {
                //System.out.println("This is the node floor: " + floorPath.elementAt(0).getFloor().replaceAll("\\s+", ""));
                String pathFloor = floorPath.elementAt(0).getFloor().replaceAll("\\s+", "");
                if (pathFloor.equals("L2")) {
                    Data.data.L2Floor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.L2Floor, null));
                } else if (pathFloor.equals("L1")) {
                    Data.data.L1Floor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.L1Floor, null));
                } else if (pathFloor.equals("0G") || pathFloor.equals("G")) {
                    Data.data.GFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.GFloor, null));
                } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                    Data.data.firstFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.firstFloor, null));
                } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                    Data.data.secondFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.secondFloor, null));
                } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                    Data.data.thirdFloor = testDrawDirections(floorPath, SwingFXUtils.fromFXImage(Data.data.thirdFloor, null));
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
            if(i + 1 < length){
                Node node2 = path.get(i+1);
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
    }*/

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

            startLabel.setText(SearchEngine.SearchPath(destination.getText(),CurMap,Kiosk).getLongName());
        }
        else{

            endLabel.setText(SearchEngine.SearchPath(destination.getText(),CurMap,Kiosk).getLongName());
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
