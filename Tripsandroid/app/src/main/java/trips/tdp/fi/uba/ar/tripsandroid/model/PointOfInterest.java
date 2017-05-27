package trips.tdp.fi.uba.ar.tripsandroid.model;

import trips.tdp.fi.uba.ar.tripsandroid.BackEndClient;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Audioguide;
import trips.tdp.fi.uba.ar.tripsandroid.model.media.Image;

/**
 * Created by mbosco on 5/26/17.
 */

public class PointOfInterest {

    private String name;
    private String description;
    private Image image;
    private Audioguide audioguide;

    public PointOfInterest(){}

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Audioguide getAudioguide() {
        return audioguide;
    }

    public void setAudioguide(Audioguide audioguide) {
        this.audioguide = audioguide;
    }

    public boolean hasAudioguide() {
        return this.audioguide != null && !this.audioguide.getPath().equals("");
    }

    public String getFullImageUrl() {
        return BackEndClient.getCityImageUrl(image.getPath());
    }
}
