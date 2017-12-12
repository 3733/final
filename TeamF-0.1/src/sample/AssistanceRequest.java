package sample;

import java.util.ArrayList;

/**This is the AssistanceRequest class
 * <p>
 *     The AssistanceRequest extends the abstract class ServiceRequest
 * </p>
 *
 */

public class AssistanceRequest extends ServiceRequest {
    private int urgency;

    public AssistanceRequest(Node destination, String description, int serviceID, String serviceTime,
                             String acceptTime, String finishTime, int serviceEmployeeID, String typeOfRequest,
                             String completionStatus, int urgency) {
        super(destination, description, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID, typeOfRequest,
                completionStatus);
        this.urgency = urgency;
    }

    public int getUrgency() {
        return this.urgency;
    }

}
