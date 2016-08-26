package uz.androidclub.votgotnews.news;

/**
 * Created by yusuf.abdullaev on 8/24/2016.
 */
public class News {

    private int id, views, at_create;
    private String title, image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getAt_create() {
        return at_create;
    }

    public void setAt_create(int at_create) {
        this.at_create = at_create;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
