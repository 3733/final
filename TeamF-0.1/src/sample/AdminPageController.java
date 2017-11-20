package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

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
