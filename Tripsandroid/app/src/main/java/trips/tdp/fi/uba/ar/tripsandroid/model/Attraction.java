package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;

/**
 * Created by mbosco on 3/22/17.
 */

public class Attraction extends Stop{

    private int id;
    private float latitude;
    private float longitude;
    private String scheduleTime;
    private float cost;
    private int averageTime;
    //Classification classification;
    private ArrayList<String> images;

    public Attraction(int id, String name, String description) {
        super(name, description);
        this.id = id;
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

    public void addImage(String path){
        if (images == null){
            images = new ArrayList<String>();
        }
        images.add(path);
    }

    public String getImage(int i){
        try{
            return images.get(i);
        }catch (Exception e){
            return "";
        }
    }

    public String getFullImageUrl(int i){
        String imageUrl = getImage(i);
        return BackEndClient.getAttractionImageUrl(imageUrl);
    }


    public int getId() {
        return id;
    }
}

