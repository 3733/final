package sample;

import javafx.fxml.FXML;
import sample.Main;

/**This is the controller for the "you've encountered an error screen"
 *
 */

public class GenErrorController {

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void back(){ Main.startScreen();}
}
