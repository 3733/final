package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.Main;

import javax.swing.*;

public class EditUserWindowController {
    private Main mainController;

    @FXML
    private JFXButton addButton;
    @FXML
    private  JFXButton editButton;
    @FXML
    private JFXTextField lNameBox;
    @FXML
    private JFXTextField fNameBox;
    @FXML
    private JFXTextField idBox;
    @FXML
    private JFXTextField posBox;
    @FXML
    private JFXTextField emailBox;
    @FXML
    private JFXTextField usernameBox;
    @FXML
    private JFXTextField pwBox;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void back(){
        lNameBox.clear();
        fNameBox.clear();
        idBox.clear();
        posBox.clear();
        emailBox.clear();
        usernameBox.clear();
        pwBox.clear();
        idBox.setDisable(false);
        addButton.setDisable(false);
        addButton.setVisible(true);
        editButton.setDisable(false);
        editButton.setVisible(true);

        Main.editUsersScreen();
    }

    public long getID(){
        return Long.parseLong(idBox.getText());
    }

    public void addingUsers(){
        editButton.setDisable(true);
        editButton.setVisible(false);
    }

    public void editingUsers(){
        addButton.setDisable(true);
        addButton.setVisible(false);
    }

    public void fillFields(Staff staff){
        lNameBox.setText(staff.getLastName().trim());
        fNameBox.setText(staff.getFirstName().trim());
        idBox.setText(Long.toString(staff.getEmployeeID()));
        idBox.setDisable(true);
        posBox.setText(staff.getEmployeeType().trim());
        emailBox.setText(staff.getEmployeeEmail().trim());
        usernameBox.setText(staff.getUsername().trim());
        pwBox.setText(staff.getPassword().trim());
    }

    public void addUserButton(){
        Staff addMe = new Staff(lNameBox.getText(), fNameBox.getText(), getID(),
                posBox.getText(), emailBox.getText(), usernameBox.getText(), pwBox.getText());
        testEmbeddedDB.addStaff(addMe);
        back();
    }

    public void editUserButton(){
        idBox.setDisable(false);
        if(!idBox.getText().trim().equals(null) && testEmbeddedDB.getStaff(getID())!=null) {
            if (!lNameBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffLName(getID(), lNameBox.getText());
            }
            if (!fNameBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffFName(getID(), fNameBox.getText());
            }
            if (!posBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffEType(getID(), posBox.getText());
            }
            if (!emailBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffEmail(getID(), emailBox.getText());
            }
            if (!usernameBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateUsername(getID(), usernameBox.getText());
            }
            if (!pwBox.getText().trim().equals(null)) {
                testEmbeddedDB.updatePassword(getID(), pwBox.getText());
            }
            back();
        }

    }


}
