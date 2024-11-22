package com.alban.gods;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

public class PrefStorage {

    private final String KEY_PREF_EMAIL = "key_pref_email", KEY_PREF_PASSWORD = "key_pref_password";
    private final String KEY_PREF_FAVOURITE = "key_pref_favourite", KEY_PREF_RECENT = "key_pref_recent";
    private final String KEY_PREF_PLAYLIST = "key_pref_PLAYLIST";
    private static PrefStorage sharedInstance;

    private SharedPreferences sharedPreferences = null;

    public static PrefStorage getInstance() {
        return  sharedInstance;
    }

    public static void initialize(Context context) {
        sharedInstance = new PrefStorage(context);
    }

    private PrefStorage(Context context) {
         sharedPreferences = context.getSharedPreferences("PrefStorage", Context.MODE_PRIVATE);
    }

    public void saveEmailAndPassword(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_PREF_EMAIL,email);
        editor.putString(KEY_PREF_PASSWORD,password);
        editor.commit();
    }

    public String getEamil() {
        return sharedPreferences.getString(KEY_PREF_EMAIL,"");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PREF_PASSWORD,"");
    }

    public void addToFavourite(String songId) {
        try {
            String str = sharedPreferences.getString(KEY_PREF_FAVOURITE,"");
            Log.v("PrefStorage","onprepared add ");
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            jsonArray.put(songId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_PREF_FAVOURITE,jsonArray.toString());
            editor.commit();
            Log.v("PrefStorage","onprepared  add "+songId+" added");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addToPlaylist(String songId) {
        try {
            String str = sharedPreferences.getString(KEY_PREF_PLAYLIST,"");
            Log.v("PrefStorage","onprepared add ");
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            jsonArray.put(songId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_PREF_PLAYLIST,jsonArray.toString());
            editor.commit();
            Log.v("PrefStorage","onprepared  add "+songId+" added");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isFavourite(String songId) {
        String str = sharedPreferences.getString(KEY_PREF_FAVOURITE,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            Log.v("PrefStorage","onprepared pref "+str);
            String favId;
            for (int i = 0; i < jsonArray.length(); i++) {
                favId = jsonArray.getString(i);
                if (favId.equals(songId)) {
                    return true;
                }
            }
        } catch (Exception e){

        }
        return false;
    }
    public boolean isPLaylist(String songId) {
        String str = sharedPreferences.getString(KEY_PREF_PLAYLIST,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            Log.v("PrefStorage","onprepared pref "+str);
            String favId;
            for (int i = 0; i < jsonArray.length(); i++) {
                favId = jsonArray.getString(i);
                if (favId.equals(songId)) {
                    return true;
                }
            }
        } catch (Exception e){

        }
        return false;
    }

    public ArrayList<String> allFavourite() {
        ArrayList<String> list = new ArrayList<>();
        String str = sharedPreferences.getString(KEY_PREF_FAVOURITE,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (Exception e){

        }
        return  list;
    }
    public ArrayList<String> allPlaylist() {
        ArrayList<String> list = new ArrayList<>();
        String str = sharedPreferences.getString(KEY_PREF_PLAYLIST,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (Exception e){

        }
        return  list;
    }

    public void removeFromFavourite(String songId) {
        String str = sharedPreferences.getString(KEY_PREF_FAVOURITE,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            String favId;
            int index = -1;
            for (int i = 0; i < jsonArray.length(); i++) {
                favId = jsonArray.getString(i);
                if (favId.equals(songId)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                jsonArray.remove(index);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_PREF_FAVOURITE, jsonArray.toString());
                editor.commit();
            }
        } catch (Exception e){

        }
    }
    public void removeFromPlaylist(String songId) {
        String str = sharedPreferences.getString(KEY_PREF_PLAYLIST,"");
        try {
            JSONArray jsonArray = null;
            if (str.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(str);
            }
            String favId;
            int index = -1;
            for (int i = 0; i < jsonArray.length(); i++) {
                favId = jsonArray.getString(i);
                if (favId.equals(songId)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                jsonArray.remove(index);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_PREF_PLAYLIST, jsonArray.toString());
                editor.commit();
            }
        } catch (Exception e){

        }
    }
    public void addToRecent(String songId) {
        try {
            String recents = sharedPreferences.getString(KEY_PREF_RECENT,"");
            JSONArray jsonArray = null;
            if (recents.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(recents);
            }
            int index = - 1;
            for (int i = 0; i < jsonArray.length(); i++) {
                if (songId.equals(jsonArray.getString(i))) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                jsonArray.remove(index);
            }
            if (jsonArray.length() >= 5) {
                jsonArray.remove(0);
            }
            jsonArray.put(songId);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_PREF_RECENT,jsonArray.toString());
            editor.commit();
        } catch (Exception e) {

        }
    }

    public ArrayList<String> allRecent() {
        ArrayList<String> list = new ArrayList<>();
        try {
            String recents = sharedPreferences.getString(KEY_PREF_RECENT,"");
            JSONArray jsonArray = null;
            if (recents.isEmpty()) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = new JSONArray(recents);
            }
            for (int i = jsonArray.length()-1; i >= 0 ; i--) {
                list.add(jsonArray.getString(i));
            }
        } catch (Exception  e) {

        }
        return  list;
    }
}
