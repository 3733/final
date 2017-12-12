package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.io.IOException;
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
        rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{

            try {
                Main.mapScreen();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

        });



    }


    }












