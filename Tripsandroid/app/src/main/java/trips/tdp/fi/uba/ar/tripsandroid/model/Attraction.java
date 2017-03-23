package trips.tdp.fi.uba.ar.tripsandroid.model;

/**
 * Created by mbosco on 3/22/17.
 */

public class Attraction extends Stop{

    private float latitude;
    private float longitude;
    private String scheduleTime;
    private float cost;
    private int averageTime;
    //Classification classification;

    public Attraction(String name, String description) {
        super(name, description);
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






}

