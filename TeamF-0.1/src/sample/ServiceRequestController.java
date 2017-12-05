/*It Request Work - Floris and Steph
* Purpose: to add additional Request for IT ServiceRequestell */


package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.testEmbeddedDB;

//import javax.xml.ws.Service;   <--doesn't work on Talal's computer
import java.net.URL;
import java.util.*;
import java.text.*;

import static sample.Main.getLoggedInGuy;


public class ServiceRequestController implements Initializable {


    //top menu bar
    @FXML
    public void backToStart() {
        Main.startScreen();
    }

    @FXML
    public void backToAdmin() {
        Main.adminScreen();
    }

    private Main mainController;

    public void setMainController(Main main) {
        this.mainController = main;
    }

    @FXML
    public void help() {
        Main.setHelpScreenServiceRequestScreen();
    }

    @FXML
    public void logout() {
        Main.startScreen();
    }

    @FXML
    public void toServiceAccept() {
        Main.acceptScreen();
    }

    public static int ID = 0;   //service ID counter
    Node n1 = new Node("FDEPT00101", 1614, 829, "1", "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");      //for formatting the time functions
    ArrayList<ServiceRequest> requestList = new ArrayList<ServiceRequest>();  //list to hold local service requests

    //fixes issue with database that creates unnecessary spaces in strings
    public String removeWhiteSpace(String input) {
        return input.replace(" ", "");
    }

    //assistance requests
    @FXML
    private Tab assistancePane;

    @FXML
    private Label assistanceID;

    @FXML
    private JFXTimePicker assistanceTime;

    @FXML
    private JFXTextField assistanceUrgency;

    @FXML
    private TextArea assistanceDescription;

    @FXML
    public void updateAssistance() {                                      //when a request tab is opened
        assistanceID.setText(Integer.toString(ID));                      //sets correct service ID
    }

    private Node assistanceNode;

    @FXML
    public void assistanceThisLocation() {
        assistanceNode = n1;
    }

    @FXML
    public void assistanceChooseLocation() {
    }

    @FXML
    public void assistanceSendRequest() throws MissingFieldException {    //when the Send button is pressed
        AssistanceRequest newAssist = new AssistanceRequest(assistanceNode, assistanceDescription.getText(),
                Integer.parseInt(assistanceID.getText()), assistanceTime.getValue().format(formatter), "",
                "", 0000, "assistance", "unaccepted",
                Integer.parseInt(assistanceUrgency.getText()));

        requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addAssistanceRequest(newAssist);

        assistanceTime.setValue(null);            //clears textfields
        assistanceUrgency.clear();
        assistanceDescription.clear();
        ID++;
        assistanceID.setText(Integer.toString(ID));   //increments and sets correct service ID

        refreshTable();
    }


    //food requests
    @FXML
    private Tab foodPane;

    @FXML
    private Label foodID;

    @FXML
    private JFXTimePicker foodTime;

    @FXML
    private JFXTextField foodPatient;

    @FXML
    private JFXTimePicker foodServingTime;

    @FXML
    private ChoiceBox foodMenu;

    @FXML
    private TextArea foodDescription;

    @FXML
    public void updateFood() {
        foodID.setText(Integer.toString(ID));

        foodMenu.setItems(FXCollections.observableArrayList(
                "Apple pie", "Banana", "Catfish soup", "Chicken parmesan", "Chocolate cake", "Hamburger", "Lasagna",
                "Loaf of bread", "Lobster casserole", "Mashed potatoes", "Olive pizza", "Orange juice", "Oreos",
                "Popcorn shrimps", "Sardines", "Smoked salmon", "Steak with lamb sauce", "Tuna potato", "Water"));
    }

    private Node foodNode;

    @FXML
    public void foodThisLocation() {
        foodNode = n1;
    }

    @FXML
    public void foodChooseLocation() {
    }

    @FXML
    public void foodSendRequest() throws MissingFieldException {
        FoodRequest newFood = new FoodRequest(foodNode, foodDescription.getText(), Integer.parseInt(foodID.getText()),
                foodTime.getValue().format(formatter), "", "", 0000, "food",
                "unaccepted", foodPatient.getText(), foodServingTime.getValue().format(formatter),
                (String) foodMenu.getValue());

        requestList.add(newFood);
        testEmbeddedDB.addFoodRequest(newFood);

        foodTime.setValue(null);
        foodPatient.clear();
        foodServingTime.setValue(null);
        foodDescription.clear();
        ID++;
        foodID.setText(Integer.toString(ID));

        refreshTable();
    }


    //transport requests
    @FXML
    Tab transportPane;

    @FXML
    private Label transportID;

    @FXML
    private JFXTimePicker transportTime;

    @FXML
    private JFXTextField transportPatient;

    @FXML
    private ChoiceBox transportMenu;

    @FXML
    private TextArea transportDescription;

    @FXML
    public void updateTransport() {
        transportID.setText(Integer.toString(ID));

        transportMenu.setItems(FXCollections.observableArrayList(
                "Wheelchair", "Stretcher"));
    }

    private Node transportNode;

    @FXML
    public void transportThisLocation() {
        transportNode = n1;
    }

    @FXML
    public void transportChooseLocation() {
    }

    @FXML
    public void transportSendRequest() throws MissingFieldException {
        ArrayList<Integer> transportingEmployees = new ArrayList<Integer>();
        TransportRequest newTransport = new TransportRequest(transportNode, transportDescription.getText(),
                Integer.parseInt(transportID.getText()), transportTime.getValue().format(formatter), "",
                "", 0000, "transport", "unaccepted", false,
                transportPatient.getText(), (String) transportMenu.getValue());

        requestList.add(newTransport);
        testEmbeddedDB.addTransportRequest(newTransport);

        transportTime.setValue(null);
        transportPatient.clear();
        transportDescription.clear();
        ID++;
        transportID.setText(Integer.toString(ID));

        refreshTable();
    }


    //cleaning requests
    @FXML
    private Tab cleanPane;

    @FXML
    private Label cleanID;

    @FXML
    private JFXTimePicker cleanTime;

    @FXML
    private JFXTextField cleanUrgency;

    @FXML
    private TextArea cleanDescription;

    @FXML
    public void updateClean() {
        cleanID.setText(Integer.toString(ID));
    }

    private Node cleanNode;

    @FXML
    public void cleanThisLocation() {
        cleanNode = n1;
    }

    @FXML
    public void cleanChooseLocation() {
    }

    @FXML
    public void cleanSendRequest() throws MissingFieldException {
        ArrayList<Integer> cleaningEmployees = new ArrayList<Integer>();
        CleaningRequest newClean = new CleaningRequest(cleanNode, cleanDescription.getText(),
                Integer.parseInt(cleanID.getText()), cleanTime.getValue().format(formatter), "", "",
                0000, "cleaning", "unaccepted",
                Integer.parseInt(cleanUrgency.getText()));

        requestList.add(newClean);
        testEmbeddedDB.addCleaningRequest(newClean);

        cleanTime.setValue(null);
        cleanUrgency.clear();
        cleanDescription.clear();
        ID++;
        cleanID.setText(Integer.toString(ID));

        refreshTable();
    }


    //security requests
    @FXML
    private Tab securityPane;

    @FXML
    private Label securityID;

    @FXML
    private JFXTimePicker securityTime;

    @FXML
    private JFXTextField securityUrgency;

    @FXML
    private TextArea securityDescription;

    @FXML
    public void updateSecurity() {
        securityID.setText(Integer.toString(ID));
    }

    private Node securityNode;

    @FXML
    public void securityThisLocation() {
        securityNode = n1;
    }

    @FXML
    public void securityChooseLocation() {
    }

    @FXML
    public void securitySendRequest() throws MissingFieldException {

        SecurityRequest newSecurity = new SecurityRequest(securityNode, securityDescription.getText(),
                Integer.parseInt(securityID.getText()), securityTime.getValue().format(formatter), "",
                "", 0000, "security", "unaccepted",
                Integer.parseInt(securityUrgency.getText()));

        requestList.add(newSecurity);
        testEmbeddedDB.addSecurityRequest(newSecurity);

        securityTime.setValue(null);
        securityUrgency.clear();
        securityDescription.clear();
        ID++;
        securityID.setText(Integer.toString(ID));

        refreshTable();
    }


    //it requests
    @FXML
    private Tab itPane;

    @FXML
    private TextArea itDescription;

    @FXML
    private JFXTextField itUrgency;

    @FXML
    private Label itID;

    @FXML
    private JFXTimePicker itTime;

    @FXML
    public Label missingField;

    @FXML
    public void updateIt() {
        itID.setText(Integer.toString(ID));
    }

    @FXML
    public void itSendRequest() throws MissingFieldException {
        ArrayList<Integer> itEmployees = new ArrayList<Integer>();
        ItRequest newIt = new ItRequest(n1, itDescription.getText(),
                Integer.parseInt(itID.getText()), itTime.getValue().format(formatter), "", "",
                0000, "it", "unaccepted", Integer.parseInt(itUrgency.getText()));

        requestList.add(newIt);
        testEmbeddedDB.addItRequest(newIt);

        itTime.setValue(null);
        itUrgency.clear();
        itDescription.clear();
        ID++;
        itID.setText(Integer.toString(ID));

        refreshTable();
    }


    //viewable list of unfinished requests

    @FXML
    private TableView<ServiceRequest> tableView;

    @FXML
    private TableColumn<ServiceRequest, String> requests;

    @FXML
    private TableColumn<ServiceRequest, String> status;

    @FXML
    private JFXButton refrrresh;



    //formatted list of requests that go into table
    private ObservableList<ServiceRequest> requestObserve = FXCollections.observableArrayList();

    //formats a string into being able to be displayed in table
    public StringProperty stringToStringProperty(String cellEntry) {
        return new SimpleStringProperty(cellEntry);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requests.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getType()).trim()));   //sets service name in column
        status.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getStatus()).trim()));   //sets service status in column
        refreshTable();
    }


    @FXML
    public void refreshTable() {
        //using data from database
        Vector requestsFromDatabase = testEmbeddedDB.getAllServiceRequests();
        ArrayList<ServiceRequest> arrayOfRequestsFromDatabase = new ArrayList<ServiceRequest>(requestsFromDatabase);

        for (int i = 0; i < arrayOfRequestsFromDatabase.size(); i++) {   //searches through the list from the database
            boolean alreadyInTable = false;
                for (int j = 0; j < requestObserve.size(); j++) {        //searches through the list in the table
                    if ((arrayOfRequestsFromDatabase.get(i)).getServiceID() == (requestObserve.get(j)).getServiceID()) {  //if the service is already in the table
                        alreadyInTable = true;
                        if (((arrayOfRequestsFromDatabase.get(i)).getStatus()).trim().equals("accepted"))   //updates services in table with any possible changes
                            requestObserve.set(j, arrayOfRequestsFromDatabase.get(i));
                        if (((arrayOfRequestsFromDatabase.get(i)).getStatus()).trim().equals("finished"))
                            requestObserve.remove(j);
                    }
                }
                if ( !alreadyInTable && !((((arrayOfRequestsFromDatabase.get(i)).getStatus()).trim()).equals("finished")))
                    requestObserve.add(arrayOfRequestsFromDatabase.get(i));
        }

        tableView.setItems(requestObserve);
    }
}
