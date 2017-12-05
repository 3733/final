package sample;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class PathTest {

    Node start, end, mid, corner, sharpRight, right, left;
    Vector<Node> straightNodes = new Vector<Node>();
    Vector<Node> sharpLeftNodes = new Vector<>();
    Vector<Node> sharpRightNodes = new Vector<>();
    Vector<Node> rightNodes = new Vector<>();
    Vector<Node> leftNodes = new Vector<>();
    Vector<Node> nullNodes = new Vector<>();
    NavigationPageController n = new NavigationPageController();




/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 *DIRECTIONS TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
//All pass - Steph
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
 *DIRECTIONS TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/


    @Test
    public void separator() throws Exception {
    }

    @Test
    public void go() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void multiFloorPathDrawing() throws Exception {
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