package trips.tdp.fi.uba.ar.tripsandroid.model;

/**
 * Created by joako on 14/4/17.
 */

public class LoggedUser {
    private static LoggedUser instance = new LoggedUser();
    private boolean logged;
    private String name;
    private String facebookId;

    private LoggedUser() {
        logged = false;
    }

    public static LoggedUser instance(){
            return instance;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacebookId(String id) {
        this.facebookId = id;
    }
}
