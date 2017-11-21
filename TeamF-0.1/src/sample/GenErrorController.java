package sample;

import javafx.fxml.FXML;
import sample.Main;

public class GenErrorController {

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void back(){
        Main.startScreen();}
}
