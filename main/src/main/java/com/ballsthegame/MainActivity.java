package com.ballsthegame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by Esa on 19.11.2014.
 * asfd
 */

public class MainActivity extends Activity
        implements MainMenuFragment.CallbackInterface, GameFragment.CallbackInterface,
        PauseFragment.CallbackInterface, GoalReachedFragment.CallbackInterface, GuideFragment.CallbackInterface {

    Fragment mainMenuFragment = new MainMenuFragment();
    Fragment gameFragment = new GameFragment();
    Fragment pauseFragment = new PauseFragment();
    Fragment guideFragment = new GuideFragment();
    GoalReachedFragment goalReachedFragment = new GoalReachedFragment();
    Bundle arguments = new Bundle();
    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("BallsTheGame", "in MainActivity: onCreate()");


        if (savedInstanceState == null) {

            // Start the mainMenuFragment
            ft = fm.beginTransaction();
            ft.add(R.id.container, mainMenuFragment);
            ft.addToBackStack("mainmenu");
            ft.commit();
        }
    }

    public void onGoGuide(){
        setContentView(R.layout.activity_main);

        ft = fm.beginTransaction();
        ft.replace(R.id.container, guideFragment);
        ft.commit();

    }

    // Define interface methods here
    public void onStartNewGame() {
        // Set view to activity_main so that fragment knows where to inflate its own
        setContentView(R.layout.activity_main);
        // Replace the currently active fragment with gameFragment
        gameFragment = new GameFragment();
        CanvasView.mPlayer.setPosition(40f, 40f);
        CanvasView.mGoal.placeGoal();
        ft = fm.beginTransaction();
        ft.replace(R.id.container, gameFragment);
        ft.commit();

    }

    public void onExitGame() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void onPauseGame() {
        Log.w("BallsTheGame", "in MainActivity: onPauseGame()");
        // Set view to activity_main so that fragment knows where to inflate its own
        setContentView(R.layout.activity_main);
        // Replace the currently active fragment with gameFragment
        ft = fm.beginTransaction();
        ft.replace(R.id.container, pauseFragment);
        ft.addToBackStack("game");
        ft.commit();
    }

    public void onReturnGame() {
        // Set view to activity_main so that fragment knows where to inflate its own
        setContentView(R.layout.activity_main);
        // Pop gameFragment
        fm.popBackStack("game", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void onReturnMainMenu() {
        // Set view to activity_main so that fragment knows where to inflate its own
        setContentView(R.layout.activity_main);
        // Replace the currently active fragment with gameFragment
        ft = fm.beginTransaction();
        ft.replace(R.id.container, mainMenuFragment);
        ft.commit();

    }

    public void onGameOver(int gameOverTime) {
        Log.w("BallsTheGame", "in MainActivity: onGameOver()");
        // Set view to activity_main so that fragment knows where to inflate its own
        setContentView(R.layout.activity_main);
        // Replace the currently active fragment with goalReachedFragment
        String message = "Time elapsed: " + gameOverTime + " seconds.";
        arguments.putString("argString", message);
        goalReachedFragment.setArguments(arguments);
        ft = fm.beginTransaction();
        ft.replace(R.id.container, goalReachedFragment);
        ft.commit();
    }



}
