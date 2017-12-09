package sample;

import java.util.Vector;

public class Time {


    private int minutes;
    private int seconds;

    public Time(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    public static Time getETE(Vector<Node> Path){

        double dist = Map.TotalDistance(Path)/10;

        double distInSeconds = dist/1.5;

        int minutes = (((int) distInSeconds))/60;
        int seconds = (((int) distInSeconds))%60;

        Time t1 = new Time(minutes,seconds);
        return t1;
    }




}
