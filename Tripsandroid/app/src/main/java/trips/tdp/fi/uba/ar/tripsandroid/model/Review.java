package trips.tdp.fi.uba.ar.tripsandroid.model;

import java.util.Date;

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

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", author=" + author +
                ", score=" + score +
                '}';
    }
}
