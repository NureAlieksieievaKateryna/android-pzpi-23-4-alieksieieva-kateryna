package java.nure.labtask4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    NoteAdapter noteAdapter;
    String currentSubstring = "";
    RecyclerView notesRecycleView;
    Spinner importanceFilter;
    Notes Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Notes = ((NotesApplication) getApplication()).GetNotes();
        db = Notes.getDBHelper();
        Button newNoteButton = findViewById(R.id.newNoteButton);
        notesRecycleView = findViewById(R.id.notesRecycleView);
        SearchView searchView = findViewById(R.id.noteSearchView);
        notesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        Notes.updateNotes();
        noteAdapter = new NoteAdapter(Notes.getNotes(), this, getApplication());
        notesRecycleView.setAdapter(noteAdapter);
        importanceFilter = findViewById(R.id.importanceFilter);
        String[] importanceLevels = new String[]{getString(R.string.all), getString(R.string.lowImportance), getString(R.string.mediumImportance), getString(R.string.highImportance)};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, importanceLevels);
        importanceFilter.setAdapter(arrayAdapter);
        importanceFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Filter(currentSubstring, notesRecycleView, importanceFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Filter(newText, notesRecycleView, importanceFilter);
                currentSubstring = newText;
                return true;
            }
        });
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Filter(currentSubstring, notesRecycleView, importanceFilter);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            intent.putExtra("edit", true);
            intent.putExtra("view", false);
            intent.putExtra("index", noteAdapter.currentIndex);
            startActivityForResult(intent, 1);
        } else if (item.getItemId() == R.id.delete) {
            if (noteAdapter.currentIndex == -1 || noteAdapter.currentIndex >= Notes.getNotes().size()) {
                Log.e("Error", "Invalid index for deletion");
                return super.onContextItemSelected(item);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.deleteNote);
            builder.setMessage(R.string.deleteConfirmation);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Notes.DeleteNote(noteAdapter.currentIndex, Notes.getNotes().get(noteAdapter.currentIndex).getNumber());
                    noteAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.create().show();
        }
        return super.onContextItemSelected(item);
    }

    public void Filter(String newText, RecyclerView notesRecycleView, Spinner importanceFilter) {
        noteAdapter = new NoteAdapter(Notes.Filter(newText, newText,
                importanceFilter.getSelectedItemPosition() != 0,
                importanceFilter.getSelectedItemPosition() - 1),
                this,
                getApplication()
        );
        notesRecycleView.setAdapter(noteAdapter);


        noteAdapter.currentIndex = -1;
    }

}