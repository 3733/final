package sample;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;


import java.io.IOException;
import java.util.Timer;

// For listening to events
import java.awt.event.*;
import java.util.Scanner;

public class Main extends Application implements Data{

    @Override
    public void stop() throws Exception {
        super.stop();
        Runtime.getRuntime().exit(0);
    }

    private  static String destination;
    private  static Staff loggedInGuy = new Staff("Placeholder", "McPlaceholderface", 0000, "PlaceMe", "NotMe", "Janitor", "nope@nope.net");
    private String filePath = "/sample/UI/Icons/";
    private Staff guest = new Staff("2", "B", 999999, "9", "2", "User", "A2@yorha.net");

    private static Stage stage;
    private static Stage anotherStage;
    private static Stage popUp;
    private static Scene start;
    private static Scene login;
    private static Scene map;
    private static Scene admin;
    private static Scene service;
    private static Scene accept;
    private static Scene itError;
    private static Scene itRequest;
    private static Scene mapEdit;
    private static Scene nodeEdit;
    private static Scene edgeEdit;
    private static Scene editUsers;
    private static Scene genError;
    private static Scene editUserWin;
    private static Scene helpRequest;
    private static Scene aboutWin;

    // This is a  setter for the scene for the momento-timeout.
    public static void setStartScene()
    {
        stage.setScene(map);
    }
    private static Scene menuDrawer;




    public static StartPageController  startPageController = new StartPageController();
    public static LoginPageController loginPageController = new LoginPageController();
    public static NavigationPageController navigationPageController = new NavigationPageController();
    public static ServiceRequestController serviceRequestController = new ServiceRequestController();
    public static ServiceAcceptController serviceAcceptController = new ServiceAcceptController();
    public static MapEditPageController mapEditPageController = new MapEditPageController();
    public static EditNodesController editNodesController = new EditNodesController();
    public static EditEdgesController editEdgesController = new EditEdgesController();
    public static EditUsersController editUsersController = new EditUsersController();
    public static GenErrorController genErrorController = new GenErrorController();
    public static EditUserWindowController editUserWindowController = new EditUserWindowController();
    public static HelpScreenServiceRequestScreenController helpScreenServiceRequestScreenController = new HelpScreenServiceRequestScreenController();
    public static AboutPageController aboutPageController = new AboutPageController();

    public static MenuDrawerController menuDrawerController = new MenuDrawerController();


    // private static AboutPageController aboutcontroller;

    // The memento pattern needs these objects to be created in Main
    // This is where the states are being set and stored. When a window is initilized, set here.
    public static Originator originator; // = new Originator();
    // This is the memento saved states array getup. This is only for the start page because that is where the application time-outs to.
    public static Originator.MementoWindow savedStates; // = new Originator.MementoWindow(start);

    private static Timer timer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.DataStart();
        //data.kiosk = data.graph.getNodes().get(0);
        data.loggedInGuy = guest;

        for(int i = 0;i<data.graph.getNodes().size();i++){
            if(data.graph.getNodes().get(i).getLongName().trim().equals("Lower Pike Hallway Exit Lobby")){
                data.kiosk = data.graph.getNodes().get(i);
                //System.out.println(data.kiosk.getLongName());
                break;
            }
        }

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

        FXMLLoader helpRequestLoader = new FXMLLoader(getClass().getResource("UI/HelpScreenServiceRequestScreen.fxml"));
        Parent HelpRequest = helpRequestLoader.load();
        helpScreenServiceRequestScreenController = helpRequestLoader.getController();
        helpScreenServiceRequestScreenController.setMainController(this);
        helpRequest = new Scene(HelpRequest);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("UI/NavigationScreen.fxml"));
        Parent Nav = navLoader.load();
        navigationPageController = navLoader.getController();
        navigationPageController.setMainController(this);
        map = new Scene(Nav);
        //startMap();
        navigationPageController.setKiosk(data.kiosk);
        navigationPageController.setStart(navigationPageController.getKiosk().getLongName().trim());

        FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("UI/Service_Request_Menu.fxml"));
        Parent Service = serviceLoader.load();
        serviceRequestController = serviceLoader.getController();
        serviceRequestController.setMainController(this);
        service = new Scene(Service);

        FXMLLoader acceptLoader = new FXMLLoader(getClass().getResource("UI/Service_Accept_Menu.fxml"));
        Parent Accept = acceptLoader.load();
        serviceAcceptController = acceptLoader.getController();
        serviceAcceptController.setMainController(this);
        accept = new Scene(Accept);

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

        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("UI/AboutPage.fxml"));
        Parent about = aboutLoader.load();
        aboutPageController = aboutLoader.getController();
        aboutPageController.setMainController(this);
        aboutWin = new Scene(about);

        FXMLLoader editUserWinLoader = new FXMLLoader(getClass().getResource("UI/EditUserWindow.fxml"));
        Parent userWin = editUserWinLoader.load();
        editUserWindowController = editUserWinLoader.getController();
        editUserWindowController.setMainController(this);
        editUserWin = new Scene(userWin);

        FXMLLoader menuWinLoader = new FXMLLoader(getClass().getResource("UI/mainMenuDrawer.fxml"));
        Parent menuWin = menuWinLoader.load();
        menuDrawerController = menuWinLoader.getController();
        menuDrawerController.setMainController(this);
        menuDrawer = new Scene(menuWin);


        stage = primaryStage;
        popUp = new Stage();
        //start = new Scene(FXMLLoader.load(getClass().getResource("UI/StartPage.fxml")), 600, 344);
        start.getStylesheets().add("sample/UI/style.css");
        //login = new Scene(FXMLLoader.load(getClass().getResource("UI/LogIn.fxml")), 600, 344);
        login.getStylesheets().add("sample/UI/style.css");
        //map = new Scene(FXMLLoader.load(getClass().getResource("UI/NavigationScreen.fxml")), 1386, 810);
        map.getStylesheets().add("sample/UI/style.css");
        //service = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Request_Menu.fxml")), 1386, 810);
        service.getStylesheets().add("sample/UI/style.css");
        //accept = new Scene(FXMLLoader.load(getClass().getResource("UI/Service_Accept_Menu.fxml")), 1386, 810);
        accept.getStylesheets().add("sample/UI/style.css");
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
        aboutWin.getStylesheets().add("sample/UI/style.css");
        helpRequest.getStylesheets().add("sample/UI/style.css");

        menuWin.getStylesheets().add("sample/UI/style.css");


        stage.setTitle("Team F Hospital GPS");
        stage.setScene(map);
        stage.setResizable(true);
        stage.setFullScreen(true);

        //primaryStage.setFullScreen(true);
        stage.centerOnScreen();
        stage.show();
        Data.data.XWindow = stage.getX();
        Data.data.YWindow = stage.getY();
        destination = "";
    }

    public static void loginScreen(JFXButton btn1){
        //SingletonTTS.getInstance().say("Hey Sexy?");
        popUp = new Stage();
        loginPageController.someAction();
        popUp.setScene(login);
        popUp.setTitle("Log In");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    public static void startScreen(){
        stage.setScene(start);
        stage.centerOnScreen();
    }

    public void search(){
        menuDrawerController.setStart();
        menuDrawerController.setEnd();
    }
    public static void mapScreen() throws IOException, InterruptedException {
        System.out.println("Nav screen showed up");
        // closePopup();
        stage.setScene(map);
        stage.centerOnScreen();
        Data.data.XWindow = stage.getX();
        Data.data.YWindow = stage.getY();
        //startMap();
        if(getDestination().length() > 0){
            navigationPageController.setSearch(getDestination());
            navigationPageController.settingFields();
        }
    }

    public static void serviceScreen(){
        serviceRequestController.refreshTable();
        stage.setScene(service);
        stage.centerOnScreen();
    }

    public static void acceptScreen(){
        serviceAcceptController.refreshTable();
        stage.setScene(accept);
        stage.centerOnScreen();
    }

    public static void mapEditScreen(){
        stage.setScene(mapEdit);
        mapEditPageController.someAction();
        // The timeout thing needs to go here
        stage.centerOnScreen();
    }

    public static void nodeEditScreen(JFXButton btn1){
        popUp = new Stage();
        popUp.setScene(nodeEdit);
        popUp.setTitle("Edit Node");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    public static void nodeEditScreenClick(Node selected, JFXButton btn1) {
        popUp = new Stage();
        popUp.setScene(nodeEdit);
        popUp.setTitle("Edit Node");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());

        editNodesController.clear();
        editNodesController.setScreen(selected);

        double windowX = 616;
        double windowY = 440;
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double x = MouseInfo.getPointerInfo().getLocation().x;
        double y = MouseInfo.getPointerInfo().getLocation().y;

        if(( x + windowX) > screenWidth) {
            popUp.setX(screenWidth - windowX);
        } else {
            popUp.setX(x);
        }
        popUp.setY(y);
        popUp.showAndWait();
    }

    public static void nodeAddEditScreenClick(JFXButton btn1, double x, double y) {
        popUp = new Stage();
        popUp.setScene(nodeEdit);
        popUp.setTitle("Edit Node");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());

        editNodesController.clear();
        editNodesController.setScreenAdd(x,y);

        double windowX = 616;
        double windowY = 440;
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double xClick = MouseInfo.getPointerInfo().getLocation().x;
        double yClick = MouseInfo.getPointerInfo().getLocation().y;

        if(( xClick + windowX) > screenWidth) {
            popUp.setX(screenWidth - windowX);
        } else {
            popUp.setX(xClick);
        }
        popUp.setY(yClick);
        popUp.showAndWait();
    }

    public static void edgeEditScreen(JFXButton btn1){
        popUp = new Stage();
        popUp.setScene(edgeEdit);
        popUp.setTitle("Edit Edge");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    public static void edgeStartEditScreen(JFXButton btn1, Node startNode){
        popUp = new Stage();
        popUp.setScene(edgeEdit);
        popUp.setTitle("Edit Edge");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        editEdgesController.setScreenStart(startNode);
        popUp.showAndWait();
    }

    public static void edgeEndEditScreen(JFXButton btn1, Node endNode){
        popUp = new Stage();
        popUp.setScene(edgeEdit);
        popUp.setTitle("Edit Edge");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        editEdgesController.setScreenEnd(endNode);
        popUp.showAndWait();
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
        editUsersController.someAction();
        stage.centerOnScreen();
        editUsersController.disableButtons();
        editUsersController.refreshTable();
    }

    public static void genErrorScreen(){
        stage.setScene(genError);
        stage.centerOnScreen();
    }

    public static void setHelpScreenServiceRequestScreen(){
        stage.setScene(helpRequest);
        stage.centerOnScreen();
    }

    public static void editUserWindow(JFXButton btn1){ // for adding users
        popUp = new Stage();
        editUserWindowController.someAction();
        popUp.setScene(aboutWin);
        popUp.setTitle("About Team F");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    public static void aboutWindow(JFXButton btn1){
        popUp = new Stage();
        aboutPageController.someAction();
        popUp.setScene(aboutWin);
        popUp.setTitle("About Team F");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    // This is called by the thread from the timer.
    public static void closePopup()
    {
        popUp.close();
    }

    public static void editUserWindowEdit(Staff staff, JFXButton btn1){ // for editing users
        popUp = new Stage();
        editUserWindowController.fillFields(staff);
        editUserWindowController.editingUsers();
        editUserWindowController.someAction();
        popUp.setScene(editUserWin);
        popUp.setTitle("Edit User");
        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.initOwner(btn1.getScene().getWindow());
        popUp.showAndWait();
    }

    public static void closePopUp(Button btn2){
        popUp=(Stage)btn2.getScene().getWindow();
        popUp.close();
    }

    public static void sendDirections(){
        menuDrawerController.setDirectionSteps();
    }

    public static void logOutUser(){
        Staff guest = new Staff("2", "B", 999999, "9", "2", "User", "z@yorha.net");
        data.loggedInGuy = guest;
    }

    public static void setLoggedInGuy(Staff user){
        data.loggedInGuy = user;
    }

    public static Staff getLoggedInGuy(){
        return data.loggedInGuy;
    }


    public static void main(String[] args) throws IOException{

        // The memento pattern needs these objects to be created in Main
        // This is where the states are being set and stored. When a window is initilized, set here.
        originator = new Originator();
        // This is the memento saved states array getup. This is only for the start page because that is where the application time-outs to.
        savedStates = new Originator.MementoWindow(map); // start screen is going away

        // For memento - Andrew S
        originator.set(map);
        // This is the state to revert to.
        // savedStates.add(originator.saveToMemento());
        // ates = new ArrayList<Originator.MementoWindow>();
        //    originator.set(start); // For the memento

//       timer = new Timer();
//       timer.schedule(AndrewTimer.testNewTask(), 2 * 1000);

        //testEmbeddedDB db = new testEmbeddedDB();
        /*ObservableList<String> o = testEmbeddedDB.getAllLongNames();

        for(String s : o){
            System.out.println(s + " trim?");
        }*/

        //startMap();

        /*HashMap<String, Node> test = new HashMap<>();

        test = testEmbeddedDB.getNodesByFloor(1);

        for (java.util.Map.Entry<String, Node> entry : test.entrySet())
        {
            System.out.println(entry.getKey() + " trimmed/" + entry.getValue() + " trimmed");
        }*/




//        testEmbeddedDB db = new testEmbeddedDB();
//        testEmbeddedDB.dropNodes();
//        testEmbeddedDB.dropTables();
//        testEmbeddedDB.createTable();

        /*Staff admin = new Staff("Admin", "Admin", 11, "Admin", "Admin", "Admin", "Admin@gmail.com");
        Staff staff = new Staff("Staff", "Staff", 12, "Staff", "Staff", "IT", "Staff@gmail.com");

        testEmbeddedDB.addStaff(admin);
        testEmbeddedDB.addStaff(staff);

        Staff Eirin = new Staff("Eirin", "Yagokoro", 1200, "eYago", "Kaguya", "Nurse", "eyago@yagokorolab.net");
        Staff Gary = new Staff("Gary", "Oak", 6678, "Samuel", "Oak", "Janitor", "gary@droak.com");
        Staff Talal = new Staff("Talal", "Jaber", 0, "Talal", "Jaber", "Admin", "tjaber15@gmail.com");
        Staff Griffin = new Staff("Griffin", "Roth", 1, "Griffin", "Roth", "Admin", "rothgr16@gmail.com");
        Staff Floris = new Staff("Floris", "van Rossum", 2, "Floris", "van Rossum", "Admin", "florisvanrossum@gmail.com");
        Staff Luke = new Staff("Luke", "Ludington", 3, "Luke", "Ludington", "Admin", "Pmwws1@gmail.com");
        Staff Will = new Staff("William", "Godsey", 4, "William", "Godsey", "Admin", "willgodsey@gmail.com");
        Staff Ben = new Staff("Benjamin", "Mattiuzzi", 5, "Benjamin", "Mattiuzzi", "Admin", "ultranerd3.14@gmail.com");
        Staff Willis = new Staff("Yuan", "Wang", 6, "Yuan", "Wang", "Admin", "WillisWang514@gmail.com");
        Staff Parm = new Staff("Parmenion", "Patias", 7, "Parmenion", "Patias", "Admin", "Parmenion.Patias@gmail.com");
        Staff Steph = new Staff("Stephanie", "Raca", 8, "Stephanie", "Raca", "Admin", "stephanie.r.racca@gmail.com");
        Staff Nik = new Staff("Nikolaos", "Kalampalikis", 9, "Nikolaos", "Kalampalikis", "Admin", "nkalampalikis97@gmail.com");
        Staff Andrew = new Staff("Andrew", "Schueler", 10, "Andrew", "Schueler", "Admin", "andrewtheschueler@gmail.com");
        testEmbeddedDB.addStaff(Gary);
        testEmbeddedDB.addStaff(Eirin);
        testEmbeddedDB.addStaff(Talal);
        testEmbeddedDB.addStaff(Griffin);
        testEmbeddedDB.addStaff(Floris);
        testEmbeddedDB.addStaff(Luke);
        testEmbeddedDB.addStaff(Will);
        testEmbeddedDB.addStaff(Ben);
        testEmbeddedDB.addStaff(Willis);
        testEmbeddedDB.addStaff(Parm);
        testEmbeddedDB.addStaff(Steph);
        testEmbeddedDB.addStaff(Nik);
        testEmbeddedDB.addStaff(Andrew);//*/

        launch(args);
        System.out.println(" Finished program. Have all threads closed? ");
        //controller.drawDirections(Vec);
    }

    public static Map startMap() throws IOException{

        Map CurMap = testEmbeddedDB.dbBuildMap();
        return CurMap;
    }

    public void DataStart() throws IOException {
        data.graph = startMap();
        Data.data.firstFloor = new Image(getClass().getResourceAsStream(filePath + "01_thefirstfloor.png"));
        Data.data.secondFloor = new Image(getClass().getResourceAsStream(filePath + "02_thesecondfloor.png"));
        Data.data.thirdFloor = new Image(getClass().getResourceAsStream(filePath + "03_thethirdfloor.png"));
        Data.data.GFloor = new Image(getClass().getResourceAsStream(filePath + "00_thegroundfloor.png"));
        Data.data.L1Floor = new Image(getClass().getResourceAsStream(filePath + "00_thelowerlevel1.png"));
        Data.data.L2Floor = new Image(getClass().getResourceAsStream(filePath + "00_thelowerlevel2.png"));
        Data.data.currentMap = "1";
    }
}
