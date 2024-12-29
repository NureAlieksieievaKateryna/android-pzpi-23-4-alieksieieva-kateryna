package java.nure.labtask4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nure.labtask4.entity.ImportanceItem;
import java.nure.labtask4.entity.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.nure.labtask4.databinding.FragmentActivityCreateAndEditNoteBinding;

public class ActivityCreateAndEditNoteFragment extends Fragment {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;

    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            Activity activity = getActivity();
            if (activity != null && activity.getWindow() != null) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            }
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    };

    private FragmentActivityCreateAndEditNoteBinding binding;
    private ImageView iconImageView;
    private List<Note> notes;
    private byte[] imageBytes;
    private Notes Notes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActivityCreateAndEditNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Notes = ((NotesApplication) requireActivity().getApplication()).GetNotes();
        iconImageView = binding.iconImageView;
        Button createOrEditButton = binding.createOrEditButton;
        EditText titleEditTextButton = binding.titleEditText;
        EditText descriptionEditTextButton = binding.descriptionEditText;
        Spinner importanceSpinner = binding.importanceSpinner;
        EditText editTextDate = binding.editTextDate;
        EditText editTextTime = binding.editTextTime;
        Button chooseImageButton = binding.chooseImageButton;
        Button cancleButton = binding.cancleButton;

        List<ImportanceItem> importanceItems = new ArrayList<>();
        importanceItems.add(new ImportanceItem(getString(R.string.lowImportance), R.drawable.baseline_low_priority_24));
        importanceItems.add(new ImportanceItem(getString(R.string.mediumImportance), R.drawable.baseline_checklist_24));
        importanceItems.add(new ImportanceItem(getString(R.string.highImportance), R.drawable.baseline_priority_high_24));
        ImportanceAdapter importanceAdapter = new ImportanceAdapter(requireContext(), importanceItems);
        importanceSpinner.setAdapter(importanceAdapter);

        notes = Notes.getNotes();

        cancleButton.setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity != null) {
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (getActivity().getIntent().getBooleanExtra("view", false) || getActivity().getIntent().getBooleanExtra("edit", false)) {
            Note note = notes.get(getActivity().getIntent().getIntExtra("index", 0));
            importanceSpinner.setSelection(note.getImportance());
            titleEditTextButton.setText(note.getTitle());
            descriptionEditTextButton.setText(note.getDescription());
            editTextDate.setText(note.getEventDate());
            editTextTime.setText(note.getEventTime());
            imageBytes = NoteAdapter.StandartIconCheck(note.getImageData(), requireContext());
            iconImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));

            if (getActivity().getIntent().getBooleanExtra("view", false)) {
                disableFields(titleEditTextButton, descriptionEditTextButton, editTextDate, editTextTime, importanceSpinner, chooseImageButton, createOrEditButton);
            } else {
                createOrEditButton.setText(getString(R.string.edit));
            }
        }

        chooseImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });

        createOrEditButton.setOnClickListener(v -> {
            if (titleEditTextButton.getText().toString().isEmpty()) {
                showAlertDialog(getString(R.string.incorrectInput), getString(R.string.noTitle));
                return;
            }

            if (getActivity().getIntent().getBooleanExtra("edit", false)) {
                Note note = notes.get(getActivity().getIntent().getIntExtra("index", 0));
                Notes.EditNote(note.getNumber(),
                        titleEditTextButton.getText().toString(),
                        descriptionEditTextButton.getText().toString(),
                        importanceSpinner.getSelectedItemPosition(),
                        editTextDate.getText().toString(),
                        editTextTime.getText().toString(),
                        note.getCreationDate(),
                        imageBytes);
            } else {
                Notes.AddNote(titleEditTextButton.getText().toString(),
                        descriptionEditTextButton.getText().toString(),
                        importanceSpinner.getSelectedItemPosition(),
                        editTextDate.getText().toString(),
                        editTextTime.getText().toString(),
                        dayOfMonth + "." + (month + 1) + "." + year,
                        imageBytes);
            }
            Activity activity = getActivity();
            if (activity != null) {
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
            }
        });

        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view1, year1, month1, dayOfMonth1) -> editTextDate.setText(dayOfMonth1 + "." + (month1 + 1) + "." + year1), year, month, dayOfMonth);
            datePickerDialog.show();
        });

        editTextTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), (view12, hourOfDay1, minute1) -> editTextTime.setText(hourOfDay1 + ":" + minute1), hourOfDay, minute, true);
            timePickerDialog.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            iconImageView.setImageURI(data.getData());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ок", null)
                .create()
                .show();
    }

    private void disableFields(EditText titleEditText, EditText descriptionEditText, EditText dateEditText, EditText timeEditText, Spinner importanceSpinner, Button chooseImageButton, Button createOrEditButton) {
        titleEditText.setEnabled(false);
        descriptionEditText.setEnabled(false);
        descriptionEditText.setHint("");
        dateEditText.setEnabled(false);
        dateEditText.setHint("");
        timeEditText.setEnabled(false);
        timeEditText.setHint("");
        chooseImageButton.setVisibility(View.INVISIBLE);
        importanceSpinner.setEnabled(false);
        createOrEditButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Nullable
    private ActionBar getSupportActionBar() {
        if (getActivity() instanceof AppCompatActivity) {
            return ((AppCompatActivity) getActivity()).getSupportActionBar();
        }
        return null;
    }
}
