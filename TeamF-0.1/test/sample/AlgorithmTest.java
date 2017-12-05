/*PURPOSE: To test all aspects of the algorithms
* Steph's Check: All tests pass*/

package sample;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class AlgorithmTest {


    Dijkstras testDijk = new Dijkstras();
    Astar testAStar = new Astar();
    BeamFirstSearch testBeam = new BeamFirstSearch();
    BFSearch testBFS = new BFSearch();
    BestFirstSearch testBest = new BestFirstSearch();
    DFSearch testDepth = new DFSearch();
    Node start, end, mid;
    Edge edge1, edge2, edge3;
    Map map;
    Vector<Node> allNodes = new Vector<Node>();
    Vector<Edge> allEdges = new Vector<Edge>();
    PathAlgorithm pathAStar = new PathAlgorithm(new Astar());
    PathAlgorithm pathBFS = new PathAlgorithm(new BFSearch());
    PathAlgorithm pathDFS = new PathAlgorithm(new DFSearch());
    PathAlgorithm pathDijk = new PathAlgorithm(new Dijkstras());
    PathAlgorithm pathBeam = new PathAlgorithm(new BeamFirstSearch());
    PathAlgorithm pathBest = new PathAlgorithm(new BestFirstSearch());


    @Before
    public void setUp(){
        start = new Node("id", 1, 1, "1", "Tower", "testNode", "testNode", "test",
                'F');
        end = new Node("anotherId", 100, 100, "2", "Tower", "anotherTest", "yetAnotherTest",
                "aTest", 'F');
        mid = new Node("anotherId", 50, 50, "2", "Tower", "anotherTest", "yetAnotherTest",
                "aTest", 'F');
        allNodes.add(start);
        allNodes.add(mid);
        allNodes.add(end);
        edge1 = new Edge("edge1", start, end);
        edge2 = new Edge("edge2", start, mid);
        edge3 = new Edge("edge3", mid, end);
        allEdges.add(edge1);
        allEdges.add(edge2);
        allEdges.add(edge3);
        map = new Map(allNodes, allEdges);
        map.BuildMap();
    }

/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* HEURISTIC COST TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/
    //Cost between same node
    @Test
    public void heuristicDijkTestSame() throws Exception {
        double actual = testDijk.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }

    @Test
    public void heuristicAStartTestSame(){
        double actual = testAStar.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }

    @Test
    public void heuristicBeamTestSame(){
        double actual = testBeam.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }

    @Test
    public void heuristicBestTestSame(){
        double actual = testBest.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }

    @Test
    public void heuristicDepthTestSame(){
        double actual = testDepth.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }

    @Test
    public void heuristicBFSTestSame(){
        double actual = testBFS.HeuristicCost(start, start);
        assertEquals(0, actual, 0.1);
    }


    //using node with larger values than start
    @Test
    public void heuristicDijkTestPos(){
        double actual = testDijk.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }

    @Test
    public void heuristicAStarTestPos(){
        double actual = testAStar.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }

    @Test
    public void heuristicBeamTestPos(){
        double actual = testBeam.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }

    @Test
    public void heuristicBestTestPos(){
        double actual = testBest.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }

    @Test
    public void heuristicDepthTestPos(){
        double actual = testDepth.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }

    @Test
    public void heuristicBFSTestPos(){
        double actual = testBFS.HeuristicCost(start, end);
        assertEquals(140.0071426749364, actual, 0.1);
    }


    //using node before starting node
    @Test
    public void heuristicDijkTestNeg(){
        double actual = testDijk.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }

    @Test
    public void heuristicAStarTestNeg(){
        double actual = testAStar.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }

    @Test
    public void heuristicBeamTestNeg(){
        double actual = testBeam.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }

    @Test
    public void heuristicBestTestNeg(){
        double actual = testBest.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }

    @Test
    public void heuristicDepthTestNeg(){
        double actual = testDepth.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }

    @Test
    public void heuristicBFSTestNeg(){
        double actual = testBFS.HeuristicCost(end, mid);
        assertEquals(70.71067811865476, actual, 0.1);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* ASTAR FIND PATH
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //using simple values to check for validity
    @Test
    public void aStarFindPathTestA() throws Exception {
        Vector<Node> actualVec = pathAStar.executeStrategy(start,end, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(end);
        assertEquals(expectedVec, actualVec);
    }

    //checking a mid point
    @Test
    public void aStarFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathAStar.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* BEAM FIRST FIND PATH TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

//using simple values to check for validity
@Test
public void beamFindPathTestA() throws Exception {
    Vector<Node> actualVec = pathBeam.executeStrategy(start,end, map);
    Vector<Node> expectedVec = new Vector<Node>();
    expectedVec.add(start);
    expectedVec.add(end);
    assertEquals(expectedVec, actualVec);
}

    //checking a mid point
    @Test
    public void beamFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathBeam.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* BEST FIRST FIND PATH TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //using simple values to check for validity
    @Test
    public void bestFindPathTestA() throws Exception {
        Vector<Node> actualVec = pathBest.executeStrategy(start,end, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(end);
        assertEquals(expectedVec, actualVec);
    }

    //checking a mid point
    @Test
    public void bestFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathBest.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* DIJKSTRA'S FIND PATH TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //using simple values to check for validity
    @Test
    public void dijkFindPathTestA() throws Exception {
        Vector<Node> actualVec = pathDijk.executeStrategy(start,end, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(end);
        assertEquals(expectedVec, actualVec);
    }

    //checking a mid point
    @Test
    public void dijkFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathDijk.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }


/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* DEPTH FIRST FIND PATH TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //using simple values to check for validity
    @Test
    public void depthFindPathTestA() throws Exception {
        Vector<Node> actualVec = pathDFS.executeStrategy(start,end, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        expectedVec.add(end);
        assertEquals(expectedVec, actualVec);
    }

    //checking a mid point
    @Test
    public void depthFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathDFS.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }

/*//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
* BFS FIND PATH TESTS
* ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////*/

    //using simple values to check for validity
    @Test
    public void bfsFindPathTestA() throws Exception {
        Vector<Node> actualVec = pathBFS.executeStrategy(start,end, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        expectedVec.add(end);
        assertEquals(expectedVec, actualVec);
    }

    //checking a mid point
    @Test
    public void bfsFindPathTestB()throws Exception{
        Vector<Node> actualVec = pathBFS.executeStrategy(start, mid, map);
        Vector<Node> expectedVec = new Vector<Node>();
        expectedVec.add(start);
        expectedVec.add(mid);
        assertEquals(expectedVec, actualVec);
    }

}