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
import javafx.scene.layout.HBox;

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
    private JFXListView<HBox> directionSteps, floorPoints;

    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;
    // Contains the desired user destination
    @FXML
    private JFXTextField destination;
    // Contains the Invalid email error message
    @FXML
    private static Label invalidEmailText;

    //@FXML
    //private JFXRadioButton start, end;

    @FXML
    private JFXTextField startField, endField;

    @FXML
    private ToggleGroup points;

    @FXML
    private String defaultStart;

    @FXML
    private JFXTextField startLabel, endLabel;

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
        startLabel.setText(data.kiosk.getLongName().trim());
        if(data.destinationNode!=null){
            endLabel.setText(data.destinationNode.getLongName().trim());
        }

        setDirectionSteps();
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

    public JFXTextField getStartLabel(){return this.startLabel;}

    public JFXTextField getEndLabel(){return this.endLabel;}

    //public JFXRadioButton getRadioStart(){return this.start;}

    public void setDestination(String s){destination.setText(s);}

    //public JFXRadioButton getRadioEnd(){return this.end;}

    @FXML
    public void closeMenuDrawer(){

    }

    @FXML
    public void settingSearch(){
     /*   if (points.getSelectedToggle() == start) {

            destination.setText(startLabel.getText());

        }
        else{
            destination.setText(endLabel.getText());
        }
        */
    }

    @FXML
    public void setStart(){
        startLabel.setText(data.kiosk.getLongName().trim());
    }


    //setting start and end nodes
    /*@FXML
    public void settingFields() throws IOException, InterruptedException {
        String destinationText = destination.getText();
        if (points.getSelectedToggle() == start) {

            System.out.println("LABEL!!!!!");
            startLabel.setText(SearchEngine.SearchPath(destinationText).getLongName().trim());
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
    }*/

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

    public void setEnd(){
        endLabel.setText(data.destinationNode.getLongName().trim());
    }

    public void setDirectionSteps(){
        directionSteps.setVisible(true);
        directionSteps.setItems(data.directions);
    }

}
