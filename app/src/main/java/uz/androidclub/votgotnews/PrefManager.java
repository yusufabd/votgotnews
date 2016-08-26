package uz.androidclub.votgotnews;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yusuf.abdullaev on 8/26/2016.
 */
public class PrefManager {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(context.getString(R.string.app_pref_name), Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveUserId(int userId){
        editor.putInt(context.getString(R.string.pref_user_id_key), userId);
        editor.apply();
    }

    public int getUserId(){
        return preferences.getInt(context.getString(R.string.pref_user_id_key), 0);
    }
}
