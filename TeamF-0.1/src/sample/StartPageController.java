package sample;

import com.jfoenix.controls.JFXButton;
//import dev2dev.textclient.TextClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.ObjectUtils;
import sample.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class StartPageController {
    @FXML
    private TextField searchBox;

    @FXML
    private Button helpButton;

    @FXML
    private JFXButton loginButton;

    private Main mainController;

    public void setMainController(Main main){
        this.mainController = main;
    }

    @FXML
    public String getSearch(){
        return searchBox.getText();
    }

    @FXML
    public void search() throws IOException, InterruptedException {
        Main.mapScreen();
    }

    @FXML
    public void search2() throws IOException, InterruptedException {
        //System.out.print(searchBox.getText());
        if(getSearch().length()>0) {
            Main.setDestination(getSearch());
        }
        Main.mapScreen();
        searchBox.clear();
    }

    @FXML
    public void help(){
        try{
            /*ProcessBuilder pb = new ProcessBuilder("iperf", "-s");
            pb.redirectOutput(new File("testresult.txt"));
            Process p = pb.start();*/
            messenger.Main m = new messenger.Main();
            String[] args = new String[6];
            m.run(60, 500, 600,300,
                    "/sample/UI/style.css", "test", "Test");

        } catch (Exception e){
            System.out.println("api issue in search: " + e.getMessage());
        }
    }

    @FXML
    public void login(){
        Main.loginScreen(loginButton);
    }

    @FXML
    public void language(){

    }

}
