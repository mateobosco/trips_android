package trips.tdp.fi.uba.ar.tripsandroid.model;

/**
 * Created by mbosco on 3/22/17.
 */

public class Stop {

    private String name;
    private String description;

    public Stop(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }
}
