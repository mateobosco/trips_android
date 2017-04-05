package trips.tdp.fi.uba.ar.tripsandroid.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

/**
 * Created by mbosco on 3/22/17.
 */

public class City implements Comparable<City> {

    private int id;
    private String name;
    @SerializedName("country_name")
    private String countryName;
    private ArrayList<Attraction> attractions;
    private Image image;

    public City(int id, String name, String countryName){
        this.name = name;
        this.countryName = countryName;
        this.id = id;
    }

    public String getImageUrl() {
        return image.getPath();
    }

    public String getFullImageUrl(){
        return BackEndClient.getCityImageUrl(this.getImageUrl());
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getCountryName(){
        return this.countryName;
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
        return name + " - " + countryName;
    }

}
