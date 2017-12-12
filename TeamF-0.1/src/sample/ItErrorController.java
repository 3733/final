package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

import java.util.Timer;

public class ItErrorController implements ITimed{

    private Timer atimer;

    @FXML
    private Label itErrorLabel;

    @FXML
    private Label itErrorText;

    @FXML
    private Button yesIt;

    @FXML
    private Button noIt;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void yesRequest(){
        //Main.itRequestScreen();
        //ServiceRequestController.updateIt();
    }

    @FXML
    public void noRequest(){
        Main.startScreen();
    }

    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction()//  throws IOException, InterruptedException
    {
    }

    public void initialize()
    {
    }
}