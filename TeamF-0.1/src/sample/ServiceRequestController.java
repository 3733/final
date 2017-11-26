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


public class ServiceRequestController implements Initializable{


    //top menu bar
    @FXML
    public void backToStart() { Main.startScreen();}

    @FXML
    public void backToAdmin() {Main.adminScreen();}

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void help(){Main.genErrorScreen();}

    @FXML
    public void logout(){Main.startScreen();}


    public static int ID = 1;   //service ID counter
    Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
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
    public void updateAssistance(){                                      //when a request tab is opened
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
    public void assistanceSendRequest() throws MissingFieldException{    //when the Send button is pressed
        AssistanceRequest newAssist = new AssistanceRequest(assistanceNode, assistanceDescription.getText(),
                Integer.parseInt(assistanceID.getText()), assistanceTime.getValue().format(formatter), "",
                "", 0000, "assistance", "unaccepted",
                Integer.parseInt(assistanceUrgency.getText()));

        requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addAssistanceRequest(newAssist);

        assistanceTime.setValue(null);            //clears textfields
        assistanceUrgency.clear();
        assistanceDescription.clear();
        assistanceID.setText(Integer.toString(++ID));   //increments and sets correct service ID

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
    public void updateFood(){
        foodID.setText(Integer.toString(ID));

        foodMenu.setItems(FXCollections.observableArrayList(
                "Apple pie", "Banana", "Catfish soup", "Chicken parmesan", "Chocolate cake", "Lasagna",
                "Loaf of bread", "Lobster casserole", "Mashed potatoes", "Olive pizza", "Orange juice", "Oreos",
                "Popcorn shrimps", "Sardines", "Smoked salmon", "Steak with lamb sauce", "Tuna potato", "Water"));
    }

    private Node foodNode;

    @FXML
    public void foodThisLocation() {
        foodNode = n1;
    }

    @FXML
    public void foodChooseLocation() {}

    @FXML
    public void foodSendRequest() throws MissingFieldException{
        FoodRequest newFood = new FoodRequest(foodNode, foodDescription.getText(), Integer.parseInt(foodID.getText()),
                foodTime.getValue().format(formatter),"", "", 0000, "food",
                "unaccepted", foodPatient.getText(), foodServingTime.getValue().format(formatter),
                (String)foodMenu.getValue());

        requestList.add(newFood);
        testEmbeddedDB.addFoodRequest(newFood);

        foodTime.setValue(null);
        foodPatient.clear();
        foodServingTime.setValue(null);
        foodDescription.clear();
        foodID.setText(Integer.toString(++ID));

        refreshTable();
    }


    //transport requests
    @FXML Tab transportPane;

    @FXML
    private Label transportID;

    @FXML
    private JFXTimePicker transportTime;

    @FXML
    private JFXTextField transportPatient;

    @FXML
    private JFXTextField transportType;

    @FXML
    private TextArea transportDescription;

    @FXML
    public void updateTransport(){
        transportID.setText(Integer.toString(ID));
    }

    private Node transportNode;

    @FXML
    public void transportThisLocation() {
        transportNode = n1;
    }

    @FXML
    public void transportChooseLocation() {}

    @FXML
    public void transportSendRequest() throws MissingFieldException{
        ArrayList<Integer> transportingEmployees = new ArrayList<Integer>();
        TransportRequest newTransport = new TransportRequest(transportNode, transportDescription.getText(),
                Integer.parseInt(transportID.getText()), transportTime.getValue().format(formatter), "",
                "", 0000, "transport", "unaccepted",false,
                transportPatient.getText(), transportType.getText());

        requestList.add(newTransport);
        testEmbeddedDB.addTransportRequest(newTransport);

        transportTime.setValue(null);
        transportPatient.clear();
        transportType.clear();
        transportDescription.clear();
        transportID.setText(Integer.toString(++ID));

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
    public void updateClean(){
        cleanID.setText(Integer.toString(ID));
    }

    private Node cleanNode;

    @FXML
    public void cleanThisLocation() {
        cleanNode = n1;
    }

    @FXML
    public void cleanChooseLocation() {}

    @FXML
    public void cleanSendRequest() throws MissingFieldException{
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
        cleanID.setText(Integer.toString(++ID));

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
    public void updateSecurity(){
        securityID.setText(Integer.toString(ID));
    }

    private Node securityNode;

    @FXML
    public void securityThisLocation() {
        securityNode = n1;
    }

    @FXML
    public void securityChooseLocation() {}

    @FXML
    public void securitySendRequest() throws MissingFieldException{

        SecurityRequest newSecurity = new SecurityRequest(securityNode, securityDescription.getText(),
                Integer.parseInt(securityID.getText()), securityTime.getValue().format(formatter), "",
                "", 0000, "security", "unaccepted",
                Integer.parseInt(securityUrgency.getText()));

        requestList.add(newSecurity);
        testEmbeddedDB.addSecurityRequest(newSecurity);

        securityTime.setValue(null);
        securityUrgency.clear();
        securityDescription.clear();
        securityID.setText(Integer.toString(++ID));

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
    public void updateIt(){
        itID.setText(Integer.toString(ID));
    }

    @FXML
    public void itSendRequest() throws MissingFieldException{
        ArrayList<Integer> itEmployees = new ArrayList<Integer>();
        ItRequest newIt = new ItRequest(n1, itDescription.getText(),
                Integer.parseInt(itID.getText()), itTime.getValue().format(formatter), "", "",
                0000, "it", "unaccepted", Integer.parseInt(itUrgency.getText()));

        requestList.add(newIt);
        testEmbeddedDB.addItRequest(newIt);

        itTime.setValue(null);
        itUrgency.clear();
        itDescription.clear();
        itID.setText(Integer.toString(++ID));

        refreshTable();
    }



    //viewable list of requests

    @FXML
    private TableView<ServiceRequest> tableView;

    @FXML
    private TableColumn<ServiceRequest, String> requests;

    @FXML
    private JFXButton refrrresh;

    @FXML
    private TableColumn<ServiceRequest, String> status;

             //formatted list of requests that go into table
    private ObservableList<ServiceRequest> requestObserve = FXCollections.observableArrayList();

             //formats a string into being able to be displayed in table
    public StringProperty stringToStringProperty(String cellEntry) {
        return new SimpleStringProperty(cellEntry);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requests.setCellValueFactory(cellData -> stringToStringProperty(removeWhiteSpace(cellData.getValue().getType())));   //sets service name in column
        status.setCellValueFactory(cellData -> stringToStringProperty(removeWhiteSpace(cellData.getValue().getStatus())));   //sets service status in column
        counter = 0;
    }

    private int counter;

    @FXML
    private JFXButton deletebutt;

    @FXML
    private ImageView icon;

    public void acceptRequest() throws InvalidEmailException, IOException {
        ServiceRequest requestSelected =  tableView.getSelectionModel().getSelectedItem();  //gets the selected service


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");

        requestSelected.acceptRequest();
        requestSelected.setAcceptTime(ft.format(date));

        //links the service request with updated status and acceptTime to a staff member in the database
        testEmbeddedDB.addAssignment(requestSelected.getServiceID(), getLoggedInGuy().getEmployeeID(),
                requestSelected.getAcceptTime(), requestSelected.getStatus());

        for(int i = 0; i < requestObserve.size(); i++)             //looks for the selected service in the table
            if(requestSelected.serviceID == (requestObserve.get(i)).serviceID)
                requestObserve.set(i, requestSelected);

        refreshTable();

        String serviceInformation = "Service type: " + requestSelected.getType() + "<br>";
        serviceInformation += "Time created: " + requestSelected.getServiceTime() + "<br>";
        serviceInformation += "Service Location: " + requestSelected.getDestination().getLongName() + "<br>";
        serviceInformation += "Time accepted: " + requestSelected.getAcceptTime() + "<br>";

        if(removeWhiteSpace(requestSelected.getType()).equals("food")) {
            serviceInformation += "Time to be served: " + ((FoodRequest) requestSelected).getServingTime() + "<br>";
            serviceInformation += "Patient name: " + ((FoodRequest) requestSelected).getPatientName() + "<br>";
            serviceInformation += "Meal ordered: " + ((FoodRequest) requestSelected).getFoodOrder() + "<br>";
        }

        if(removeWhiteSpace(requestSelected.getType()).equals("transport")) {
            serviceInformation += "Patient name: " + ((TransportRequest) requestSelected).getPatientName() + "<br>";
            serviceInformation += "Method of transportation: " + ((TransportRequest) requestSelected).getTypeOfTransport() + "<br>";
        }

        if(removeWhiteSpace(requestSelected.getType()).equals("assistance"))
            serviceInformation += "Urgency level: " + ((AssistanceRequest) requestSelected).getUrgency() + "<br>";

        if(removeWhiteSpace(requestSelected.getType()).equals("cleaning"))
            serviceInformation += "Urgency level: " + ((CleaningRequest) requestSelected).getUrgency() + "<br>";

        if(removeWhiteSpace(requestSelected.getType()).equals("security"))
            serviceInformation += "Urgency level: " + ((SecurityRequest) requestSelected).getUrgency() + "<br>";

        if(removeWhiteSpace(requestSelected.getType()).equals("it"))
            serviceInformation += "Urgency level: " + ((ItRequest) requestSelected).getUrgency() + "<br>";

        serviceInformation += "Service Description: " + requestSelected.getDescription() + "<br>";

      //emailing the service request to the employee
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", icon);
        emailService.sendRequestEmail(serviceInformation, "tjaber15@gmail.com");

    }

    public void deleteRequest()
    {
        ObservableList<ServiceRequest> selectedRows, allrequests;
        allrequests = tableView.getItems();

        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the ServiceRequest objects from the table
        for (ServiceRequest req: selectedRows)
        {
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
            req.setFinishTime(ft.format(date));
            req.finishRequest();

            testEmbeddedDB.editCompletionStatus(req.getServiceID(), req.getStatus());
            testEmbeddedDB.editFinishTime(req.getServiceID(), req.getFinishTime());

            allrequests.remove(req);
        }
    }


    @FXML
    public void refreshTable() {
        int i = 0;
        //using data from database
        Vector requestsFromDatabase = testEmbeddedDB.getAllServiceRequests();
        ArrayList<ServiceRequest> arrayOfRequestsFromDatabase = new ArrayList<ServiceRequest>(requestsFromDatabase);

      for (i = counter; i < arrayOfRequestsFromDatabase.size();i++)
          requestObserve.add(arrayOfRequestsFromDatabase.get(i));


/*
        //using local data from array
        for(i = counter; i < requestList.size(); i++)
            requestObserve.add(requestList.get(i));
*/
        counter = i;
        tableView.setItems(requestObserve);
    }


}