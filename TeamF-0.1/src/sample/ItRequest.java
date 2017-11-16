package sample;

public class ItRequest extends ServiceRequest{

    private int urgency;

    public ItRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, int urgency) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
        this.urgency = urgency;
    }

    public int getUrgency(){ return this.urgency; }
}