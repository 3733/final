package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Main;

import java.util.Timer;

/**This is the IT error page controller
 * this controller allows the user to send IT help requests
 *
 */

public class ItErrorController implements ITimed{

    private TimeoutController timeoutController;

    private Timer atimer;

    @FXML
    private Label itErrorLabel;

    @FXML
    private Label itErrorText;

    @FXML
    private Button yesIt;

    @FXML
    private Button noIt;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void yesRequest(){
        //Main.itRequestScreen();
        //ServiceRequestController.updateIt();
    }

    @FXML
    public void noRequest(){
        Main.startScreen();
    }

    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction()//  throws IOException, InterruptedException
    {
        try
        {
            timeoutController.doTimer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not start timer.");
        }
    }

    public void initialize()
    {
        timeoutController = new TimeoutController();
        atimer = new Timer();
        timeoutController.updateDelay(10); // per steph request.
        timeoutController.setTimer(atimer, true);
    }
}