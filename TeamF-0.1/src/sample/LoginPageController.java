package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.Duration;
import sample.Main;

import java.io.IOException;
import java.util.LinkedList;

public class LoginPageController {
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label invalidLoginText;

    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton close;

    private Main mainController;

    private NavigationPageController navController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}


    @FXML
    public void login() throws IOException, InterruptedException{

        if(checkUser(username.getText(), password.getText())){
            AuthenticationInfo newUser = new AuthenticationInfo(username.getText(),AuthenticationInfo.Privilege.ADMIN);
            SettingSingleton.getSettingSingleton().setAuthProperty(newUser);
            Main.mapScreen();
            Main.closePopUp(loginButton);
        }else {
            invalidLoginText.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(
                    Duration.seconds(3)
            );
            visiblePause.setOnFinished(
                    event -> invalidLoginText.setVisible(false)
            );
            visiblePause.play();
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
    public void back(){
        username.clear();
        password.clear();
        Main.closePopUp(close);
    }

}
