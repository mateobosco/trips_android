package trips.tdp.fi.uba.ar.tripsandroid.model;

/**
 * Created by joako on 14/4/17.
 */

public class LoggedUser {
    private static LoggedUser instance = new LoggedUser();
    private boolean logged;

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
}
