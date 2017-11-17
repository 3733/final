package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;
import sample.testEmbeddedDB;

public class EditEdgesController {
    @FXML
    private Button backButton;
    @FXML
    private TextField edgeIDField;
    @FXML
    private TextField startNodeField;
    @FXML
    private TextField endNodeField;
    @FXML
    private Button addEdgebtn;
    @FXML
    private Button editEdgebtn;
    @FXML
    private Button removeEdgebtn;

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
