package sample;

import java.io.IOException;

public interface ITimed {

    // All UI controllers that timeout need this method. It is called in the respective fxml files.
    void someAction();


}
