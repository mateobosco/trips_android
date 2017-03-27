package trips.tdp.fi.uba.ar.tripsandroid.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by mbosco on 3/22/17.
 */

public class City implements Comparable<City> {

    private int id;
    private String name;
    private Country country;
    private ArrayList<Attraction> attractions;



    public City(int id, String name, Country country){
        this.name = name;
        this.country = country;
        this.id = id;
    }

    public int getId() {
        return id;
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


    @Override
    public int compareTo(@NonNull City another) {
        return this.name.compareTo(another.getName());
    }

    @Override
    public String toString() {
        return name + " - " + country.getName();
    }

}
