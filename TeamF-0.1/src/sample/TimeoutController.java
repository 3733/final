package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeoutController implements Initializable{

    @FXML
    private JFXRadioButton logOpt, chatOpt, createOpt, viewOpt, mapOpt, usersOpt, popOpt;
    @FXML
    private JFXButton submit, back;
    @FXML
    private ToggleGroup windowOpt;
    @FXML
    private JFXTextField time;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void close() {
        Main.mapEditScreen();
        Main.closePopUp(back);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOpt.setToggleGroup(windowOpt);
        chatOpt.setToggleGroup(windowOpt);
        createOpt.setToggleGroup(windowOpt);
        viewOpt.setToggleGroup(windowOpt);
        mapOpt.setToggleGroup(windowOpt);
        usersOpt.setToggleGroup(windowOpt);
        popOpt.setToggleGroup(windowOpt);
        logOpt.setSelected(true);
    }

    @FXML
    public void submit(){
        int sec = Integer.parseInt(time.getText());
        if(logOpt.isSelected()){}
        if(chatOpt.isSelected()){}
        if(createOpt.isSelected()){}
        if (viewOpt.isSelected()){}
        if(mapOpt.isSelected()){}
        if(usersOpt.isSelected()){}
        if(popOpt.isSelected()){}
    }
}
