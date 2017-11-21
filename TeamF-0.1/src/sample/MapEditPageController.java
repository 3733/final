package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

public class MapEditPageController {
    @FXML
    private Button backButton;

    @FXML
    public void editNodes(){
        Main.nodeEditScreen();
    }

    @FXML
    public void editEdges(){
        Main.edgeEditScreen();
    }

    @FXML
    public void openMapScreen() {
        Main.mapScreen();
    }
    @FXML
    public void help(){Main.genErrorScreen();}
    @FXML
    public void logout(){Main.startScreen();}
    @FXML
    public void back(){Main.adminScreen();}
}
