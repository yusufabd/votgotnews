package uz.androidclub.votgotnews.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by yusuf.abdullaev on 8/24/2016.
 */
public class MyDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "news_app_db";
    private static final int DB_VERSION = 1;

    SQLiteDatabase db;

    private static final String CREATE_TABLE_NEWS = "CREATE TABLE news (" +
            "id INTEGER PRIMARY KEY," +
            "title TEXT," +
            "views INTEGER," +
            "image TEXT," +
            "at_create INTEGER);";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS news");
            onCreate(db);
        }
    }

    public void insertNewslist(List<News> newsList){
        for (News n : newsList) {
            insertNews(n);
        }
    }

    private void insertNews(News news){
        ContentValues values = new ContentValues();
        values.put("id", news.getId());
        values.put("title", news.getTitle());
        values.put("views", news.getViews());
        values.put("image", news.getImage());
        values.put("at_create", news.getAt_create());

        db.insertWithOnConflict("news", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor getNewsList(){
        Cursor c;
        String query = "SELECT * FROM news";
        c = db.rawQuery(query, null);
        return c;
    }
}
