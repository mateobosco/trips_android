package trips.tdp.fi.uba.ar.tripsandroid.model;

import com.facebook.AccessToken;

/**
 * Created by joako on 14/4/17.
 */

public class LoggedUser {
    private static LoggedUser instance = new LoggedUser();
    private String name;

    private LoggedUser() {

    }

    public static LoggedUser instance(){
            return instance;
    }

    public boolean isLogged() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void setName(String name) {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String getFacebookId() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken.getUserId();
    }
}
