package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import static sample.Main.getLoggedInGuy;

public class ServiceAcceptController implements Initializable{

    //top menu bar
    @FXML
    public void backToAdmin() throws IOException, InterruptedException{Main.mapScreen();}

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    @FXML
    public void logout(){Main.startScreen();}

    @FXML
    public void toServiceRequest() {Main.serviceScreen();}

    //fixes issue with database that creates unnecessary spaces in strings
    public String removeWhiteSpace(String input) {
        return input.replace(" ", "");
    }


    //for current service requests
    @FXML
    private TableView<ServiceRequest> tableView;

    @FXML
    private TableColumn<ServiceRequest, String> requests;

    @FXML
    private TableColumn<ServiceRequest, String> status;

    @FXML
    private JFXCheckBox emailCheck;

    //formatted list of requests that go into table
    private ObservableList<ServiceRequest> requestObserve = FXCollections.observableArrayList();


    //for finished service requests
    @FXML
    private TableView<ServiceRequest> finishedTableView;

    @FXML
    private TableColumn<ServiceRequest, String> finishedRequests;

    @FXML
    private  TableColumn<ServiceRequest, Integer> requestIDs;


    //formatted list of requests that go into table
    private ObservableList<ServiceRequest> finishedRequestObserve = FXCollections.observableArrayList();




    //formats a string into being able to be displayed in table
    public StringProperty stringToStringProperty(String cellEntry) {
        return new SimpleStringProperty(cellEntry);
    }

    //formats an int into being able to be displayed in table
    public ObservableValue<Integer> intToObsValue(int cellEntry) {return new SimpleIntegerProperty(cellEntry).asObject(); }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requests.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getType()).trim()));   //sets service name in column
        status.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getStatus()).trim()));   //sets service status in column
        finishedRequests.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getType()).trim()));   //sets service name in column
        requestIDs.setCellValueFactory(cellData -> intToObsValue(cellData.getValue().getServiceID()));
        refreshTable();
    }

    @FXML
    private ImageView icon;

    public void acceptRequest() throws InvalidEmailException, IOException {
        ServiceRequest requestSelected =  tableView.getSelectionModel().getSelectedItem();  //gets the selected service

        if( (getLoggedInGuy().getEmployeeType().trim().equals("Admin")) ||
                (requestSelected.getType().trim().equals("assistance") && getLoggedInGuy().getEmployeeType().trim().equals("Interpreter")) ||
                (requestSelected.getType().trim().equals("cleaning") && getLoggedInGuy().getEmployeeType().trim().equals("Janitor")) ||
                (requestSelected.getType().trim().equals("food") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse")) ||
                (requestSelected.getType().trim().equals("security") && getLoggedInGuy().getEmployeeType().trim().equals("Security guard")) ||
                (requestSelected.getType().trim().equals("transport") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse"))) {

            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("h:mm a");

            requestSelected.acceptRequest();
            requestSelected.setAcceptTime(ft.format(date));

            //links the service request with updated status and acceptTime to a staff member in the database
            testEmbeddedDB.addAssignment(requestSelected.getServiceID(), getLoggedInGuy().getEmployeeID(),
                    requestSelected.getAcceptTime(), requestSelected.getStatus());

            for (int i = 0; i < requestObserve.size(); i++)             //looks for the selected service in the table
                if (requestSelected.serviceID == (requestObserve.get(i)).serviceID)
                    requestObserve.set(i, requestSelected);

            refreshTable();

            String serviceInformation = "Service type: " + requestSelected.getType() + "<br>";
            serviceInformation += "Time created: " + requestSelected.getServiceTime() + "<br>";
            serviceInformation += "Service Location: " + requestSelected.getDestination().getLongName() + "<br>";
            serviceInformation += "Time accepted: " + requestSelected.getAcceptTime() + "<br>";

            if ((requestSelected.getType().trim()).equals("food")) {
                serviceInformation += "Time to be served: " + ((FoodRequest) requestSelected).getServingTime() + "<br>";
                serviceInformation += "Patient name: " + ((FoodRequest) requestSelected).getPatientName() + "<br>";
                serviceInformation += "Meal ordered: " + ((FoodRequest) requestSelected).getFoodOrder() + "<br>";
            }

            if ((requestSelected.getType().trim()).equals("transport")) {
                serviceInformation += "Patient name: " + ((TransportRequest) requestSelected).getPatientName() + "<br>";
                serviceInformation += "Method of transportation: " + ((TransportRequest) requestSelected).getTypeOfTransport() + "<br>";
            }

            if ((requestSelected.getType().trim()).equals("assistance"))
                serviceInformation += "Urgency level: " + ((AssistanceRequest) requestSelected).getUrgency() + "<br>";

            if ((requestSelected.getType().trim()).equals("cleaning"))
                serviceInformation += "Urgency level: " + ((CleaningRequest) requestSelected).getUrgency() + "<br>";

            if ((requestSelected.getType().trim()).equals("security"))
                serviceInformation += "Urgency level: " + ((SecurityRequest) requestSelected).getUrgency() + "<br>";

            if ((requestSelected.getType().trim()).equals("it"))
                serviceInformation += "Urgency level: " + ((ItRequest) requestSelected).getUrgency() + "<br>";

            serviceInformation += "Service Description: " + requestSelected.getDescription() + "<br>";

            //emailing the service request to the employee
            if (emailCheck.isSelected()) {
                EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng", icon);
                emailService.sendRequestEmail(serviceInformation, getLoggedInGuy().getEmployeeEmail());
            }
        }
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
            if( (getLoggedInGuy().getEmployeeType().trim().equals("Admin")) ||
                    (req.getType().trim().equals("assistance") && getLoggedInGuy().getEmployeeType().trim().equals("Interpreter")) ||
                    (req.getType().trim().equals("cleaning") && getLoggedInGuy().getEmployeeType().trim().equals("Janitor")) ||
                    (req.getType().trim().equals("food") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse")) ||
                    (req.getType().trim().equals("security") && getLoggedInGuy().getEmployeeType().trim().equals("Security guard")) ||
                    (req.getType().trim().equals("transport") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse"))) {

                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("h:mm a");
                req.setFinishTime(ft.format(date));
                req.finishRequest();

                testEmbeddedDB.editCompletionStatus(req.getServiceID(), req.getStatus());
                testEmbeddedDB.editFinishTime(req.getServiceID(), req.getFinishTime());

                allrequests.remove(req);
            }
        }

        refreshTable();
    }


    @FXML
    public void refreshTable() {
        //int i = 0;
        //using data from database
        Vector requestsFromDatabase = testEmbeddedDB.getAllServiceRequests();
        ArrayList<ServiceRequest> arrayOfRequestsFromDatabase = new ArrayList<ServiceRequest>(requestsFromDatabase);

        for ( int z = 0; z<tableView.getItems().size(); z++) {
            tableView.getItems().clear();
        }
        for ( int u = 0; u<finishedTableView.getItems().size(); u++) {
            finishedTableView.getItems().clear();
        }

        //for current requests table
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
            if ( !alreadyInTable && !((((arrayOfRequestsFromDatabase.get(i)).getStatus()).trim()).equals("finished")) && (getLoggedInGuy().getEmployeeType().trim().equals("Admin")) ||
                    (arrayOfRequestsFromDatabase.get(i).getType().trim().equals("assistance") && getLoggedInGuy().getEmployeeType().trim().equals("Interpreter")) ||
                    (arrayOfRequestsFromDatabase.get(i).getType().trim().equals("cleaning") && getLoggedInGuy().getEmployeeType().trim().equals("Janitor")) ||
                    (arrayOfRequestsFromDatabase.get(i).getType().trim().equals("food") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse")) ||
                    (arrayOfRequestsFromDatabase.get(i).getType().trim().equals("security") && getLoggedInGuy().getEmployeeType().trim().equals("Security guard")) ||
                    (arrayOfRequestsFromDatabase.get(i).getType().trim().equals("transport") && getLoggedInGuy().getEmployeeType().trim().equals("Nurse")))
                requestObserve.add(arrayOfRequestsFromDatabase.get(i));
        }

        tableView.setItems(requestObserve);


        //for finished requests table
        for (int i = 0; i < arrayOfRequestsFromDatabase.size(); i++) {   //searches through the list from the database
            boolean alreadyInTable = false;
            for (int j = 0; j < finishedRequestObserve.size(); j++) {        //searches through the list in the table
                if ((arrayOfRequestsFromDatabase.get(i)).getServiceID() == (finishedRequestObserve.get(j)).getServiceID()) {  //if the service is already in the table
                    alreadyInTable = true;
                }
            }
            if ( !alreadyInTable && (((arrayOfRequestsFromDatabase.get(i)).getStatus()).trim()).equals("finished"))
                finishedRequestObserve.add(arrayOfRequestsFromDatabase.get(i));
        }

        finishedTableView.setItems(finishedRequestObserve);

    }


    @FXML
    private Label currentType;

    @FXML
    private Label currentStatus;

    @FXML
    private Label currentID;

    @FXML
    private Label currentCreatedTime;

    @FXML
    private Label currentAcceptTime;

    @FXML
    private Label currentUrgency;

    @FXML
    private Label currentDestination;

    @FXML
    private Label currentDeliveryTime;

    @FXML
    private Label currentMenu;

    @FXML
    private Label currentPatient;

    @FXML
    private Label currentTransportation;

    @FXML
    private Label currentDescription;

    @FXML
    private Label finishType;

    @FXML
    private Label finishStatus;

    @FXML
    private Label finishID;

    @FXML
    private Label finishCreatedTime;

    @FXML
    private Label finishAcceptTime;

    @FXML
    private Label finishUrgency;

    @FXML
    private Label finishDestination;

    @FXML
    private Label finishDeliveryTime;

    @FXML
    private Label finishMenu;

    @FXML
    private Label finishPatient;

    @FXML
    private Label finishTransportation;

    @FXML
    private Label finishDescription;

    @FXML
    private Label finishFinishTime;


    @FXML
    public void currentViewRequest() {
        currentType.setText("");
        currentStatus.setText("");
        currentID.setText("");
        currentCreatedTime.setText("");
        currentAcceptTime.setText("");
        currentDestination.setText("");
        currentDescription.setText("");
        currentUrgency.setText("");
        currentDeliveryTime.setText("");
        currentMenu.setText("");
        currentPatient.setText("");
        currentTransportation.setText("");

        //this gives us the rows that were selected
        ObservableList<ServiceRequest> selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and display properties
        for (ServiceRequest req: selectedRows)
        {
            currentType.setText((req.getType()).trim());
            currentStatus.setText((req.getStatus()).trim());
            currentID.setText((Integer.toString(req.getServiceID())).trim());
            currentCreatedTime.setText((req.getServiceTime()).trim());
            currentAcceptTime.setText((req.getAcceptTime()).trim());
            currentDestination.setText((req.getDestination().getLongName()).trim());
            currentDescription.setText((req.getDescription()).trim());

            if((req.getType()).trim().equals("cleaning"))
                currentUrgency.setText((Integer.toString(((CleaningRequest)req).getUrgency())).trim());

            if((req.getType()).trim().equals("assistance"))
                currentUrgency.setText((Integer.toString(((AssistanceRequest)req).getUrgency())).trim());

            if((req.getType()).trim().equals("security"))
                currentUrgency.setText((Integer.toString(((SecurityRequest)req).getUrgency())).trim());

            if((req.getType()).trim().equals("it"))
                currentUrgency.setText((Integer.toString(((ItRequest)req).getUrgency())).trim());

            if((req.getType()).trim().equals("food")) {
                currentDeliveryTime.setText((((FoodRequest) req).getServingTime()).trim());
                currentMenu.setText((((FoodRequest) req).getFoodOrder()).trim());
                currentPatient.setText(((((FoodRequest) req).getPatientName())).trim());
            }

            if((req.getType().trim()).equals("transport")) {
                currentTransportation.setText((((TransportRequest) req).getTypeOfTransport()).trim());
                currentPatient.setText(((((TransportRequest) req).getPatientName())).trim());
            }

        }
    }

    @FXML
    public void finishedViewRequest() {
        finishType.setText("");
        finishStatus.setText("");
        finishID.setText("");
        finishCreatedTime.setText("");
        finishAcceptTime.setText("");
        finishFinishTime.setText("");
        finishDestination.setText("");
        finishDescription.setText("");
        finishUrgency.setText("");
        finishDeliveryTime.setText("");
        finishMenu.setText("");
        finishPatient.setText("");
        finishTransportation.setText("");

        //this gives us the rows that were selected
        ObservableList<ServiceRequest> selectedRows = finishedTableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and display properties
        for (ServiceRequest req: selectedRows)
        {
            finishType.setText((req.getType()).trim());
            finishStatus.setText((req.getStatus()).trim());
            finishID.setText((Integer.toString(req.getServiceID())).trim());
            finishCreatedTime.setText((req.getServiceTime()).trim());
            finishAcceptTime.setText((req.getAcceptTime()).trim());
            finishFinishTime.setText((req.getFinishTime()).trim());
            finishDestination.setText((req.getDestination().getLongName()).trim());
            finishDescription.setText((req.getDescription()).trim());

            if((req.getType().trim()).equals("cleaning"))
                finishUrgency.setText((Integer.toString(((CleaningRequest)req).getUrgency())).trim());

            if((req.getType().trim()).equals("assistance"))
                finishUrgency.setText((Integer.toString(((AssistanceRequest)req).getUrgency())).trim());

            if((req.getType().trim()).equals("security"))
                finishUrgency.setText((Integer.toString(((SecurityRequest)req).getUrgency())).trim());

            if((req.getType()).trim().equals("it"))
                finishUrgency.setText((Integer.toString(((ItRequest)req).getUrgency())).trim());

            if((req.getType().trim()).equals("food")) {
                finishDeliveryTime.setText((((FoodRequest) req).getServingTime()).trim());
                finishMenu.setText((((FoodRequest) req).getFoodOrder()).trim());
                finishPatient.setText(((((FoodRequest) req).getPatientName())).trim());
            }

            if((req.getType().trim()).equals("transport")) {
                finishTransportation.setText((((TransportRequest) req).getTypeOfTransport()).trim());
                finishPatient.setText(((((TransportRequest) req).getPatientName())).trim());
            }

        }
    }


}

