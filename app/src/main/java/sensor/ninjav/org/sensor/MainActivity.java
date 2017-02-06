package sensor.ninjav.org.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorService;
    private Sensor sensor;

    // m/s**2
    private static final int ACCELEROMETER_X = 0;
    private static final int ACCELEROMETER_Y = 1;
    private static final int ACCELEROMETER_Z = 2;

    // m/s**2
    private static final int GRAVITY_X = 0;
    private static final int GRAVITY_Y = 1;
    private static final int GRAVITY_Z = 2;

    // rad/s
    private static final int GYROSCOPE_X = 0;
    private static final int GYROSCOPE_Y = 1;
    private static final int GYROSCOPE_Z = 2;

    // rad/s
    private static final int GYROSCOPE_UNCALIBRATED_X = 0;
    private static final int GYROSCOPE_UNCALIBRATED_Y = 1;
    private static final int GYROSCOPE_UNCALIBRATED_Z = 2;
    private static final int GYROSCOPE_UNCALIBRATED_DRIFT_X = 3;
    private static final int GYROSCOPE_UNCALIBRATED_DRIFT_Y = 4;
    private static final int GYROSCOPE_UNCALIBRATED_DRIFT_Z = 5;

    // m/s**2 (excluding gravity)
    private static final int LINEAR_ACCELERATION_X = 0;
    private static final int LINEAR_ACCELERATION_Y = 1;
    private static final int LINEAR_ACCELERATION_Z = 2;

    // Unitless
    private static final int ROTATION_VECTOR_X = 0; // (x * sin(angle/2))
    private static final int ROTATION_VECTOR_Y = 1; // (y * sin(angle/2))
    private static final int ROTATION_VECTOR_Z = 2; // (z * sin(angle/2))
    private static final int ROTATION_SCALAR = 3;   // ((cos(angle/2))

    // Steps
    private static final int STEP_COUNTER = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registered for ORIENTATION sensor");
        } else {
            Log.e("Compass MainActivity", "Registered for ORIENTATION sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South,270=West
            float azimuth = event.values[0];
            TextView azimuthText = (TextView)findViewById(R.id.orientation_azimuth);
            azimuthText.setText(String.format("%f", azimuth));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do nothing
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensorService.unregisterListener(mySensorEventListener);
        }
    }
}
