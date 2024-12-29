package java.nure.labtask4;

import android.app.Application;


public class NotesApplication extends Application {
    private Notes notes;

    @Override
    public void onCreate() {
        super.onCreate();
        notes = new Notes(this);
    }

    public Notes GetNotes() {
        return notes;
    }

}
