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
    protected String acceptTime;                     //time request is accepted
    protected String finishTime;                     //time request is completed
    protected int serviceEmployeeID;
    protected String typeOfRequest;
    protected String completionStatus;


    public ServiceRequest(Node destination, String description, int serviceID, String serviceTime, String acceptTime,
                          String finishTime, int serviceEmployeeID, String typeOfRequest, String completionStatus) {
        this.destination = destination;
        this.description = description;
        this.serviceID = serviceID;
        this.serviceTime = serviceTime;
        this.acceptTime = acceptTime;
        this.finishTime = finishTime;
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
    public String getAcceptTime() { return acceptTime; }
    public String getFinishTime() { return finishTime; }
    public int getServiceEmployeeIDs() { return this.serviceEmployeeID; }
    public String getType() {
        return this.typeOfRequest;
    }
    public String getStatus() { return this.completionStatus; }

    public void setAcceptTime(String acceptTime) { this.acceptTime = acceptTime; }
    public void setFinishTime(String finishTime) { this.finishTime = finishTime; }
    public void acceptRequest() {this.completionStatus = "accepted"; }


}



