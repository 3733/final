package sample;

//import javax.xml.soap.Node;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;

//Talal and willis
public abstract class ServiceRequest {

    protected Node destination;
    protected String description;
    protected int serviceID;
    protected String serviceTime;                    //time request is sent out
    protected int serviceEmployeeID;
    protected String typeOfRequest;
    protected String completionStatus;


    public ServiceRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, String completionStatus) {
        this.destination = destination;
        this.description = description;
        this.serviceID = serviceID;
        this.serviceTime = serviceTime;
        this.serviceEmployeeID = serviceEmployeeID;
        this.typeOfRequest = typeOfRequest;
        this.completionStatus = completionStatus;
    }


    public Node getDestination() {
        return this.destination;
    }
    public String getDescription() {
        return this.description;
    }
    public int getServiceID() {
        return this.serviceID;
    }
    public String getServiceTime() {
        return this.serviceTime;
    }
    public int getServiceEmployeeIDs() {
        return this.serviceEmployeeID;
    }
    public String getType() {
        return this.typeOfRequest;
    }
    public String getStatus() { return completionStatus; }

    public void acceptRequest() {this.completionStatus = "accepted"; }


}



