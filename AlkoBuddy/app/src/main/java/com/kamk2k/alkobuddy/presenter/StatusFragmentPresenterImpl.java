package com.kamk2k.alkobuddy.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.events.UpdateEvent;
import com.kamk2k.alkobuddy.view.StatusView;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class StatusFragmentPresenterImpl implements StatusFragmentPresenter {

    private static final String TAG = StatusFragmentPresenterImpl.class.getSimpleName();

    protected UserAlcoState userState;
    private StatusView statusView;
    public static final DateFormat DISPLAYED_TO_SOBER_DATE_FORMAT = DateFormat
            .getDateTimeInstance();

    @Inject
    public StatusFragmentPresenterImpl(Context context, UserAlcoState userState) {
        this.userState = userState;
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void setMVPView(StatusView mvpView) {
        statusView = mvpView;
    }

    //TODO text formattin
    private void update() {
        Log.d(TAG, "update()");
        String perMileText = String.format("%.2f", userState.getCurrentPerMile());
        Date dateToSober = getDateToSober();
        String timeToSoberText = DISPLAYED_TO_SOBER_DATE_FORMAT.format(dateToSober);
        statusView.displayPerMileText(perMileText);
        statusView.displayTimeToSoberText(timeToSoberText);
    }

    @NonNull
    private Date getDateToSober() {
        Date date = new Date();
        date.setTime(date.getTime() + userState.getTimeToSoberInMs());
        return date;
    }

    public void onEvent(UpdateEvent event){
        if(event.getUserAlcoState() != null) {
            userState = event.getUserAlcoState();
        }
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
