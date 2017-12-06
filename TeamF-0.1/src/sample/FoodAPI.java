package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class FoodAPI {// implements Data{

    private static String destination;
    private static String origin;

    private String filePath = "/sample/UI/Icons/";

    private static Stage stage;
    private static Scene service;
    private static Scene accept;
    private static Scene food;

    private static Scene genError;


    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static ServiceAcceptController serviceAcceptController = new ServiceAcceptController();
    public static GenErrorController genErrorController = new GenErrorController();
    public static FoodController foodController = new FoodController();



    public void start() throws IOException {
        Stage primaryStage = new Stage();
        service = new Scene (FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1024, 768);
        accept = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Accept_Menu.fxml")), 1024, 768);
        genError = new Scene(FXMLLoader.load(getClass().getResource("UI/GenErrorScreen.fxml")), 1024, 768);
        food = new Scene(FXMLLoader.load(getClass().getResource("UI/Food_Menu.fxml")), 1024, 768);


        stage = primaryStage;
        service.getStylesheets().add(cssStyle);
        accept.getStylesheets().add(cssStyle);
        food.getStylesheets().add(cssStyle);

        stage.setTitle("Team F Food Menu");
        stage.setScene(food);
        stage.setX(x);
        stage.setY(y);
        if(width == 0 && length == 0)
            stage.setFullScreen(true);
        else {
            stage.setHeight(length);
            stage.setWidth(width);
        }
        stage.setResizable(true);
        stage.show();
    }


    public static void serviceScreen(){
        stage.setScene(service);
        stage.centerOnScreen();
    }

    public static void acceptScreen(){
        stage.setScene(accept);
        stage.centerOnScreen();
    }


    public static void setDestination(String place){
        destination = place;
    }

    public static String getDestination(){
        String place = destination;
        return place;
    }

    public static void setOrigin(String place){
        origin = place;
    }

    public static String getOrigin(){
        String place = origin;
        return place;
    }


    public static void genErrorScreen(){
        stage.setScene(genError);
        stage.centerOnScreen();
    }

    public static int x;
    public static int y;
    public static int width = 0;
    public static int length = 0;
    public static String cssStyle;

    public void run(int xcoord, int ycoord, int windowWidth, int windowLength, String
    cssPath, String destNodeID, String originNodeID) throws ServiceException{
        if(xcoord >= 0  &&  ycoord >= 0  &&  windowWidth >= 0  && windowLength >= 0) {

            x = xcoord;
            y = ycoord;
            width = windowWidth;
            length = windowLength;
            cssStyle = cssPath;

            setOrigin(originNodeID);
            setDestination(destNodeID);
        }
        else throw new ServiceException();
        try{
            start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

/*
    public static void main(String[] args) throws IOException, ServiceException {

        String[] args;
        api.run(args);

        //Main m = new Main();

       //m.run(args);

        System.out.println("hurrdedurrr");

        //run(0, 0, 1500, 1000, "sample/UI/style.css", "FDEPT00501", "FDEPT00101");
        //startMap();
        //launch(args);


    }
*/
}
