package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import static sample.Main.setFoodString;

public class FoodController implements Initializable {

    private Main mainController;

    public void setMainController(Main main) {
        this.mainController = main;
    }

    @FXML
    private TableView<Food> foodMenu;

    @FXML
    private TableColumn<Food, String> names;

    @FXML
    private TableColumn<Food, String> prices;

    @FXML
    private TableColumn<Food, ImageView> pictures;

    @FXML
    private JFXTextField name;

    @FXML
    private GridPane priceBox;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXButton upload;

    @FXML
    private HBox selectionBox;

    @FXML
    private JFXButton done;


    String fileLocation;


    Food applePie = new Food("apple pie", 8.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thN0HD2MIW.jpg");
    Food banana = new Food("banana", 0.58, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/banana.png");
    Food sardines = new Food("sardines", 2.50, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/sardines.png");
    Food smokedSalmon = new Food("smoked salmon", 2.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/smoked_salmon.png");
    Food steak = new Food("steak with lamb sauce", 15.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thR6E02SIM.jpg");
    Food oreos = new Food("oreos", 3.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/th5MUPYTVT.jpg");
    Food water = new Food("water", 1.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/th6FB1GCKJ.jpg");
    Food soup = new Food("catfish soup", 8.59, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/th6W9F71G7.jpg");
    Food pizza = new Food("olive pizza", 13.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thB0H4P1OS.jpg");
    Food cake = new Food("chocolate cake", 4.00, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thFUUOROA2.jpg");
    Food orangeJuice = new Food("orange juice", 3.59, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thI21VG95P.jpg");
    Food mashedPotatoes = new Food("mashed potatoes", 3.99, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thIIS9OC4M.jpg");
    Food bread = new Food("loaf of bread", 2.40, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thJHMODTJX.jpg");
    Food burger = new Food("hamburger", 1.25, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thOM2SZM21.jpg");
    Food casserole = new Food("lobster casserole", 18.95, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thQC8LHS8B.jpg");
    Food parmesan = new Food("chicken parmesan", 7.99, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thS1391HVZ.jpg");
    Food tuna = new Food("tuna potato", 5.52, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thT0K5P8SP.jpg");
    Food shrimps = new Food("popcorn shrimps", 8.10, "C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src/sample/UI/Icons/foodpics/thT9IF11RU.jpg");


    //formatted list of food that go into menu
    private ObservableList<Food> foodObserve = FXCollections.observableArrayList(applePie, banana, sardines,
            smokedSalmon, steak, oreos, water, soup, pizza, cake, orangeJuice, mashedPotatoes, bread, burger,
            casserole, parmesan, tuna, shrimps);

    //formats the food fields into being able to be displayed in table
    public StringProperty stringToStringProperty(String cellEntry) {
        return new SimpleStringProperty(cellEntry);
    }

    public StringProperty doubleToObsValue(Double cellEntry) {
        NumberFormat numberFormat = new DecimalFormat("###.00");
        return new SimpleStringProperty(numberFormat.format(cellEntry));
    }

    public SimpleObjectProperty<ImageView> fileLocationToObsValue(String cellEntry) throws FileNotFoundException {
        FileInputStream apath = new FileInputStream(cellEntry);
        Image foodImage = new Image(apath);
        //Image foodImage = new Image(getClass().getResourceAsStream(cellEntry));
        ImageView foodPic = new ImageView();
        foodPic.setImage(foodImage);
        foodPic.setFitHeight(100);
        foodPic.setFitWidth(100);
        return new SimpleObjectProperty<ImageView>(foodPic);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        foodMenu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        names.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getName()).trim()));   //sets service name in column
        prices.setCellValueFactory(cellData -> doubleToObsValue(cellData.getValue().getPrice()));   //sets service status in column
        pictures.setCellValueFactory(cellData -> {
            try {
                return fileLocationToObsValue(cellData.getValue().getFileLocation());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });

        names.setCellFactory(TextFieldTableCell.forTableColumn());
        prices.setCellFactory(TextFieldTableCell.forTableColumn());

        refresh();
    }

    public void refresh() {
        foodMenu.setItems(foodObserve);
    }

    @FXML
    public void startEdit() {
        foodMenu.setEditable(true);
        names.setEditable(true);
        prices.setEditable(true);
        name.setVisible(true);
        priceBox.setVisible(true);
        upload.setVisible(true);
        selectionBox.setVisible(true);
        done.setVisible(true);
    }

    @FXML
    public void addFoodMenu() throws FileNotFoundException {
        ImageView foodPic = new ImageView();
        Image foodImage;
        Food newFood = new Food("", 0.00, null);

        newFood.setName(name.getText());
        newFood.setPrice(Double.parseDouble(price.getText()));
        newFood.setFileLocation(fileLocation);
        /*FileInputStream apath = new FileInputStream(fileLocation);
        foodImage = new Image(apath);
        //foodImage = new Image(getClass().getResourceAsStream(fileLocation));
        foodPic.setImage(foodImage);
        foodPic.setFitHeight(100);
        foodPic.setFitWidth(100);
*/
        foodObserve.add(newFood);
        foodMenu.setItems(foodObserve);

    }

    @FXML
    public void deleteFoodMenu() {
        ObservableList<Food> selectedRows, allfood;
        allfood = foodMenu.getItems();

        //this gives us the rows that were selected
        selectedRows = foodMenu.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Food objects from the table
        for (Food i : selectedRows)
            allfood.remove(i);
    }

    @FXML
    public void finishEdit() {
        foodMenu.setEditable(false);
        names.setEditable(false);
        prices.setEditable(false);
        name.setVisible(false);
        priceBox.setVisible(false);
        upload.setVisible(false);
        selectionBox.setVisible(false);
        done.setVisible(false);
    }

    public void uploadImage() throws IOException {
        Stage plsEnd = new Stage();
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(plsEnd);
        if (file != null) {
            fileLocation = file.getCanonicalPath();
        }
    }

    @FXML
    private JFXButton submit;

    @FXML
    public void foodSubmit() {
        ObservableList<Food> listOfOrders = foodMenu.getSelectionModel().getSelectedItems();
        String order = "";

        for(int i = 0; i < listOfOrders.size(); i++)
            order += listOfOrders.get(i).getName() + "!";

        setFoodString(order);

        Main.closePopUp(submit);
    }
}
