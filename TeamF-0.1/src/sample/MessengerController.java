package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
//import dev2dev.textclient.TextClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class MessengerController implements Initializable{

    @FXML
    private JFXListView chatListView;
    @FXML
    private JFXTextField toField, questionAsked;
    @FXML
    private Label fromLabel;
    @FXML
    private JFXButton sendAsk, backButton;


    public ObservableList currentChat = FXCollections.observableArrayList();

    private Main mainController;

    //TextClient t = new TextClient();

    //Purpose: to initialize the fxml
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //chatListView.setItems(currentChat);
        //t.boot("bmat2z", 6969);
    }

    //getters and setters
    public void setMainController(Main in){
        mainController = in;
    }

    public ObservableList<String> getCurrentChat(){
        return currentChat;
    }

    public JFXListView getChatListView() {
        return chatListView;
    }

    //other functions
    public void sendQuestion(){
        String question = questionAsked.getText();
        questionAsked.setText("");
        currentChat.add(question);
        //call answering part?

    }

    public void back(){
        Main.closePopUp(backButton);
        clear();
    }

    public void clear(){
        toField.setText("");
        questionAsked.setText("");
        chatListView.getItems().clear();
    }

    //send button
    //public void send(){
        //t.sendMsg(fromLabel.getText(), questionAsked.getText());
    //}

}
