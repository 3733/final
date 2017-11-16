package sample;

public class CleaningRequest extends ServiceRequest {


    private int urgency;

    public CleaningRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, int urgency) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
        this.urgency = urgency;
    }

    public int getUrgency() {
        return this.urgency;
    }

}
