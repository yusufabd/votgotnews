package uz.androidclub.votgotnews.VOT;

import java.io.Serializable;

/**
 * Created by yusuf.abdullaev on 8/26/2016.
 */
public class Music implements Serializable{


    private String title, author, url;
    private int state, duration, start_second;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStart_second() {
        return start_second;
    }

    public void setStart_second(int start_second) {
        this.start_second = start_second;
    }
}
