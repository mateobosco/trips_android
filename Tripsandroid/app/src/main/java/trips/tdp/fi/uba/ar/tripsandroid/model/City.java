package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by mbosco on 3/22/17.
 */

public class City {

    private String name;
    private ArrayList<Attraction> attractions;

    public City(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAttractions(ArrayList<Attraction> attractions){
        this.attractions = attractions;
    }

    public ArrayList<Attraction> getAttractions(){
        return this.attractions;
    }


}
