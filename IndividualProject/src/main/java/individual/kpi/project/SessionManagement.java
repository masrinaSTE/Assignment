package individual.kpi.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by masrina on 5/3/14.
 */
public class SessionManagement {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context _context;
    LoginActivity2 loginActivity2;
    int PRIVATE_MODE = 0; // Shared Preferences mode
    private static final String PREF_NAME = "pref_account"; // Shared Preferences file name
    public static final String KEY_NAME = "name"; // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email"; // Email adress

    public SessionManagement(Context context) {
        this._context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        editor = preferences.edit();
    }

    /**
     * Create login session. Store login status, name, email in shared preferences
     *
     * @param name
     * @param email
     */
    public void createLoginSession(String name, String email) {

        editor.putString(KEY_NAME, name); // Store name in preferences
        editor.putString(KEY_EMAIL, email); // Store email in preferences
        editor.commit();
    }

    /**
     * Get stored session data
     *
     * @return
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null)); // user name
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null)); // user email
        return user;
    }

    /**
     * Check user login status
     */
    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, LoginActivity2.class);
            i.putExtra("finish", true);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Close all activities
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new activity
            _context.startActivity(i);
        }


    }

    /**
     * Clear all data from sp
     */
    public void logoutUser() {
        editor.clear(); // clear all data from shared pref
        editor.commit();

        Intent i = new Intent(_context, LoginActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Close all activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new activity
        _context.startActivity(i);
    }

    public void clearSavedData(){
        _context.getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
    }

    /**
     * Quick check for login. get login state
     *
     * @return
     */
    public boolean isLoggedIn() {
        return preferences.getString(PREF_NAME, null) != null;
    }
}
