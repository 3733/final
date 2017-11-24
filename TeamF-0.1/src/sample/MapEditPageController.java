package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditPageController implements Initializable{
    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollMap;

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

    @FXML
    public void help(){Main.genErrorScreen();}
    @FXML
    public void logout(){Main.startScreen();}
    @FXML
    public void back(){Main.adminScreen();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }
}
