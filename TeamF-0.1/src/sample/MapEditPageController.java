package sample;

import com.jfoenix.controls.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.WeakEventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.Vector;

public class MapEditPageController implements Initializable, Data, ITimed{

    private TimeoutController timeoutController;

    private Timer atimer;

    //fxml components
    @FXML
    private JFXToggleButton nodeEdgeEdit;
    @FXML
    private ScrollPane scrollMap;
    @FXML
    private ImageView map;
    @FXML
    private JFXListView threeList, twoList, oneList, groundList, lowerTwoList, lowerOneList;
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private RadioMenuItem chooseAStar, chooseDepth, chooseBreadth, chooseDijk, chooseBeam, chooseBest;
    @FXML
    private SplitMenuButton algoMenu;
    @FXML
    private JFXButton editNodeBtn, editEdgeBtn;
    @FXML
    private Canvas pathCanvas;
    @FXML
    private Canvas pathCanvas1;
    @FXML
    private Tab floorThree, floorTwo, floorOne, floorLowerTwo, floorLowerOne, floorGround;
    @FXML
    private JFXCheckBox allNodes;
    @FXML
    private JFXCheckBox allEdges;
    
    //other variables
    private Main mainController;


    public Node selectedNode; // Used for opening map editing windows

    public double calcX; // Used for opening map editing windows

    public double calcY; // Used for opening map editing windows

    boolean editEdges;

    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction()//  throws IOException, InterruptedException
    {
        try
        {
            timeoutController.doTimer();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Could not start timer.");
        }
    }

    //initialization
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeoutController = new TimeoutController();
        atimer = new Timer();
        timeoutController.updateDelay(30); // 30 per steph request.
        timeoutController.setTimer(atimer, false); // This is not a popup

        updateNodes();
        updateEdges();
        Data.data.gc1 = pathCanvas1.getGraphicsContext2D();
        Data.data.gc2 = pathCanvas.getGraphicsContext2D();

        scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        //popluating list view -- three
        ObservableList<String> threeItems = FXCollections.observableArrayList (
                "Bridge to Dana-Farber Cancer Institute", "Brigham Circle Medical Associates", "Center for Infertility and Reproductive Surgery",
                "Clinical Trials", "Conference Center","Dialysis,", "Dialysis Waiting Room", "Fetal Med & Genetics", "General Surgical Specialties Suite A",
                "General Surgical Specialties Suite B", "Gynecology", "Gyencology Oncology MIGS", "Innovation Hub", "Maternal Fetal Practice",
                "MICU 3B/C Waiting Room", "OB/GYN Blood Lab", "Obstetrics", "The Porch", "Reproductive Endocrine Labs", "Urology", "Watkins Clinic C");
        threeList.setItems(threeItems);

        //populating list view -- second
        ObservableList<String> twoItems = FXCollections.observableArrayList("Bridge to Children's", "Brigham Health", "Carrie M. Hall Conference Center",
                "Chest Diseases", "Coffee Connection", "Comprehensive Breast Health", "Conference Center", "Duncan Reid Conference Room", "Ear, Nose, & Throat",
                "Endoscopy", "Garden Cafe", "Gift Shop", "Jen Center for Primary Care", "Lee Bell Breast Center", "Louis Bornstein Family Amphitheater",
                "Medical Surgical Specialties", "MRI Associates", "Oral Medicine and Dentistry", "Orthopedics and Rhematology", "Outpatient Specimen Collection",
                "Pat's Place", "Patient Financial Services", "Plastic Surgery", "Thoracic Surgery Clinic", "Vascular Diagnostic Lab", "Watkins A", "Watkins B",
                "Weiner Center for Preoperative Evaluation");
        twoList.setItems(twoItems);

        //populating list view -- first
        ObservableList<String> oneItems = FXCollections.observableArrayList("Ambulatory X-Ray", "Asthma Research Center", "Au Bon Pain",
                "Bretholtz Center for Patients and Families", "CART Waiting", "Connor's Center Security Desk", "CPE Classroom", "International Patient Center",
                "Kessler Library", "MS Waiting", "Multifaith Chapel", "Neuroscience Waiting Room", "Obstetrics Admitting", "Occupational Health", "Partner's Shuttle",
                "Rehabilitation Services", "Shapiro Board Room", "Sharf Admitting Center", "Spiritual Care Office", "Wound Care Center Ambulatory Treatment Room",
                "Zinner Breakout Room");
        oneList.setItems(oneItems);

        //populating list view -- lower two
        ObservableList<String> lowerTwoItems = FXCollections.observableArrayList("Cardiac Stress Test Lab", "Cardiovascular Imaging Center", "CVRR",
                "Interpreter Services", "MRI/CT Scan Imaging", "Radiation Oncology", "Radiation Oncology Conference Room", "Radiation Oncology T/X Suite");
        lowerTwoList.setItems(lowerTwoItems);

        //populating list view -- lower one
        ObservableList<String> lowerOneItems = FXCollections.observableArrayList("Abrams Conference Room", "Anesthesia Conference Room", "CSIR MRI",
                "Day Surgery Family Waiting", "Helen Hogan Conference Room", "Medical Records Conference Room", "Medical Records Film Library", "Nuclear Medicine",
                "Outpatient Fluoroscopy", "Pre-OP PACU", "Ultrasound", "Volunteers");
        lowerOneList.setItems(lowerOneItems);

        //populating list -- ground
        ObservableList<String> groundItems = FXCollections.observableArrayList("Infusion", "Neuro Testing", "Outpatient Plebotomy");
        groundList.setItems(groundItems);
        tabPane.getSelectionModel().select(floorOne);
        map.setImage(data.firstFloor);//new Image(getClass().getResourceAsStream("/sample/UI/Icons/01_thefirstfloor.png")));

            /*
            * AStar -1
            * Breadth -2
            * Depth -3
            * Dijk -4
            * Beam -5
            * Best -6
            * */

        //default is Astar
        algoMenu.setText(chooseAStar.getText());
        chooseAStar.setSelected(true);

        chooseAStar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(1);
                algoMenu.setText(chooseAStar.getText());
            }
        });

        chooseBreadth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(2);
                algoMenu.setText(chooseBreadth.getText());
            }
        });

        chooseDepth.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(3);
                algoMenu.setText(chooseDepth.getText());
            }
        });

        chooseDijk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(4);
                algoMenu.setText(chooseDijk.getText());
            }
        });

        chooseBeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(5);
                algoMenu.setText(chooseBeam.getText());
            }
        });

        chooseBest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.navigationPageController.setCurrentAlgo(6);
                algoMenu.setText(chooseBest.getText());
            }
        });

        try {
            clickNearestNodeSelected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateNodes() {
        Data.data.firstFloorNodes = testEmbeddedDB.getNodesByFloor(3);
        Data.data.groundFloorNodes = testEmbeddedDB.getNodesByFloor(2);
        Data.data.lowerLevel01FloorNodes = testEmbeddedDB.getNodesByFloor(1);
        Data.data.lowerLevel02FloorNodes = testEmbeddedDB.getNodesByFloor(0);
        Data.data.secondFloorNodes = testEmbeddedDB.getNodesByFloor(4);
        Data.data.thirdFloorNodes = testEmbeddedDB.getNodesByFloor(5);
    }

    public static void updateEdges() {
        Data.data.thirdFloorEdges = testEmbeddedDB.getEdgesByFloor(5);
        Data.data.secondFloorEdges = testEmbeddedDB.getEdgesByFloor(4);
        Data.data.firstFloorEdges = testEmbeddedDB.getEdgesByFloor(3);
        Data.data.groundFloorEdges = testEmbeddedDB.getEdgesByFloor(2);
        Data.data.lower1FloorEdges = testEmbeddedDB.getEdgesByFloor(1);
        Data.data.lower2FloorEdges = testEmbeddedDB.getEdgesByFloor(0);
    }
    //getter and setters
    public void setMainController(Main main){
        this.mainController = main;
    }

    public void clearCanvas(GraphicsContext gcIn, Canvas pathCanvasIn) {
        double y = pathCanvasIn.getHeight();
        double x = pathCanvasIn.getWidth();
        if(gcIn != null)
            gcIn.clearRect(0,0,x,y);
        else {
            System.out.println("IT WAS NULL" + gcIn);
        }
    }

    @FXML
    public void changeFloorL1() {
        clearCanvas(data.gc2,pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.L1Floor);
    }

    @FXML
    public void changeFloor1() {
        clearCanvas(data.gc2,pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.firstFloor);
    }

    @FXML
    public void changeFloor2() {
        clearCanvas(data.gc2,pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.secondFloor);
    }

    @FXML
    public void changeFloor3() {
        clearCanvas(data.gc2, pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.thirdFloor);
    }

    @FXML
    public void changeFloorG() {
        clearCanvas(data.gc2,pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.GFloor);
    }

    @FXML
    public void changeFloorL2() {
        clearCanvas(data.gc2,pathCanvas);
        clearCanvas(data.gc1,pathCanvas1);
        map.setImage(Data.data.L2Floor);
    }


    //functions to open pages
    @FXML
    public void editNodes(){
        Main.nodeEditScreen(editNodeBtn);
    }

    @FXML
    public void editEdges(){
        Main.edgeEditScreen(editEdgeBtn);
    }

    @FXML
    public void openMapScreen() throws IOException, InterruptedException {
        Main.mapScreen();
    }

    @FXML
    public void help(){Main.setHelpScreenServiceRequestScreen();}

    @FXML
    public void logout(){Main.startScreen();}

    @FXML
    public void back() throws IOException, InterruptedException{Main.mapScreen();}

    //other functions
    @FXML
    public void importCSV(){
        testEmbeddedDB.fillEdgesTable();
        testEmbeddedDB.fillNodesTable();
    }

    @FXML
    public void exportCSV(){
        testEmbeddedDB.writeToCSV();
    }


    /**
     * function to draw the nodes of the  floors
     * @param FloorNodes
     */
    public void drawNodesCircle(Vector<Node> FloorNodes)
    {
        if(FloorNodes != null) {
            int length = FloorNodes.size();

            Data.data.gc1.setStroke(Color.BLUE);
            Data.data.gc1.stroke();
            // Iterate through all the given nodes to draw the node
            for (int i = 0; i < length; i++)
            {
                Node nodesMap = FloorNodes.get(i);
                double divisionCst = 4.15;
                int offset = 0;
                data.gc1.strokeOval(nodesMap.getxCoordinate()/divisionCst + offset ,nodesMap.getyCoordinate()/divisionCst + offset, 4.0, 4.0);
                data.gc1.fillOval(nodesMap.getxCoordinate()/divisionCst + offset ,nodesMap.getyCoordinate()/divisionCst + offset, 4.0, 4.0);
            }
        }
    }

    /**
     * function to draw all the edges of the floor
     */
    public void drawEdges(Vector<Edge> floorEdges) {
        if(floorEdges != null) {
            Data.data.gc1.setStroke(Color.RED);
            Data.data.gc1.stroke();
            Data.data.gc1.setLineWidth(4);
            for (Edge edge:floorEdges) {
                double divisionCst = 4.15;
                Node start = edge.getStart();
                Node stop = edge.getEnd();
                data.gc1.strokeLine(start.getxCoordinate() / divisionCst + 1,start.getyCoordinate() / divisionCst + 1, stop.getxCoordinate() / divisionCst + 1, stop.getyCoordinate()/ divisionCst  + 1);
            }
        }
    }

    /**
     * mouse click location done!
     * @throws IOException
     */

    public void clickNearestNodeSelected() throws IOException {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem item1 = new MenuItem("Edit Node");
            MenuItem item2 = new MenuItem("Add Node");
            contextMenu.getItems().addAll(item1, item2);

            ContextMenu contextMenu2 = new ContextMenu();
            MenuItem item3 = new MenuItem("Set Start Node");
            MenuItem item4 = new MenuItem("Set End Node");
            contextMenu2.getItems().addAll(item3, item4);

            pathCanvas1.setOnMousePressed((javafx.scene.input.MouseEvent e) -> {
                e.consume();
                Data.data.gc1.clearRect(0, 0, 1143, 783);

                double newX1 = (e.getSceneX());
                double newY1 = (e.getSceneY());

                double divisionCst = 4.15;
                int offset = 1;
                if (floorLowerTwo.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.lowerLevel02FloorNodes);
                } else if (floorLowerOne.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.lowerLevel01FloorNodes);
                } else if (floorGround.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.groundFloorNodes);
                } else if (floorOne.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.firstFloorNodes);
                } else if (floorTwo.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.secondFloorNodes);
                } else if (floorThree.isSelected()) {
                    selectedNode = mousePosition((newX1 - 2) * divisionCst + offset, (newY1 - 2) * divisionCst + offset, Data.data.thirdFloorNodes);
                }

                if (e.isPrimaryButtonDown()) {
                    e.consume();
                    Data.data.gc1.setStroke(Color.RED);
                    Data.data.gc1.stroke();

                    Data.data.gc1.strokeOval(selectedNode.getxCoordinate() / divisionCst, selectedNode.getyCoordinate() / divisionCst, 7.0, 7.0);
                    Data.data.gc1.fillOval(selectedNode.getxCoordinate() / divisionCst, selectedNode.getyCoordinate() / divisionCst, 7.0, 7.0);
                    Main.nodeEditScreenClick(selectedNode, editNodeBtn);
                    drawFloorNodes();
                } else if (e.isSecondaryButtonDown()) {
                    if(editEdges) {
                        contextMenu2.show(pathCanvas1, newX1 - 40, newY1);
                        contextMenu2.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Event handling for the first item, open the edit edge screen
                                Main.edgeStartEditScreen(editEdgeBtn, selectedNode);
                                drawFloorEdges();
                            }
                        });
                        contextMenu2.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Main.edgeEndEditScreen(editEdgeBtn, selectedNode);
                                drawFloorEdges();
                            }
                        });
                    } else {
                        contextMenu.show(pathCanvas1, newX1 - 40, newY1);
                        contextMenu.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Event handling for the first item, open the edit node screen
                                Main.nodeEditScreenClick(selectedNode, editNodeBtn);
                                drawFloorNodes();
                            }
                        });
                        contextMenu.getItems().get(1).setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // Event handling for the second item, open add node screen
                                calcX = (int) (e.getSceneX() - data.offset) * divisionCst;
                                calcY = (e.getSceneY() - data.offset) * divisionCst;
                                Main.nodeAddEditScreenClick(editNodeBtn, calcX, calcY);
                                drawFloorNodes();
                            }
                        });
                    }
                }
            });
        }

    @FXML
    public void drawFloorNodes(){
            if (floorLowerTwo.isSelected()) {
                drawNodesCircle(data.lowerLevel02FloorNodes);
            } else if (floorLowerOne.isSelected()) {
                drawNodesCircle(data.lowerLevel01FloorNodes);
            } else if (floorGround.isSelected()) {
                drawNodesCircle(data.groundFloorNodes);
            } else if (floorOne.isSelected()) {
                drawNodesCircle(data.firstFloorNodes);
            } else if (floorTwo.isSelected()) {
                drawNodesCircle(data.secondFloorNodes);
            } else if (floorThree.isSelected()) {
                drawNodesCircle(data.thirdFloorNodes);
            }
    }

    @FXML
    public void drawFloorEdges() {
            if (floorLowerTwo.isSelected()) {
                drawEdges(data.lower2FloorEdges);
            } else if (floorLowerOne.isSelected()) {
                drawEdges(data.lower1FloorEdges);
            } else if (floorGround.isSelected()) {
                drawEdges(data.groundFloorEdges);
            } else if (floorOne.isSelected()) {
                drawEdges(data.firstFloorEdges);
            } else if (floorTwo.isSelected()) {
                drawEdges(data.secondFloorEdges);
            } else if (floorThree.isSelected()) {
                drawEdges(data.thirdFloorEdges);
            }
    }


    /**
     * Function to calculate the nearest node depend on the mouse click location.
     * @param x
     * @param y
     * @param NodesOfTheFloor
     * @return
     */
    public Node mousePosition (double x, double y,Vector<Node> NodesOfTheFloor) {
        double newMouseX = x;
        double newMouseY = y;
        Node GoodOne = null;
        double MinDist = 100000000.0;
        double dist;



        for (Node i: NodesOfTheFloor) {
            dist = data.graph.MouseNodeDist(newMouseX,newMouseY,i);
            if(dist < MinDist) {
                MinDist = dist;
                GoodOne = i;
            }
        }
        return GoodOne;

    }

    @FXML
    public void updateBoolean() {
        editEdges = nodeEdgeEdit.isSelected();
    }

    @FXML
    public void clear() {
        clearCanvas(Data.data.gc1,pathCanvas1);
        clearCanvas(Data.data.gc,pathCanvas);
    }
}
