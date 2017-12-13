package sample;

import java.io.IOException;
import java.util.Timer;

public class TimeoutController {

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