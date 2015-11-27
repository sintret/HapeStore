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


    public String getEmail() {
        return pref.getString(AppConfig.TAG_EMAIL, "");
    }
    public String getToken() {
        return pref.getString(AppConfig.TAG_TOKEN, "");
    }
    public String token() {
        return pref.getString(AppConfig.TAG_TOKEN, "");
    }
    public String getFullname() {
        return pref.getString("fullname", "");
    }
    public String getStore() {
        return pref.getString("store", "");
    }
    public String getAddress() {
        return pref.getString("address", "");
    }
    public String getLatitude() {
        return pref.getString("latitude", "");
    }
    public String getLongitude() {
        return pref.getString("longitude", "");
    }
    public String getQrCode() {
        return pref.getString("qr_code", "");
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
        editor.putString(AppConfig.TAG_JSON_PROFILE, jsonString);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String fullname = jsonObject.getString("fullname");
            String email = jsonObject.getString(AppConfig.TAG_USERNAME);
            String token = jsonObject.getString(AppConfig.TAG_TOKEN);
            String store_id = jsonObject.getString(AppConfig.TAG_STORE_ID);
            editor.putBoolean(KEY_IS_LOGGEDIN, true);

            // Storing name in pref
            editor.putString(AppConfig.TAG_EMAIL, email);
            editor.putString(AppConfig.TAG_TOKEN, token);
            editor.putString(AppConfig.TAG_STORE_ID, store_id);
            editor.putString("fullname", fullname);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        // commit changes
        editor.commit();
        Log.d(TAG, "Profile Changes!");


    }

    public void setStores(String jsonString) {
        editor.putString(AppConfig.TAG_JSON_STORE, jsonString);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String store = jsonObject.getString("title");
            String address = jsonObject.getString("address");
            String latitude = jsonObject.getString("latitude");
            String longitude = jsonObject.getString("longitude");
            String qr_code = jsonObject.getString("qr_code");
            // Storing name in pref
            editor.putString("store", store);
            editor.putString("address", address);
            editor.putString("latitude", latitude);
            editor.putString("longitude", longitude);
            editor.putString("qr_code", qr_code);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // commit changes
        editor.commit();
        Log.d(TAG, "Store Changes!");
    }

    public String getStores() {
        return pref.getString(AppConfig.TAG_JSON_STORE, "");
    }

    public void setMessages(String jsonString) {
        editor.putString(AppConfig.TAG_JSON_MESSAGES, jsonString);
        // commit changes
        editor.commit();
        Log.d(TAG, "Store Changes!");
    }

    public String getMessages() {
        return pref.getString(AppConfig.TAG_JSON_MESSAGES, "");
    }



}