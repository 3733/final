package sample;

import javafx.application.Platform;
import org.apache.derby.iapi.sql.execute.ExecCursorTableReference;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AndrewTimer {

    private static long timeDelay = 6; // This is set to by the Admin controller singleton.

    public static long getDelay()
    {
        return timeDelay;
    }

    public static void setDelay(long input)
    {
        if (input <= 0)
        {
            timeDelay = 30;
        }
        else {
            timeDelay = input;
        }
    }

    // Test thread
    public static TimerTask testNewTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("This is the return of the test timer thread.");
            }
        };
        return timerTask;
    }

    public static TimerTask restoreNavScreen(Timer atime, boolean closePop) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    atime.cancel();
                    timeoutAbout(closePop);
                    System.out.println("This is the end of timer thread.");
                    // atime.cancel(); // have to cancel the thread
                } catch (Exception e) {
                    System.out.println("The timeout thing broke.");
                    e.printStackTrace();
                }
            }
        };
        return timerTask;
    }

    public static void timeoutAbout(boolean closePop) throws IOException, InterruptedException
    {
        Platform.runLater(new Runnable() {
            public void run()  {
                System.out.println("This is the start of the timer thread.");
                if (closePop)
                {
                    Main.closePopup(); // not happening
                }
                else
                {
                    try {
                        Main.mapScreen();
                    }
                    catch (Exception e)
                    {
                        System.out.println("I derped out. No switch to Nav screen.");
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}