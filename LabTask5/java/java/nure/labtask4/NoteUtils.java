package java.nure.labtask4;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nure.labtask4.entity.Note;
import java.util.List;


public class NoteUtils {
    private static final String PREF_NAME = "notes_prefs";
    private static final String NOTES_KEY = "notes";


    public static void saveNotes(Context context, List<Note> notes) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        editor.putString(NOTES_KEY, json);
        editor.apply();
    }


    public static List<Note> loadNotes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = preferences.getString(NOTES_KEY, "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {}.getType();
        return gson.fromJson(json, type);
    }


    public static void clearNotes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }
}