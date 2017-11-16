package sample;

import java.util.PriorityQueue;

public class ServiceRequestList extends PriorityQueue{
    private PriorityQueue<ServiceRequest> RequestList;

    public ServiceRequestList(PriorityQueue<ServiceRequest> requestList) {
        this.RequestList = requestList;
    }

    public void addRequest(ServiceRequest serviceRequest) {
        (this.RequestList).add(serviceRequest);
    }

    public void removeRequest(ServiceRequest serviceRequest) {
        (this.RequestList).remove(serviceRequest);
    }

    public ServiceRequest getTopRequest() {
        return (this.RequestList).peek();
    }
}

