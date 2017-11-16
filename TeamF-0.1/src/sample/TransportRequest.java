package sample;

public class TransportRequest extends ServiceRequest {

    /*public TransportRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
    }*/

    public TransportRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, boolean arrival, String patientName, String type) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
        this.arrival = arrival;
        this.patientName = patientName;
        this.typeOfTransport = typeOfRequest;
    }

    private boolean arrival;
    private String patientName;
    private String typeOfTransport;

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
