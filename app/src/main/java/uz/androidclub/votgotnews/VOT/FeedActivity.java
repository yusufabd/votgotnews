package uz.androidclub.votgotnews.VOT;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import uz.androidclub.votgotnews.R;

public class FeedActivity extends AppCompatActivity {

    ArrayList<Music> musics;
    AsyncHttpClient client;
    MusicListAdapter adapter;
    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        musics = new ArrayList<>();
        view = (RecyclerView)findViewById(R.id.recycler_posts);
        client = new AsyncHttpClient();
        LinearLayoutManager manager = new LinearLayoutManager(FeedActivity.this);
        view.setLayoutManager(manager);
        client.get("http://myhost.uz/androidstudio_vkmusic/?controller=post", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject object = new JSONObject(new String(responseBody));
                    if (object.getInt("status") == 0){
                        JSONArray arrayMus = object.getJSONArray("models");
                        for (int i = 0; i < arrayMus.length(); i++) {
                            JSONObject item = arrayMus.getJSONObject(i);
                            Music m = new Music();
                            m.setTitle(item.getString("title"));
                            m.setAuthor(item.getString("artist"));
                            m.setUrl(item.getString("url"));
                            m.setStart_second(item.getInt("start_second"));
                            m.setDuration(item.getInt("duration"));
                            musics.add(m);
                        }

                        adapter = new MusicListAdapter(FeedActivity.this, musics);
                        view.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
