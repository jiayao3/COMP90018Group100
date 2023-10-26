package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AndroidLauncher extends AndroidApplication {
	private static final int RECORD_AUDIO_PERMISSION_CODE = 1;
	private AudioSensor audioSensor;
	private GyroscopeSensor gyroscopeSensor;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		Game game = new Game();
		super.onCreate(savedInstanceState);
		// initialize AudioSensor here
		audioSensor = new AudioSensor(this);
		gyroscopeSensor = new GyroscopeSensor(this);

		// Check and request permissions
		if (checkPermission()) {
			// permissions are already granted, start game
			startSensor();
		} else {
			// request permissions
			requestPermission();
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(game, config);
	}

	private boolean checkPermission() {
		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
		return result == PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermission() {
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
	}

	private void startSensor() {
		// start game here
		gyroscopeSensor.start();
		audioSensor.startGame();

	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// permission granted, start the game
				startSensor();
			} else {
				// permission denied, ask user to enable for this mode, maybe return to home screen first
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		gyroscopeSensor.sensorManager.registerListener(gyroscopeSensor.accelerometerListener, gyroscopeSensor.accelerometerSensor, gyroscopeSensor.sensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		gyroscopeSensor.sensorManager.unregisterListener(gyroscopeSensor.accelerometerListener);
	}

}
