package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.Main;

import javax.swing.*;

public class EditUsersController {
    private Main mainController;

    private ObservableList<Staff> userObserve = FXCollections.observableArrayList();

    public void setMainController(Main main){
        this.mainController = main;
    }
    @FXML
    public void back(){ Main.adminScreen();}

    @FXML
    public void help(){Main.genErrorScreen();}

    public void addUserButton(){
        Main.editUserWindow();
    }

    public void editUserButton(){
        Main.editUserWindow();
    }

    public void removeUserButton(){

    }


}
