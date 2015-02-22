package com.ballsthegame;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by Esa on 19.11.2014.
 * Here we will implement the Main Menu of the application.
 */
public class MainMenuFragment extends Fragment {
    private Button newGameButton;
    private Button guideButton;
    private Button exitGameButton;

    // Create a callback interface for communicating with MainActivity
    CallbackInterface mCallback;

    public interface CallbackInterface {
        // This interface method is used to start a new game
        public void onStartNewGame();
        public void onGoGuide();
        public void onExitGame();
    }

    @Override
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainmenu, container, false);

        // Bind a Listener on the newGameButton
        newGameButton = (Button) rootView.findViewById(R.id.button_newgame);
        newGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mCallback.onStartNewGame();
            }
        });

        // Bind a Listener on the guideButton
        newGameButton = (Button) rootView.findViewById(R.id.button_guide);
        newGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mCallback.onGoGuide();
            }
        });

        // Bind a Listener on the exitGameButton
        exitGameButton = (Button) rootView.findViewById(R.id.button_exitgame);
        exitGameButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mCallback.onExitGame();
            }
        });


        return rootView;
    }


}
