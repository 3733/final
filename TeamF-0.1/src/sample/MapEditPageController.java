package sample;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapEditPageController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scrollMap;
    @FXML
    private ImageView map;
    @FXML
    private JFXButton upButton, downButton;
    @FXML
    private Label floorLabel;
    @FXML
    private String currentFloor = "First Floor";
    @FXML
    private SplitMenuButton quickFloor;
    @FXML
    private RadioMenuItem groundSet, lowerOneSet, lowerTwoSet, oneSet, twoSet, threeSet;


    private Main mainController;


    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public void editNodes(){
        Main.nodeEditScreen();
    }

    @FXML
    public void editEdges(){
        Main.edgeEditScreen();
    }

    @FXML
    public void openMapScreen() throws IOException, InterruptedException {
        Main.mapScreen();
    }

    @FXML
    public void help(){Main.genErrorScreen();}
    @FXML
    public void logout(){Main.startScreen();}
    @FXML
    public void back(){Main.adminScreen();}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            zoom();
            scrollMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

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

            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
            floorLabel.setText("First Floor");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
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
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }
    // shift the viewport of the imageView by the specified delta, clamping so
// the viewport does not move off the actual image:
    private void shift(ImageView imageView, Point2D delta) {
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
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }

    //switches images based on floors
    public void floorUp() throws FileNotFoundException {
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

}
