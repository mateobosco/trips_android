package trips.tdp.fi.uba.ar.tripsandroid.model.media;

/**
 * Created by mbosco on 4/5/17.
 */

public class Image {
    private String path;
    private int id;

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath(){
        return path;
    }

    public int getId(){
        return id;
    }
}
