package java.nure.alieksieieva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView textView;
    private TextView textView1;
    private EditText editText;
    private String savedText;

    private int clickCount = 0;
    private TextView clickCountTextView;

    private Handler handler = new Handler();
    private int timerCount = 0;
    private TextView timerTextView;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate called");

        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        editText = findViewById(R.id.editText);
        clickCountTextView = findViewById(R.id.clickCountTextView);
        timerTextView = findViewById(R.id.textView);

        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString("SAVED_TEXT");
            clickCount = savedInstanceState.getInt("CLICK_COUNT", 0);
            editText.setText(savedText);
        }
        updateClickCountDisplay();
        startTimer();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();
        });


        Button buttonGoToRegistration = findViewById(R.id.buttonGoToRegistration);
        buttonGoToRegistration.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> onButtonChangeText(v));

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> onButtonShowToast(v));

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> incrementClickCount());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
        stopTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        stopTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState called");

        outState.putString("SAVED_TEXT", editText.getText().toString());
        outState.putInt("CLICK_COUNT", clickCount);
        outState.putInt("TIMER_COUNT", timerCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState called");

        savedText = savedInstanceState.getString("SAVED_TEXT");
        clickCount = savedInstanceState.getInt("CLICK_COUNT", 0);
        timerCount = savedInstanceState.getInt("TIMER_COUNT", 0);
        editText.setText(savedText);
        updateClickCountDisplay();
        timerTextView.setText("Timer: " + timerCount);
    }

    public void onButtonChangeText(View view) {
        textView1.setText("Text Changed!");
    }

    public void onButtonShowToast(View view) {
        Toast.makeText(this, "Button 2 clicked!", Toast.LENGTH_SHORT).show();
    }

    private void incrementClickCount() {
        clickCount++;
        updateClickCountDisplay();
    }

    private void updateClickCountDisplay() {
        clickCountTextView.setText("Click Count: " + clickCount);
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                timerCount++;
                timerTextView.setText("Timer: " + timerCount);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(timerRunnable, 1000);
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable);
    }
}