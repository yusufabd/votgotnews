package uz.androidclub.votgotnews.VOT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import uz.androidclub.votgotnews.PrefManager;
import uz.androidclub.votgotnews.R;

public class VKActivity extends AppCompatActivity {

    ArrayList<Music> musicArrayList;
    RecyclerView view;
    AsyncHttpClient client;
    PrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk);
        musicArrayList = new ArrayList<>();
        view = (RecyclerView)findViewById(R.id.recycler);
        pref = new PrefManager(this);
        VKSdk.login(this, "account,audio");
        client = new AsyncHttpClient();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(final VKAccessToken res) {

                VKRequest profileRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, res.userId));
                profileRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        String str_res = response.responseString;
                        Log.w("VKLogs", "User info: " + str_res);
                        try {
                            JSONObject object = new JSONObject(str_res);
                            JSONArray respArray = object.getJSONArray("response");
                            JSONObject userObj = respArray.getJSONObject(0);
                            int id = userObj.getInt("id");
                            String first_name = userObj.getString("first_name");
                            String last_name = userObj.getString("last_name");
                            RequestParams params = new RequestParams();
                            params.put("name", first_name + " " + last_name);
                            if (pref.getUserId() == 0){
                                client.post("http://myhost.uz/androidstudio_vkmusic/?controller=user",
                                        params, new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                try {
                                                    JSONObject userRespObj = new JSONObject(new String(responseBody));
                                                    if (userRespObj.getInt("status") == 0){
                                                        int userId = userRespObj.getInt("model_id");
                                                        pref.saveUserId(userId);

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
                            startActivity(new Intent(VKActivity.this, MusicActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }
                });



            }

            @Override
            public void onError(VKError error) {

            }
        }))

        super.onActivityResult(requestCode, resultCode, data);
    }
}
