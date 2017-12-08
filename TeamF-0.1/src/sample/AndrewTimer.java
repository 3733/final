package sample;

import java.util.TimerTask;

public class AndrewTimer {

    // Andrew added
    public static TimerTask getNewTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("This thread says that the application timed out."); // Switch pages

            }
        };
        return timerTask;
    }

}
