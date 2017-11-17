package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

public class AdminPageController {
    @FXML
    public void logout(){
        Main.startScreen();
    }

    @FXML
    public void edit(){
        Main.mapEditScreen();
    }
    @FXML
    public void serviceRequest() {Main.serviceScreen();}

    @FXML
    public void addUser(){

    }

    @FXML
    public void removeUser(){

    }

    @FXML
    public void settings(){

    }
}
