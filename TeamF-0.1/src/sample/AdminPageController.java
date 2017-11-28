package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;


public class AdminPageController implements Initializable{

    @FXML
    private SplitMenuButton algoMenu;
    @FXML
    private JFXButton upFloor, downFloor;
    @FXML
    private JFXTextField destination;
    @FXML
    private Label floorLabel;
    @FXML
    private ScrollPane scrollMap;
    @FXML
    private ImageView map;
    @FXML
    private JFXButton upButton, downButton;
    @FXML
    private String currentFloor = "First Floor";
    @FXML
    private SplitMenuButton quickFloor;
    @FXML
    private RadioMenuItem groundSet, lowerOneSet, lowerTwoSet, oneSet, twoSet, threeSet;
    private Vector<Node> path = new Vector<Node>();
    private Map CurMap;

    private Node Kiosk;

    @FXML
    public void logout(){
        Main.startScreen();
    }

    @FXML
    public void edit(){
        Main.mapEditScreen();
    }

    @FXML
    public void serviceRequest() {Main.serviceScreen();}
    @FXML
    public void acceptRequest() {Main.acceptScreen();}

    public void setMap(Map m){
        this.CurMap = m;
        //System.out.println("KSJHDFUZBXCGV"+CurMap.getNodes().size());
    }

    public void setKiosk(Node k){
        this.Kiosk = k;
    }

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void editUsers(){Main.editUsersScreen();}
    @FXML
    public void back(){Main.startScreen();}
    @FXML
    public void help(){Main.genErrorScreen();}

    @FXML
    public void setAlgorithm(){}

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            zoom();
            scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
            floorLabel.setText("First Floor");

            //setting menu item actions
            groundSet.setOnAction(event -> {
                currentFloor = "Ground Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(true);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thegroundfloor.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

            lowerOneSet.setOnAction(event -> {
                currentFloor = "Lower Level One";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

            lowerTwoSet.setOnAction(event -> {
                currentFloor = "Lower Level Two";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

            oneSet.setOnAction(event -> {
                currentFloor = "First Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

            twoSet.setOnAction(event -> {
                currentFloor = "Second Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

            threeSet.setOnAction(event -> {
                currentFloor = "Third Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(true);
                downButton.setDisable(false);
                try {
                    map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/03_thethirdfloor.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(currentFloor);
            });

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //switches images based on floors
    public void floorUp() throws FileNotFoundException{
        switch (currentFloor){
            case "Ground Floor":
                currentFloor = "Lower Level One";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png")));
                System.out.println(currentFloor);
                break;
            case "Lower Level One":
                currentFloor = "Lower Level Two";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png")));
                System.out.println(currentFloor);
                break;
            case "Lower Level Two":
                currentFloor = "First Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
                System.out.println(currentFloor);
                break;
            case "First Floor":
                currentFloor = "Second Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png")));
                System.out.println(currentFloor);
                break;
            case "Second Floor":
                currentFloor = "Third Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(true);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/03_thethirdfloor.png")));
                System.out.println(currentFloor);
                break;
            default: break;
        }
    }

    public void floorDown() throws FileNotFoundException{
        switch (currentFloor){
            case "Lower Level One":
                currentFloor = "Ground Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(true);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thegroundfloor.png")));
                System.out.println(currentFloor);
                break;
            case "Lower Level Two":
                currentFloor = "Lower Level One";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel1.png")));
                System.out.println(currentFloor);
                break;
            case "First Floor":
                currentFloor = "Lower Level Two";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/00_thelowerlevel2.png")));
                System.out.println(currentFloor);
                break;
            case "Second Floor":
                currentFloor = "First Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
                System.out.println(currentFloor);
                break;
            case "Third Floor":
                currentFloor = "Second Floor";
                floorLabel.setText(currentFloor);
                upButton.setDisable(false);
                downButton.setDisable(false);
                map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/02_thesecondfloor.png")));
                System.out.println(currentFloor);
                break;
            default: break;
        }
    }

    @FXML
    private void zoom() {
        int MIN_PIXELS = 15;
        //zoom
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();

        map.setPreserveRatio(true);
        reset(map, width, height);

        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

        map.setOnMousePressed(e -> {

            Point2D mousePress = imageViewToImage(map, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });

        map.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(map, new Point2D(e.getX(), e.getY()));
            shift(map, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(map, new Point2D(e.getX(), e.getY())));
        });

        map.setOnScroll(e -> {
            double delta = e.getDeltaY();
            Rectangle2D viewport = map.getViewport();

            double scale = clamp(Math.pow(1.01, delta),

                    // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                    Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                    // don't scale so that we're bigger than image dimensions:
                    Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );

            Point2D mouse = imageViewToImage(map, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving
            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                    0, height - newHeight);

            map.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
        });

        map.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                reset(map, width, height);
            }
        });

        map.setPreserveRatio(true);
        map.fitWidthProperty().bind(scrollMap.widthProperty());
        map.fitHeightProperty().bind(scrollMap.heightProperty());
    }

    // reset to the top left:
    private void reset(javafx.scene.image.ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }
    // shift the viewport of the imageView by the specified delta, clamping so
// the viewport does not move off the actual image:
    private void shift(javafx.scene.image.ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(javafx.scene.image.ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    public void findPath(String in) throws IOException {
        //Returns
        long st = System.currentTimeMillis();
        this.path= SearchEngine.SearchPath(in,CurMap,Kiosk);
        long et = System.currentTimeMillis();
        System.out.println(et-st+"<===ALGO");

        st = System.currentTimeMillis();
        testDrawDirections(path);
        et = System.currentTimeMillis();
        System.out.println(et-st+"<===DR");
    }
    @FXML
    public void go() throws IOException,InterruptedException{


        for (int i =0; i<CurMap.getNodes().size();i++){

            System.out.println((i+1)+ " : "+CurMap.getNodes().get(i).getLongName());

            for (int j =0; j<CurMap.getNodes().get(i).getNeighbors().size();j++){

                System.out.println( "      =====> "+CurMap.getNodes().get(i).getNeighbors().get(j).getLongName());
            }
        }

        System.out.println(destination.getText());
        findPath(destination.getText());

    }
    // Purpose: Draw a path on the map
    @FXML
    public void testDrawDirections(Vector<Node> path) throws IOException {
        BufferedImage firstFloor = ImageIO.read(getClass().getResource("/sample/UI/Icons/01_thefirstfloor.png"));
        Graphics2D pathImage = firstFloor.createGraphics();
        int length = path.size();
        String nameDest = path.get(length-1).getShortName();
        String nameDept = path.get(0).getShortName();

        Data.data.currentMap = "path" + nameDept + "-" + nameDest;
        // Setting up the proper color settings
        pathImage.setStroke(new BasicStroke(10)); // Controlling the width of the shapes drawn
        // Iterate through all the path nodes to draw the path
        for(int i = 0; i < length ; i++) {
            Node node = path.get(i);
            if(i + 1 < length){
                Node node2 = path.get(i+1);
                // Lines are drawn offset,
                pathImage.setColor( new java.awt.Color(0,0,0)); // This color is black
                pathImage.drawLine(node.getxCoordinate(), node.getyCoordinate(),node2.getxCoordinate() ,node2.getyCoordinate());
            }
        }
        map.setImage(SwingFXUtils.toFXImage(firstFloor,null));
        System.out.println("Image set on map");
    }



}
