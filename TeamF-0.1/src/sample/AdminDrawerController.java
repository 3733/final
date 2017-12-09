package sample;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static sample.Data.data;

public class AdminDrawerController implements Initializable{


    //FXML UI Components
    // Contains the map, object path is necessary otherwise the wrong imageview loads -F
    @FXML
    private javafx.scene.image.ImageView map;
    // Contains the desired user destination
    @FXML
    private JFXTextField destination;
    // Contains stairs option
    @FXML
    private JFXRadioButton start, end;

    @FXML
    private String defaultStart;


    //other components
    @FXML
    private Main mainController;

    private Map CurMap;

    private String filePath = "/sample/UI/Icons/";



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialization and Start

    //Purpose: Initialize all the UI components
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters and Setters

    public void setMainController(Main in){
        mainController = in;
    }


    public void setKiosk(Node k){
        data.kiosk = k;
    }

    public void setSearch(String s){
        this.destination.setText(s);
    }


    public JFXTextField getDestination(){return this.destination;}

    public JFXRadioButton getRadioStart(){return this.start;}

    public void setDestination(String s){destination.setText(s);}

    public Node getKiosk(){
        return data.kiosk;
    }

    public void setMap(Map m) throws IOException{
        this.CurMap = m;
        data.currentMap = "1";
    }

    public Map getMap(){
        return this.CurMap;
    }


    // Button to return to the welcome screen
    @FXML
    public void back() throws IOException{
        Main.startScreen();
        clearFields();
        clear();
    }

    @FXML
    public void clearFields(){
        double width = map.getImage().getWidth();
        double height = map.getImage().getHeight();
        destination.setText("");
        reset(map, width, height);
    }


    // reset to the top left:
    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Other Functions

    // Purpose: Method to clear the path on the map when the user presses clear map
    @FXML
    public void clear() throws FileNotFoundException {
        data.pathThird = null;
        data.pathSecond = null;
        data.pathFirst = null;
        data.pathL1 = null;
        data.pathL2 = null;
        data.pathG = null;

        for(int i = 0; i < data.floorList.size() ; i++){
            data.floorList.set(i,false);
        }
    }


    //  EDIT LATER
    @FXML
    public void edit() throws IOException, InterruptedException{
        Main.mapEditScreen();
        clearFields();
        clear();
    }

    @FXML
    public void serviceRequest() {Main.serviceScreen();}
    @FXML
    public void acceptRequest() {Main.acceptScreen();}

    @FXML
    public void editUsers(){Main.editUsersScreen();}


}
