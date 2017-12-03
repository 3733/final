package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class EditUsersController implements Initializable{
    private Main mainController;

    @FXML
    private JFXButton editUserButt;
    @FXML
    private  JFXButton remButton;
    @FXML
    private JFXButton addButton;

    public void setMainController(Main main){
        this.mainController = main;
    }
    @FXML
    public void back()throws IOException, InterruptedException{ Main.mapScreen();}

    @FXML
    public void help(){Main.genErrorScreen();}

    public void addUserButton(){
        Main.editUserWindow(addButton);
    }

    public void editUserButton(){
        ObservableList<Staff> selectedRows, allrequests;
        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        Main.editUserWindowEdit(selectedRows.get(0), editUserButt);
    }

    //Creates a JOptionPane to make sure the admin wants to remove a user
    public void removeUserButton(){
        JFrame frame = new JFrame();
        Object[] options = {"Yes",
                "No"};
        int n = JOptionPane.showOptionDialog(frame,
                "Are you sure you want to remove this user?",
                "Remove User",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if(n == 0){
            deleteStaff();
        }
    }

    @FXML
    private TableView<Staff> tableView;

    @FXML
    private TableColumn<Staff, String> lastName;

    @FXML
    private TableColumn<Staff, String> firstName;

    @FXML
    private TableColumn<Staff, String> id;

    @FXML
    private TableColumn<Staff, String> position;

    @FXML
    private TableColumn<Staff, String> email;

    private ObservableList<Staff> staffObserve = FXCollections.observableArrayList();

    public StringProperty stringToStringProperty(String cellEntry){
        return new SimpleStringProperty(cellEntry);
    }

    private int counter;

    //Initializes the table
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lastName.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getLastName()).trim()));
        firstName.setCellValueFactory(cellData ->stringToStringProperty((cellData.getValue().getFirstName()).trim()));
        id.setCellValueFactory(cellData ->stringToStringProperty(Long.toString(cellData.getValue().getEmployeeID())));
        position.setCellValueFactory(cellData ->stringToStringProperty((cellData.getValue().getEmployeeType()).trim()));
        email.setCellValueFactory(cellData ->stringToStringProperty((cellData.getValue().getEmployeeEmail()).trim()));
        refreshTable();
    }

    //Disables the Edit Users and Remove Users buttons until a user is selected
    @FXML
    public void disableButtons(){
        editUserButt.setDisable(true);
        remButton.setDisable(true);
    }

    //Enables the Edit Users and Remove Users buttons when a user is selected
    @FXML
    public void enableButtons(){
        if(tableView.getSelectionModel().getSelectedIndex() >= 0){
            editUserButt.setDisable(false);
            remButton.setDisable(false);
        }
    }

    //Refreshes the Table. Called whenever the scene is loaded.
    @FXML
    public void refreshTable() {
        //using data from database
        LinkedList<Staff> allStaff = testEmbeddedDB.getAllStaff();

        for ( int z = 0; z<tableView.getItems().size(); z++) {
            tableView.getItems().clear();
        }

        for (int i = 0; i < allStaff.size();i++) {
            staffObserve.add(allStaff.get(i));
        }
/*
        //using local data from array
        for(i = counter; i < requestList.size(); i++)
            requestObserve.add(requestList.get(i));
*/
        tableView.setItems(staffObserve);
    }

    //Removes a selected user
    public void deleteStaff()
    {
        ObservableList<Staff> selectedRows, allrequests;
        allrequests = tableView.getItems();

        //this gives us the rows that were selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the ServiceRequest objects from the table
        for (Staff people: selectedRows)
        {
            testEmbeddedDB.removeStaff(people.getEmployeeID());

            allrequests.remove(people);
        }
    }


}
