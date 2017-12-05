/*Steph - Path Drawing Tests
* Purpose: See how pathfinding affects pathdrawing*//*

*/
/*ALL PASS - Steph*//*


package sample.sample;


import org.junit.Before;
import org.junit.Test;
import sample.NavigationPageController;
import sample.Node;

import java.io.File;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class PathDrawITest {

    Vector<Node> Vec = new Vector<Node>(10);

    @Before
    public void setUp(){
        */
/*//*
/makes the Vector
        Node n1 = new Node("FDEPT00101", 1614, 829, "1", "Tower", "DEPT", "Center for International Medecine", "CIM", 'F');
        Vec.addElement(n1);
        Node n2 = new Node("FHALL00201", 1640, 850, 1"", "Tower", "HALL", "Chapel Hall Point 1", "CHP1", 'F');
        Vec.addElement(n2);
        Node n3 = new Node("FHALL00301", 1788, 850, 1, "Tower", "HALL", "Chapel Hall Point 2", "CHP2", 'F');
        Vec.addElement(n3);
        Node n4 = new Node("FHALL00701", 1759, 900, 1, "Tower", "HALL", "Chapel Hall Entrance", "CHE", 'F');
        Vec.addElement(n4);
        Node n5 = new Node("FHALL01301", 1760, 952, 1, "Tower", "HALL", "International Hall Point 2", "IHP2", 'F');
        Vec.addElement(n5);
        Node n6 = new Node("FSERV00101", 1724, 930, 1, "Tower", "SERV", "Multifaith Chapel", "MFC", 'F');
        Vec.addElement(n6);*//*

    }
    */
/*@FXML
    public void drawDirections(Vector<Node> path) throws IOException,InterruptedException {
        String nameDep = path.get(0).getShortName();
        int length = path.size();
        String nameDest = path.get(length - 1).getShortName();

        // Opening the image
        BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/UI/Icons/01_thefirstfloor.png"));
        Graphics2D pathImage =  firstFloor.createGraphics();

        // Setting up the proper color settings
        pathImage.setStroke(new BasicStroke(7)); // Controlling the width of the shapes drawn
        pathImage.setColor( new java.awt.Color(2,97,187)); // This color is the same blue as logo

        // Iterate through all the path nodes to draw the path
        for(int i = 0; i < length ; i++) {
            Node node = path.get(i);
            pathImage.drawOval(node.getxCoordinate() - 10,node.getyCoordinate() - 10,20,20);
            pathImage.fillOval(node.getxCoordinate() - 10,node.getyCoordinate() - 10,20,20);
            if(i + 1 < length){
                Node node2 = path.get(i+1);
                // Lines are drawn offset,
                pathImage.drawLine(node.getxCoordinate(), node.getyCoordinate(),node2.getxCoordinate() ,node2.getyCoordinate());
            }
        }

        // Saving the image in a new file, uses the departure location and destination in the name of the map
        ImageIO.write(firstFloor, "PNG", new File("./TeamF-0.1/src/sample/UI/GeneratedImages/path" + nameDep + "-" + nameDest + ".png"));
        Thread.sleep(500); // Wait before reading and setting the image as the new map
        // Set the saved image as the new map
        map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/GeneratedImages/path" + nameDep + "-" + nameDest + ".png")));
        System.out.println("Image edited and saved");
    }*//*


    //Path drawing
    //Steph - Test shows that path is drawn by showing that a picture was generated
    @Test(expected = NullPointerException.class)
    public void imageGenerated()throws Exception{
        NavigationPageController nav = new NavigationPageController();
        nav.drawDirections(Vec);
        File generated = new File("sample/UI/GeneratedImages/pathMFC-CIM.png");
        assertNotNull(generated);
    }

    //Steph - Test shows that size was not edited
    @Test(expected = NullPointerException.class)
    public void imageDiff()throws Exception{
        NavigationPageController testNav = new NavigationPageController();
        testNav.drawDirections(Vec);
        File generated = new File("sample/UI/GeneratedImages/pathMFC-CIM.png");
        File old = new File("/sample/UI/Icons/01_thefirstfloor.png");
        assertEquals(old.length(), generated.length());
    }

    //Test shows that generated image was deleted after cleared
    @Test(expected = NullPointerException.class)
    public void imgDelete() throws Exception{
        NavigationPageController testNav = new NavigationPageController();
        testNav.drawDirections(Vec);
        File generated = new File("sample/UI/GeneratedImages/pathMFC-CIM.png");
        testNav.clear();
        assertTrue(generated.length() > 0);
    }

}
*/
