package sample;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;

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



    //to login from navigation screen
    @FXML
    public void login(){
        Main.loginScreen();
    }

    @FXML
    public void help(){Main.genErrorScreen();}

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
    public static JFXTextField destination;

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


    public void setMap(Map m){
        this.CurMap = m;
        //System.out.println("KSJHDFUZBXCGV"+CurMap.getNodes().size());
    }

    public void setKiosk(Node k){
        this.Kiosk = k;
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
        testDrawDirections(path);
        et = System.currentTimeMillis();
        System.out.println(et-st+"<===DR");
    }
    @FXML
    public void go() throws IOException,InterruptedException{
        email.setVisible(true);
        sendButton.setVisible(true);
        directionSteps.setVisible(true);
        sendLabel.setVisible(true);
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

    // Purpose: Draw a path on the map
    @FXML
    public void testDrawDirections(Vector<Node> path) throws IOException {
        BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/UI/Icons/01_thefirstfloor.png"));
        Graphics2D pathImage = firstFloor.createGraphics();
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
        map.setImage(SwingFXUtils.toFXImage(firstFloor,null));
        System.out.println("Image set on map");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

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

            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    public void autoComplete(){
        searchList.setVisible(true);
        SortedSet<String> entries = new TreeSet<String>();
        Collections.addAll(entries, "Bridge to Dana-Farber Cancer Institute", "Brigham Circle Medical Associates", "Center for Infertility and Reproductive Surgery",
                "Clinical Trials", "Conference Center","Dialysis,", "Dialysis Waiting Room", "Fetal Med & Genetics", "General Surgical Specialties Suite A",
                "General Surgical Specialties Suite B", "Gynecology", "Gyencology Oncology MIGS", "Innovation Hub", "Maternal Fetal Practice",
                "MICU 3B/C Waiting Room", "OB/GYN Blood Lab", "Obstetrics", "The Porch", "Reproductive Endocrine Labs", "Urology", "Watkins Clinic C", "Bridge to Children's",
                "Brigham Health", "Carrie M. Hall Conference Center", "Chest Diseases", "Coffee Connection", "Comprehensive Breast Health", "Conference Center", "Duncan Reid Conference Room",
                "Ear, Nose, & Throat", "Endoscopy", "Garden Cafe", "Gift Shop", "Jen Center for Primary Care", "Lee Bell Breast Center", "Louis Bornstein Family Amphitheater",
                "Medical Surgical Specialties", "MRI Associates", "Oral Medicine and Dentistry", "Orthopedics and Rhematology", "Outpatient Specimen Collection",
                "Pat's Place", "Patient Financial Services", "Plastic Surgery", "Thoracic Surgery Clinic", "Vascular Diagnostic Lab", "Watkins A", "Watkins B",
                "Weiner Center for Preoperative Evaluation", "Ambulatory X-Ray", "Asthma Research Center", "Au Bon Pain", "Bretholtz Center for Patients and Families", "CART Waiting",
                "Connor's Center Security Desk", "CPE Classroom", "International Patient Center", "Kessler Library", "MS Waiting", "Multifaith Chapel", "Neuroscience Waiting Room",
                "Obstetrics Admitting", "Occupational Health", "Partner's Shuttle", "Rehabilitation Services", "Shapiro Board Room", "Sharf Admitting Center", "Spiritual Care Office",
                "Wound Care Center Ambulatory Treatment Room", "Zinner Breakout Room", "Cardiac Stress Test Lab", "Cardiovascular Imaging Center", "CVRR", "Interpreter Services",
                "MRI/CT Scan Imaging", "Radiation Oncology", "Radiation Oncology Conference Room", "Radiation Oncology T/X Suite", "Abrams Conference Room", "Anesthesia Conference Room", "CSIR MRI",
                "Day Surgery Family Waiting", "Helen Hogan Conference Room", "Medical Records Conference Room", "Medical Records Film Library", "Nuclear Medicine",
                "Outpatient Fluoroscopy", "Pre-OP PACU", "Ultrasound", "Volunteers", "Infusion", "Neuro Testing", "Outpatient Plebotomy");
        AutoCompleteTextField auto = new AutoCompleteTextField(entries);
        auto.setPopupHidden(true);
        SimpleListProperty filteredEntries = new SimpleListProperty(auto.getFilteredEntries());
        searchList.itemsProperty().bind(filteredEntries);
    }

    public void autoClose(){
        searchList.setVisible(false);
    }
}
