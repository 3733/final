package sample;

import com.opencsv.CSVWriter;
//import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.plaf.nimbus.State;
import java.io.FileWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;


public class testEmbeddedDB {

    Connection c;
    Vector<Node> goodNodes;


    public testEmbeddedDB(){

        try{

            //these two lines are a last ditch effort to make the database work
            //delete it completely (from the project) and run these lines
            //final String url = "jdbc:derby:Skynet;create=true";
            //Connection con = DriverManager.getConnection(url);

            final String url = "jdbc:derby:Skynet";
            Connection con = DriverManager.getConnection(url);

            //testEmbeddedDB.dropTables();

            testEmbeddedDB.createTable();

            testEmbeddedDB.fillNodesTable();

            //testEmbeddedDB.createPrimKey();

            testEmbeddedDB.fillEdgesTable();
/*
            Node test = new Node("dickbutt", 4, 4,
                    4, "test", "test", "test",
                    "test",'t');

            Staff bob = new Staff("bob", "larkson", 1111, "boblrksn",
                    "test", "assistance", "bob@bob.com");

            Staff tom = new Staff("tom", "larkson", 2222, "tomlrksn",
                    "test", "assistance", "bob@bob.com");

            Staff rob = new Staff("rob", "larkson", 3333, "roblrksn",
                    "test", "assistance", "bob@bob.com");

            FoodRequest f = new FoodRequest(test, "test", 2, "test",
                    "test", "test", 12321, "food",
                    "incomplete", "bob", "now", "pizzza");

            TransportRequest t = new TransportRequest(test, "test", 982734, "3",
                    "now", "test", 33333, "transport",
                    "not done", true, "bob", "test");

            AssistanceRequest a = new AssistanceRequest(test, "tesT", 747474, "66",
                    "now", "later", 88, "service",
                    "done", 12);

            ItRequest i = new ItRequest(test, "test", 777474, "eventually",
                    "never", "testWords", 999, "IT",
                    "almost done", 3);

            CleaningRequest c = new CleaningRequest(test, "testClean", 2343, "plzWork",
                    "in a while", "test", 777555, "cleaning",
                    "nope", 55);

            SecurityRequest s = new SecurityRequest(test, "test", 5555, "test",
                    "tetst", "test", 5,"security",
                    "security", 4);
*/
            /*testEmbeddedDB.addFoodRequest(f);

            testEmbeddedDB.addAssistanceRequest(a);

            testEmbeddedDB.addTransportRequest(t);

            testEmbeddedDB.addCleaningRequest(c);

            testEmbeddedDB.addItRequest(i);

            testEmbeddedDB.addSecurityRequest(s);

            testEmbeddedDB.addStaff(bob);

            testEmbeddedDB.addStaff(tom);

            testEmbeddedDB.addStaff(rob);*/

            //testEmbeddedDB.addAssignment(5555, 1111, "now" , "started");

            //testEmbeddedDB.getAllServiceRequests();

            //NOTE THE ASSIGNMENTS TABLE MUST BE DROPPED BEFORE YOU CAN
            // DROP SERVICEREQUESTS OR STAFF

            testEmbeddedDB.dropAssignmentsTable();

            testEmbeddedDB.dropServiceRequestsTable();

            testEmbeddedDB.dropStaffTable();

            testEmbeddedDB.createServiceRequestTable();

            testEmbeddedDB.createStaffTable();

            testEmbeddedDB.createAssignmentsTable();

           // testEmbeddedDB.createFoodTable();

            /*testEmbeddedDB.addFoodRequest("dickbutt", "penis", 6969, "6969",
                    420, "gimme the g00dSucc", "Joseph Stalin",
                    14411, "the Bourgoisies head");

            testEmbeddedDB.addAssistanceRequest("assistance", "not a penis", 68686, "4444",
                    823450, "assistance", 4);

            testEmbeddedDB.addTransportRequest("test", "test", 1123, "234234",
                    2, "test", true, "bob");*/

            //testEmbeddedDB.writeToCSV();

            con.close();

            System.out.println("done.");



        } catch (Exception e){
            System.out.println("Error Creating the Database");
            System.out.println(e.getMessage());
        }
    }

    //depricated
    public static void createPrimKey(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("ALTER TABLE NODES ADD PRIMARY KEY (NODEID)");

            c.close();


        } catch (Exception e){
            System.out.println("createPrimKey error: " + e.getMessage());
        }
    }

    public static void dropTables(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE Nodes");
            System.out.println("Nodes dropped.");

            s.execute("DROP TABLE Edges");
            System.out.println("Edges dropped.");

        } catch (Exception e){
            System.out.println("dropTables error: " + e.getMessage());
        }
    }

    public static void dropNodes(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE NODES");
            System.out.println("Nodes dropped.");

        } catch (Exception e){
            System.out.println("dropNodes error: " + e.getMessage());
        }
    }

    public static void dropServiceRequestsTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE SERVICEREQUESTS");

        } catch (Exception e){
            System.out.println("dropServiceRequest error: " + e.getMessage());
        }
    }

    public static void dropStaffTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE STAFF");

            s.close();

        } catch (Exception e){
            System.out.println("dropStaff error: " + e.getMessage());
        }
    }

    public static void dropAssignmentsTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE ASSIGNMENTS");

        } catch (Exception e){
            System.out.println("dropAssignments error: " + e.getMessage());
        }
    }

    public static void createFoodTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("CREATE TABLE FOOD (" +
                    "foodName char(75) PRIMARY KEY ," +
                    "price INTEGER," +
                    "photourl CHAR(40))");

        } catch (Exception e){
            System.out.println("createFoodTable Error: " + e.getMessage());
        }

    }

    public static void dropFoodTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE FOOD");


        } catch (Exception e){
            System.out.println("dropFoodTable error: " + e.getMessage());
        }
    }

    public static void addFood(String foodName, int price, String path){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("INSERT INTO FOOD(foodName, price, photourl) VALUES (" +
                    "'" + foodName + "', " + price + ", '" + path + "')");

        } catch (Exception e){
            System.out.println("addFood error: " + e.getMessage());
        }
    }

    public static void editFoodName(String food, String newfood){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE Food WHERE foodName = " +
                    "'" + food + "' set foodName = '" + newfood + "'");

        } catch (Exception e){
            System.out.println("editFoodName error: " + e.getMessage());
        }
    }

    public static void editFoodPrice(String food, int price){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE Food WHERE foodName = " +
                    "'" + food + "' set price = " + price + "");

        } catch (Exception e){
            System.out.println("editFoodName error: " + e.getMessage());
        }
    }

    public static void editFoodURL(String food, String newURL){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE Food WHERE foodName = " +
                    "'" + food + "' set photourl = '" + newURL + "'");

        } catch (Exception e){
            System.out.println("editFoodName error: " + e.getMessage());
        }
    }

    //Talal wants to work on this

    public static void createServiceRequestTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            //remove the servieceEmployee ID from this table!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            s.execute("CREATE TABLE ServiceRequests (" +
                    "destination CHAR(25) NOT NULL ," +
                    "description CHAR(150) NOT NULL ," +
                    "serviceID BIGINT NOT NULL," +
                    "serviceTime CHAR(20) NOT NULL ," +
                    "typeOfRequest CHAR(30) NOT NULL ," +
                    "patientName CHAR(40) DEFAULT NULL ," +
                    "timeToBeServed CHAR(20) DEFAULT NULL ," +
                    "foodOrder CHAR(50) DEFAULT NULL ," +
                    "urgency INTEGER DEFAULT NULL ," +
                    "arrival BOOLEAN DEFAULT FALSE ," +
                    "typeOfTransport CHAR(60) DEFAULT NULL," +
                    "completionStatus CHAR(11) NOT NULL ," +
                    "acceptTime CHAR(20) NOT NULL , " +
                    "finishTime CHAR(20) NOT NULL , " +
                    "serviceemployeeid BIGINT," +
                    "PRIMARY KEY (serviceID))");

            s.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    public static void createStaffTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("CREATE TABLE Staff (" +
                    "firstName Char(25) NOT NULL," +
                    "lastName CHAR(30), " +
                    "employeeID BIGINT NOT NULL PRIMARY KEY," +
                    "employeeType CHAR (30)," +
                    "employeeEmail CHAR(30)," +
                    "password CHAR(25)," +
                    "username CHAR(25) UNIQUE )");

            c.close();

        } catch (Exception e){
            System.out.println("createStaffTable error: " + e.getMessage());
        }
    }

    public static void createAssignmentsTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("CREATE TABLE Assignments (" +
                    "employeeID BIGINT," +
                    "serviceID BIGINT," +
                    "FOREIGN KEY (employeeID) REFERENCES STAFF(EMPLOYEEID)," +
                    "FOREIGN KEY (serviceID) REFERENCES SERVICEREQUESTS(SERVICEID))");

        } catch (Exception e){
            System.out.println("createAssignmentsTable error: " + e.getMessage());
        }
    }

    public static Vector<ServiceRequest> getAllServiceRequests(){
        Vector<ServiceRequest> requests = new Vector<ServiceRequest>();

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM SERVICEREQUESTS");

            while(r.next()){
                ServiceRequest req = null;
                Node n;
                String dest = r.getString("destination");
                String desc = r.getString("description");
                String serviceTime = r.getString("servicetime");
                String acceptTime = r.getString("accepttime");
                String finishTime = r.getString("finishtime");
                String typeofreq = r.getString("typeofrequest");
                String patName = r.getString("patientname");
                String timeToBeServed = r.getString("timetobeserved");
                String order = r.getString("foodorder");
                int urgency = r.getInt("urgency");
                boolean arrival = r.getBoolean("arrival");
                String typeOfTransport = r.getString("typeoftransport");
                String completionStatus = r.getString("completionstatus");
                int serviceID = r.getInt("serviceid");
                int serviceEmployeeID = r.getInt("serviceemployeeid");

                n = testEmbeddedDB.getNode(dest);

                if(typeofreq.contains("food")){
                    req = new FoodRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, patName, timeToBeServed, order);

                } else if(typeofreq.contains("assistance")){
                    req = new AssistanceRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, urgency);

                } else if(typeofreq.contains("transport")){
                    req = new TransportRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, arrival, patName, typeOfTransport);

                } else if(typeofreq.contains("cleaning")){
                    req = new CleaningRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, urgency);

                } else if(typeofreq.contains("security")){
                    req = new SecurityRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, urgency);

                } else if(typeofreq.contains("it")){
                    req = new ItRequest(n, desc, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID,
                            typeofreq, completionStatus, urgency);
                }

                requests.add(req);

            }


        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return requests;
    }

    public static Vector<Node> getAllNodes(){
        //ArrayList<Node> allNodes = new ArrayList<Node>();
        Vector<Node> allNodes = new Vector<Node>();
        try{
            Node n;
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM NODES");

            while(r.next()) {
                String nodeID = r.getString("nodeID");
                int xcord = r.getInt("xcoord");
                int ycoord = r.getInt("ycoord");
                String floor = r.getString("floor");
                String building = r.getString("building");
                String nodetype = r.getString("nodeType");
                String longname = r.getString("longname");
                String shortname = r.getString("shortname");
                char team = r.getString("teamassigned").charAt(0);

                n = new Node(nodeID, xcord, ycoord, floor, building, nodetype, longname, shortname, team);

                n = testEmbeddedDB.getNode(r.getString("nodeID"));

                allNodes.add(n);
                //System.out.println("nodeID: " + name);
            }

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return allNodes;

    }

    public static Vector<Edge> getAllEdges(HashMap<String, Node> n){
        //ArrayList<Node> allNodes = new ArrayList<Node>();
        Vector<Edge> allEdges = new Vector<Edge>();
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM EDGES");

            while(r.next()) {
                String edgeID = r.getString("edgeid");
                String start = r.getString("startnode");
                String end = r.getString("endnode");

                Node startNode;
                Node endNode;

                Edge e = new Edge(edgeID, n.get(start), n.get(end));

                allEdges.add(e);
                //System.out.println("nodeID: " + name);
            }

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return allEdges;

    }

    public static void addFoodRequest(FoodRequest f){

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement eee = c.createStatement();

            eee.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, PATIENTNAME, TIMETOBESERVED, FOODORDER," +
                    "COMPLETIONSTATUS, ACCEPTTIME, FINISHTIME) " +
                    "VALUES ('" + f.destination.getNodeID() + "', '" + f.description + "', " + f.serviceID +
                    ", '" + f.serviceTime + "'," + f.serviceEmployeeID + ",'" + f.typeOfRequest + "','" +
                    f.getPatientName() + "', '" + f.getServingTime() + "','" + f.getFoodOrder() + "','" +
                    f.getStatus() + "', '" + f.getAcceptTime() + "', '" + f.getFinishTime()  + "')");

            eee.close();

        } catch (Exception e){
            System.out.println("addFoodRequest error: " + e.getMessage());
        }

    }

    public static void addAssistanceRequest(AssistanceRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY, COMPLETIONSTATUS, " +
                    "ACCEPTTIME, FINISHTIME) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ",'" +
                    r.getStatus() + "', '"+ r.getAcceptTime() + "', '" + r.getFinishTime() + "')");

            q.close();

        } catch (Exception e){
            System.out.println("addAssistanceRequest error: " + e.getMessage());
        }

    }

    public static void addCleaningRequest(CleaningRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY, COMPLETIONSTATUS, " +
                    "ACCEPTTIME, FINISHTIME) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ",'" +
                    r.completionStatus + "','" + r.acceptTime + "', '" + r.finishTime + "')");

            q.close();

        } catch (Exception e){
            System.out.println("addCleaningRequest error: " + e.getMessage());
        }

    }

    public static void addSecurityRequest(SecurityRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY, COMPLETIONSTATUS," +
                    "ACCEPTTIME, FINISHTIME) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ",'" +
                    r.completionStatus + "', '" + r.acceptTime + "', '" + r.finishTime + "')");

            q.close();

        } catch (Exception e){
            System.out.println("addSecurityRequest error: " + e.getMessage());
        }

    }

    public static void addItRequest(ItRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY, COMPLETIONSTATUS" +
                    ", ACCEPTTIME , FINISHTIME) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ",'" +
                    r.completionStatus + "', '" + r.acceptTime + "', '" + r.finishTime + "')");

            q.close();

        } catch (Exception e){
            System.out.println("addItRequest error: " + e.getMessage());
        }

    }

    //depricated, do not use.

    private static void addRequest(ServiceRequest r){

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+
                    ((AssistanceRequest) r).getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("addRequest error: " + e.getMessage());
        }

    }

    public static void addTransportRequest(TransportRequest t){

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement eee = c.createStatement();

            eee.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, ARRIVAL, PATIENTNAME, COMPLETIONSTATUS, " +
                    "ACCEPTTIME , FINISHTIME, TYPEOFTRANSPORT) " +
                    "VALUES ('" + t.destination.getNodeID() + "', '" + t.description + "', " + t.serviceID +
                    ", '" + t.getServiceTime() + "'," + t.serviceEmployeeID + ",'" + t.typeOfRequest + "'," +
                    t.getArrival() + ", '" + t.getPatientName() + "', '" + t.completionStatus +
                    "', '" + t.acceptTime + "', '" + t.finishTime + "', '" + t.getTypeOfTransport() + "')");

            eee.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    public static void addStaff(Staff p){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("INSERT INTO STAFF (FIRSTNAME, LASTNAME, EMPLOYEEID, EMPLOYEETYPE, " +
                    "EMPLOYEEEMAIL, PASSWORD, USERNAME) VALUES ('" + p.firstName + "', '" + p.lastName + "'," +
                    p.employeeID + ", '" + p.employeeType + "', '" + p.employeeEmail + "','" +
                    p.password + "', '" + p.username + "')");

            s.close();

        } catch (Exception e){
            System.out.println("addStaff error: " + e.getMessage());
        }
    }

    public static void updateStaffFName(long staffID, String fName){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set FIRSTNAME = '" + fName + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void updateStaffLName(long staffID, String lName){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set LASTNAME = '" + lName + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void updateStaffEType(long staffID, String type){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set EMPLOYEETYPE = '" + type + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void updateStaffEmail(long staffID, String eMail){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set EMPLOYEEEMAIL = '" + eMail + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void updatePassword(long staffID, String password){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set PASSWORD = '" + password + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void updateUsername(long staffID, String username){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE STAFF set USERNAME = '" + username + "' where EMPLOYEEID = " + staffID);
            c.close();
        } catch (Exception e){
            System.out.println("error : " + e.getMessage());
        }

    }

    public static void removeStaff(long staffID){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("DELETE FROM STAFF WHERE EMPLOYEEID = "+staffID);

            c.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    public static Staff getStaff(long personalID){
        Staff n = null;

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM STAFF WHERE EMPLOYEEID = "+ personalID);

            while(r.next()){
                String firstName = r.getString("FIRSTNAME");
                String lastName = r.getString("LASTNAME");
                long employeeID = r.getLong("EMPLOYEEID");
                String username = r.getString("USERNAME");
                String password = r.getString("PASSWORD");
                String employeeType = r.getString("EMPLOYEETYPE");
                String employeeEmail = r.getString("EMPLOYEEEMAIL");

                n = new Staff(firstName, lastName, employeeID, username, password, employeeType, employeeEmail);
            }

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return n;
    }

    public static LinkedList<Staff> getAllStaff(){
        LinkedList<Staff> allStaff = new  LinkedList<Staff>();
        try{
            Staff p;
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM STAFF");

            while(r.next()) {
                String firstName = r.getString("FIRSTNAME");
                String lastName = r.getString("LASTNAME");
                long employeeID = r.getLong("EMPLOYEEID");
                String username = r.getString("USERNAME");
                String password = r.getString("PASSWORD");
                String employeeType = r.getString("EMPLOYEETYPE");
                String employeeEmail = r.getString("EMPLOYEEEMAIL");

                p = new Staff(firstName, lastName, employeeID, username, password, employeeType, employeeEmail);

                p = testEmbeddedDB.getStaff(r.getLong("EMPLOYEEID"));

                allStaff.add(p);
                //System.out.println("nodeID: " + name);
            }

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return allStaff;

    }

    public static void addAssignment(long serviceID, long employeeID, String startTime, String compStat){
        try{

            //update the start-time in the assignment table

            testEmbeddedDB.editStartTime(serviceID, startTime);

            //update the completion status

            testEmbeddedDB.editCompletionStatus(serviceID, compStat);


            //add the sid and eid to assignment table
            final String url = "jdbc:derby:Skynet";

            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("INSERT INTO ASSIGNMENTS (EMPLOYEEID, SERVICEID) VALUES ((SELECT EMPLOYEEID " +
                    "from STAFF WHERE STAFF.EMPLOYEEID = " + employeeID + "), (SELECT SERVICEID from " +
                    "SERVICEREQUESTS WHERE SERVICEREQUESTS.SERVICEID = " + serviceID + "))");

            s.close();


        } catch (Exception e){
            System.out.println("addAssignment error: " + e.getMessage());
        }
    }

    public static void editStartTime(long serviceID, String startTime){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("UPDATE SERVICEREQUESTS SET ACCEPTTIME = '" + startTime + "' WHERE SERVICEID = " +
                    serviceID);

            s.close();

        } catch (Exception e){
            System.out.println("editStartTime error: " + e.getMessage());
        }
    }

    public static void editFinishTime(long serviceID, String finishTime){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("UPDATE SERVICEREQUESTS SET FINISHTIME = '" + finishTime + "' WHERE SERVICEID = " +
                    serviceID);

            s.close();

        } catch (Exception e){
            System.out.println("editFinishTime error: " + e.getMessage());
        }
    }

    public static void editCompletionStatus(long serviceID, String completionStatus){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("UPDATE SERVICEREQUESTS SET completionstatus = '" + completionStatus +
                    "' WHERE SERVICEID = " + serviceID);

            s.close();

        } catch (Exception e){
            System.out.println("editCompletionStatus error: " + e.getMessage());
        }
    }

    public static Map dbBuildMap(){

        Vector<Node> nodes = getAllNodes();

        HashMap<String, Node> nodeMap = new HashMap<>();

        for(Node n : nodes){
            nodeMap.put(n.getNodeID(), n);
        }

        Vector edges = getAllEdges(nodeMap);

        Map CurMap = new Map(nodes, edges);

        CurMap.BuildMap();

        return CurMap;
    }

    public static Node getNode(String nodeID){
        Node n = null;

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM NODES WHERE NODEID = '"+ nodeID + "'");

            while(r.next()){
                String ID = r.getString("nodeID");
                int xcord = r.getInt("xcoord");
                int ycoord = r.getInt("ycoord");
                String floor = r.getString("floor");
                String building = r.getString("building");
                String nodetype = r.getString("nodeType");
                String longname = r.getString("longname");
                String shortname = r.getString("shortname");
                char team = r.getString("teamassigned").charAt(0);

                n = new Node(ID, xcord, ycoord, floor, building, nodetype, longname, shortname, team);
            }

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

        return n;
    }

    public static Edge getEdge(String edgeID) {
        Edge n = null;

        try {
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM EDGES WHERE EDGEID = '" + edgeID + "'");

            while (r.next()) {
                String id = r.getString("edgeID");
                Node startNode = getNode(r.getString("startNode"));
                Node endNode = getNode(r.getString("endNode"));

                n = new Edge(id, startNode, endNode);
            }

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }

        return n;
    }

    public static void createTable() {

        try {
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            // Create the table.
            s.execute("CREATE TABLE Nodes (" +
                    "nodeID CHAR(25) NOT NULL, " +
                    "xcoord INTEGER, " +
                    "ycoord INTEGER, " +
                    "floor CHAR(4), " +
                    "building CHAR(15), " +
                    "nodeType CHAR(4), " +
                    "longName CHAR(60), " +
                    "shortName CHAR(60), " +
                    "teamAssigned CHAR(6)," +
                    "PRIMARY KEY (nodeID)" +
                    ")");

            s.execute("CREATE TABLE Edges (" +
                    "edgeID CHAR(25) NOT NULL, " +
                    "startNode CHAR(25), " +
                    "endNode CHAR(25), " +
                    "PRIMARY KEY (edgeID)" +
                    ")");

            c.close();

            System.out.println("done");
        } catch (Exception e) {
            System.out.println("createTable ERROR: " + e.getMessage());
        }

    }

    public static void fillNodesTable(){
        CSVLoader l;

        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            l.loadCSV("TeamF-0.1/src/sample/Data/MapFNodes.csv", "NODES", true);
            loadNodesFile("TeamF-0.1/src/sample/Data/MapAnodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapBnodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapCnodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapDnodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapENodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapGNodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapHnodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapInodes.csv");
            loadNodesFile("TeamF-0.1/src/sample/Data/MapWnodes.csv");
            c.close();

        } catch (BatchUpdateException e){
            int[] updateCount = e.getUpdateCounts();

            int count = 1;

            for (int i : updateCount) {
                if  (i == Statement.EXECUTE_FAILED) {
                    System.out.println("Error on request " + count +": Execute failed");
                } else {
                    System.out.println("Request " + count +": OK");
                }
                count++;
            }

        } catch (Exception e){
            System.out.println("fillNodesTable error: " + e.getMessage());
        }
    }

    public static void fillEdgesTable(){
        CSVLoader l;
        final String url = "jdbc:derby:Skynet";
        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            l.loadCSV("TeamF-0.1/src/sample/Data/MapFEdges.csv", "EDGES", true);
            c.close();
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapAedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapBedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapCedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapDedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapEEdges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapGEdges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapHedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapIedges.csv");
            loadEdgesFile("TeamF-0.1/src/sample/Data/MapWedges.csv");
        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }



    }

    public static void loadEdgesFile(String fileName){
        CSVLoader l;
        final String url = "jdbc:derby:Skynet";
        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            System.out.println("Loading Edges file: " + fileName);
            l.loadCSV(fileName, "EDGES", false);
            c.close();
        } catch (Exception e){
            System.out.println("fillEdgesTable error: " + e.getMessage());
        }
    }

    public static void loadNodesFile(String fileName){
        CSVLoader l;
        final String url = "jdbc:derby:Skynet";
        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            System.out.println("Loading node file: " + fileName);
            l.loadCSV(fileName, "NODES", false);
            c.close();
        } catch (Exception e){
            System.out.println("loadNodesFile error: " + e.getMessage());
        }
    }

    public static void writeToCSV(){

        CSVWriter w = null;
        FileWriter f = null;
        final String url = "jdbc:derby:Skynet";


        try{
            f = new FileWriter("TeamF-0.1/src/sample/Data/databaseOutput.csv");
            w = new CSVWriter(f, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM NODES WHERE NODETYPE = 'DEPT'");

            /*while(r.next()) {
                String name = r.getString("nodeID");
                System.out.println("nodeID: " + name);
            }*/

            w.writeAll(r, true);

            c.close();

            System.out.println("printed!");


        } catch (Exception e){
            System.out.println("writeToCSV error: " + e.getMessage());
        } finally {
            try{
                w.close();

            } catch (Exception e){
                System.out.println("finally error: " + e.getMessage());
            }
        }
    }

    public static Node findNode(String lName){
        Node n;
        LinkedList<Node> allNodes = new LinkedList<Node>();

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT NODEID FROM NODES WHERE LONGNAME = '" +lName +"'");

            while(r.next()) {
                n = testEmbeddedDB.getNode(r.getString("nodeID"));

                allNodes.add(n);
            }
            c.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
        return allNodes.get(0);
    }

    public static void addNodes(String nodeID, int xCoord, int yCoord, String floor, String building,
                         String nodeType, String longName, String shortName, String team){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("INSERT INTO NODES (NODEID, XCOORD, YCOORD, FLOOR, BUILDING, " +
                    "NODETYPE, LONGNAME, SHORTNAME, TEAMASSIGNED)" +
                    " VALUES ('"+nodeID+"',"+ xCoord+","+yCoord+",'"+ floor+"','"+ building+"','" +
                    nodeType+"','"+ longName+"','"+ shortName+"','" + team +"')");

            c.close();


        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    public static void addEdges(String edgeID, String startNodes, String endNodes){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("INSERT INTO EDGES (EDGEID, STARTNODE, ENDNODE) VALUES ('"+edgeID+"','"
                    +startNodes+"','" +endNodes+"')");

            c.close();


        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void updateNodeXCoord(String nodeID, int xcoord){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set XCOORD = " + xcoord + " where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("updateXCord error: " + e.getMessage());
        }

    }

    public static void updateNodeYCoord(String nodeID, int ycoord){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set YCOORD = " + ycoord + " where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("updateYCord error: " + e.getMessage());
        }

    }

    public static void updateNodeFloor(String nodeID, String floor){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set FLOOR = '" + floor + "' where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("updateFloor error: " + e.getMessage());
        }

    }

    public static void updateNodeBuilding(String nodeID, String building){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set BUILDING = '" + building + "' where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println(" updateNodeBuilding error: " + e.getMessage());
        }

    }

    public static void updateNodeType(String nodeID, String nodeType){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set NODETYPE = '" + nodeType + "' where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("updateNodeType error: " + e.getMessage());
        }

    }

    public static void updateNodeLongName(String nodeID, String longName){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set LONGNAME = '" + longName + "' where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("updateNodeLongName error: " + e.getMessage());
        }

    }

    public static void updateNodeShortName(String nodeID,String shortName){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("UPDATE NODES set SHORTNAME = '" + shortName + "' where NODEID = '" + nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("upadateNodeShortname error: " + e.getMessage());
        }

    }

    public static void updateEdgeStart(String edgeID, String start){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE EDGES set STARTNODE = '" + start + "' where EDGEID = '" + edgeID+"'");
            c.close();
        } catch (Exception e){
            System.out.println("updateEdgeStart error : " + e.getMessage());
        }

    }

    public static void updateEdgeEnd(String edgeID, String end){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("UPDATE EDGES set ENDNODE = '" + end + "' where EDGEID = '" + edgeID+"'");
            c.close();
        } catch (Exception e){
            System.out.println("updateEdgeEnd error : " + e.getMessage());
        }

    }

    public static void removeNode(String nodeID){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("DELETE FROM NODES WHERE NODEID = '"+nodeID+"'");

            c.close();


        } catch (Exception e){
            System.out.println("removeNode error: " + e.getMessage());
        }
    }

    public static void removeEdge(String edgeID){
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();
            s.execute("DELETE FROM EDGES WHERE EDGEID = '"+edgeID+"'");

            c.close();

        } catch (Exception e){
            System.out.println("removeEdge error: " + e.getMessage());
        }
    }

    public static ObservableList getAllLongNames(){
        ObservableList names = FXCollections.observableArrayList();

        try{

            String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();


            ResultSet r = s.executeQuery("select LONGNAME from NODES " +
                    "where NODETYPE NOT like 'HALL' and NODETYPE NOT like 'ELEV' and NODETYPE " +
                    "NOT like 'STAI'");

            while(r.next()){
                String lname = r.getString("longname");
                names.add(lname.trim());
            }

        } catch (Exception e){
            System.out.println("getAllLongNames error: " + e.getMessage());
        }

        return names;
    }

    public static ObservableList getLongNamesByFloor(String q){
        ObservableList names = FXCollections.observableArrayList();

        try{

            String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);

            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("select LONGNAME from NODES " +
                    "where NODETYPE NOT like 'HALL' and NODETYPE NOT like 'ELEV' and NODETYPE " +
                    "NOT like 'STAI' and FLOOR = '" + q + "'");

            while(r.next()){
                String lname = r.getString("longname");
                names.add(lname.trim());
            }

        } catch (Exception e){
            System.out.println("getAllLongNames error: " + e.getMessage());
        }

        return names;
    }

    public static Vector<Node> getNodesByFloor(int floor){
        Vector<Node> nodesByFloor = new Vector<Node>();
        Node n;

        String dbFloor = testEmbeddedDB.floorIntToString(floor);

        try{
            if(dbFloor.equals("uh oh")){
                throw new Exception("error with the given floor int");
            }

            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM NODES WHERE FLOOR = '" + dbFloor + "'");

            while(r.next()){
                String nodeID = r.getString("nodeID").trim();
                int xcord = r.getInt("xcoord");
                int ycoord = r.getInt("ycoord");
                String tableFloor = r.getString("floor").trim();
                String building = r.getString("building").trim();
                String nodetype = r.getString("nodeType").trim();
                String longname = r.getString("longname").trim();
                String shortname = r.getString("shortname").trim();
                char team = r.getString("teamassigned").charAt(0);

                n = new Node(nodeID, xcord, ycoord, tableFloor, building, nodetype, longname, shortname, team);

                nodesByFloor.add(n);
            }


        } catch (Exception e){
            System.out.println("getNodesByFloor error: " + e.getMessage());
        }


        return nodesByFloor;
    }

    public static Vector<Edge> getEdgesByFloor(int floor){

        Vector<Node> nodesByFloor = getNodesByFloor(floor);

        HashMap<String, Node> nodeMap = new HashMap<>();



        for(Node n : nodesByFloor){
            nodeMap.put(n.getNodeID(), n);
        }


        System.out.println("num Nodes: " + nodeMap.size());

        Vector<Edge> edgesByFloor = new Vector<>();

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM EDGES");

            while(r.next()){
                String edgeID = r.getString("edgeid").trim();
                String startNode = r.getString("startnode").trim();
                String endNode = r.getString("endnode").trim();

                /*System.out.println("start: " + startNode);
                System.out.println("end: " + endNode);
                System.out.println("edgeid: " + edgeID);*/

                if(nodeMap.containsKey(startNode) && nodeMap.containsKey(endNode)){
                    Edge e = new Edge(edgeID, nodeMap.get(startNode), nodeMap.get(endNode));
                    edgesByFloor.add(e);
                }
            }

        } catch (Exception e){
            System.out.println("getNodesByFloor error: " + e.getMessage());
        }

        System.out.println(" DB Size: "+edgesByFloor.size());
        return edgesByFloor;
    }

    private static String floorIntToString(int i){
        String ret = new String();

        switch (i){
            case 0:
                return "L2";
            case 1:
                return "L1";
            case 2:
                return "G";
            case 3:
                return "1";
            case 4:
                return "2";
            case 5:
                return "3";
            default:
                return "oh no";
        }

    }

    public static void addStaffTestData(){
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
        addStaff(Gary);
        addStaff(Eirin);
        addStaff(Talal);
        addStaff(Griffin);
        addStaff(Floris);
        addStaff(Luke);
        addStaff(Will);
        addStaff(Ben);
        addStaff(Willis);
        addStaff(Parm);
        addStaff(Steph);
        addStaff(Nik);
        addStaff(Andrew);//*/

    }



}
