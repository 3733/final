package sample;

import javafx.fxml.FXML;

import java.io.IOException;

public class SwitchingController {

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void back()throws IOException, InterruptedException{ Main.startScreen();}
    @FXML
    public void yes() throws IOException, InterruptedException{Main.mapScreen();}
}
