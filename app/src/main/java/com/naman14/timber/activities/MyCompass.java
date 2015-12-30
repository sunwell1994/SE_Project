package com.naman14.timber.activities;

/**
 * Created by mayintong on 2015/8/6.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.List;

public class MyCompass extends Activity implements SensorEventListener
{
    private static final String TAG = "Compass";
//    private boolean        mRegisteredSensor;
//
//    private SensorManager mSensorManager;
//    static public float x;
//    static public float curX;

    private SensorManager sensorManager;
    private Sensor gsensor;
    private Sensor msensor;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    public float x = 0f;
    public float curx= 0f;


    /** Called when the activity is first created. */


    public MyCompass(Context context) {
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        start();
    }




    public void start() {
        sensorManager.registerListener(this, gsensor,
                SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, msensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onPause()
    {
        stop();

        super.onPause();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                mGravity[0] = alpha * mGravity[0] + (1 - alpha)
                        * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha)
                        * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha)
                        * event.values[2];

                // mGravity = event.values;

                Log.e(TAG, Float.toString(mGravity[0]));
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // mGeomagnetic = event.values;

                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
                        * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
                        * event.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
                        * event.values[2];
                // Log.e(TAG, Float.toString(event.values[0]));

            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, null, mGravity,
                    mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                curx=x;

                x = (float) Math.toDegrees(orientation[0]); // orientation
                x = ((x + 360) % 360);
                x-=45;
                Log.d(TAG, "x (deg): " + x);

            }
        }
    }


}