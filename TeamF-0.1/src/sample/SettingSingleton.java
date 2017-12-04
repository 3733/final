package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SettingSingleton {

    private volatile static SettingSingleton settingSingleton;

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
            synchronized (SingletonTTS.class)
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
