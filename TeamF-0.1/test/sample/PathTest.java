package sample;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Vector;

import static org.junit.Assert.*;

public class PathTest {


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 *DIRECTIONS TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
//All pass - Steph

    Node start, end, mid, corner, sharpRight, right, left;
    Vector<Node> straightNodes = new Vector<Node>();
    Vector<Node> sharpLeftNodes = new Vector<>();
    Vector<Node> sharpRightNodes = new Vector<>();
    Vector<Node> rightNodes = new Vector<>();
    Vector<Node> leftNodes = new Vector<>();
    Vector<Node> nullNodes = new Vector<>();
    NavigationPageController n = new NavigationPageController();

    @Before
    public void setUpDirections(){

        start = new Node("id", 1, 1, "1", "Tower", "testNode", "testNode", "test",
                'F');
        end = new Node("anotherId", 100, 100, "2", "Tower", "anotherTest", "yetAnotherTest",
                "aTest", 'F');
        mid = new Node("anotherId", 50, 50, "2", "Tower", "anotherTest", "evenMoreTests",
                "aTest", 'F');
        corner = new Node("corner", 50, 75, "2", "Tower","testNode", "cornerNode", "corner",
                'F');
        sharpRight = new Node("sharpRight", 50, 25, "2", "Tower", "sharpRight", "sharpRightNode",
                "sharpRight", 'F');
        right = new Node("right", 7000, 7000, "2", "Tower", "right", "rightNode",
                "right", 'F');
        straightNodes.add(start);
        straightNodes.add(mid);
        straightNodes.add(end);


        sharpLeftNodes.add(start);
        sharpLeftNodes.add(mid);
        sharpLeftNodes.add(end);
        sharpLeftNodes.add(corner);

        rightNodes.add(start);
        rightNodes.add(mid);
        rightNodes.add(sharpRight);

        sharpRightNodes.add(start);
        sharpRightNodes.add(right);
        sharpRightNodes.add(sharpRight);

        nullNodes.add(end);
        nullNodes.add(corner);
        nullNodes.add(left);

        leftNodes.add(start);
        leftNodes.add(mid);
        leftNodes.add(corner);
    }

    //tests straight line of directions
    @Test
    public void directionsTestStraight() throws Exception {
        String expected = "Start at testNode<br>Go towards evenMoreTests<br>When you arrive at evenMoreTests go straight towards yetAnotherTest<br>";
        assertEquals(expected, n.directions(straightNodes));
    }

    //tests a corner direction -- sharply left
    @Test
    public void directionsTestSharpLeft() throws Exception{
        String expected = "Start at testNode<br>Go towards evenMoreTests<br>When you arrive at evenMoreTests go straight towards yetAnotherTest<br>" +
                "When you arrive at yetAnotherTest go sharply left towards cornerNode<br>";
        assertEquals(expected, n.directions(sharpLeftNodes));
    }

    //tests a corner direction -- sharply right
    @Test
    public void directionsTestRight() throws Exception{
        String expected = "Start at testNode<br>Go towards evenMoreTests<br>When you arrive at evenMoreTests go right towards sharpRightNode<br>";
        assertEquals(expected, n.directions(sharpRightNodes));
    }

    //tests a corner direction -- right

    @Test
    public void directionsTestSharpRight() throws Exception{
        String expected = "Start at testNode<br>Go towards rightNode<br>When you arrive at rightNode go sharply right towards sharpRightNode<br>";
        assertEquals(expected, n.directions(rightNodes));
    }

    @Test
    public void directionsTestLeft() throws Exception{
        String expected = "Start at testNode<br>Go towards evenMoreTests<br>When you arrive at evenMoreTests go left towards cornerNode<br>";
        assertEquals(expected, n.directions(leftNodes));
    }

    //tests for Null Pointer Exception
    @Test(expected = NullPointerException.class)
    public void directionsTestNPE() throws Exception{
        n.directions(nullNodes);
    }

/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 * MULTI FLOOR PATH DRAWING  TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    Node oneNode;
    Node twoNode;
    Node threeNode;
    Node groundNode;
    Node lowerOneNode;
    Node lowerTwoNode;
    Vector<Node> allFloorNodes = new Vector<>();

    @Before
    public void multiSetUp(){

        oneNode = new Node("01", 50, 50, "1", "Tower", "HALL", "floorOneNode",
                "floorOne", 'F');
        twoNode = new Node("02", 50, 50, "2", "Tower", "HALL", "floorTwoNode",
                "floorTwo", 'F');
        threeNode = new Node("03", 50, 50, "3", "Tower", "HALL", "floorThreeNode",
                "floorThree", 'F');
        groundNode = new Node("0G", 50, 50, "G", "Tower", "HALL", "floorGroundNode",
                "floorGroud", 'F');
        lowerOneNode = new Node("0L1", 50, 50, "L1", "Tower", "HALL", "floorLowerOneNode",
                "floorLowerOne", 'F');
        lowerTwoNode = new Node("0L2", 50, 50, "L2", "Tower", "HALL", "floorLowerTwoNode",
                "floorLowerTwo", 'F');

        allFloorNodes.add(oneNode);
        allFloorNodes.add(twoNode);
        allFloorNodes.add(threeNode);
        allFloorNodes.add(groundNode);
        allFloorNodes.add(lowerOneNode);
        allFloorNodes.add(lowerTwoNode);
    }

    //path draws on all floors
    @Test
    public void multiTestAll()throws IOException, InterruptedException{
        Vector<String> expected = new Vector<>();
        expected.add("L2");
        expected.add("L1");
        expected.add("G");
        expected.add("1");
        expected.add("2");
        expected.add("3");
        n.MultiFloorPathDrawing(allFloorNodes);
        assertEquals(expected, n.getFloorsVisited());
    }

    //path draws on one floor
    @Test
    public void multiTestOne(){

    }

    //path draws on more than one floor
    @Test
    public void multiTestMore(){

    }

    //path draws on none
    @Test
    public void multiTestNone(){}

    //throws an exception
    @Test
    public void multiTestError(){

    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void setMap() throws Exception {
    }

    @Test
    public void testDrawDirections() throws Exception {
    }

    @Test
    public void settingSearch() throws Exception {
    }

    @Test
    public void findPath() throws Exception {
    }

    @Test
    public void settingFields() throws Exception {
    }

    @Test
    public void changeFloorL1() throws Exception {
    }

    @Test
    public void changeFloorL2() throws Exception {
    }

    @Test
    public void changeFloor1() throws Exception {
    }

    @Test
    public void changeFloor2() throws Exception {
    }

    @Test
    public void changeFloor3() throws Exception {
    }

    @Test
    public void changeFloorG() throws Exception {
    }


}