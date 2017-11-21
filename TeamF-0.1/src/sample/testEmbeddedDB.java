package sample;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.sql.*;
import java.util.Vector;


public class testEmbeddedDB {

    Connection c;


    public testEmbeddedDB(){

        try{

            //these two lines are a last ditch effort to make the database work
            //delete it completely (from the project) and run these lines
            //final String url = "jdbc:derby:Skynet;create=true";
            //Connection c = DriverManager.getConnection(url);

            final String url = "jdbc:derby:Skynet";
            c = DriverManager.getConnection(url);

            testEmbeddedDB.dropTables();

            testEmbeddedDB.createTable();

            testEmbeddedDB.fillNodesTable();

            //testEmbeddedDB.createPrimKey();

            testEmbeddedDB.fillEdgesTable();

            /*Node test = new Node("dickbutt", 4, 4,
                    4, "test", "test", "test",
                    "test",'t');

            FoodRequest f = new FoodRequest(test, "penis", 6969, "6969",
                    420, "gimme the g00dSucc", "Joseph Stalin",
                    "14411", "the Bourgoisies head");

            AssistanceRequest a = new AssistanceRequest(test, "not a penis", 68686,
                    "4444", 823450, "assistance", 4);

            TransportRequest t = new TransportRequest(test, "test", 22222222,
                    "131", 141414, "assistance", true,
                    "test", "assistance");

            testEmbeddedDB.addFoodRequest(f);

            testEmbeddedDB.addAssistanceRequest(a);

            testEmbeddedDB.addTransportRequest(t);

            testEmbeddedDB.getAllServiceRequests();*/

            testEmbeddedDB.dropServiceRequests();

            testEmbeddedDB.createServiceRequestTable();

            /*testEmbeddedDB.addFoodRequest("dickbutt", "penis", 6969, "6969",
                    420, "gimme the g00dSucc", "Joseph Stalin",
                    14411, "the Bourgoisies head");

            testEmbeddedDB.addAssistanceRequest("assistance", "not a penis", 68686, "4444",
                    823450, "assistance", 4);

            testEmbeddedDB.addTransportRequest("test", "test", 1123, "234234",
                    2, "test", true, "bob");*/

            //testEmbeddedDB.writeToCSV();

            System.out.println("done.");

            c.close();

        } catch (Exception e){
            System.out.println("Error Creating the Database");
            System.out.println(e.getMessage());
        }
    }

    public static void createPrimKey(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("ALTER TABLE NODES ADD PRIMARY KEY (NODEID)");

            c.close();


        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
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
            System.out.println("errorqqqqqqqq: " + e.getMessage());
        }
    }

    public static void dropNodes(){
        System.out.println("literalliiiiiy got here");
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE NODES");
            System.out.println("Nodes dropped.");

        } catch (Exception e){
            System.out.println("errorqqqqiiiiiiiqqqq: " + e.getMessage());
        }
    }

    public static void dropServiceRequests(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE SERVICEREQUESTS");

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }
    }

    public static void createServiceRequestTable(){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();

            s.execute("CREATE TABLE ServiceRequests (" +
                    "destination CHAR(25) NOT NULL ," +
                    "description CHAR(60) NOT NULL ," +
                    "serviceID INTEGER NOT NULL," +
                    "serviceTime CHAR(20) NOT NULL ," +
                    "serviceEmployeeID INTEGER NOT NULL ," +
                    "typeOfRequest CHAR(30) NOT NULL ," +
                    "patientName CHAR(40) DEFAULT NULL ," +
                    "timeToBeServed CHAR(20) DEFAULT NULL ," +
                    "foodOrder CHAR(30) DEFAULT NULL ," +
                    "urgency INTEGER DEFAULT NULL ," +
                    "arrival BOOLEAN DEFAULT FALSE ," +
                    "typeOfTransport CHAR(60) DEFAULT NULL ," +
                    "PRIMARY KEY (serviceID))");

            s.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
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
                int serviceID = r.getInt("serviceid");
                String serviceTime = r.getString("servicetime");
                int serviceEmployeeID = r.getInt("serviceemployeeid");
                String typeofreq = r.getString("typeofrequest");
                String patName = r.getString("patientname");
                String timeToBeServed = r.getString("timetobeserved");
                String order = r.getString("foodorder");
                int urgency = r.getInt("urgency");
                boolean arrival = r.getBoolean("arrival");
                String typeOfTransport = r.getString("typeoftransport");

                n = testEmbeddedDB.getNode(dest);

                if(typeofreq.contains("food")){
                    req = new FoodRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, patName, timeToBeServed, order);

                } else if(typeofreq.contains("assistance")){
                    req = new AssistanceRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, urgency);

                } else if(typeofreq.contains("transport")){
                    req = new TransportRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, arrival, patName, typeOfTransport);

                } else if(typeofreq.contains("cleaning")){
                    req = new CleaningRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, urgency);

                } else if(typeofreq.contains("security")){
                    req = new SecurityRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, urgency);

                } else if(typeofreq.contains("it")){
                    req = new ItRequest(n, desc, serviceID, serviceTime, serviceEmployeeID,
                            typeofreq, urgency);
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
                char team = r.getString("teamassigned").charAt(5);

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
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, PATIENTNAME, TIMETOBESERVED,FOODORDER) " +
                    "VALUES ('" + f.destination.getNodeID() + "', '" + f.description + "', " + f.serviceID +
                    ", '" + f.serviceTime + "'," + f.serviceEmployeeID + ",'" + f.typeOfRequest + "','" +
                    f.getPatientName() + "', '" + f.getServingTime() + "','" + f.getFoodOrder() + "')");

            eee.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void addAssistanceRequest(AssistanceRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void addCleaningRequest(CleaningRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void addSecurityRequest(SecurityRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void addItRequest(ItRequest r){
        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement q = c.createStatement();

            //SUPER SHIFTY CAST HERE WATCH OUT
            q.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, URGENCY) " +
                    "VALUES ('" + r.destination.getNodeID() + "', '" + r.description +
                    "', " + r.serviceID + ", '" + r.serviceTime + "'," +
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ r.getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

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
                    r.serviceEmployeeID + ",'" + r.typeOfRequest + "',"+ ((AssistanceRequest) r).getUrgency() + ")");

            q.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        }

    }

    public static void addTransportRequest(TransportRequest t){

        try{
            final String url = "jdbc:derby:Skynet";
            Connection c = DriverManager.getConnection(url);
            Statement eee = c.createStatement();

            eee.execute("INSERT into SERVICEREQUESTS (DESTINATION, DESCRIPTION, SERVICEID, " +
                    "SERVICETIME, SERVICEEMPLOYEEID, TYPEOFREQUEST, ARRIVAL, PATIENTNAME) " +
                    "VALUES ('" + t.destination.getNodeID() + "', '" + t.description + "', " + t.serviceEmployeeID +
                    ", '" + t.getServiceTime() + "'," + t.serviceEmployeeID + ",'" + t.typeOfRequest + "'," +
                    t.getArrival() + ", '" + t.getPatientName() + "')");

            eee.close();

        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
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
            l.loadCSV("TeamF-0.1/src/sample/Data/MapFNodes.csv", "NODES", true);

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
            l.loadCSV("TeamF-0.1/src/sample/Data/MapFEdges.csv", "EDGES", true);

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
            s.execute("INSERT INTO EDGES (EDGEID, STARTNODE, ENDNODE) VALUES ('"+edgeID+"','" +startNodes+"','" +endNodes+"')");

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
