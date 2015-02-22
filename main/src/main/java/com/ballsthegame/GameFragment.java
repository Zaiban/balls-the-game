package com.ballsthegame;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Esa on 19.11.2014.
 * Here we will implement the Game portion of the application.
 */
public class GameFragment extends Fragment
        implements SensorEventListener{

    private SensorManager mSensorManager;
    private CanvasView mCanvas;
    private float[] mGravity = new float[3];
    private float[] mMotion = new float[3];
    private double mRatio;
    private double mAngle;
    private int mTimeElapsed;
    private int mFramesDrawn;
    private MyCountDown mCountdown;
    private TextView gameOverTime;
    CallbackInterface mCallback;

    public interface CallbackInterface {
        public void onPauseGame();

        public void onGameOver(int gameOverTime);
    }



    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (CallbackInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        mTimeElapsed = 0;
        mFramesDrawn = 0;

        mCountdown = new MyCountDown(1000, 20);


        Log.w("BallsTheGame", "in GameFragment: onCREATE()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w("BallsTheGame", "in GameFragment: onCreateView()");
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);


        mCanvas = (CanvasView) rootView.findViewById(R.id.signature_canvas);

        mCountdown.start();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.getUserVisibleHint()) {
            this.registerSensorListener();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Log.w("BallsTheGame", "Back button pressed");
                    mCallback.onPauseGame();

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        this.unregisterSensorListener();
        mCountdown.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0], y = event.values[1];

        // Use a low-pass filter to get gravity. Motion is what's left over
        for (int i = 0; i < 3; i++) {
            mGravity[i] = (float) (0.1 * event.values[i] + 0.9 * mGravity[i]);
            mMotion[i] = event.values[i] - mGravity[i];
        }

        // ratio is gravity on the Y axis compared to full gravity
        // should be no more than 1, no less than -1
        mRatio = mGravity[1] / SensorManager.GRAVITY_EARTH;
        if (mRatio > 1.0) mRatio = 1.0;
        if (mRatio < -1.0) mRatio = -1.0;

        // convert ratio to radians to degrees, make negative if facing up
        mAngle = Math.toDegrees(Math.acos(mRatio));
        if (mGravity[2] < 0) {
            mAngle = -mAngle;
        }

        mCanvas.updateGravity(mGravity[0], mGravity[1]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // First starts (gets called before everything else)
        if (mSensorManager == null) {
            return;
        }

        if (menuVisible) {
            this.registerSensorListener();
        } else {
            this.unregisterSensorListener();
        }
    }

    private void registerSensorListener() {
        mSensorManager.registerListener(this, mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }

    public class MyCountDown extends CountDownTimer {

        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            Log.w("BallsTheGame", "Timer created");
        }

        @Override
        public void onFinish() {

            Log.w("BallsTheGame", "FPS:  " + mFramesDrawn);
            mFramesDrawn = 0;

            mTimeElapsed++;
            mCanvas.updateTime(mTimeElapsed);

            Log.w("BallsTheGame", "time elapsed: " + mTimeElapsed);
            start();
        }

        @Override
        public void onTick(long millisUntilFinished) {


            if (mCanvas.checkGoal())
                mCallback.onGameOver(mTimeElapsed);
            else {
                // Redraw the canvas
                mCanvas.drawNext();
                mFramesDrawn++;
            }
        }
    }

}
