package sample;

import javafx.fxml.FXML;
import sample.Main;

public class EditUsersController {
    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }
    @FXML
    public void back(){ Main.adminScreen();}

    @FXML
    public void help(){Main.genErrorScreen();}
}
