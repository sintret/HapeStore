package eizougraphic.sintret.hpstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by andy on 11/15/2015.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "HPStore";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String PREF_GCM_REG_ID = "GCMRegId";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin() {
        editor.putBoolean(KEY_IS_LOGGEDIN, false);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setGCM(String gcmRegId) {
        editor.putString(AppConfig.TAG_GCM_REGID, gcmRegId);
        editor.commit();
        Log.d(TAG, "User login session modified!");

    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }


    public String email() {
        return pref.getString(AppConfig.TAG_EMAIL, "DEFAULT");
    }

    public String token() {
        return pref.getString(AppConfig.TAG_TOKEN, "DEFAULT");
    }

    public void createLoginSession(String email, String token, String created_at) {
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        // Storing name in pref
        editor.putString(AppConfig.TAG_EMAIL, email);
        editor.putString(AppConfig.TAG_TOKEN, token);


        // commit changes
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void setProfile(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            String email = jsonObject.getString(AppConfig.TAG_EMAIL);
            String token = jsonObject.getString(AppConfig.TAG_TOKEN);
            String store_id = jsonObject.getString(AppConfig.TAG_STORE_ID);
            editor.putBoolean(KEY_IS_LOGGEDIN, true);

            // Storing name in pref
            editor.putString(AppConfig.TAG_EMAIL, email);
            editor.putString(AppConfig.TAG_TOKEN, token);
            editor.putString(AppConfig.TAG_STORE_ID, store_id);

            // commit changes
            editor.commit();
            Log.d(TAG, "Profile Changes!");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setStore(String jsonString) {
        editor.putString(AppConfig.TAG_JSON_STORE, jsonString);
            // commit changes
            editor.commit();
            Log.d(TAG, "Store Changes!");
    }

    public String getStore() {
        return pref.getString(AppConfig.TAG_JSON_STORE, "");
    }


}