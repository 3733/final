/*It Request Work - Floris and Steph
* Purpose: to add additional Request for IT ServiceRequestell */


package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Main.getLoggedInGuy;


public class ServiceRequestController implements Initializable {

    ObservableList<String> allEntries;
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
        Main.genErrorScreen();
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
    private JFXTextField foodDestination;

    @FXML
    private JFXListView foodSearchList;

    @FXML
    private ChoiceBox foodMenu;

    @FXML
    private TextArea foodDescription;

    @FXML
    public void autoComplete(){
        foodSearchList.setVisible(true);
        ObservableList<String> filteredEntries = FXCollections.observableArrayList("empty");
        //filtering
        foodDestination.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                LinkedList<String> searchResult = new LinkedList<>();
                //Check if the entered Text is part of some entry
                String text = foodDestination.getText();
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
                foodSearchList.setItems(filteredEntries);
            }
        });

        foodSearchList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //System.out.println(newValue);
                foodDestination.setText(newValue);
                foodSearchList.setVisible(false);
            }
        });

    }

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
        foodDestination.setText(foodNode.getLongName());
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
        // All entries
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
        foodSearchList.setItems(allEntries);

        refreshTable();
    }

    public void autoClose(){
        foodSearchList.setVisible(false);
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
