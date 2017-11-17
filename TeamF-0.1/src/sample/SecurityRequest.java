package sample;

import java.util.ArrayList;

public class SecurityRequest extends ServiceRequest {
    private int urgency;

    public SecurityRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, String completionStatus, int urgency) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest, completionStatus);
        this.urgency = urgency;
    }

    public int getUrgency() {
        return this.urgency;
    }

}
