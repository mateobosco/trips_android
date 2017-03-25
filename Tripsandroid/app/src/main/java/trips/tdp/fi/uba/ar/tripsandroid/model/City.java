package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.ArrayList;

/**
 * Created by mbosco on 3/22/17.
 */

public class City {

    private String name;
    private Country country;
    private ArrayList<Attraction> attractions;

    public City(String name, Country country){
        this.name = name;
        this.country = country;
    }

    public Country getCountry(){
        return this.country;
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
