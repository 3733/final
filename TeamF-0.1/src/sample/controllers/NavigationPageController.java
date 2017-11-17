package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import sample.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

public class NavigationPageController {

    // Contains the user zoom setting
    @FXML
    private Slider zoom;

    // Contains the user email entry
    @FXML
    private TextField email;

    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;

    // Contains the desired user destination
    @FXML
    private TextField destination;

    // Contains stairs option
    @FXML
    private CheckBox stairs;

    // Contains the elevator option
    @FXML
    private  CheckBox elevator;

    // Contains the Invalid email error message
    @FXML
    private static Label invalidEmailText;

    private Vector<Node> path = new Vector<Node>();

    @FXML
    public void dragMap(){
        //map.setX();
        //map.setY();
    }

    // Purpose: Zoom the map ImageView
    @FXML
    public void zoomMap(){
        System.out.println("Zooming ");
        double sliderSetting = zoom.getValue();
        map.setScaleX(sliderSetting * 0.1);
        map.setScaleY(sliderSetting * 0.1);
    }

    // The go button next to the destination text field, starts pathfinding algorithm, direction print, map drawing
    @FXML
    public void go() throws IOException,InterruptedException{


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


        for (int i =0; i<CuurMap.getMap().size();i++){

            System.out.println((i+1)+ " : "+CuurMap.getMap().get(i).getLongName());

            for (int j =0; j<CuurMap.getMap().get(i).getNeighbors().size();j++){

                System.out.println( "      =====> "+CuurMap.getMap().get(i).getNeighbors().get(j).getLongName());
            }
        }

        SearchEngine Engine = new SearchEngine(CuurMap.getMap());

        Vector<Node> SearchLocations = Engine.SearchPath(destination.getText());



        //KIOSK LOCATION
        Node MinNode = CuurMap.getMap().get(0);
        Double MinDistance=1000000.0;

        for(int i =0; i<SearchLocations.size();i++ ){
            if(MinDistance  > CuurMap.TotalDistance(CuurMap.AStar(CuurMap.getMap().get(0),SearchLocations.get(i)))){
                MinDistance = CuurMap.TotalDistance(CuurMap.AStar(CuurMap.getMap().get(0),SearchLocations.get(i)));
                MinNode = SearchLocations.get(i);
            }
        }



         Vector<Node> Path =CuurMap.AStar(CuurMap.getMap().get(0),MinNode);

        path = Path;
        drawDirections(Path);
    }

    // Method to clear the path on the map when the user presses clear map
    @FXML
    public void clear() throws FileNotFoundException{
        map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
    }

    //sets invalid email label when necessary for errorhandling
    @FXML
    public static void setInvalidEmail(){
        invalidEmailText.setVisible(true);
    }

    // User clicks send email button
    @FXML
    public void sendMsg() throws Exception{
        //Vector<Node> msgVec = new Vector<Node>(10);
        EmailService emailService = new EmailService("teamFCS3733@gmail.com", "FuschiaFairiesSoftEng");
        //go();
        emailService.sendEmail(NavigationPageController.directions(path), email.getText());
    }

    // Button to return to the welcome screen
    @FXML
    public void back(){
        Main.startScreen();
    }

    // Purpose: Print out directions for a path of nodes
    public static String directions(Vector<Node> in){
        String out = "";
        Node a, b, c;
        if(in.size()<2){
            out = out.concat("Path too short");
        }
        a = in.get(0);
        b = in.get(1);
        out = out.concat("Start at " + a.getLongName()+"<br>");
        out = out.concat("Go towards " + b.getLongName()+"<br>");

        for(int i = 2; i < in.size(); i++){
            a = in.get(i-2);
            b = in.get(i-1);
            c = in.get(i);
            String turn = "";
            double angle = NodeMath.findAngle(a.getxCoordinate(), a.getyCoordinate(), b.getxCoordinate(), b.getyCoordinate(), c.getxCoordinate(), c.getyCoordinate());
            if(angle<45){
                turn = "sharply right";
            }else if(angle < 135){
                turn = "right";
            }else if(angle < 225){
                turn = "straight";
            }else if(angle <315){
                turn = "left";
            }else{
                turn = "sharply left";
            }

            out = out.concat("When you arrive at " + b.getLongName() + " go " + turn + " towards " + c.getLongName() + "<br>");
        }
        return out;
    }

    // Purpose: Draw a path on the map
    @FXML
    public void drawDirections(Vector<Node> path) throws IOException,InterruptedException {
        String nameDep = path.get(0).getShortName();
        int length = path.size();
        String nameDest = path.get(length - 1).getShortName();

        // Opening the image
        BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/views/Icons/01_thefirstfloor.png"));
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
        ImageIO.write(firstFloor, "PNG", new File("path" + nameDep + "-" + nameDest + ".png"));

        //clearFile("./TeamF-0.1/src/sample/UI/GeneratedImages/path" + nameDep + "-" + nameDest + ".png");
        /*FileWriter data = new FileWriter("./TeamF-0.1/src/sample/Data/Data.txt", false);
        PrintWriter writer2 = new PrintWriter(data);
        writer2.printf("%s","./TeamF-0.1/src/sample/UI/GeneratedImages/path" + nameDep + "-" + nameDest + ".png");
        writer2.close();
        data.close();*/
        // Set the saved image as the new map
        Data.data.map = map.getImage();
        Data.data.currentMap = "path" + nameDep + "-" + nameDest + ".png";
        Thread.sleep(2000); // Wait before reading and setting the image as the new map
        map.setImage(new Image(new FileInputStream("path" + nameDep + "-" + nameDest + ".png")));
        System.out.println("Image edited and saved");
    }
}
