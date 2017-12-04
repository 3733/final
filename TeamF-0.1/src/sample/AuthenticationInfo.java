package sample;

import java.net.Authenticator;

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
