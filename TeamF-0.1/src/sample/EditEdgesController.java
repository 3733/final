package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;
import sample.testEmbeddedDB;

public class EditEdgesController {
    @FXML
    private Button backButton;
    @FXML
    private JFXTextField edgeIDField;
    @FXML
    private JFXTextField startNodeField;
    @FXML
    private JFXTextField endNodeField;
    @FXML
    private JFXButton addEdgebtn;
    @FXML
    private JFXButton editEdgebtn;
    @FXML
    private JFXButton removeEdgebtn;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    public void addEdgeButton(){
        testEmbeddedDB.addEdges(edgeIDField.getText(), startNodeField.getText(), endNodeField.getText());
    }
    public void editEdgeButton(){
        if(!edgeIDField.getText().equals(null) && testEmbeddedDB.getEdge(edgeIDField.getText())!=null) {
            if (!startNodeField.getText().equals(null)) {
                testEmbeddedDB.updateEdgeStart(edgeIDField.getText(), startNodeField.getText());
            }
            if (!endNodeField.getText().equals(null)) {
                testEmbeddedDB.updateEdgeEnd(edgeIDField.getText(), endNodeField.getText());
            }
        }
    }
    public void removeEdgeButton(){
        testEmbeddedDB.removeEdge(edgeIDField.getText());
    }

    public void openMapEditing() {
        Main.mapEditScreen();
    }
}
