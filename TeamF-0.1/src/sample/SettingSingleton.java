package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;

public class SettingSingleton {

    private volatile static SettingSingleton settingSingleton;
    private volatile static long admindelay = 30;
    private volatile static long editmapdelay = 30;
    private volatile static long staffdelay = 30;
    private volatile static long servicerequestdelay = 30;
    private volatile static long serviceacceptdelay = 30;
    private volatile static long aboutdelay = 10;
    private volatile static long helpelay = 10;
    private volatile static long fooddelay = 10;

    public static void setAdmindelay(long admindelay) {
        SettingSingleton.admindelay = admindelay;
    }

    public static void setEditmapdelay(long editmapdelay) {
        SettingSingleton.editmapdelay = editmapdelay;
    }

    public static void setStaffdelay(long staffdelay) {
        SettingSingleton.staffdelay = staffdelay;
    }

    public static void setServicerequestdelay(long servicerequestdelay) {
        SettingSingleton.servicerequestdelay = servicerequestdelay;
    }

    public static void setServiceacceptdelay(long serviceacceptdelay) {
        SettingSingleton.serviceacceptdelay = serviceacceptdelay;
    }

    public static void setAboutdelay(long aboutdelay) {
        SettingSingleton.aboutdelay = aboutdelay;
    }

    public static void setHelpelay(long helpelay) {
        SettingSingleton.helpelay = helpelay;
    }

    public static void setFooddelay(long fooddelay) {
        SettingSingleton.fooddelay = fooddelay;
    }

    public static long getAdmindelay() {
        return admindelay;
    }

    public static long getEditmapdelay() {
        return editmapdelay;
    }

    public static long getStaffdelay() {
        return staffdelay;
    }

    public static long getServicerequestdelay() {
        return servicerequestdelay;
    }

    public static long getServiceacceptdelay() {
        return serviceacceptdelay;
    }

    public static long getAboutdelay() {
        return aboutdelay;
    }

    public static long getHelpelay() {
        return helpelay;
    }

    public static long getFooddelay() {
        return fooddelay;
    }

    private Property<AuthenticationInfo> authProperty = new SimpleObjectProperty<>();

    private SettingSingleton() { }

    //Bill Pugh solution
    private static class SingletonHelper{
        //Nested class is referenced after getCaptain() is called
        private static final SettingSingleton settingSingleton = new SettingSingleton();
    }

    public static SettingSingleton getSettingSingleton() {
        return SingletonHelper.settingSingleton;
    }

    public Property<AuthenticationInfo> getauthPropertyProperty() {
        return authProperty;
    }

    public void setAuthProperty(AuthenticationInfo authProperty) {
        this.authProperty.setValue(authProperty);
    }




    public static SettingSingleton getInstance() {
        // Double Checked Locking: google this on wikipedia
        if (settingSingleton == null) { // Single check
            synchronized (SettingSingleton.class)
            {
                if(settingSingleton == null) // double check
                {
                    settingSingleton = new SettingSingleton();
                }
            }
        }
        return settingSingleton;
    }

}
