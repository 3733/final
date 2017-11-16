package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginPageController {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label invalidLoginText;

    @FXML
    public void cancel(){
        Main.startScreen();
    }

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
    public void sendMsg(){

    }

}
