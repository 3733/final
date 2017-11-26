package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Vector;

public class Main extends Application {

    private  static String destination;
    private  static Staff loggedInGuy;

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
    private static Scene editUserWin;


    public static StartPageController  startPageController = new StartPageController();
    public static LoginPageController loginPageController = new LoginPageController();
    public static NavigationPageController navigationPageController = new NavigationPageController();
    public static AdminPageController adminPageController = new AdminPageController();
    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static MapEditPageController mapEditPageController = new MapEditPageController();
    public static EditNodesController editNodesController = new EditNodesController();
    public static EditEdgesController editEdgesController = new EditEdgesController();
    public static EditUsersController editUsersController = new EditUsersController();
    public static GenErrorController genErrorController = new GenErrorController();
    public static EditUserWindowController editUserWindowController = new EditUserWindowController();

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

        FXMLLoader editUsersLoader = new FXMLLoader(getClass().getResource("UI/EditUserScreen.fxml"));
        Parent UserEdit = editUsersLoader.load();
        editUsersController = editUsersLoader.getController();
        editUsersController.setMainController(this);
        editUsers = new Scene(UserEdit);

        FXMLLoader genErrorLoader = new FXMLLoader(getClass().getResource("UI/GenErrorScreen.fxml"));
        Parent Error = genErrorLoader.load();
        genErrorController = genErrorLoader.getController();
        genErrorController.setMainController(this);
        genError = new Scene(Error);

        FXMLLoader editUserWinLoader = new FXMLLoader(getClass().getResource("UI/EditUserWindow.fxml"));
        Parent userWin = editUserWinLoader.load();
        editUserWindowController = editUserWinLoader.getController();
        editUserWindowController.setMainController(this);
        editUserWin = new Scene(userWin);

        stage = primaryStage;
        //start = new Scene(FXMLLoader.load(getClass().getResource("UI/StartPage.fxml")), 600, 344);
        start.getStylesheets().add("sample/UI/style.css");
        //login = new Scene(FXMLLoader.load(getClass().getResource("UI/LogIn.fxml")), 600, 344);
        login.getStylesheets().add("sample/UI/style.css");
        //map = new Scene(FXMLLoader.load(getClass().getResource("UI/NavigationScreen.fxml")), 1386, 810);
        map.getStylesheets().add("sample/UI/style.css");
        //admin = new Scene(FXMLLoader.load(getClass().getResource("UI/AdminControls.fxml")), 1386, 810);
        admin.getStylesheets().add("sample/UI/style.css");
        //service = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1386, 810);
        service.getStylesheets().add("sample/UI/style.css");
        //mapEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/MapEditingScreen.fxml")), 1386,810);
        mapEdit.getStylesheets().add("sample/UI/style.css");
        //nodeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditNodesWindow.fxml")), 600,400);
        nodeEdit.getStylesheets().add("sample/UI/style.css");
        //edgeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditEdgesWindow.fxml")), 600,400);
        edgeEdit.getStylesheets().add("sample/UI/style.css");
        //editUsers = new Scene(FXMLLoader.load(getClass().getResource("UI/EditUserScreen.fxml")), 1386,810);
        editUsers.getStylesheets().add("sample/UI/style.css");
        //genError = new Scene(FXMLLoader.load(getClass().getResource("UI/GenErrorScreen.fxml")), 600,178);
        genError.getStylesheets().add("sample/UI/style.css");
        editUserWin.getStylesheets().add("sample/UI/style.css");

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
            startMap();
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

    public static void editUsersScreen(){
        stage.setScene(editUsers);
        stage.centerOnScreen();
    }

    public static void genErrorScreen(){
        stage.setScene(genError);
        stage.centerOnScreen();
    }

    public static void editUserWindow(){
        stage.setScene(editUserWin);
        stage.centerOnScreen();
    }



    public static void setLoggedInGuy(Staff user){
        loggedInGuy = user;
    }

    public static Staff getLoggedInGuy(){
        return loggedInGuy;
    }
    public static void main(String[] args) throws IOException{

        //testEmbeddedDB db = new testEmbeddedDB();
        startMap();




        //controller.drawDirections(Vec);
        launch(args);
    }

    public static void startMap(){

        Vector<Node> dbnodes = testEmbeddedDB.getAllNodes();

        Vector <Edge> EdgesBad = testEmbeddedDB.getAllEdges();

        Vector <Edge> EdgesGood = new Vector<>();


        for(int i =0;i<EdgesBad.size();i++){

            String ID;
            String StID;
            String EndID;
            Node Start = null;
            Node End = null;

            ID = EdgesBad.get(i).getEdgeID();
            StID = EdgesBad.get(i).getStart().getNodeID();
            EndID = EdgesBad.get(i).getEnd().getNodeID();

            for(int j = 0; j< dbnodes.size();j++){

                if(dbnodes.get(j).getNodeID().equals(StID)){
                    Start = dbnodes.get(j);

                }else if(dbnodes.get(j).getNodeID().equals(EndID)){

                    End = dbnodes.get(j);
                }
            }

            Edge e = new Edge(ID,Start,End);


            EdgesGood.add(e);
        }

        Map CurMap = new Map(dbnodes, EdgesGood);

        CurMap.BuildMap();


        for (int i =0; i<CurMap.getNodes().size();i++){

            System.out.println((i+1)+ " : "+CurMap.getNodes().get(i).getLongName());

            for (int j =0; j<CurMap.getNodes().get(i).getNeighbors().size();j++){

                System.out.println( "      =====> "+CurMap.getNodes().get(i).getNeighbors().get(j).getLongName());
            }
        }

        navigationPageController.setMap(CurMap);
        //Default kiosk location is the Center for International Medecine
        navigationPageController.setKiosk(CurMap.getNodes().get(0));
    }
}
