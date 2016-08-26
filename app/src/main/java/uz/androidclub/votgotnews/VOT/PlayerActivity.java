package uz.androidclub.votgotnews.VOT;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import uz.androidclub.votgotnews.PrefManager;
import uz.androidclub.votgotnews.R;

public class PlayerActivity extends AppCompatActivity {

    ImageView button;SeekBar seekBar;TextView title, author;
    Music music;
    MediaPlayer player;
    AsyncHttpClient client;
    PrefManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        button = (ImageView)findViewById(R.id.play_button);
        seekBar = (SeekBar)findViewById(R.id.seekbar);
        title = (TextView)findViewById(R.id.text_title);
        author = (TextView)findViewById(R.id.text_author);
        client= new AsyncHttpClient();

        player = new MediaPlayer();
        music = (Music) getIntent().getSerializableExtra("music_info");
        manager = new PrefManager(this);
        title.setText(music.getTitle());
        author.setText(music.getAuthor());
        seekBar.setMax(music.getDuration());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying()){
                    button.setImageResource(R.drawable.pause);
                    try {

                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        player.setDataSource(music.getUrl());
                        player.prepare();
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                player.seekTo(music.getStart_second());
                                player.start();
                                seekBar.setProgress(player.getCurrentPosition());
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    button.setImageResource(R.drawable.play);
                    player.pause();
                }
            }
        });

    }

    public void createPost(View view) {
        RequestParams params = new RequestParams();
        params.put("user_id", manager.getUserId());
        params.put("title", music.getTitle());
        params.put("artist", music.getAuthor());
        params.put("url", music.getUrl());
        params.put("duration", music.getDuration());
        params.put("start_second", player.getCurrentPosition());
        client.post("http://myhost.uz/androidstudio_vkmusic/?controller=post", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String res = new String(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
