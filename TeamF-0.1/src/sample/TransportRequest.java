package sample;

import java.util.ArrayList;

public class TransportRequest extends ServiceRequest {

    private boolean arrival;
    private String patientName;
    private String typeOfTransport;

    public TransportRequest(Node destination, String description, int serviceID, String serviceTime,  int serviceEmployeeID, String typeOfRequest, String completionStatus, boolean arrival, String patientName, String typeOfTransport) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest, completionStatus);
        this.arrival = arrival;
        this.patientName = patientName;
        this.typeOfTransport = typeOfTransport;
    }


    public boolean getArrival() {
        return this.arrival;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public String getTypeOfTransport() {
        return this.typeOfTransport;
    }





}
