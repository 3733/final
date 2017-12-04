package sample.UI;

import javafx.fxml.FXML;
import sample.Main;

public class HelpScreenServiceRequestScreenController {
    private Main mainController;
    public void setMainController(Main main){ this.mainController = main;}

    @FXML
    public void back(){Main.startScreen();}

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}



}
