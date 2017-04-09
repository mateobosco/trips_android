package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.Date;

/**
 * Created by joako on 9/4/17.
 */
public class Review {

    private int id;
    private String text;
    private Date date;
    private User author;
    private float score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getScore() {
        return (int) score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}