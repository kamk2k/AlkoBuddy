package com.kamk2k.alkobuddy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;
import com.kamk2k.alkobuddy.model.events.ChangeStatusTextEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusFragment extends MVPFragmentView implements StatusView {

    public static final String TAG = StatusFragment.class.getSimpleName();

    @InjectView(R.id.promil_text_field) TextView perMileTextView;
    @InjectView(R.id.time_to_sober_text_field) TextView timeToSoberTextView;
    @Inject StatusFragmentPresenter presenter;

    public StatusFragment() {
    }

    @Override
    public void displayPerMileText(String perMileText) {
        perMileTextView.setText(perMileText);
    }

    @Override
    public void displayTimeToSoberText(String timeToSoberText) {
        timeToSoberTextView.setText(timeToSoberText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.inject(this, root);
        return root;
    }

    @Override
    public void injectFragment(ApplicationComponent component) {
        component.inject(this);
        presenter.setMVPView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}