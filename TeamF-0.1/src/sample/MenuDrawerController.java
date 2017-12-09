package sample;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import static sample.Data.data;

public class MenuDrawerController implements Initializable{

    //FXML UI Components

    @FXML
    private Label sendLabel;
    @FXML
    private JFXButton sendButton;
    @FXML
    private javafx.scene.canvas.Canvas pathCanvas;
    @FXML
    private JFXListView directionSteps, floorPoints;

    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;
    // Contains the desired user destination
    @FXML
    private JFXTextField destination;
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

    // Email UI Components
    @FXML
    private JFXTextField email;

    //other components
    @FXML
    private Main mainController;

    private Vector<Node> path = new Vector<Node>();

    private Map CurMap;

    private String filePath = "/sample/UI/Icons/";

    private Vector<String> floorsVisited = new Vector<>();

    private Vector<JFXButton> floorButtons = new Vector<>();

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

        end.setSelected(true);

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

    public void setKiosk(Node k){
        data.kiosk = k;
    }

    public void setSearch(String s){
        this.destination.setText(s);
    }

    public JFXTextField getDestination(){return this.destination;}

    public Label getStartLabel(){return this.startLabel;}

    public Label getEndLabel(){return this.endLabel;}

    public JFXRadioButton getRadioStart(){return this.start;}

    public void setDestination(String s){destination.setText(s);}

    public JFXRadioButton getRadioEnd(){return this.end;}

    @FXML
    public void closeMenuDrawer(){

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
        String destinationText = destination.getText();
        if (points.getSelectedToggle() == start) {

            System.out.println("LABEL!!!!!");
            startLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            if(!destinationText.equals("")&&!startLabel.getText().equals("")) {
                //go();
            }
        }
        else{

            System.out.println("LABEL!!!!!");
            endLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());

            System.out.println(endLabel.getText()+"<=============DESTINATION LABEL");
            destination.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            endLabel.setText(SearchEngine.SearchPath(destinationText,data.graph,data.kiosk).getLongName().trim());
            if(!destinationText.equals("")) {
                //go();
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
        Data.data.currentMap = "L1";
    }

    @FXML
    public void changeFloorL2() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.L2Floor);
        Data.data.currentMap = "L2";
    }

    @FXML
    public void changeFloor1() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,x,y);
        map.setImage(Data.data.firstFloor);
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
        Data.data.currentMap = "3";
    }

    @FXML
    public void changeFloorG() {
        double y = pathCanvas.getHeight();
        double x = pathCanvas.getWidth();
        Data.data.gc.clearRect(0,0,1000,1000);
        map.setImage(Data.data.GFloor);
        Data.data.currentMap = "G";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Change Screen Functions

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Path Finding Functions


    @FXML
    public void clearFields(){
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();
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

    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

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
    public void edit() throws IOException, InterruptedException{
        Main.mapEditScreen();
        clearFields();
        clear();
    }

    // Purpose: Send an email when user clicks send email button
    @FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", map);
        emailService.sendEmail(NavigationPageController.directions(Data.data.path), email.getText());
    }


}
