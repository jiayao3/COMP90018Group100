package com.example.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener accelerometerListener;
    private boolean isInitial = true; // Flag to track the initial position

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

                // Define thresholds for left and right tilt angles (adjust as needed)
                double leftTiltThreshold = -15.0; // Example threshold for left tilt
                double rightTiltThreshold = 15.0; // Example threshold for right tilt

                // Check if the phone is tilted to the left
                if (tiltAngleDegrees < leftTiltThreshold) {
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                // Check if the phone is tilted to the right
                else if (tiltAngleDegrees > rightTiltThreshold) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                // If the phone is near the initial position, set the background to white
                else {
                    if (!isInitial) {
                        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ensure that the flag is set to true when the activity is destroyed
        isInitial = true;
    }
}
