package sample;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.sql.*;
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
            //Connection c = DriverManager.getConnection(url);

            final String url = "jdbc:derby:Skynet";
            Connection con = DriverManager.getConnection(url);

            //testEmbeddedDB.dropTables();

            //testEmbeddedDB.createTable();

            //testEmbeddedDB.fillNodesTable();

            //testEmbeddedDB.createPrimKey();

            //testEmbeddedDB.fillEdgesTable();

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

            /*testEmbeddedDB.dropAssignmentsTable();

            testEmbeddedDB.dropServiceRequestsTable();

            testEmbeddedDB.dropStaffTable();

            testEmbeddedDB.createServiceRequestTable();

            testEmbeddedDB.createStaffTable();

            testEmbeddedDB.createAssignmentsTable();*/

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

    //Talal wants to work on this

    public static void createServiceRequestTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            //remove the servieceEmployee ID from this table!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            s.execute("CREATE TABLE ServiceRequests (" +
                    "destination CHAR(25) NOT NULL ," +
                    "description CHAR(60) NOT NULL ," +
                    "serviceID BIGINT NOT NULL," +
                    "serviceTime CHAR(20) NOT NULL ," +
                    "typeOfRequest CHAR(30) NOT NULL ," +
                    "patientName CHAR(40) DEFAULT NULL ," +
                    "timeToBeServed CHAR(20) DEFAULT NULL ," +
                    "foodOrder CHAR(30) DEFAULT NULL ," +
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
                int floor = Integer.parseInt(Character.toString(r.getString("floor").charAt(0)));
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

    public static Vector<Edge> getAllEdges(){
        //ArrayList<Node> allNodes = new ArrayList<Node>();
        Vector<Edge> allEdges = new Vector<Edge>();
        try{
            Edge e;
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM EDGES");

            while(r.next()) {
                String edgeID = r.getString("edgeid");
                String start = r.getString("startnode");
                String end = r.getString("endnode");

                Node startNode = testEmbeddedDB.getNode(start);
                Node endNode = testEmbeddedDB.getNode(end);

                e = new Edge(edgeID, startNode, endNode);

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
                    "ACCEPTTIME , FINISHTIME) " +
                    "VALUES ('" + t.destination.getNodeID() + "', '" + t.description + "', " + t.serviceEmployeeID +
                    ", '" + t.getServiceTime() + "'," + t.serviceEmployeeID + ",'" + t.typeOfRequest + "'," +
                    t.getArrival() + ", '" + t.getPatientName() + "', '" + t.completionStatus +
                    "', '" + t.acceptTime + "', '" + t.finishTime + "')");

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
        //ArrayList<Node> allNodes = new ArrayList<Node>();
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
            //add the sid and eid to assignment table
            final String url = "jdbc:derby:Skynet";

            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("INSERT INTO ASSIGNMENTS (EMPLOYEEID, SERVICEID) VALUES ((SELECT EMPLOYEEID " +
                    "from STAFF WHERE STAFF.EMPLOYEEID = " + employeeID + "), (SELECT SERVICEID from " +
                    "SERVICEREQUESTS WHERE SERVICEREQUESTS.SERVICEID = " + serviceID + "))");

            s.close();

            //update the start-time in the assignment table

            testEmbeddedDB.editStartTime(serviceID, startTime);

            //update the completion status

            testEmbeddedDB.editCompletionStatus(serviceID, compStat);

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

    public static void editCompletionStatus(long serviceID, String completeionStatus){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("UPDATE SERVICEREQUESTS SET completionstatus = '" + completeionStatus +
                    "' WHERE SERVICEID = " + serviceID);

            s.close();

        } catch (Exception e){
            System.out.println("editCompletionStatus error: " + e.getMessage());
        }
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
                int floor = Integer.parseInt(Character.toString(r.getString("floor").charAt(0)));
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

    public static void createTable(){

        try{
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
                    "shortName CHAR(20), " +
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
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    public static void fillNodesTable(){
        CSVLoader l;
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            l.loadCSV("TeamF-0.1/src/sample/Data/newNodes.csv", "NODES", true);

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
            System.out.println("error: " + e.getMessage());
        }



    }

    public static void fillEdgesTable(){
        CSVLoader l;
        final String url = "jdbc:derby:Skynet";

        try{
            Connection c = DriverManager.getConnection(url);
            l = new CSVLoader(c);
            l.loadCSV("TeamF-0.1/src/sample/Data/newEdges.csv", "EDGES", true);

            c.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
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

            while(r.next()) {
                String name = r.getString("nodeID");
                System.out.println("nodeID: " + name);
            }

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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error : " + e.getMessage());
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
            System.out.println("error : " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
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
            System.out.println("error: " + e.getMessage());
        }
    }
}
