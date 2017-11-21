package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class MapEditPageController {
    @FXML
    private Button backButton;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void editNodes(){
        Main.nodeEditScreen();
    }

    @FXML
    public void editEdges(){
        Main.edgeEditScreen();
    }

    @FXML
    public void openMapScreen() throws IOException, InterruptedException {
        Main.mapScreen();
    }
}
