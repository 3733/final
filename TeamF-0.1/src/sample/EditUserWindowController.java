package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import sample.Main;

import javax.swing.*;
import java.util.Timer;

public class EditUserWindowController implements ITimed{

    private TimeoutController timeoutController;

    private Timer atimer;

    private Main mainController;

    @FXML
    private JFXButton addButton;
    @FXML
    private  JFXButton editButton;
    @FXML
    private  JFXButton close;
    @FXML
    private JFXTextField lNameBox;
    @FXML
    private JFXTextField fNameBox;
    @FXML
    private JFXTextField idBox;
    @FXML
    private ComboBox posBox;
    @FXML
    private JFXTextField emailBox;
    @FXML
    private JFXTextField usernameBox;
    @FXML
    private JFXTextField pwBox;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction() //  throws IOException, InterruptedException
    {
        try
        {
            timeoutController.doTimer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not start timer.");
        }
    }

    public void initialize()
    {
        timeoutController = new TimeoutController();
        atimer = new Timer();
        timeoutController.updateDelay(30); // 30 per steph request.
        timeoutController.setTimer(atimer, false);
    }

    //When going back, clears the text fields
    @FXML
    public void back(JFXButton btn2){
        lNameBox.clear();
        fNameBox.clear();
        idBox.clear();
        posBox.setValue("");
        emailBox.clear();
        usernameBox.clear();
        pwBox.clear();
        idBox.setDisable(false);
        addButton.setDisable(false);
        addButton.setVisible(true);
        editButton.setDisable(false);
        editButton.setVisible(true);

        Main.editUsersScreen();
        Main.closePopUp(btn2);
    }

    public void closeBtn(){
        back(close);
    }

    public long getID(){
        return Long.parseLong(idBox.getText());
    }

    //If adding users, disables the edit users button
    public void addingUsers(){
        editButton.setDisable(true);
        editButton.setVisible(false);
        posBox.setItems(FXCollections.observableArrayList(
                "Admin", "Helper", "Cleaning", "Medical", "Security", "IT"));
    }

    //If editing users, disables the add users button
    public void editingUsers(){
        addButton.setDisable(true);
        addButton.setVisible(false);
        posBox.setItems(FXCollections.observableArrayList(
                "Admin","Helper", "Cleaning", "Medical", "Security", "IT"));
    }

    //Fills the Text Fields when editing a user
    public void fillFields(Staff staff){
        lNameBox.setText(staff.getLastName().trim());
        fNameBox.setText(staff.getFirstName().trim());
        idBox.setText(Long.toString(staff.getEmployeeID()));
        idBox.setDisable(true);
        posBox.setValue(staff.getEmployeeType().trim());
        emailBox.setText(staff.getEmployeeEmail().trim());
        usernameBox.setText(staff.getUsername().trim());
        pwBox.setText(staff.getPassword().trim());
    }

    //Adds a user based on information in the text fields
    public void addUserButton(){
        Staff addMe = new Staff(fNameBox.getText(), lNameBox.getText(), getID(),
                usernameBox.getText(), pwBox.getText(), (String)posBox.getValue(), emailBox.getText());
        testEmbeddedDB.addStaff(addMe);
        back(addButton);
    }

    //Edits a user based on information in the text fields
    public void editUserButton(){
        idBox.setDisable(false);
        if(!idBox.getText().trim().equals(null) && testEmbeddedDB.getStaff(getID())!=null) {
            if (!lNameBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffLName(getID(), lNameBox.getText());
            }
            if (!fNameBox.getText().trim().equals(null)) {
                testEmbeddedDB.updateStaffFName(getID(), fNameBox.getText());
            }
            if (!((String)posBox.getValue()).trim().equals(null)) {
                testEmbeddedDB.updateStaffEType(getID(), (String)posBox.getValue());
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
            back(editButton);
        }

    }


}
