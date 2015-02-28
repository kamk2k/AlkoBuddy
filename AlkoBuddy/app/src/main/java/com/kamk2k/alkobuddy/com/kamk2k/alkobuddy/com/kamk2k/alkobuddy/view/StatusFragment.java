package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller.MainActivityController;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller.StatusTextContainer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusFragment extends Fragment implements StatusTextContainer {

    @InjectView(R.id.promil_text_field) TextView perMileTextView;
    @InjectView(R.id.time_to_sober_text_field) TextView timeToSoberTextView;

    public StatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.inject(this, root);
        MainActivityController.getStatusDisplayerInstance().setStatusTextContainer(this);
        return root;
    }

    @Override
    public void setPerMileText(String text) {
        perMileTextView.setText(text);
    }

    @Override
    public void setTimeToSober(String text) {
        timeToSoberTextView.setText(text);
    }
}