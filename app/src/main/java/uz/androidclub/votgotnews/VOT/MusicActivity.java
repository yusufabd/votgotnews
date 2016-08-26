package uz.androidclub.votgotnews.VOT;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uz.androidclub.votgotnews.PrefManager;
import uz.androidclub.votgotnews.R;

public class MusicActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    ArrayList<Music> musicArrayList;
    RecyclerView view;
    PrefManager pref;
    AsyncHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mPlayer = new MediaPlayer();

        musicArrayList = new ArrayList<>();
        view = (RecyclerView)findViewById(R.id.recycler_view);
        pref = new PrefManager(this);
        client = new AsyncHttpClient();

        VKRequest request = VKApi.audio().get();
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                String resp = response.responseString;
                try {
                    JSONObject object = new JSONObject(resp);
                    JSONObject responseObject = object.getJSONObject("response");
                    JSONArray items = responseObject.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject itemObj = items.getJSONObject(i);
                        Music music = new Music();
                        music.setTitle(itemObj.getString("title"));
                        music.setAuthor(itemObj.getString("artist"));
                        music.setUrl(itemObj.getString("url"));
                        music.setStart_second(0);
                        music.setDuration(itemObj.getInt("duration"));
                        musicArrayList.add(music);
                    }

                    LinearLayoutManager manager = new LinearLayoutManager(MusicActivity.this);
                    MusicListAdapter adapter = new MusicListAdapter(MusicActivity.this, musicArrayList);
                    view.setLayoutManager(manager);
                    view.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.w("VKLogs", resp);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.w("VKLogs", "Error: " + error.errorMessage);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }
        });
    }

    public void openFeed(View view) {
        startActivity(new Intent(this, FeedActivity.class));
    }
}
