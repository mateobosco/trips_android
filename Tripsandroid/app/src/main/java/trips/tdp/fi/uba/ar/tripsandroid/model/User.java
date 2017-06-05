package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.ArrayList;

public class User {

    private String username;
    private int id;
    private ArrayList<Favourite> favourites;
    private boolean blocked;

    public User(){}

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(ArrayList<Favourite> favourites) {
        this.favourites = favourites;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean hasFavourite(Attraction attraction){
        if (favourites == null){
            return false;
        }
        for (Favourite fav: favourites){
            if (attraction.getId() == fav.getAttraction().getId()){
                return true;
            }
        }
        return false;
    }
}