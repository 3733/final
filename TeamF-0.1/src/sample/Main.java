package sample;

import sample.controllers.NavigationPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

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
    private static Scene editUsers;
    private static Scene genError;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        start = new Scene(FXMLLoader.load(getClass().getResource("UI/StartPage.fxml")), 600, 344);
        start.getStylesheets().add("sample/UI/style.css");
        login = new Scene(FXMLLoader.load(getClass().getResource("UI/LogIn.fxml")), 600, 344);
        login.getStylesheets().add("sample/UI/style.css");
        map = new Scene(FXMLLoader.load(getClass().getResource("UI/NavigationScreen.fxml")), 1386, 810);
        map.getStylesheets().add("sample/UI/style.css");
        admin = new Scene(FXMLLoader.load(getClass().getResource("UI/AdminControls.fxml")), 1386, 810);
        admin.getStylesheets().add("sample/UI/style.css");
        service = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1386, 810);
        service.getStylesheets().add("sample/UI/style.css");
        mapEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/MapEditingScreen.fxml")), 1386,810);
        mapEdit.getStylesheets().add("sample/UI/style.css");
        nodeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditNodesWindow.fxml")), 600,400);
        nodeEdit.getStylesheets().add("sample/UI/style.css");
        edgeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditEdgesWindow.fxml")), 600,400);
        edgeEdit.getStylesheets().add("sample/UI/style.css");
        editUsers = new Scene(FXMLLoader.load(getClass().getResource("UI/EditUserScreen.fxml")), 1386,810);
        editUsers.getStylesheets().add("sample/UI/style.css");
        genError = new Scene(FXMLLoader.load(getClass().getResource("UI/GenErrorScreen.fxml")), 600,178);
        genError.getStylesheets().add("sample/UI/style.css");
        stage.setTitle("Team F Hospital GPS");
        stage.setScene(start);
        //primaryStage.setFullScreen(true);
        stage.centerOnScreen();
        stage.show();
    }

    public static void loginScreen(){
        stage.setScene(login);
        stage.centerOnScreen();
    }

    public static void startScreen(){
        stage.setScene(start);
        stage.centerOnScreen();
    }

    public static void mapScreen(){
        stage.setScene(map);
        stage.centerOnScreen();
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

    public static void editUsersScreen(){
        stage.setScene(editUsers);
        stage.centerOnScreen();
    }

    public static void genErrorScreen(){
        stage.setScene(genError);
        stage.centerOnScreen();
    }


    public static void main(String[] args) throws IOException{

        NavigationPageController controller = new NavigationPageController();

        // testEmbeddedDB db = new testEmbeddedDB();


        //controller.drawDirections(Vec);
        launch(args);
    }
}
