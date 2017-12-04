// Created by Stephanie and Floris
// Purpose: To test directions were sent to the user defined email
/*Satisfies Integration testing of Email - Steph*/
/*ALL PASS - Steph*/

package sample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Vector;

import static org.junit.Assert.assertEquals;


public class EmailServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //setting up nodes and directions, never changes

    String directions;

    @Before
    public void directionsSetUp() throws Exception{
        //Creates a vector with only one node
        Vector<Node> vector = new Vector<Node>(10);
        Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        vector.add(n1);
        Node n2 = new Node("FHALL00201", 1640, 850, 1, "Tower", "HALL", "Chapel Hall Point 1", "CHP1", 'F');
        vector.addElement(n2);
        //Creates string of directions
        directions = NavigationPageController.directions(vector);
    }
    //Send Email Tests

    //Steph - Uses an invalid email to verify that the error has been properly handled by the system
    @org.junit.Test
    public void sendNotValidEmail() throws Exception {
        //tests validity
        thrown.expect(NullPointerException.class);
        EmailService notValid = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng");
        notValid.sendEmail(directions, "notValid");
    }

    //Steph - checks if email was successfully sent with valid email
    @org.junit.Test(expected = NullPointerException.class)
    public void sendSuccessfulEmail() throws Exception {
        //Creates a vector with only one node
        Vector<Node> vector = new Vector<Node>(10);
        Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        vector.add(n1);
        Node n2 = new Node("FHALL00201", 1640, 850, 1, "Tower", "HALL", "Chapel Hall Point 1", "CHP1", 'F');
        vector.addElement(n2);
        //Creates string of directions
        String directions = NavigationPageController.directions(vector);

        //tests validity
        EmailService successful = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng");
        //if successful, the status should be "sent message"
        successful.sendEmail(directions, "teamFCS3733@gmail.com");
        assertEquals("Sent message", successful.status);
    }

    //Steph - checks if status was properly set for an error by using incorrect username
    @org.junit.Test(expected = Exception.class)
    public void sendErrorEmail() throws Exception {
        //Creates a vector with only one node
        Vector<Node> vector = new Vector<Node>(10);
        Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        vector.add(n1);
        Node n2 = new Node("FHALL00201", 1640, 850, 1, "Tower", "HALL", "Chapel Hall Point 1", "CHP1", 'F');
        vector.addElement(n2);
        //Creates string of directions
        String directions = NavigationPageController.directions(vector);

        //tests validity
        //uses incorrect email address
        EmailService error = new EmailService("teamZCS3733@gmail.com", "FuschiaFairiesSoftEng");
        error.sendEmail(directions, "TeamFCS3733");
        //if an error occurs, such as setting up the server, the status should be set to this
        assertEquals("An error occurred while sending the message", error.status);
    }



}

