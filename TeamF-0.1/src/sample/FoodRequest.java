package sample;

import java.util.ArrayList;

public class FoodRequest extends ServiceRequest {

    private String patientName;
    private String timeToBeServed;
    private String foodOrder;

    public FoodRequest(Node destination, String description, int serviceID, String serviceTime, String acceptTime,
                       String finishTime, int serviceEmployeeID, String typeOfRequest,String completionStatus,
                       String patientName, String timeToBeServed, String foodOrder) {
        super(destination, description, serviceID, serviceTime, acceptTime, finishTime, serviceEmployeeID, typeOfRequest,
                completionStatus);
        this.patientName = patientName;
        this.timeToBeServed = timeToBeServed;
        this.foodOrder = foodOrder;
    }

    public String getPatientName() {
        return this.patientName;
    }
    public String getServingTime() {
        return this.timeToBeServed;
    }
    public String getFoodOrder() {
        return this.foodOrder;
    }

}
