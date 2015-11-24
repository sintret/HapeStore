package eizougraphic.sintret.hpstore;

/**
 * Created by andy on 11/15/2015.
 */
public class AppConfig {
    // variable to get data from async
    public static final String TAG_ERROR = "error";
    public static final String TAG_ERROR_MESSAGE = "error_message";

    public static final String TAG_USER = "user";
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_UNIQUE_ID = "unique_id";
    public static final String TAG_TOKEN = "token";


    public static final String TAG_CREATED_AT = "created_at";
    public static final String TAG_UPDATED_AT = "updated_at";

    public static final String TAG__HELP_DESCRIPTION = "description";
    public static final String TAG__ABOUT_DESCRIPTION = "description";

    public static final String TAG_JSON_PROFILE = "json_profile";
    public static final String TAG_JSON_MESSAGES = "json_messages";
    public static final String TAG_JSON_STORE = "json_store";
    public static final String TAG_STORE_ID = "store_id";

    public static String SERVER_SUCCESS = "Succcess to Register";
    public static final String TAG_GCM_REGID = "gcm_regid";


    public static String URL_CORE = "http://hushpuppies.co.id/mobile";

    // Server user login url
    public static String URL_LOGIN = URL_CORE + "/api/login_store.php";

    // Server user register url
    public static String URL_SCAN = URL_CORE + "/api/scan_store.php";
    public static String URL_PULL_MESSAGES = URL_CORE + "/api/pull_messages_store.php";

    public static boolean contains(String haystack, String needle) {
        haystack = haystack == null ? "" : haystack;
        needle = needle == null ? "" : needle;

        // Works, but is not the best.
        //return haystack.toLowerCase().indexOf( needle.toLowerCase() ) > -1

        return haystack.toLowerCase().contains(needle.toLowerCase());
    }

}
