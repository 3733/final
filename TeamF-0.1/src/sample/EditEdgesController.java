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
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton clickStartNode;
    @FXML
    private JFXButton clickEndNode;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    public void addEdgeButton(){
        testEmbeddedDB.addEdges(edgeIDField.getText(), startNodeField.getText(), endNodeField.getText());
        edgeIDField.clear();
        startNodeField.clear();
        endNodeField.clear();
        MapEditPageController.updateEdges();
        Main.closePopUp(addEdgebtn);
        Data.data.graph = testEmbeddedDB.dbBuildMap();
    }
    public void editEdgeButton(){
        if(!edgeIDField.getText().equals(null) && testEmbeddedDB.getEdge(edgeIDField.getText())!=null) {
            if (!startNodeField.getText().equals(null)) {
                testEmbeddedDB.updateEdgeStart(edgeIDField.getText(), startNodeField.getText());
            }
            if (!endNodeField.getText().equals(null)) {
                testEmbeddedDB.updateEdgeEnd(edgeIDField.getText(), endNodeField.getText());
            }
            edgeIDField.clear();
            startNodeField.clear();
            endNodeField.clear();
            MapEditPageController.updateEdges();
            Main.closePopUp(editEdgebtn);
            Data.data.graph = testEmbeddedDB.dbBuildMap();
        }
    }
    public void removeEdgeButton(){
        testEmbeddedDB.removeEdge(edgeIDField.getText());
        edgeIDField.clear();
        startNodeField.clear();
        endNodeField.clear();
        MapEditPageController.updateEdges();
        Main.closePopUp(removeEdgebtn);
        Data.data.graph = testEmbeddedDB.dbBuildMap();
    }

    public void openMapEditing() {
        Main.mapEditScreen();
        Main.closePopUp(close);
    }

    @FXML
    public void selectStartNode() {
        Main.mapEditScreen();
    }

    @FXML
    public void selectEndNode() {
        Main.mapEditScreen();
    }

    @FXML
    public void setScreenStart(Node start){
        startNodeField.setText(start.getNodeID());
    }

    @FXML
    public void setScreenEnd(Node end){
        endNodeField.setText(end.getNodeID());
    }

    @FXML
    public void clear() {
        edgeIDField.clear();
        startNodeField.clear();
        endNodeField.clear();
    }
}
