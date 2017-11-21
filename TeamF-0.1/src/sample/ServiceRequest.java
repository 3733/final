package sample;

//import javax.xml.soap.Node;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Talal and willis
public abstract class ServiceRequest {

    protected Node destination;
    protected String description;
    protected int serviceID;
    protected String serviceTime;
    protected int serviceEmployeeID;
    public String typeOfRequest;

    public ServiceRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest) {
        this.destination = destination;
        this.description = description;
        this.serviceID = serviceID;
        this.serviceTime = serviceTime;
        this.serviceEmployeeID = serviceEmployeeID;
        this.typeOfRequest = typeOfRequest;
    }

    public StringProperty getRequestType()
    {
        return new SimpleStringProperty(this.typeOfRequest);
    }

    public StringProperty getStatus()
    {
        String swag = "Okay";
        return new SimpleStringProperty(swag);
    }




    public void setServiceEmployeeID(int a) {
        return;
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
    public int getServiceEmployeeID() {
        return this.serviceEmployeeID;
    }
    public String getType() {
        return this.typeOfRequest;
    }





}



