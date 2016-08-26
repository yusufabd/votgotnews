package uz.androidclub.votgotnews.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import uz.androidclub.votgotnews.R;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    String url = "http://darakchi.uz/api/article/list?secret_key=111&lang=ru";
    List<News> newsList;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new MyDatabase(this);
        newsList = new ArrayList<>();

        client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("models");
                    for (int i = 0; i < array.length(); i++) {
                        News news = new News();
                        JSONObject newsObject = array.getJSONObject(i);
                        news.setId(newsObject.getInt("id"));
                        news.setTitle(newsObject.getString("title"));
                        news.setViews(newsObject.getInt("views"));
                        news.setImage(newsObject.getString("image"));
                        news.setAt_create(newsObject.getInt("at_create"));

                        newsList.add(news);
                    }
                    database.insertNewslist(newsList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });}
}
