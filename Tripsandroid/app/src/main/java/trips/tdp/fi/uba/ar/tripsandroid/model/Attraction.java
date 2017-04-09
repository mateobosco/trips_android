package trips.tdp.fi.uba.ar.tripsandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

/**
 * Created by mbosco on 3/22/17.
 */

public class Attraction extends Stop{

    private int id;
    private float latitude;
    private float longitude;
    @SerializedName("schedule")
    private String scheduleTime;
    private float cost;
    private int averageTime;
    private Classification classification;
    private City city;
    private String address;
    private String telephone;
    private ArrayList<Image> images;

    public Attraction(int id, String name, String description) {
        super(name, description);
        this.id = id;
    }

    public ArrayList<Image> getImages(){
        return this.images;
    }

    public float getLatitude(){
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getSchedule() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public Image getImage(int i){
        try{
            return images.get(i);
        }catch (Exception e){
            return null;
        }
    }

    public String getFullImageUrl(int i){
        Image image = getImage(i);
        return BackEndClient.getAttractionImageUrl(image.getPath());
    }

    public int getId() {
        return id;
    }
}

