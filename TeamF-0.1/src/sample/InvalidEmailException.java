package sample;

import controllers.NavigationPageController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InvalidEmailException extends Exception{
    String invalidEmail;
    public InvalidEmailException(String invalidEmail){
        super(invalidEmail + "is not a valid email, please enter in a valid email address");
        //need help to set to true what is Controller object to call?
        NavigationPageController.setInvalidEmail();
    }
}
