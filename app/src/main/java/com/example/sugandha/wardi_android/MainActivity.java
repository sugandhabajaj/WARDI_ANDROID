package com.example.sugandha.wardi_android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor rotationSensor;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        txt=(TextView)findViewById(R.id.show);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() ==  Sensor.TYPE_GAME_ROTATION_VECTOR){
            float[] rotMatrix = new float[9];
            float[] rotVals = new float[3];

            SensorManager.getRotationMatrixFromVector(rotMatrix, sensorEvent.values);
            SensorManager.remapCoordinateSystem(rotMatrix,
                    SensorManager.AXIS_X, SensorManager.AXIS_Y, rotMatrix);

            SensorManager.getOrientation(rotMatrix, rotVals);
            float azimuth = (float) Math.toDegrees(rotVals[0]);
            float pitch = (float) Math.toDegrees(rotVals[1]);
            float roll = (float) Math.toDegrees(rotVals[2]);

            if(roll>=0 && roll<=180)
            {
                txt.setText("See, I told you");
            }
            else
            {

                txt.setText("Don't you get bored of me");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
