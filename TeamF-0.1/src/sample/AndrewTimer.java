package sample;

import javafx.application.Platform;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AndrewTimer {

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

    public static TimerTask restoreNavScreen(Timer atime) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    atime.cancel();
                    timeoutAbout();
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

    public static void timeoutAbout() throws IOException, InterruptedException
    {
        Platform.runLater(new Runnable() {
            public void run()  {
                    System.out.println("This is the start of the timer thread.");
                    Main.closeAbout(); // not happening
            }
        });
    }
}
/*
 try {
                    System.out.println("This is the start of the timer thread.");
                    Main.closeAbout();
                    // Main.mapScreen();
                    // Main.closeAbout();
                    // Main.setStartScene(); /// don't use this
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }

 */