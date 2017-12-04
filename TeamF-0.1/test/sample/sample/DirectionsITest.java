/*Steph - Testing Node Integration with Controller
* Purpose: To see if Nodes are correctly integrated with directions*/
/*UNFINISHED - Steph*/

package sample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class DirectionsITest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //vector with valid nodes
    Vector<Node> successNode = new Vector<Node>(10);
    //vector with invalid nodes
    Vector<Node> inverseNode = new Vector<Node>(10);

    @Before
    public void setUp(){
        //valid nodes
        Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        Node n2 = new Node("FHALL00201", 1640, 850, 1, "Tower", "HALL", "Chapel Hall Point 1", "CHP1", 'F');
        Node n3 = new Node("FHALL00301", 1788, 850, 1, "Tower", "HALL", "Chapel Hall Point 2", "CHP2", 'F');
        //invalid Nodes
        Node v1 = new Node("null", -1, -1, 1, "Tower", "HALL", "Chapel Hall Point 2", "CHP2", 'F');

        //sets up valid Vector
        successNode.addElement(n1);
        successNode.addElement(n2);
        successNode.addElement(n3);

        //sets up inverse vector
        inverseNode.addElement(n3);
        inverseNode.addElement(n2);
        inverseNode.addElement(n1);
    }


    //Test path with only one node
    //Make sure test fails with no/null node
    //Test path with multiple nodes
    //STILL NEED TO TEST USING NODES FROM THE DB, WAITING FOR FULL OK WITH ASTAR AND INTEGRATION

    //Steph - makes sure PathException is thrown
    @Test
    public void throwsPathExceptionA() throws Exception{
        Vector<Node> nullNode = new Vector<Node>(10);
        nullNode.add(null);
        //get directions
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        NavigationPageController.directions(nullNode);
    }

    //Steph - Test path with only one node
    @Test
    public void throwsPathExceptionB() throws Exception{
        //adds a singular node
        Vector<Node> oneNode = new Vector<Node>(10);
        Node n1 = new Node("FDEPT00101", 1614, 829, 1, "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        oneNode.add(n1);
        //getdirections
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        NavigationPageController.directions(oneNode);
    }

    //Steph - successful path using multiple valid Nodes
    @Test
    public void sucessfulPathForward() throws Exception{
        String output = NavigationPageController.directions(successNode);
        String expected = "Start at Center for International Medecine<br>Go towards Chapel Hall Point 1<br>When you arrive " +
                "at Chapel Hall Point 1 go straight towards Chapel Hall Point 2<br>";
        assertEquals(expected, output);
    }

    //Steph - successful path with nodes inverted
    @Test
    public void successfulPathReverse() throws Exception{
        String output = NavigationPageController.directions(inverseNode);
        String expected = "Start at Chapel Hall Point 2<br>Go towards Chapel Hall Point 1<br>" +
                "When you arrive at Chapel Hall Point 1 go straight towards Center for International Medecine<br>";
        System.out.println(output);
        assertEquals(expected, output);
    }

}