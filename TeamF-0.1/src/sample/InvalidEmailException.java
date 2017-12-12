package sample;

/**This is the invalid email exception
 *
 */

public class InvalidEmailException extends Exception{
    String invalidEmail;
    public InvalidEmailException(String invalidEmail){
        super(invalidEmail + "is not a valid email, please enter in a valid email address");
        //need help to set to true what is Controller object to call?
        NavigationPageController.setInvalidEmail();
    }
}
