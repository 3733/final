package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

public class TimeoutController implements Initializable{

    @FXML
    private JFXRadioButton logOpt, chatOpt, createOpt, viewOpt, mapOpt, usersOpt, popOpt;
    @FXML
    private JFXButton submit, back;
    @FXML
    private ToggleGroup windowOpt;
    @FXML
    private JFXTextField time;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void close() {
        Main.mapEditScreen();
        Main.closePopUp(back);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logOpt.setToggleGroup(windowOpt);
        chatOpt.setToggleGroup(windowOpt);
        createOpt.setToggleGroup(windowOpt);
        viewOpt.setToggleGroup(windowOpt);
        mapOpt.setToggleGroup(windowOpt);
        usersOpt.setToggleGroup(windowOpt);
        popOpt.setToggleGroup(windowOpt);
        logOpt.setSelected(true);
    }

    @FXML
    public void submit(){
        int sec = Integer.parseInt(time.getText());
        if(logOpt.isSelected()){}
        if(chatOpt.isSelected()){}
        if(createOpt.isSelected()){}
        if (viewOpt.isSelected()){}
        if(mapOpt.isSelected()){}
        if(usersOpt.isSelected()){}
        if(popOpt.isSelected()){}
    }

    protected volatile long delayTime = 10; // volatile just in case.

    protected volatile boolean isshowing; // volatile so that threads can use it

    private volatile boolean isPopUp; // This is the AndrewTimer class boolean that defines how to use the timer.

    protected Timer atime;

    public void setTimer(Timer time, boolean isPop)
    {
        atime = time;
        isPopUp = isPop;
        System.out.println(" Timer Set. ");
    }

    public void setTimerNav(Timer time)
    {
        atime = time;
        isPopUp = false;
    }

    public void setShowing(boolean in)
    {
        isshowing = in;
    }

    public boolean getShowing()
    {
        return isshowing;
    }

    public void doTimer()
    {
        System.out.println("   1_1_1   ");
        atime.cancel();
        System.out.println("   2_2_2   ");
        startTimer();
    }

    public void doNavTimer()
    {
        System.out.println("   1_1_1   ");
        atime.cancel();
        System.out.println("   2_2_2   ");
        startNavTimer();
    }

    public void startNavTimer()
    {
        System.out.println("    I suck a lot.    ");
        atime = new Timer();
        atime.schedule(AndrewTimer.restoreNavFromNav(atime), this.delayTime * 1000); // AndrewTimer.getDelay() * 1000);
    }

    public void startTimer()
    {
        System.out.println("    I suck a lot.    ");
        atime = new Timer();
        atime.schedule(AndrewTimer.restoreNavScreen(atime, isPopUp), this.delayTime * 1000); // AndrewTimer.getDelay() * 1000);
    }

    public void updateDelay(long inputTime)
    {
        delayTime = inputTime;
        if (inputTime <= 0 || inputTime > 5*60) // Prevent stupid timeout
        {
            delayTime = 30;
        }
        else {
            delayTime = inputTime;
        }
    }

    public long getDelay()
    {
        return this.delayTime;
    }

}
