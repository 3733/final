package sample;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import sample.Main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;


// This class is now depreciated.
public class HelpScreenServiceRequestScreenController {


    private Main mainController;
    public void setMainController(Main main){ this.mainController = main;}

    @FXML
    public void back() throws IOException, InterruptedException {Main.mapScreen();}

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    Node n1 = new Node("FDEPT00101", 1614, 829, "1", "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');

    @FXML
    public void someAction()
    {

    }

    @FXML
    public JFXTextArea box;

    public static int id = (int) System.currentTimeMillis(); //sets ID according to time, prevents IDs from being the same

    @FXML
    public void sendITHelpRequest() throws MissingFieldException { //when the Send button is pressed
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("h:mm a"); //sets the created time field in the right format

        ItRequest newIt = new ItRequest(n1 , box.getText(),
                id, ft.format(date), "",
                "", 0000, "it", "unaccepted",
               1);

        //requestList.add(newAssist);               //new service request is made and added to priority queue
        testEmbeddedDB.addItRequest(newIt);

        box.clear();
        id = (int) System.currentTimeMillis();
    }
}
