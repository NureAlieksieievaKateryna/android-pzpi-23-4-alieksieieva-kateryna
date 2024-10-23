package nure.alieksieiva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.weatherapp.freeweather.free.app.mobile.androidapp.lab1_pzpi23_4_alieksieieva.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        textView.setText("Task 1");
        textView.setTextSize(24);
        textView.setGravity(android.view.Gravity.CENTER);

        setContentView(textView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
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
    }   @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("savedText", "Some text");
        Log.d(TAG, "onSaveInstanceState called");
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedText = savedInstanceState.getString("savedText");
        Log.d(TAG, "onRestoreInstanceState called with text: " + savedText);
    }
    
}