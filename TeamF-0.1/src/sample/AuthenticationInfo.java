package sample;

import java.net.Authenticator;

/**This is the Authentication Info page
 * <p>
 *     This page contains the information and methodology used to authenticate a user
 * </p>
 *
 */

public class AuthenticationInfo {

    private String user;
    public enum Privilege{
        STAFF, ADMIN, USER;
    }
    private Privilege priv;

    public AuthenticationInfo(String user, Privilege privilege){
        this.user = user;
        this.priv = privilege;
    }

    public String getUser() {
        return user;
    }

    public Privilege getPriv(){
        return priv;
    }

}
