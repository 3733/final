/*It Request Work - Floris and Steph
* Purpose: to add additional Request for IT ServiceRequestell */


package sample.controllers;

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
import java.util.Properties;


import sample.*;

import javax.xml.ws.Service;
import java.net.URL;
import java.util.*;
import java.text.*;


public class ServiceRequestController implements Initializable{

    //top menu bar
    @FXML
    public void backToStart() { Main.startScreen();}

    @FXML
    public void backToAdmin() {Main.adminScreen();}


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
    public void assistanceSendRequest() throws MissingFieldException {    //when the Send button is pressed
        AssistanceRequest newAssist = new AssistanceRequest(assistanceNode, assistanceDescription.getText(),
                Integer.parseInt(assistanceID.getText()), assistanceTime.getText(), 00000,
                "assistance", Integer.parseInt(assistanceUrgency.getText()));
        requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addAssistanceRequest(newAssist);
        // tableView.getItems().add(newAssist);
        System.out.println(newAssist.typeOfRequest);

        assistanceUrgency.clear();                      //clears textfields
        assistanceDescription.clear();
        ID++;                                           //increments service ID counter
        assistancePane.setExpanded(false);              //closes the request menu
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
    private TextField foodOrder;

    @FXML
    private TextArea foodDescription;

    @FXML
    public void updateFood(){
        foodID.setText(Integer.toString(ID));
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("h:mm a");
        foodTime.setText(ft.format(date));;
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
                foodTime.getText(), 00000, "food", foodPatient.getText(),
                        foodServingTime.getText(), foodOrder.getText());
        requestList.add(newFood);
        testEmbeddedDB.addFoodRequest(newFood);
       // tableView.getItems().add(newFood);

        foodPatient.clear();
        foodServingTime.clear();
        foodOrder.clear();
        foodDescription.clear();
        ID++;
        foodPane.setExpanded(false);
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
        TransportRequest newTransport = new TransportRequest(transportNode, transportDescription.getText(),
                Integer.parseInt(transportID.getText()), transportTime.getText(), 00000,
                "transport", false, transportPatient.getText(), transportType.getText());
        requestList.add(newTransport);
        testEmbeddedDB.addTransportRequest(newTransport);
       // tableView.getItems().add(newTransport);

        transportPatient.clear();
        transportType.clear();
        transportDescription.clear();
        ID++;
        transportPane.setExpanded(false);
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
        CleaningRequest newClean = new CleaningRequest(cleanNode, cleanDescription.getText(),
                Integer.parseInt(cleanID.getText()), cleanTime.getText(), 00000,
                "cleaning", Integer.parseInt(cleanLevel.getText()));
        requestList.add(newClean);
        testEmbeddedDB.addCleaningRequest(newClean);
      //  tableView.getItems().add(newClean);

        cleanLevel.clear();
        cleanDescription.clear();
        ID++;
        cleanPane.setExpanded(false);
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
        SecurityRequest newSecurity = new SecurityRequest(securityNode, securityDescription.getText(),
                Integer.parseInt(securityID.getText()), securityTime.getText(), 00000,
                "security", Integer.parseInt(securityLevel.getText()));
        requestList.add(newSecurity);
        testEmbeddedDB.addSecurityRequest(newSecurity);
      //  tableView.getItems().add(newSecurity);

        securityLevel.clear();
        securityDescription.clear();
        ID++;
        securityPane.setExpanded(false);
    }

    @FXML
    private TitledPane itPane;

    @FXML
    private TextArea itDescription;

    @FXML
    private Button sendItRequest;

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
        ItRequest newIt = new ItRequest(n1, itDescription.getText(),
                Integer.parseInt(itID.getText()), itTime.getText(), 00000,
                "it", Integer.parseInt(itUrgency.getText()));
        requestList.add(newIt);
        testEmbeddedDB.addItRequest(newIt);
     //   tableView.getItems().add(newIt);

        itUrgency.clear();
        itDescription.clear();
        ID++;
        itPane.setExpanded(false);
    }

    @FXML
    private TableView<ServiceRequest> tableView;

    @FXML
    private TableColumn<ServiceRequest, String> requests;

    @FXML
    private TableColumn<ServiceRequest, String> status;

    @FXML
    private Button refresh;

    private ObservableList<ServiceRequest> requestObserve = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        requests.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Requests"));
//        status.setCellValueFactory(new PropertyValueFactory<ServiceRequest, String>("Status"));
        requests.setCellValueFactory(cellData -> cellData.getValue().getRequestType());
        status.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        counter = 0;
    }

    private int counter;

    @FXML
    private Button deletebutt;

    public void deleterequest()
    {
        ObservableList<ServiceRequest> selectedRows, allrequests;
        allrequests = tableView.getItems();

        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Person objects from the table
        for (ServiceRequest req: selectedRows)
        {
            allrequests.remove(req);
        }
    }


    @FXML
    public void refreshTable() {
        int i = 0;
        //using data from database
        Vector requestsFromDatabase = testEmbeddedDB.getAllServiceRequests();
        ArrayList<ServiceRequest> arrayOfRequestsFromDatabase = new ArrayList<ServiceRequest>(requestsFromDatabase);
        //System.out.println(arrayOfRequestsFromDatabase.size());
        // for(i = counter; i < requestsFromDatabase.size(); i++) {
            // arrayOfRequestsFromDatabase.add(requestsFromDatabase.get(i));
        // }
      /*for(i = counter; i < requestsFromDatabase.size(); i++) {
            arrayOfRequestsFromDatabase.add(requestsFromDatabase.get(i));
        }*/
  /*    System.out.println("The size of the array is:");
      System.out.println("                      ");
      System.out.println(((int)arrayOfRequestsFromDatabase.size()));

      */
//  if (!(arrayOfRequestsFromDatabase.get(0) == null))
//      {
//          for(i = counter; i < arrayOfRequestsFromDatabase.size(); i++) {
//              System.out.println(arrayOfRequestsFromDatabase.get(i));
//              if (!(arrayOfRequestsFromDatabase.get(i).getRequestType() == null))
//              {
//                  requestObserve.add(arrayOfRequestsFromDatabase.get(i));
//              }
//
//          }
//          counter = i;
//          tableView.setItems(requestObserve);
//      }
//
//
      for (i = counter; i < arrayOfRequestsFromDatabase.size();i++) {
          requestObserve.add(arrayOfRequestsFromDatabase.get(i));
      }

      counter = i;
      tableView.setItems(requestObserve);

        /*for(i = counter; i < requestList.size(); i++) {
            requestObserve.add(requestList.get(i));
            // requests.setText((requestList.get(i)).typeOfRequest);
        }*/

    }


}