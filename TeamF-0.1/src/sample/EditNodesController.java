package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import sample.Main;
import sample.testEmbeddedDB;

import java.net.URL;
import java.util.ResourceBundle;

/**This is the EditNodes pages controller
 * <p>
 *     This controller allows the nodes from the UI end to edit edges
 * </p>
 *
 */


public class EditNodesController {
    @FXML
    private JFXButton close, addNodeBtn, editNodeBtn, remNodeBtn;
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
        Main.mapEditScreen();
        nodeIDField.clear();
        xCoordField.clear();
        yCoordField.clear();
        floorField.clear();
        buildingField.clear();
        nodeTypeField.clear();
        longNameField.clear();
        shortNameField.clear();
        MapEditPageController.updateNodes();
        Main.closePopUp(addNodeBtn);
        Data.data.graph = testEmbeddedDB.dbBuildMap();
    }

    public void editNodeButton(){
       if(!nodeIDField.getText().trim().equals(null) && testEmbeddedDB.getNode(nodeIDField.getText().trim())!=null) {
           if (!xCoordField.getText().trim().equals("")) {
                testEmbeddedDB.updateNodeXCoord(nodeIDField.getText(), getxCoord());
           }
           if (!yCoordField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeYCoord(nodeIDField.getText(), getyCoord());
           }
           if (!floorField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeFloor(nodeIDField.getText(), floorField.getText());
           }
           if (!buildingField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeBuilding(nodeIDField.getText(), buildingField.getText());
           }
           if (!nodeTypeField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeType(nodeIDField.getText(), nodeTypeField.getText());
           }
           if (!longNameField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeLongName(nodeIDField.getText(), longNameField.getText());
           }
           if (!shortNameField.getText().trim().equals("")) {
               testEmbeddedDB.updateNodeShortName(nodeIDField.getText(), shortNameField.getText());
           }
           Main.mapEditScreen();
           nodeIDField.clear();
           xCoordField.clear();
           yCoordField.clear();
           floorField.clear();
           buildingField.clear();
           nodeTypeField.clear();
           longNameField.clear();
           shortNameField.clear();
           MapEditPageController.updateNodes();
           Main.closePopUp(editNodeBtn);
           Data.data.graph = testEmbeddedDB.dbBuildMap();
       }
    }
    public void removeNodeButton(){
        testEmbeddedDB.removeNode(nodeIDField.getText());
        Main.mapEditScreen();
        nodeIDField.clear();
        xCoordField.clear();
        yCoordField.clear();
        floorField.clear();
        buildingField.clear();
        nodeTypeField.clear();
        longNameField.clear();
        shortNameField.clear();
        MapEditPageController.updateNodes();
        Main.closePopUp(remNodeBtn);
        Data.data.graph = testEmbeddedDB.dbBuildMap();
    }

    @FXML
    public void openMapEditing() {
        Main.mapEditScreen();
        nodeIDField.clear();
        xCoordField.clear();
        yCoordField.clear();
        floorField.clear();
        buildingField.clear();
        nodeTypeField.clear();
        longNameField.clear();
        shortNameField.clear();
        Main.closePopUp(close);
    }

    @FXML
    public void setScreen(Node selected) {
        nodeIDField.setText(selected.getNodeID());
        xCoordField.setText("" + selected.getxCoordinate());
        yCoordField.setText("" + selected.getyCoordinate());
        floorField.setText(selected.getFloor());
        buildingField.setText(selected.getBuilding());
        nodeTypeField.setText(selected.getNodeType());
        longNameField.setText(selected.getLongName());
        shortNameField.setText(selected.getShortName());
    }

    @FXML
    public void setScreenAdd(double x, double y) {
        xCoordField.setText("" + (int)x);
        yCoordField.setText("" + (int)y);
    }

    @FXML
    public void clear(){
        nodeIDField.clear();
        xCoordField.clear();
        yCoordField.clear();
        floorField.clear();
        buildingField.clear();
        nodeTypeField.clear();
        longNameField.clear();
        shortNameField.clear();
    }
}
