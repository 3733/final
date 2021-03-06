package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import messenger.*;

public class StartPageController {
    @FXML
    private TextField searchBox;

    @FXML
    private Button helpButton;

    @FXML
    private JFXButton loginButton;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public String getSearch(){
        return searchBox.getText();
    }


    @FXML
    public void search() throws IOException, InterruptedException {
        //System.out.print(searchBox.getText());
        if(getSearch().length()>0) {
            Main.setDestination(getSearch());
        }
        Main.mapScreen();
        searchBox.clear();
    }

    @FXML
    public void help(){
        //Main.setHelpScreenServiceRequestScreen();
        try{
           /* messenger.API m = new messenger.API();
            m.run(6,6,600,600,
                    "/src/UI/style.css", "test", "test", "sip:HELP@130.215.213.204:6969"); */
        } catch (Exception e){
            System.out.println("API ERROR: " + e.getLocalizedMessage());
        }

    }

    @FXML
    public void login(){
        Main.loginScreen(loginButton);
    }

    @FXML
    public void language(){

    }

}
