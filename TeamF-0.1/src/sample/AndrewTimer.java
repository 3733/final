package sample;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.derby.iapi.sql.execute.ExecCursorTableReference;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.jfoenix.svg.SVGGlyphLoader.clear;

public class AndrewTimer {
    /** This is the timer functionality that does nothing
     * </p>
     * Currently this code is not enabled
     *
     * @return
     */

    // Test thread
    public static TimerTask testNewTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //System.out.println("This is the return of the test timer thread.");
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
                    //System.out.println("This is the end of timer thread.");
                    // atime.cancel(); // have to cancel the thread
                } catch (Exception e) {
                    System.out.println("The timeout thing broke.");
                    e.printStackTrace();
                }
            }
        };
        return timerTask;
    }

    public static TimerTask restoreNavFromNav(Timer atime) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    atime.cancel();
                    timeoutNav();
                  // System.out.println("This is the end of timer thread.");
                    // atime.cancel(); // have to cancel the thread
                } catch (Exception e) {
                    System.out.println("The timeout thing broke.");
                    e.printStackTrace();
                }
            }
        };
        return timerTask;
    }

    public static void timeoutNav() throws IOException, InterruptedException
    {
        Platform.runLater(new Runnable() {
            public void run()  {
                //System.out.println("This is the start of the timer thread.");
                    try {
                        // Before setting the UI, auto logout the person.
//                        AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
//                        SettingSingleton.getSettingSingleton().setAuthProperty(clearAuth);
//                        Main.logOutUser();
//                        Main.mapScreen();
                        // NavigationPageController.logout();
                        // NavigationPageController.timerlogout();
                        // Main.mapScreen();
                        AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
                        SettingSingleton.getSettingSingleton().setAuthProperty(clearAuth);
                        Main.mapScreen();

                    }
                    catch (Exception e)
                    {
                        System.out.println("I derped out. No switch to Nav screen.");
                        e.printStackTrace();
                    }
            }
        });
    }

    public static void timeoutAbout(boolean closePop) throws IOException, InterruptedException
    {
        Platform.runLater(new Runnable() {
            public void run()  {
                //System.out.println("This is the start of the timer thread.");
                if (closePop)
                {
                    Main.closePopup();
                }
                else
                {
                    try {
                        // Before setting the UI, auto logout the person.
                        AuthenticationInfo clearAuth = new AuthenticationInfo("guest", AuthenticationInfo.Privilege.USER);
                        SettingSingleton.getSettingSingleton().setAuthProperty(clearAuth);
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