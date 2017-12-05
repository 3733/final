package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NavigationPageControllerTest {

    NavigationPageController n;
    ImageView arrow1, arrow2, arrow3, arrowG, arrowL1, arrowL2;
    JFXTextField emailTest, destinationTest;
    JFXButton sendTest;
    JFXListView stepsTest;
    Label endLabelTest, startLabelTest;

    @Before
    public void clearFieldsSetUp() throws Exception {
        try {

            /*FXMLLoader navLoader = new FXMLLoader(getClass().getResource("UI/NavigationScreen.fxml"));
            Parent Nav = navLoader.load();
            navigationPageController = navLoader.getController();
            navigationPageController.setMainController(this);
            map = new Scene(Nav);*/

            FXMLLoader loader = new FXMLLoader();
            BorderPane root = loader.load(getClass().getResource("../src/sample/UI/NavigationScreen.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("../src/sample/UI/style.css");

            n = loader.getController();

            AnchorPane anchor = (AnchorPane) root.getChildren().get(2);
            arrow3 = (ImageView) anchor.getChildren().get(8);
            arrow2 = (ImageView) anchor.getChildren().get(9);
            arrow1 = (ImageView) anchor.getChildren().get(10);
            arrowG = (ImageView) anchor.getChildren().get(11);
            arrowL1 = (ImageView) anchor.getChildren().get(12);
            arrowL2 = (ImageView) anchor.getChildren().get(13);



           /* ESTDate = (DatePicker) (ESTVBox.getChildren().get(1));

            txtESTHours = (TextField) ((HBox) ESTVBox.getChildren().get(2)).getChildren().get(0);
            txtESTMinutes = (TextField) ((HBox) ESTVBox.getChildren().get(2)).getChildren().get(2);
            txtESTSeconds = (TextField) ((HBox) ESTVBox.getChildren().get(2)).getChildren().get(4);

            labelJan1970EST = ((Label) ((VBox) gridPane.getChildren().get(1)).getChildren().get(1));
            labelBeforeDecade = ((Label) ((VBox) gridPane.getChildren().get(2)).getChildren().get(1));
            labelPST = ((Label) ((VBox) gridPane.getChildren().get(3)).getChildren().get(1));*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
*OTHER FUNCTIONS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    /*@FXML
    public void clearFields(){
        threeArrow.setVisible(false);
        twoArrow.setVisible(false);
        oneArrow.setVisible(false);
        groundArrow.setVisible(false);
        lowerOneArrow.setVisible(false);
        lowerTwoArrow.setVisible(false);
        sendLabel.setVisible(false);
        email.setVisible(false);
        sendButton.setVisible(false);
        directionSteps.setVisible(false);
        endLabel.setText("");
        startLabel.setText("Lower Pike Hallway Exit Lobby");
        destination.setText("");
        directionSteps.getItems().clear();
        reset(map, width, height);
    }*/

    @Test
    public void clearFields() throws Exception {

        n.clearFields();
        assertEquals(false, arrow3.isVisible());
        assertEquals(false, arrow2.isVisible());
        assertEquals(false, arrow1.isVisible());
        assertEquals(false, arrowG.isVisible());
        assertEquals(false, arrowL1.isVisible());
        assertEquals(false, arrowL2.isVisible());
    }

    @Test
    public void autoClose() throws Exception {
    }

    @Test
    public void setArrows() throws Exception {
    }

    @Test
    public void emergencyButton() throws Exception {
    }

    @Test
    public void sendMsg() throws Exception {
    }

    @Test
    public void autoComplete() throws Exception {
    }

}