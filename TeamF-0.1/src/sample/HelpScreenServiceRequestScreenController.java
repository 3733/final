package sample;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import sample.Main;

public class HelpScreenServiceRequestScreenController {
    private Main mainController;
    public void setMainController(Main main){ this.mainController = main;}

    @FXML
    public void back(){Main.startScreen();}

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    Node n1 = new Node("FDEPT00101", 1614, 829, "1", "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
    @FXML
    public JFXTextArea box;

    public int id = 9000;


    @FXML
    public void sendITHelpRequest() throws MissingFieldException { //when the Send button is pressed
        ItRequest newIt = new ItRequest(n1 , box.getText(),
                id, "", "",
                "", 0000, "it", "unaccepted",
               1);

        //requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addItRequest(newIt);

        box.clear();
        id++;
    }
}
