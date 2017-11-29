package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class MapEditPageController implements Initializable{
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
    private JFXListView threeList, twoList, oneList, groundList, lowerTwoList, lowerOneList;
    @FXML
    private Tab floorOne;
    @FXML
    private JFXTabPane tabPane;


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
    public void changeFloorL1() {
        map.setImage(Data.data.L1Floor);
    }

    @FXML
    public void changeFloorL2() {
        map.setImage(Data.data.L2Floor);
    }

    @FXML
    public void changeFloor1() {
        map.setImage(Data.data.firstFloor);
    }

    @FXML
    public void changeFloor2() {
        map.setImage(Data.data.secondFloor);
    }

    @FXML
    public void changeFloor3() {
        map.setImage(Data.data.thirdFloor);
    }

    @FXML
    public void changeFloorG() {
        map.setImage(Data.data.GFloor);
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
            map.setImage(new Image(new FileInputStream("./TeamF-0.1/src/sample/UI/Icons/01_thefirstfloor.png")));
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
