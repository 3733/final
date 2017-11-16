package sample;

public class FoodRequest extends ServiceRequest {
    public FoodRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
    }
    private String patientName;
    private String timeToBeServed;
    private String foodOrder;

    public FoodRequest(Node destination, String description, int serviceID, String serviceTime, int serviceEmployeeID, String typeOfRequest, String patientName, String timeToBeServed, String foodOrder) {
        super(destination, description, serviceID, serviceTime, serviceEmployeeID, typeOfRequest);
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
