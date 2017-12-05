package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dev2dev.textclient.TextClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable{

    @FXML
    private JFXListView chatListView;
    @FXML
    private JFXTextField toField, questionAsked;
    @FXML
    private Label fromLabel;
    @FXML
    private JFXButton sendAsk;

    private Main mainController;

    TextClient t = new TextClient();

    //Purpose: to initialize the fxml
    @Override
    public void initialize(URL location, ResourceBundle resources){
        t.boot("bmat2z", 6969);

    }


    //getters and setters
    public void setMainController(Main in){
        mainController = in;
    }

    //send button
    public void send(){
        t.sendMsg(fromLabel.getText(), questionAsked.getText());
    }

}
