package sample;

import javafx.fxml.FXML;

public class GenErrorController {

    private API APIController;

    public void setAPIController(API API){
        this.APIController = API;
    }

    @FXML
    public void back(){ API.serviceScreen();}
}
