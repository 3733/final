package sample;

import java.util.ArrayList;

/** This is the CleaningRequest class
 * <p>
 *     The CleaningRequest class extends the abstract class ServiceRequest
 * </p>
 * @param int urgency
 */

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
