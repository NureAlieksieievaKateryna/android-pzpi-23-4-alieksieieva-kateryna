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

        // Ініціалізація елементів інтерфейсу
        colorPanel = findViewById(R.id.colorPanel);
        seekBarR = findViewById(R.id.seekBarR);
        seekBarG = findViewById(R.id.seekBarG);
        seekBarB = findViewById(R.id.seekBarB);

        // Встановлення слухачів для SeekBar
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
            // Нічого не робимо
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // Нічого не робимо
        }
    };

    private void updateColor() {
        // Отримання значень RGB з SeekBar
        int red = seekBarR.getProgress();
        int green = seekBarG.getProgress();
        int blue = seekBarB.getProgress();

        // Формування кольору і оновлення фону панелі
        int color = Color.rgb(red, green, blue);
        colorPanel.setBackgroundColor(color);
    }
}