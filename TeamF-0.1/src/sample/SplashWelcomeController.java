package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;


public class SplashWelcomeController implements Initializable {

    @FXML
    private Main mainController;
    @FXML
    private StackPane rootPane;

    public void setMainController(Main in) {
        mainController = in;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }











}
