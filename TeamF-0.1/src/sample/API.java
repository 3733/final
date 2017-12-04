package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class API {// implements Data{

    private static String destination;
    private static String origin;

    private String filePath = "/sample/UI/Icons/";

    private static Stage stage;
    private static Scene service;
    private static Scene accept;

    private static Scene genError;


    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static ServiceAcceptController serviceAcceptController = new ServiceAcceptController();
    public static GenErrorController genErrorController = new GenErrorController();




    public void start() throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("UI/Service_Request_Menu.fxml"));
        Parent Service = serviceLoader.load();
        serviceRequestController = serviceLoader.getController();
        serviceRequestController.setAPIController(this);
        service = new Scene(Service);

        FXMLLoader acceptLoader = new FXMLLoader(getClass().getResource("UI/Service_Accept_Menu.fxml"));
        Parent Accept = acceptLoader.load();
        serviceAcceptController = acceptLoader.getController();
        serviceAcceptController.setAPIController(this);
        accept = new Scene(Accept);

        FXMLLoader genErrorLoader = new FXMLLoader(getClass().getResource("UI/GenErrorScreen.fxml"));
        Parent Error = genErrorLoader.load();
        genErrorController = genErrorLoader.getController();
        genErrorController.setAPIController(this);
        genError = new Scene(Error);

        stage = primaryStage;
        service.getStylesheets().add(cssStyle);
        accept.getStylesheets().add(cssStyle);

        stage.setTitle("Team F Hospital GPS");
        stage.setScene(service);
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


    public static void main(String[] args) throws IOException, ServiceException {
/*
        String[] args;
        api.run(args);
*/
        //Main m = new Main();

       //m.run(args);

        System.out.println("hurrdedurrr");

        //run(0, 0, 1500, 1000, "sample/UI/style.css", "FDEPT00501", "FDEPT00101");
        //startMap();
        //launch(args);


    }

}
