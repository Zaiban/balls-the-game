package com.ballsthegame;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Created by Esa on 19.11.2014.
 * Here we will implement the Main Menu of the application.
 */
public class GoalReachedFragment extends Fragment {

    private Button returnMainMenuButton;
    private CallbackInterface mCallback;
    private String mTimeString;
    private TextView mTextView;

    public interface CallbackInterface {
        // This interface method is used to start a new game
        public void onReturnMainMenu();
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
        View rootView = inflater.inflate(R.layout.fragment_goalreached, container, false);

        mTimeString = this.getArguments().getString("argString");
        mTextView = (TextView) rootView.findViewById(R.id.tv_timeElapsed);
        mTextView.setText(mTimeString);

        Log.w("BallsTheGame", "in GoalReachedFragment: onCreateView()");
        // Bind a Listener on the returnMainMenuButton
        returnMainMenuButton = (Button) rootView.findViewById(R.id.button_returnmainmenu);
        returnMainMenuButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                mCallback.onReturnMainMenu();
            }
        });


        return rootView;
    }


}
