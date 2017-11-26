package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Main;
import sample.testEmbeddedDB;

public class EditNodesController {
    @FXML
    private Button backButton;
    @FXML
    private JFXTextField nodeIDField;
    @FXML
    private JFXTextField xCoordField;
    @FXML
    private JFXTextField yCoordField;
    @FXML
    private JFXTextField floorField;
    @FXML
    private JFXTextField buildingField;
    @FXML
    private JFXTextField nodeTypeField;
    @FXML
    private JFXTextField longNameField;
    @FXML
    private JFXTextField shortNameField;
    @FXML
    private Button addNodebtn;
    @FXML
    private Button editNodebtn;
    @FXML
    private Button removeNodebtn;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

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
       if(!nodeIDField.getText().trim().equals(null) && testEmbeddedDB.getNode(nodeIDField.getText().trim())!=null) {
           if (!xCoordField.getText().trim().equals(null)) {
                testEmbeddedDB.updateNodeXCoord(nodeIDField.getText(), getxCoord());
           }
           if (!yCoordField.getText().trim().equals(null)) {
               testEmbeddedDB.updateNodeYCoord(nodeIDField.getText(), getyCoord());
           }
           if (!floorField.getText().trim().equals(null)) {
               testEmbeddedDB.updateNodeFloor(nodeIDField.getText(), floorField.getText());
           }
           if (!buildingField.getText().trim().equals(null)) {
               testEmbeddedDB.updateNodeBuilding(nodeIDField.getText(), buildingField.getText());
           }
           if (!nodeTypeField.getText().trim().equals(null)) {
               testEmbeddedDB.updateNodeType(nodeIDField.getText(), nodeTypeField.getText());
           }
           if (!longNameField.getText().trim().equals(null)) {
               testEmbeddedDB.updateNodeLongName(nodeIDField.getText(), longNameField.getText());
           }
           if (!shortNameField.getText().trim().equals(null)) {
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
