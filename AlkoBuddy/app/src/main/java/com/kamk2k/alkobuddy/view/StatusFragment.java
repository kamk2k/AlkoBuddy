package com.kamk2k.alkobuddy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;
import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;
import com.kamk2k.alkobuddy.model.events.ChangeStatusTextEvent;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.view.utils.MVPRetainWorkerFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusFragment extends MVPFragmentView {

    public static final String TAG = StatusFragment.class.getSimpleName();

    @InjectView(R.id.promil_text_field) TextView perMileTextView;
    @InjectView(R.id.time_to_sober_text_field) TextView timeToSoberTextView;

    public StatusFragment() {
        if(MVPRetainWorkerFragment.getRetainedPresenter(TAG) == null) {
            presenter = new StatusFragmentPresenter(getActivity());
            MVPRetainWorkerFragment.registerPresenterToRetain(TAG, presenter);
        } else {
            presenter = (MVPFragmentPresenter)MVPRetainWorkerFragment.getRetainedPresenter(TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.inject(this, root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(ChangeStatusTextEvent event){
        perMileTextView.setText(event.getPerMileText());
        timeToSoberTextView.setText(event.getTimeToSoberText());
    }
}