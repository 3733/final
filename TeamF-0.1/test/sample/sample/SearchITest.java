/*Steph - Search Field Testing*//*

*/
/*Purpose - To test how the main page interacts with the search function and pathfinding
* Because buildMap is a part of searchPath, that satisfies component testing for that function*//*

*/
/*ALL PASS - Steph*//*


package sample.sample;

import org.junit.Before;
import org.junit.Test;
import sample.*;

import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class SearchITest {

    SearchEngine Engine;

    @Before
    public void setUp(){
        Vector<Node> dbnodes = new Vector<Node>();


        for (int i = 0; i< testEmbeddedDB.getAllNodes().size(); i++){

            dbnodes.add(testEmbeddedDB.getAllNodes().get(i));
        }


        Vector <Edge> EdgesBad = new Vector<>();

        Vector <Edge> EdgesGood = new Vector<>();

        for (int i =0;i<testEmbeddedDB.getAllEdges().size();i++){

            EdgesBad.add(testEmbeddedDB.getAllEdges().get(i));
        }

        for(int i =0;i<EdgesBad.size();i++){

            String ID;
            String StID;
            String EndID;
            Node Start = null;
            Node End = null;

            ID = EdgesBad.get(i).getEdgeID();
            StID = EdgesBad.get(i).getStart().getNodeID();
            EndID = EdgesBad.get(i).getEnd().getNodeID();

            for(int j = 0; j< dbnodes.size();j++){

                if(dbnodes.get(j).getNodeID().equals(StID)){
                    Start = dbnodes.get(j);

                }else if(dbnodes.get(j).getNodeID().equals(EndID)){

                    End = dbnodes.get(j);
                }
            }

            Edge e = new Edge(ID,Start,End);


            EdgesGood.add(e);
        }

        Map CuurMap = new Map(dbnodes, EdgesGood);



        CuurMap.BuildMap();

        Engine = new SearchEngine(CuurMap.getMap());

        //Vector<Node> SearchLocations = Engine.SearchPath(destination.getText());
    }

    //Steph - Search Engine is tested with key term to find the possible nodes to an area
    @Test
    public void searchPathTest1(){
        Vector<Node> result = new Vector<Node>(10);
        result.add(Engine.getNodeInput().get(26));
        result.add(Engine.getNodeInput().get(27));
        result.add(Engine.getNodeInput().get(28));
        String stringTest = "library";
        Vector<Node> actual = Engine.SearchPath(stringTest);
        assertEquals(actual, result);
    }

    //Steph - Search Engine is tested with new key term to find the possible nodes to an area
    @Test
    public void searchPathTest2(){
        Vector<Node> result = new Vector<Node>(10);
        result.add(Engine.getNodeInput().get(41));
        result.add(Engine.getNodeInput().get(42));
        String stringTest = "bathroom";
        Vector<Node> actual = Engine.SearchPath(stringTest);
        System.out.println(Engine.getNodeInput().get(41).getLongName());
        System.out.println(Engine.getNodeInput().get(42).getLongName());
        assertEquals(actual, result);
    }

    //Steph - properly test aStar method
    @Test
    public void testAStar(){

        Node a = new Node("1",0,0,1,"HALL","asd","big","small",'f');
        Node b = new Node("2",3,0,1,"HALL","asd","big","small",'f');
        Node c = new Node("3",0,3,1,"HALL","asd","big","small",'f');
        Node d = new Node("4",4,5,1,"HALL","asd","big","small",'f');
        Node e = new Node("5",2,6,1,"HALL","asd","big","small",'f');
        Node f = new Node("6",7,3,1,"HALL","asd","big","small",'f');
        Node g = new Node("7",8,8,1,"HALL","asd","big","small",'f');

        //test 1 answ a,c,e,g

        Edge aEdge = new Edge("1",a,b);
        Edge bEdge = new Edge("2",b,d);
        Edge cEdge = new Edge("3",d,f);
        Edge dEdge = new Edge("4",f,g);
        Edge eEdge = new Edge("5",a,c);
        Edge fEdge = new Edge("6",c,e);
        Edge gEdge = new Edge("7",e,g);


        //test2 answ a,c,d,g

        Edge hEdge = new Edge("8",c,d);
        Edge iEdge = new Edge("9",d,g);
        Edge jEdge = new Edge("10",b,f);
        Vector<Node> googlefish = new Vector<Node>();
        Vector<Edge> babble = new Vector<Edge>();

        googlefish.add(a);
        googlefish.add(b);
        googlefish.add(c);
        googlefish.add(d);
        googlefish.add(e);
        googlefish.add(f);
        googlefish.add(g);



        babble.add(aEdge);
        babble.add(bEdge);
        babble.add(cEdge);
        babble.add(dEdge);
        babble.add(eEdge);
        babble.add(fEdge);
        babble.add(gEdge);
        babble.add(hEdge);
        babble.add(iEdge);
        babble.add(jEdge);

        Map MapObj = new Map(googlefish, babble);
        MapObj.BuildMap();

        Vector<Node> Result1 = new Vector<Node>();
        Result1 = MapObj.AStar(a,g);
        Vector<Node> TestResult= new Vector<>();
        TestResult.add(a);
        TestResult.add(c);
        TestResult.add(d);
        TestResult.add(g);
        assertEquals(Result1,TestResult);




        System.out.print("\n");


    }




}
*/
