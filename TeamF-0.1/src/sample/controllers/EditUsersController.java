package sample.controllers;

import javafx.fxml.FXML;
import sample.Main;

public class EditUsersController {
    @FXML
    public void back(){ Main.adminScreen();}

    @FXML
    public void help(){Main.genErrorScreen();}
}
