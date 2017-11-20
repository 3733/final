package sample.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

public class LoginPageController {
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label invalidLoginText;

    @FXML
    public void help(){Main.genErrorScreen();}


    @FXML
    public void login(){
        System.out.print(username.getText());
        System.out.print(password.getText());
        if(username.getText().equals("admin") && password.getText().equals("admin")){
            Main.adminScreen();
        }else {
            invalidLoginText.setVisible(true);
        }
    }


    @FXML
    public void back(){Main.startScreen();}

}
