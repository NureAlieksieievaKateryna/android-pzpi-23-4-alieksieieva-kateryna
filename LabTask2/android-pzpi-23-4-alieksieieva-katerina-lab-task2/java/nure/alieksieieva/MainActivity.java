package nure.alieksieieva;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout colorPanel;
    private SeekBar seekBarR, seekBarG, seekBarB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        colorPanel = findViewById(R.id.colorPanel);
        seekBarR = findViewById(R.id.seekBarR);
        seekBarG = findViewById(R.id.seekBarG);
        seekBarB = findViewById(R.id.seekBarB);

        seekBarR.setOnSeekBarChangeListener(colorChangeListener);
        seekBarG.setOnSeekBarChangeListener(colorChangeListener);
        seekBarB.setOnSeekBarChangeListener(colorChangeListener);
    }

    private final SeekBar.OnSeekBarChangeListener colorChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateColor();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void updateColor() {

        int red = seekBarR.getProgress();
        int green = seekBarG.getProgress();
        int blue = seekBarB.getProgress();


        int color = Color.rgb(red, green, blue);
        colorPanel.setBackgroundColor(color);
    }
}