package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

public class AdminPageController {

    @FXML
    private SplitMenuButton algoMenu;

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
    public void editUsers(){Main.editUsersScreen();}
    @FXML
    public void back(){Main.startScreen();}
    @FXML
    public void help(){Main.genErrorScreen();}

    @FXML
    public void setAlgorithm(){}

}
