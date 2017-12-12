package sample;

import java.util.ArrayList;

/** This is the ITRequest Class
 * <p>
 *     The ITRequest class extends the abstract class ServiceRequest
 * </p>
 */

public class ItRequest extends ServiceRequest{

    private int urgency;

    public ItRequest(Node destination, String description, int serviceID, String serviceTime, String acceptTime,
                     String finishTime,int serviceEmployeeID, String typeOfRequest, String completionStatus, int urgency) {
        super(destination, description, serviceID, serviceTime, acceptTime, finishTime,serviceEmployeeID, typeOfRequest,
                completionStatus);
        this.urgency = urgency;
    }

    public int getUrgency(){ return this.urgency; }
}