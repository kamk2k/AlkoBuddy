package com.kamk2k.alkobuddy.presenter;

import android.content.Context;
import android.util.Log;

import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.model.events.ChangeStatusTextEvent;
import com.kamk2k.alkobuddy.model.events.UpdateEvent;
import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;
import com.kamk2k.alkobuddy.view.utils.MVPRetainWorkerFragment;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class StatusFragmentPresenter implements MVPFragmentPresenter {

    private static final String TAG = StatusFragmentPresenter.class.getSimpleName();

    @Inject
    protected UserAlcoState userState;

    @Inject
    public StatusFragmentPresenter(Context context) {
//        this.userState = UserStateProvider.getUserState();
        EventBus.getDefault().registerSticky(this);
    }

    //TODO text formattin
    private void update() {
        Log.d(TAG, "update()");
        String perMileText = String.format("%.2f", userState.getCurrentPerMile());
        Date date = new Date();
        date.setTime(date.getTime() + userState.getTimeToSoberInMs());
        DateFormat df = DateFormat.getDateTimeInstance();
        String timeToSoberText = df.format(date);
        EventBus.getDefault().post(new ChangeStatusTextEvent(perMileText, timeToSoberText));
    }

    public void onEvent(UpdateEvent event){
        update();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        update();
    }
}
