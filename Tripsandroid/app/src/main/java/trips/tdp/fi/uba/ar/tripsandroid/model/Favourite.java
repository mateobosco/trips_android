package trips.tdp.fi.uba.ar.tripsandroid.model;

/**
 * Created by mbosco on 6/5/17.
 */

public class Favourite {

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    private int stopId;

    public Attraction getAttraction() {
        return stop;
    }

    public void setAttraction(Attraction stop) {
        this.stop = stop;
    }

    private Attraction stop;
}
