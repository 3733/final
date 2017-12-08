package sample;

import com.jfoenix.controls.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Data.data;

public class MenuDrawerController implements Initializable{

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
    private JFXTextField destination;
    // Contains stairs option

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
    private VBox adminBox;

    @FXML
    private Main mainController;

    @FXML
    private JFXListView pointsList;


    private Vector<String> floorsVisited = new Vector<>();

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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization and Start

    //Purpose: Initialize all the UI components
    @Override
    public void initialize(URL location, ResourceBundle resources){

        /*groundList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                destination.setText(newValue);
            }
        });
*/

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setMainController(Main in){
        mainController = in;
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
        //startLabel.setText(t);
    }

    public Node getKiosk(){
        return data.kiosk;
    }

    //setting start and end nodes
    @FXML
    public void settingFields() throws IOException, InterruptedException {
        searchList.setVisible(false);
        String destinationText = destination.getText();
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


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Screen Functions


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Finding Functions




    @FXML
    public void clearFields(){
        sendLabel.setVisible(false);
        email.setVisible(false);
        sendButton.setVisible(false);
        directionSteps.setVisible(false);
        endLabel.setText("");
        startLabel.setText("Lower Pike Hallway Exit Lobby");
        destination.setText("");
        directionSteps.getItems().clear();
    }

    @FXML
    public void go() throws IOException,InterruptedException{
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

        floorsVisited.clear();
        for(Vector<Node> floorPath: paths){
            if (floorPath.size() > 0) {
                String pathFloor = floorPath.elementAt(0).getFloor().replaceAll("\\s+", "");
                if (pathFloor.equals("L2")) {
                    data.pathL2 = floorPath;
                    floorsVisited.add("L2");
                } else if (pathFloor.equals("L1")) {
                    data.pathL1 = floorPath;
                    floorsVisited.add("L1");
                } else if (pathFloor.equals("0G") || pathFloor.equals("G")) {
                    data.pathG = floorPath;
                    floorsVisited.add("G");
                } else if (pathFloor.equals("01") || pathFloor.equals("1")) {
                    data.pathFirst = floorPath;
                    floorsVisited.add("1");
                } else if (pathFloor.equals("02") || pathFloor.equals("2")) {
                    data.pathSecond = floorPath;
                    floorsVisited.add("2");
                } else if (pathFloor.equals("03") || pathFloor.equals("3")) {
                    data.pathThird = floorPath;
                    floorsVisited.add("3");
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Zooming Panning & Dragging functions


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Functions

    @FXML
    public void emergencyButton() throws IOException, InterruptedException{
        destination.setText("Exit");
        settingFields();
    }

    // Purpose: Send an email when user clicks send email button
    /*@FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", map);
        emailService.sendEmail(NavigationPageController.directions(data.path), email.getText());
    }*/


/*
    public void reverseNodes() throws IOException, InterruptedException {
        Vector<Node> currentPath = this.path;
        Vector<Node> reversePath = new Vector<Node>();
        int z = 0;
        for(int i = currentPath.size(); i > 0; i--){
            reversePath.add(currentPath.get(i-1));
        }
        this.path = reversePath;
        MultiFloorPathDrawing(this.path);
    }*/

}
