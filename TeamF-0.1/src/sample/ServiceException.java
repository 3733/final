package sample;

public class ServiceException extends Exception{
    public ServiceException(){
        super("One of your inputs is causing an error.");
    }
}
