package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Vector;

public class Main extends Application {

    private  static String destination;

    private static Stage stage;
    private static Scene start;
    private static Scene login;
    private static Scene map;
    private static Scene admin;
    private static Scene service;
    private static Scene itError;
    private static Scene itRequest;
    private static Scene mapEdit;
    private static Scene nodeEdit;
    private static Scene edgeEdit;

    public static StartPageController  startPageController = new StartPageController();
    public static LoginPageController loginPageController = new LoginPageController();
    public static NavigationPageController navigationPageController = new NavigationPageController();
    public static AdminPageController adminPageController = new AdminPageController();
    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static MapEditPageController mapEditPageController = new MapEditPageController();
    public static EditNodesController editNodesController = new EditNodesController();
    public static EditEdgesController editEdgesController = new EditEdgesController();

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader startLoader = new FXMLLoader(getClass().getResource("UI/StartPage.fxml"));
        Parent Start = startLoader.load();
        startPageController = startLoader.getController();
        startPageController.setMainController(this);
        start = new Scene(Start);

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("UI/LogIn.fxml"));
        Parent LogIn = loginLoader.load();
        loginPageController = loginLoader.getController();
        loginPageController.setMainController(this);
        login = new Scene(LogIn);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("UI/NavigationScreen.fxml"));
        Parent Nav = navLoader.load();
        navigationPageController = navLoader.getController();
        navigationPageController.setMainController(this);
        map = new Scene(Nav);

        FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("UI/AdminControls.fxml"));
        Parent Admin = adminLoader.load();
        adminPageController = adminLoader.getController();
        adminPageController.setMainController(this);
        admin = new Scene(Admin);

        FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("UI/Service_Request_Menu.fxml"));
        Parent Service = serviceLoader.load();
        serviceRequestController = serviceLoader.getController();
        serviceRequestController.setMainController(this);
        service = new Scene(Service);

        FXMLLoader mapEditLoader = new FXMLLoader(getClass().getResource("UI/MapEditingScreen.fxml"));
        Parent MapEdit = mapEditLoader.load();
        mapEditPageController = mapEditLoader.getController();
        mapEditPageController.setMainController(this);
        mapEdit = new Scene(MapEdit);

        FXMLLoader nodeEditLoader = new FXMLLoader(getClass().getResource("UI/EditNodesWindow.fxml"));
        Parent NodeEdit = nodeEditLoader.load();
        editNodesController = nodeEditLoader.getController();
        editNodesController.setMainController(this);
        nodeEdit = new Scene(NodeEdit);

        FXMLLoader edgeEditLoader = new FXMLLoader(getClass().getResource("UI/EditEdgesWindow.fxml"));
        Parent EdgeEdit = edgeEditLoader.load();
        editEdgesController = edgeEditLoader.getController();
        editEdgesController.setMainController(this);
        edgeEdit = new Scene(EdgeEdit);

        stage = primaryStage;

        //start = new Scene(FXMLLoader.load(getClass().getResource("UI/StartPage.fxml")), 1024, 768);
        //login = new Scene(FXMLLoader.load(getClass().getResource("UI/LogIn.fxml")), 640, 480);
        //map = new Scene(Nav, 1024, 768);
        //admin = new Scene(FXMLLoader.load(getClass().getResource("UI/AdminControls.fxml")), 1024, 768);
        //service = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1024, 768);
        //mapEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/MapEditingScreen.fxml")), 1024,768);
        //nodeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditNodesWindow.fxml")), 600,400);
        //edgeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditEdgesWindow.fxml")), 600,400);
//        itError = new Scene(FXMLLoader.load(getClass().getResource("UI/It_Error.fxml")), 500, 202);
//        itRequest = new Scene(FXMLLoader.load(getClass().getResource("UI/ItRequest.fxml")), 500, 500);


        stage.setTitle("Team F Hospital GPS");
        stage.setScene(start);
        //primaryStage.setFullScreen(true);
        stage.centerOnScreen();
        stage.show();

        destination = "";
    }

    public static void loginScreen(){
        stage.setScene(login);
        stage.centerOnScreen();
    }

    public static void startScreen(){
        stage.setScene(start);
        stage.centerOnScreen();
    }

    public static void mapScreen() throws IOException, InterruptedException {
        stage.setScene(map);
        stage.centerOnScreen();
        if(getDestination().length() > 0){
            navigationPageController.findPath(getDestination());
        }
    }

    public static void adminScreen(){
        stage.setScene(admin);
        stage.centerOnScreen();
    }

    public static void serviceScreen(){
        stage.setScene(service);
        stage.centerOnScreen();
    }

    public static void mapEditScreen(){
        stage.setScene(mapEdit);
        stage.centerOnScreen();
    }

    public static void nodeEditScreen(){
        stage.setScene(nodeEdit);
        stage.centerOnScreen();
    }

    public static void edgeEditScreen(){
        stage.setScene(edgeEdit);
        stage.centerOnScreen();
    }


    public static void setDestination(String place){
        destination = place;
    }

    public static String getDestination(){
        String place = destination;
        return place;
    }

    public static void main(String[] args) throws IOException{

        testEmbeddedDB db = new testEmbeddedDB();


        //controller.drawDirections(Vec);
        launch(args);
    }
}
