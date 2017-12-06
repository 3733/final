package sample;

import java.util.HashMap;

public class AnswerMap implements Data{

    public static String getAnswer(String question){

        HashMap<String,String> qAndA = new HashMap<String,String>();

        qAndA.put("Not connecting to server.",
                "Something may have happened to the server. Please ask an IT expert for help.");
        qAndA.put("No map displayed.",
                "Restart the application. If that does not work, the data may be corrupted.\nYou may need to reinstall the application.");
        qAndA.put("Application crashes.",
                "Your computer may not have enough RAM. You may need to upgrade your system.");
        qAndA.put("No service request has been submitted even though I filled one out.",
                "Make sure all fields are filled in when making a request. Also, make sure the request is going to a valid location.");
        qAndA.put("I don't see a path being generated.",
                "Make sure both the start and end fields are filled out with valid locations.");
        qAndA.put("The mouse not working.",
                "Unplug the mouse and plug it back in. If that does not work, you may need a new mouse.");
        qAndA.put("I am lost.",
                "You are at: " + Data.data.kiosk.getLongName());
        qAndA.put("What are visiting hours?",
                "visitors are generally welcome between 1 p.m. and 9 p.m.;\n" +
                        "all visiting children must be over the age of 12, though exceptions can be made for a patient’s own children;\n" +
                        "any visitor exposed to or having a communicable disease (the flu or a cold, tuberculosis, measles, mumps, rubella, pertussis, impetigo) should not visit patients;\n" +
                        "it is usually best to limit visitors to two at a time, to respect the comfort of all our patients.");
        qAndA.put("Can I get Assistance?",
                "Please ask a staff member.");
        qAndA.put("How do I use this application?",
                "Select whether you want to change the start or end point. The end point is selected by default.\n" +
                        "Enter a desired location in the search bar. Then click search to find a path between the two points.\n"+
                        "If you know where you want to go, you can also search the location on the start page.");
        qAndA.put("Person",
                "REAL PERSON");


        double maxLav = 0;
        String maxKey = null;
        for (String key : qAndA.keySet()) {

            if(maxLav > SearchEngine.computeDistance(question,key)){
                maxLav = SearchEngine.computeDistance(question,key);
                maxKey = key;
            }
        }

        return qAndA.get(maxKey);
    }


}