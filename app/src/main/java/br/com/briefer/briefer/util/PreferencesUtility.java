package br.com.briefer.briefer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtility {
    private static final String LOGGED_IN_PREF = "logged_in_status";
    private static final String USER_TOKEN_PREF = "user_token_pref";
    private static final String USER_ID_PREF = "user_id_pre";

    private static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn, String token, String userId){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putString(USER_TOKEN_PREF, token);
        editor.putString(USER_ID_PREF, userId);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context){
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static String getUserId(Context context) {
        return getPreferences(context).getString(USER_ID_PREF, "");
    }

    public static String getUserToken(Context context){
        return getPreferences(context).getString(USER_TOKEN_PREF, "");
    }
}