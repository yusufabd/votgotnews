package uz.androidclub.votgotnews;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by yusuf.abdullaev on 8/26/2016.
 */
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
