package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

import java.util.LinkedList;

public class LoginPageController {
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label invalidLoginText;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void help(){Main.genErrorScreen();}


    @FXML
    public void login(){
        System.out.println(username.getText());
        System.out.println(password.getText());
        if(checkUser(username.getText(), password.getText())){
            Main.adminScreen();
        }else {
            invalidLoginText.setVisible(true);
        }
        username.clear();
        password.clear();
    }

    private boolean checkUser(String name, String pass){

        LinkedList<Staff> everyone = testEmbeddedDB.getAllStaff();
        for(Staff person : everyone){
            if(person.getUsername().trim().equals(name) && person.getPassword().trim().equals(pass)){
                Main.setLoggedInGuy(person);
                return true;
            }
        }
        return false;
    }


    @FXML
    public void back(){Main.startScreen();}

}
