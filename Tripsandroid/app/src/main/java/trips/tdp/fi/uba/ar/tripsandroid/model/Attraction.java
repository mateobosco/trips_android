package trips.tdp.fi.uba.ar.tripsandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Audioguide;
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
    private float cost = -1;
    private int averageTime = -1;
    private Classification classification;
    private City city;
    private String address;
    @SerializedName("audioGuides")
    private ArrayList<Audioguide> audioguides;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public ArrayList<String> getImagesFullPath(){
        ArrayList<String> a = new ArrayList<>();
        for (Image i: images){
            String url = BackEndClient.getAttractionImageUrl(i.getPath());
            a.add(url);
        }
        return a;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public ArrayList<Audioguide> getAudioguides() {
        return audioguides;
    }

    public void setAudioguides(ArrayList<Audioguide> audioguides){
        this.audioguides = audioguides;
    }

    public boolean hasAudioguide(){
        return this.getAudioguides() != null && this.getAudioguides().size()>=1;
    }

    public boolean hasSchedule() {
        return (this.scheduleTime != null && this.scheduleTime != "");
    }

    public boolean hasAverageTime() {
        return (this.averageTime > 0);
    }

    public boolean hasCost() {
        return this.cost > 0;
    }

    public boolean hasPhoneNumber() {
        return (this.telephone != null && this.telephone != "");
    }
}

