package controllers;

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

public class StartPageController {
    @FXML
    private TextField searchBox;

    @FXML
    private Button helpButton;

    @FXML
    public void search(){
        //System.out.print(searchBox.getText());
        Main.mapScreen();
    }

    @FXML
    public void help(){
        //Main.itErrorScreen();
    }

    @FXML
    public void login(){
        Main.loginScreen();
    }

    @FXML
    public void language(){

    }

}
