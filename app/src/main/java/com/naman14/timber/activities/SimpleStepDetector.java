package com.naman14.timber.activities;

import android.util.Log;

/**
 * Created by admin on 2015/12/18.
 */
public class SimpleStepDetector {
    private static final int ACCEL_RING_SIZE =50;
    private static  final int VEL_RING_SIZE  = 10;
    private static  final float STEP_THRESHOLD =0.23f;//4f;
    private static final int STEP_DELAY_NS =250000000;//250000000;

    private static final float NS2S = 1.0f / 1000000000.0f;

    private float lowX = 0, lowY = 0, lowZ = 0;
    private final float FILTERING_VALAUE = 0.1f;

    private int accelRingCounter =0;
    private float[] accelRingX =new float[ACCEL_RING_SIZE];
    private float[] accelRingY =new float[ACCEL_RING_SIZE];
    private float[] accelRingZ =new float[ACCEL_RING_SIZE];
    private int velRingCounter =0;
    private float[] velRing =new  float[VEL_RING_SIZE];
    private long lastStepTimeNs =0;
    private float oldVelocityEstimate =0;
    private float olda=0;

    private StepListener listener;



    public void registerListener(StepListener listener){
        this.listener =listener;
    }

    /**
     * Accepts updates from the accelerometer
     */
    public void updateAccel(long timeNs, float x, float y, float z) {
        //Low-Pass Filter
        lowX = x * FILTERING_VALAUE + lowX * (1.0f - FILTERING_VALAUE);
        lowY = y * FILTERING_VALAUE + lowY * (1.0f - FILTERING_VALAUE);
        lowZ = z * FILTERING_VALAUE + lowZ * (1.0f - FILTERING_VALAUE);

        //High-pass filter
        float highX  = x -  lowX;
        float highY  = y -  lowY;
        float highZ  = z -  lowZ;



        float[] currentAccel = new float[3];
        currentAccel[0] = highX;
        currentAccel[1] = highY;
        currentAccel[2] = highZ;

        // First step is to update our guess of where the global z vector is.
        accelRingCounter++;
        accelRingX[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[0];
        accelRingY[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[1];
        accelRingZ[accelRingCounter % ACCEL_RING_SIZE] = currentAccel[2];

        float[] worldZ = new float[3];
        worldZ[0] = SensorFusionMath.sum(accelRingX) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[1] = SensorFusionMath.sum(accelRingY) / Math.min(accelRingCounter, ACCEL_RING_SIZE);
        worldZ[2] = SensorFusionMath.sum(accelRingZ) / Math.min(accelRingCounter, ACCEL_RING_SIZE);



        float a = worldZ[2];
//        Log.e("dddd", String.valueOf(worldZ[0]));


        if (worldZ[0] > 0.45|| -worldZ[0] < -0.45) {
        } else {

            if (a > STEP_THRESHOLD && olda <= STEP_THRESHOLD
                    && (timeNs - lastStepTimeNs > STEP_DELAY_NS)) {
                listener.step(timeNs);
                lastStepTimeNs = timeNs;
            }
//        oldVelocityEstimate = velocityEstimate;
            olda = a;
        }

    }
}
