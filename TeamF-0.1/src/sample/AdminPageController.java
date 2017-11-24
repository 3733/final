package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable{

    @FXML
    private SplitMenuButton algoMenu;
    @FXML
    private JFXButton upFloor, downFloor;
    @FXML
    private Label floorLabel;
    @FXML
    private ScrollPane scrollMap;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
}
