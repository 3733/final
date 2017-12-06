package sample;

import javafx.fxml.FXML;

public class GenErrorController {

    private FoodAPI APIController;

    public void setAPIController(FoodAPI API){
        this.APIController = API;
    }

    @FXML
    public void back(){ FoodAPI.serviceScreen();}
}
