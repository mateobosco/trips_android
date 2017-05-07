package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

/**
 * Created by mbosco on 5/7/17.
 */

public class Route {

    private ArrayList<Attraction> attractions;
    private ArrayList<String> stopsOrder;
    private String name;
    private String description;
    private City city;
    private Image image;

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    public ArrayList<String> getStopsOrder() {
        return stopsOrder;
    }

    public void setStopsOrder(ArrayList<String> stopsOrder) {
        this.stopsOrder = stopsOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
