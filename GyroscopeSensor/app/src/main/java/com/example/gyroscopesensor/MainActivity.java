package com.example.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize sensors and listeners
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Create a sensor event listener
        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float xAcceleration = event.values[0];
                float yAcceleration = event.values[1];
                float zAcceleration = event.values[2];

                // Calculate the total acceleration (magnitude)
                double totalAcceleration = Math.sqrt(
                        xAcceleration * xAcceleration +
                                yAcceleration * yAcceleration +
                                zAcceleration * zAcceleration
                );

                // Calculate the angle of tilt in degrees
                double tiltAngleDegrees = Math.toDegrees(Math.asin(xAcceleration / totalAcceleration));

                // Show the position value of phone
                // Load and use views afterwards
                TextView tv1 = (TextView)findViewById(R.id.position);
                tv1.setText(Double.toString(tiltAngleDegrees));

                // Change background color
                // Define thresholds for left and right tilt angles (adjust as needed)
                double leftTiltThreshold = -10.0; // Example threshold for left tilt
                double rightTiltThreshold = 10.0; // Example threshold for right tilt

                // Check if the phone is tilted to the left
                if (tiltAngleDegrees < leftTiltThreshold) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                // Check if the phone is tilted to the right
                else if (tiltAngleDegrees > rightTiltThreshold) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                // If the phone is near horizontal, set the background to white
                else {
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle accuracy changes if needed
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accelerometerListener);
    }

}
