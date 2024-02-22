package com.dauslab.signus;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WarmUpActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    public WarmUpActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warm_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        getSupportActionBar().setTitle(R.string.title_activity_login);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        findViewById(R.id.firstButton).setOnClickListener(view -> {
            Intent intent = new Intent(WarmUpActivity.this, MainActivity.class);
            intent.putExtra(Login.USERNAME, getIntent().getStringExtra(Login.USERNAME));
            startActivity(intent);
            this.finish();
        });

        LinearLayout cameraFragmentContainer = findViewById(R.id.cameraFragmentContainer);
        cameraFragmentContainer.removeAllViews();
        CameraFragment cameraFragment = CameraFragment.newInstance(0, getIntent().getStringExtra(Login.USERNAME));
        getSupportFragmentManager().beginTransaction().add(cameraFragmentContainer.getId(), cameraFragment, "").commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensor.getType()) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix,
                    event.values);

            SensorManager.getOrientation(rotationMatrix, orientationAngles);
            //float azimuth = (float) Math.toDegrees(orientationAngles[0]); // in degrees [-180, +180]
            float pitch = (float) Math.toDegrees(orientationAngles[1]);
            //float roll = (float) Math.toDegrees(orientationAngles[2]);
            if (Math.abs(pitch) < 60) {
                ((TextView) findViewById(R.id.indication)).setText(R.string.demasiado_inclinado);
            }else {
                ((TextView) findViewById(R.id.indication)).setText(R.string.ready_to_race);
            }
        }
    }
}