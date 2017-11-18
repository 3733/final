package sample;

import java.util.ArrayList;

public class CleaningRequest extends ServiceRequest {
    private int urgency;

    public CleaningRequest(Node destination, String description, int serviceID, String serviceTime, String acceptTime,
                           String finishTime,int serviceEmployeeID, String typeOfRequest, String completionStatus,
                           int urgency) {
        super(destination, description, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID, typeOfRequest,
                completionStatus);
        this.urgency = urgency;
    }

    public int getUrgency() {
        return this.urgency;
    }

}
