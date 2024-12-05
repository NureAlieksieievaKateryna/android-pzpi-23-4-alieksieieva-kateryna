package java.nure.practtask5;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorComparisonActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor selectedSensor;
    private SensorEventListener sensorEventListener;
    private TextView sensorDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_comparison);

        sensorDataText = findViewById(R.id.sensorDataText);
        Spinner sensorSpinner = findViewById(R.id.sensorSpinner);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Доступні сенсори
        String[] sensorOptions = {"Accelerometer", "Gyroscope", "Magnetometer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sensorOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sensorSpinner.setAdapter(adapter);

        // Слухач вибору сенсора
        sensorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                switch (position) {
                    case 0:
                        selectedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                        break;
                    case 1:
                        selectedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                        break;
                    case 2:
                        selectedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
                        break;
                }
                updateSensorListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Слухач подій сенсора
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                sensorDataText.setText(String.format("X: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };
    }

    // Оновлення слухача для обраного сенсора
    private void updateSensorListener() {
        sensorManager.unregisterListener(sensorEventListener); // Відключення попереднього слухача
        if (selectedSensor != null) {
            sensorManager.registerListener(sensorEventListener, selectedSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorDataText.setText("Sensor not available");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedSensor != null) {
            sensorManager.registerListener(sensorEventListener, selectedSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}