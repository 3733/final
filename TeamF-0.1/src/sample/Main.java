package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

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


    public static AdminPageController adminPageController = new AdminPageController();
    public static EditEdgesController editEdgesController = new EditEdgesController();
    public static EditNodesController editNodesController = new EditNodesController();
    public static LoginPageController loginPageController = new LoginPageController();
    public static MapEditPageController mapEditPageController = new MapEditPageController();
    public static NavigationPageController navigationPageController = new NavigationPageController();
    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static StartPageController startPageController = new StartPageController();


    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        start = new Scene(FXMLLoader.load(getClass().getResource("UI/StartPage.fxml")), 1024, 768);
        login = new Scene(FXMLLoader.load(getClass().getResource("UI/LogIn.fxml")), 640, 480);
        FXMLLoader navigationLoader = new FXMLLoader(getClass().getResource("UI/NavigationScreen.fxml"));
        //navigationPageController = new NavigationPageController();
        navigationLoader.setController(navigationPageController);
        map = new Scene(navigationLoader.load(), 1024, 768);
        admin = new Scene(FXMLLoader.load(getClass().getResource("UI/AdminControls.fxml")), 1024, 768);
        service = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1024, 768);
        mapEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/MapEditingScreen.fxml")), 1024,768);
        nodeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditNodesWindow.fxml")), 600,400);
        edgeEdit = new Scene(FXMLLoader.load(getClass().getResource("UI/EditEdgesWindow.fxml")), 600,400);
        stage.setTitle("Team F Hospital GPS");
        stage.setScene(start);
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

    public static void mapScreen(String s){

        navigationPageController.setSearch(s);
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

    public static void main(String[] args) throws IOException{



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

        launch(args);


    }
}
