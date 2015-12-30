package com.naman14.timber.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.naman14.timber.R;
import com.naman14.timber.utils.NavigationUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 2015/12/30.
 */
public class InnerLocationActivity extends Activity implements StepListener,SensorEventListener{


    private static final String DEBUG_TAG = "InnerLocationActivity";
    private MyCompass myCompass;
//    private StepCalculater stepCal;

   private  ImageView pos;

    private SensorManager sm;

    private Sensor accel;
    private Sensor msensor;
    public int stepcounter,spacecounter;


    public int flag=0;
    public int step_south;
    public int step_north;
    public int step_east;
    public int step_west;
    public int Loc_X=120;
    public int Loc_Y=580;
    public int Last_Loc_X=120;
    public int Last_Loc_Y=580;
    public int step_distance = 50;

    private SimpleStepDetector simpleStepDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i(DEBUG_TAG, "InnerCreate! ");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inner_location);

        myCompass = new MyCompass(this);
//        stepCal = new StepCalculater(this);
        pos= (ImageView)findViewById(R.id.pos);
        pos.setImageResource(R.drawable.location_icon);
        pos.setTranslationX(120);
        pos.setTranslationY(580);


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        msensor= sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sm.registerListener(this, accel,
                SensorManager.SENSOR_DELAY_FASTEST);
        sm.registerListener(this, msensor,
                SensorManager.SENSOR_DELAY_FASTEST);

        myCompass = new MyCompass(this);

        simpleStepDetector = new SimpleStepDetector();
        simpleStepDetector.registerListener(this);

        stepcounter=0;
        spacecounter=0;


    }
    @Override
    protected void onPause()
    {
        sm.unregisterListener(this);

        super.onPause();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
        if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                pos.setRotation(myCompass.x);
        }
    }

    @Override
    public void step(long timeNS){


        float angle = myCompass.x;
        Log.i("compass",":"+angle);

        flag=0;
        Log.i("step","true");
        if(angle>255 && angle<315 )
        {
            step_east++;
            //left
//								Last_Loc_X=Loc_X;
//								Loc_X+=step_distance;
            Last_Loc_X=Loc_X;
            Loc_X-=step_distance;
        }
        else if(angle>135 && angle<225)
        {
            step_north++;
            //down
            Last_Loc_Y=Loc_Y;
            Loc_Y+=step_distance;
        }
        else if(angle>45 && angle<135)
        {
            //right
            step_west++;
//								Last_Loc_X=Loc_X;
//								Loc_X-=step_distance;
            Last_Loc_X =Loc_X;
            Loc_X+=step_distance;
        }
        else if(angle<45 || angle>315)
        {
            //up
            step_south++;
            Last_Loc_Y=Loc_Y;
            Loc_Y-=step_distance;
//								Last_Loc_Y=Loc_Y;
//								Loc_Y+=step_distance;

        }
        stepcounter=stepcounter+1;
        if (Loc_X>=755||Loc_X<=8)
            Loc_X=Last_Loc_X;

        if (Loc_Y>=1175||Loc_Y<=8)
            Loc_Y=Last_Loc_Y;

        Log.i("loc", Loc_X+","+Loc_Y);
        flag=1;

        pos.setTranslationX(Loc_X);
        pos.setTranslationY(Loc_Y);
//        pos.setRotation(angle);
    }

}
