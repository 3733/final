package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.AnnotatedArrayType;

public class aboutPageController
{
    @FXML
    private AnchorPane backgroundAnchorPane;

    @FXML
    private Label developed_Label;

    @FXML
    private Label SubTitle_Label;

    @FXML
    private Label Description3_Label;

    @FXML
    private Label Description4_Label;

    @FXML
    private Label Description5_Label;

    @FXML
    private Label Description1_Label;

    @FXML
    private Label Description2_Label;

    @FXML
    private Label Description6_Label;

    @FXML
    private Label Description7_Label;

    @FXML
    private Label subTitle_Label;

    @FXML
    private Label prof_Label;

    @FXML
    private Label TC_Label;

    @FXML
    private Label subTitlle2_Lable;

    @FXML
    private Label hosp_Label;

    @FXML
    private Label and_Label;

    @FXML
    private Label hospital_Label;

    @FXML
    private Button back;

    public void Label(){
        developed_Label.setText("Developed By:");
        SubTitle_Label.setText("Team F \"The Fuschia Faeries \"");

        Description1_Label.setText("Lead Software Engineer");
        Description2_Label.setText("Assistant Lead Software Engineer");
        Description3_Label.setText("Project Manager");
        Description4_Label.setText("Product Owner");
        Description5_Label.setText("Test Engineer");
        Description6_Label.setText("Documentation Analyst");
        Description7_Label.setText("Software Engineers");

        subTitle_Label.setText("WPI CS 3733 Software Engineering");
        prof_Label.setText("Professor: Wilson Wong");
        TC_Label.setText("Team Coach: Binam Kayastha");

        subTitlle2_Lable.setText("Special Thanks To");
        hosp_Label.setText("Brigham and Women's Faulkner Hospital");
        and_Label.setText("and");
        hospital_Label.setText("Andrew Shinn");

        back.setText("Back");

    }




}