package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;
import sample.testEmbeddedDB;

public class EditNodesController {
    @FXML
    private Button backButton;
    @FXML
    private TextField nodeIDField;
    @FXML
    private TextField xCoordField;
    @FXML
    private TextField yCoordField;
    @FXML
    private TextField floorField;
    @FXML
    private TextField buildingField;
    @FXML
    private TextField nodeTypeField;
    @FXML
    private TextField longNameField;
    @FXML
    private TextField shortNameField;
    @FXML
    private Button addNodebtn;
    @FXML
    private Button editNodebtn;
    @FXML
    private Button removeNodebtn;

    public int getxCoord(){
        return Integer.parseInt(xCoordField.getText());
    }
    public int getyCoord(){
        return Integer.parseInt(yCoordField.getText());
    }

    public void addNodeButton(){
        testEmbeddedDB.addNodes(nodeIDField.getText(), getxCoord(), getyCoord(),
                floorField.getText(), buildingField.getText(), nodeTypeField.getText(),
                longNameField.getText(), shortNameField.getText(), "F");
    }
    public void editNodeButton(){
       if(!nodeIDField.getText().equals(null) && testEmbeddedDB.getNode(nodeIDField.getText())!=null) {
           if (!xCoordField.getText().equals(null)) {
                testEmbeddedDB.updateNodeXCoord(nodeIDField.getText(), getxCoord());
           }
           if (!yCoordField.getText().equals(null)) {
               testEmbeddedDB.updateNodeYCoord(nodeIDField.getText(), getyCoord());
           }
           if (!floorField.getText().equals(null)) {
               testEmbeddedDB.updateNodeFloor(nodeIDField.getText(), floorField.getText());
           }
           if (!buildingField.getText().equals(null)) {
               testEmbeddedDB.updateNodeBuilding(nodeIDField.getText(), buildingField.getText());
           }
           if (!nodeTypeField.getText().equals(null)) {
               testEmbeddedDB.updateNodeType(nodeIDField.getText(), nodeTypeField.getText());
           }
           if (!longNameField.getText().equals(null)) {
               testEmbeddedDB.updateNodeLongName(nodeIDField.getText(), longNameField.getText());
           }
           if (!shortNameField.getText().equals(null)) {
               testEmbeddedDB.updateNodeShortName(nodeIDField.getText(), shortNameField.getText());
           }
       }
    }
    public void removeNodeButton(){
        testEmbeddedDB.removeNode(nodeIDField.getText());
    }

    @FXML
    public void openMapEditing() {
        Main.mapEditScreen();
    }
}
