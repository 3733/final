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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.Vector;

import static sample.Main.setFoodString;

public class FoodController implements Initializable, ITimed{

    //private TimeoutController timeoutController;

    //private Timer atimer;

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
    private TableView<Food> yourMenu;

    @FXML
    private TableColumn<Food, String> yourNames;

    @FXML
    private TableColumn<Food, String> yourPrices;

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

    @FXML
    private Label totalCost;


    String fileLocation;


    @FXML // This is the method that gets called everywhere in the fxml files.
    public void someAction()//  throws IOException, InterruptedException
    {
//        try
//        {
//            timeoutController.doTimer();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            System.out.println("Could not start timer.");
//        }
    }

    Food applePie = new Food("apple pie", 8.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thN0HD2MIW.jpg", true);
    Food banana = new Food("banana", 0.58, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/banana.png", true);
    Food sardines = new Food("sardines", 2.50, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/sardines.png", true);
    Food smokedSalmon = new Food("smoked salmon", 2.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/smoked_salmon.png", true);
    Food steak = new Food("steak with lamb sauce", 15.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thR6E02SIM.jpg", true);
    Food oreos = new Food("oreos", 3.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/th5MUPYTVT.jpg", true);
    Food water = new Food("water", 1.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/th6FB1GCKJ.jpg", true);
    Food soup = new Food("catfish soup", 8.59, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/th6W9F71G7.jpg", true);
    Food pizza = new Food("olive pizza", 13.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thB0H4P1OS.jpg", true);
    Food cake = new Food("chocolate cake", 4.00, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thFUUOROA2.jpg", true);
    Food orangeJuice = new Food("orange juice", 3.59, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thI21VG95P.jpg", true);
    Food mashedPotatoes = new Food("mashed potatoes", 3.99, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thIIS9OC4M.jpg", true);
    Food bread = new Food("loaf of bread", 2.40, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thJHMODTJX.jpg", true);
    Food burger = new Food("hamburger", 1.25, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thOM2SZM21.jpg", true);
    Food casserole = new Food("lobster casserole", 18.95, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thQC8LHS8B.jpg", true);
    Food parmesan = new Food("chicken parmesan", 7.99, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thS1391HVZ.jpg", true);
    Food tuna = new Food("tuna potato", 5.52, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thT0K5P8SP.jpg", true);
    Food shrimps = new Food("popcorn shrimps", 8.10, /*"C:/Users/Talal/Desktop/serveIT3/iteration2/TeamF-0.1/src*/"/sample/UI/Icons/foodpics/thT9IF11RU.jpg", true);


    //formatted list of food that go into menu
    private ObservableList<Food> foodObserve = FXCollections.observableArrayList();/*(applePie, banana, sardines,
            smokedSalmon, steak, oreos, water, soup, pizza, cake, orangeJuice, mashedPotatoes, bread, burger,
            casserole, parmesan, tuna, shrimps);*/


    private ObservableList<Food> invoiceObserve = FXCollections.observableArrayList();;

    //formats the food fields into being able to be displayed in table
    public StringProperty stringToStringProperty(String cellEntry) {
        return new SimpleStringProperty(cellEntry);
    }

    public StringProperty doubleToObsValue(Double cellEntry) {
        NumberFormat numberFormat = new DecimalFormat("$##0.00");
        return new SimpleStringProperty(numberFormat.format(cellEntry));
    }

    public SimpleObjectProperty<ImageView> defaultFileLocationToObsValue(String cellEntry) throws FileNotFoundException {
        Image foodImage = new Image(getClass().getResourceAsStream(cellEntry));
        ImageView foodPic = new ImageView();
        foodPic.setImage(foodImage);
        foodPic.setFitHeight(100);
        foodPic.setFitWidth(100);
        return new SimpleObjectProperty<ImageView>(foodPic);
    }

    public SimpleObjectProperty<ImageView> newFileLocationToObsValue(String cellEntry) throws FileNotFoundException {
        FileInputStream apath = new FileInputStream(cellEntry);
        Image foodImage = new Image(apath);
        ImageView foodPic = new ImageView();
        foodPic.setImage(foodImage);
        foodPic.setFitHeight(100);
        foodPic.setFitWidth(100);
        return new SimpleObjectProperty<ImageView>(foodPic);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        timeoutController = new TimeoutController();
//        atimer = new Timer();
//        timeoutController.updateDelay(30); // per steph request.
//        timeoutController.setTimer(atimer, true);

        foodMenu.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        names.setStyle("-fx-alignment: CENTER-LEFT");
        prices.setStyle("-fx-alignment: CENTER-RIGHT;");
        names.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getName()).trim()));   //sets food name in column
        prices.setCellValueFactory(cellData -> doubleToObsValue(cellData.getValue().getPrice()));   //sets food price in column
        pictures.setCellValueFactory(cellData ->
        {
            if (cellData.getValue().getDefaultFood()) {

                try {
                    return defaultFileLocationToObsValue(cellData.getValue().getFileLocation().trim());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            else {

                try {
                    return newFileLocationToObsValue(cellData.getValue().getFileLocation().trim());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });

        refresh();

        yourNames.setStyle("-fx-alignment: CENTER-LEFT");
        yourPrices.setStyle("-fx-alignment: CENTER-RIGHT;");
        yourNames.setCellValueFactory(cellData -> stringToStringProperty((cellData.getValue().getName()).trim()));   //sets food name in column
        yourPrices.setCellValueFactory(cellData -> doubleToObsValue(cellData.getValue().getPrice()));   //sets food price in column


    }

    public void refresh() {
        foodObserve.clear();
        Vector foodFromDatabase = testEmbeddedDB.getAllFoods();
        ArrayList<Food> arrayOfFoodFromDatabase = new ArrayList<Food>(foodFromDatabase);

        for (int i = 0; i < arrayOfFoodFromDatabase.size(); i++) {   //searches through the list from the database
            boolean alreadyInTable = false;
            for (int j = 0; j < foodObserve.size(); j++) {        //searches through the list in the table
                if ((arrayOfFoodFromDatabase.get(i)).getName().trim().equals(foodObserve.get(j).getName())) {  //if the service is already in the table
                    alreadyInTable = true;
                }
            }
            if ( !alreadyInTable)
                foodObserve.add(arrayOfFoodFromDatabase.get(i));
        }

        foodMenu.setItems(foodObserve);
        //foodMenu.setItems(foodObserve);
    }

    @FXML
    public void startEdit() {
        name.setVisible(true);
        priceBox.setVisible(true);
        upload.setVisible(true);
        selectionBox.setVisible(true);
        done.setVisible(true);
    }

    @FXML
    public void addFoodMenu() throws FileNotFoundException {
        Food newFood = new Food("", 0.00, "", false);

        newFood.setName(name.getText());
        newFood.setPrice(Double.parseDouble(price.getText()));
        newFood.setFileLocation(fileLocation);
        //foodObserve.add(newFood);
        //foodMenu.setItems(foodObserve);
        testEmbeddedDB.addFood(name.getText(), Float.parseFloat(price.getText()), fileLocation, false);
        refresh();
    }

    @FXML
    public void deleteFoodMenu() {
        ObservableList<Food> selectedRows, allfood;
        allfood = foodMenu.getItems();

        //this gives us the rows that were selected
        selectedRows = foodMenu.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Food objects from the table
        for (Food i : selectedRows)
            testEmbeddedDB.deleteFood(i.getName());

        refresh();
    }

    @FXML
    public void finishEdit() {
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
    public void addToInvoice(){
        ObservableList<Food> selectedOrders = foodMenu.getSelectionModel().getSelectedItems();

        for(int i = 0; i < selectedOrders.size(); i++)
            invoiceObserve.add(selectedOrders.get(i));

        yourMenu.setItems(invoiceObserve);

        double totalPrice = 0;

        for(int i = 0; i < invoiceObserve.size(); i++)
            totalPrice += invoiceObserve.get(i).getPrice();

        NumberFormat numberFormat = new DecimalFormat("$##0.00");
        totalCost.setText(numberFormat.format(totalPrice));
    }

    @FXML
    private void removeFromInvoice() {
        ObservableList<Food> selectedRows, allfood;
        allfood = yourMenu.getItems();

        //this gives us the rows that were selected
        selectedRows = yourMenu.getSelectionModel().getSelectedItems();

        //loop over the selected rows and remove the Food objects from the table
        for (Food i : selectedRows)
            allfood.remove(i);

        double totalPrice = 0;

        for(int i = 0; i < invoiceObserve.size(); i++)
            totalPrice += invoiceObserve.get(i).getPrice();

        NumberFormat numberFormat = new DecimalFormat("$##0.00");
        totalCost.setText(numberFormat.format(totalPrice));
    }

    @FXML
    private JFXButton submit;

    @FXML
    public void foodSubmit() {
        String order = "";

        for(int i = 0; i < invoiceObserve.size(); i++)
            order += invoiceObserve.get(i).getName().trim() + "!";

        setFoodString(order);

        invoiceObserve.clear();
        totalCost.setText("");

        Main.closePopUp(submit);
    }
}
