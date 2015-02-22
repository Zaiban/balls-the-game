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
public class GuideFragment extends Fragment {

    private Button returnMainMenuButton;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
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
        View rootView = inflater.inflate(R.layout.fragment_guide, container, false);

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
