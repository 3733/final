package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.Main;

import javax.swing.*;

public class EditUserWindowController {
    private Main mainController;

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
        Main.editUsersScreen();
    }

    public long getID(){
        return Long.parseLong(idBox.getText());
    }

    public void addUserButton(){
        Staff addMe = new Staff(lNameBox.getText(), fNameBox.getText(), getID(),
                posBox.getText(), emailBox.getText(), usernameBox.getText(), pwBox.getText());
        testEmbeddedDB.addStaff(addMe);
        lNameBox.clear();
        fNameBox.clear();
        idBox.clear();
        posBox.clear();
        emailBox.clear();
        usernameBox.clear();
        pwBox.clear();
    }

    public void editUserButton(){
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
            lNameBox.clear();
            fNameBox.clear();
            idBox.clear();
            posBox.clear();
            emailBox.clear();
            usernameBox.clear();
            pwBox.clear();
        }

    }

    public void removeUserButton(){
        testEmbeddedDB.removeStaff(Long.parseLong(idBox.getText()));
        lNameBox.clear();
        fNameBox.clear();
        idBox.clear();
        posBox.clear();
        emailBox.clear();
        usernameBox.clear();
        pwBox.clear();
    }

}
