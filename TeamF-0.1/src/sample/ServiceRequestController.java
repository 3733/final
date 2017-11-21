/*It Request Work - Floris and Steph
* Purpose: to add additional Request for IT ServiceRequestell */


package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Properties;



import sample.testEmbeddedDB;

import javax.xml.ws.Service;
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

    public static int ID = 1;   //service ID counter
    Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
    ArrayList<ServiceRequest> requestList = new ArrayList<ServiceRequest>();  //list to hold local service requests

    //assistance requests
    @FXML
    private TitledPane assistancePane;

    @FXML
    private Label assistanceID;

    @FXML
    private Label assistanceTime;

    @FXML
    private TextField assistanceUrgency;

    @FXML
    private TextArea assistanceDescription;

    @FXML
    public void updateAssistance(){                                      //when a request menu is opened
        assistanceID.setText(Integer.toString(ID));                      //sets correct service ID
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");    //for correct time format
        assistanceTime.setText(ft.format(date));;                        //sets current time
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
        ArrayList<Integer> assistingEmployees = new ArrayList<Integer>();
        AssistanceRequest newAssist = new AssistanceRequest(assistanceNode, assistanceDescription.getText(),
                Integer.parseInt(assistanceID.getText()), assistanceTime.getText(), "", "",
                0000, "assistance", "unaccepted",
                Integer.parseInt(assistanceUrgency.getText()));
        requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addAssistanceRequest(newAssist);

        assistanceUrgency.clear();                      //clears textfields
        assistanceDescription.clear();
        ID++;                                           //increments service ID counter
        assistancePane.setExpanded(false);              //closes the request menu

        refreshTable();
    }


    //food requests
    @FXML
    private TitledPane foodPane;

    @FXML
    private Label foodID;

    @FXML
    private Label foodTime;

    @FXML
    private TextField foodPatient;

    @FXML
    private TextField foodServingTime;

    @FXML
    private ChoiceBox foodMenu;

    @FXML
    private TextArea foodDescription;

    @FXML
    public void updateFood(){
        foodID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        foodTime.setText(ft.format(date));;

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
        ArrayList<Integer> foodEmployees = new ArrayList<Integer>();
        FoodRequest newFood = new FoodRequest(foodNode, foodDescription.getText(), Integer.parseInt(foodID.getText()),
                foodTime.getText(),"", "", 0000, "food",
                "unaccepted", foodPatient.getText(), foodServingTime.getText(), (String)foodMenu.getValue());

        requestList.add(newFood);
        testEmbeddedDB.addFoodRequest(newFood);

        foodPatient.clear();
        foodServingTime.clear();
        foodDescription.clear();
        ID++;
        foodPane.setExpanded(false);

        refreshTable();
    }


    //transport requests
    @FXML TitledPane transportPane;

    @FXML
    private Label transportID;

    @FXML
    private Label transportTime;

    @FXML
    private TextField transportPatient;

    @FXML
    private TextField transportType;

    @FXML
    private TextArea transportDescription;

    @FXML
    public void updateTransport(){
        transportID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        transportTime.setText(ft.format(date));;
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
                Integer.parseInt(transportID.getText()), transportTime.getText(), "", "",
                0000, "transport", "unaccepted",false,
                transportPatient.getText(), transportType.getText());
        requestList.add(newTransport);
        testEmbeddedDB.addTransportRequest(newTransport);

        transportPatient.clear();
        transportType.clear();
        transportDescription.clear();
        ID++;
        transportPane.setExpanded(false);

        refreshTable();
    }


    //cleaning requests
    @FXML
    private TitledPane cleanPane;

    @FXML
    private Label cleanID;

    @FXML
    private Label cleanTime;

    @FXML
    private TextField cleanLevel;

    @FXML
    private TextArea cleanDescription;

    @FXML
    public void updateClean(){
        cleanID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        cleanTime.setText(ft.format(date));;
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
                Integer.parseInt(cleanID.getText()), cleanTime.getText(), "", "",0000,
                "cleaning", "unaccepted", Integer.parseInt(cleanLevel.getText()));
        requestList.add(newClean);
        testEmbeddedDB.addCleaningRequest(newClean);

        cleanLevel.clear();
        cleanDescription.clear();
        ID++;
        cleanPane.setExpanded(false);

        refreshTable();
    }


    //security requests
    @FXML
    private TitledPane securityPane;

    @FXML
    private Label securityID;

    @FXML
    private Label securityTime;

    @FXML
    private TextField securityLevel;

    @FXML
    private TextArea securityDescription;

    @FXML
    public void updateSecurity(){
        securityID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        securityTime.setText(ft.format(date));;
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
        ArrayList<Integer> securityEmployees = new ArrayList<Integer>();
        SecurityRequest newSecurity = new SecurityRequest(securityNode, securityDescription.getText(),
                Integer.parseInt(securityID.getText()), securityTime.getText(), "", "",
                0000, "security", "unaccepted",
                Integer.parseInt(securityLevel.getText()));
        requestList.add(newSecurity);
        testEmbeddedDB.addSecurityRequest(newSecurity);

        securityLevel.clear();
        securityDescription.clear();
        ID++;
        securityPane.setExpanded(false);

        refreshTable();
    }


    //it requests
    @FXML
    private TitledPane itPane;

    @FXML
    private TextArea itDescription;

    @FXML
    private TextField itUrgency;

    @FXML
    private Label itID;

    @FXML
    private Label itTime;

    @FXML
    public Label missingField;

    @FXML
    public void updateIt(){
        itID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        itTime.setText(ft.format(date));
    }

    @FXML
    public void itSendRequest() throws MissingFieldException{
        ArrayList<Integer> itEmployees = new ArrayList<Integer>();
        ItRequest newIt = new ItRequest(n1, itDescription.getText(),
                Integer.parseInt(itID.getText()), itTime.getText(), "", "",0000,
                "it", "unaccepted", Integer.parseInt(itUrgency.getText()));
        requestList.add(newIt);
        testEmbeddedDB.addItRequest(newIt);

        itUrgency.clear();
        itDescription.clear();
        ID++;
        itPane.setExpanded(false);

        refreshTable();
    }



    //viewable list of requests

    @FXML
    private TableView<ServiceRequest> tableView;

    @FXML
    private TableColumn<ServiceRequest, String> requests;

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
        requests.setCellValueFactory(cellData -> stringToStringProperty(cellData.getValue().getType().replace(" ", "")));   //sets service name in column
        status.setCellValueFactory(cellData -> stringToStringProperty(cellData.getValue().getStatus().replace(" ", "")));   //sets service status in column
        counter = 0;
    }

    private int counter;


    public void acceptRequest()
    {
        ServiceRequest requestSelected =  tableView.getSelectionModel().getSelectedItem();  //gets the selected service


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");

        requestSelected.acceptRequest();
        requestSelected.setAcceptTime(ft.format(date));

        //links the service request with updated status and acceptTime to a staff member in the database
        testEmbeddedDB.addAssignment(requestSelected.getServiceID(), getLoggedInGuy().getEmployeeID(),
                requestSelected.getAcceptTime(), requestSelected.getStatus());

        for(int i = 0; i < requestObserve.size(); i++){             //looks for the selected service in the table
            if(requestSelected.serviceID == (requestObserve.get(i)).serviceID)
                requestObserve.set(i, requestSelected);
        }
        refreshTable();

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

      for (i = counter; i < arrayOfRequestsFromDatabase.size();i++) {
          requestObserve.add(arrayOfRequestsFromDatabase.get(i));
      }

/*
        //using local data from array
        for(i = counter; i < requestList.size(); i++)
            requestObserve.add(requestList.get(i));
*/
        counter = i;
        tableView.setItems(requestObserve);
    }


}