package com.kamk2k.alkobuddy.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.view.StatusView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by PC on 2015-02-25.
 */
public class StatusFragmentPresenterImpl implements StatusFragmentPresenter {

    private static final String TAG = StatusFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    protected UserAlcoState userState;
    private StatusView statusView;
    public static final String DISPLAYED_SOBER_TIME_FORMAT = "HH:mm";

    @Inject
    public StatusFragmentPresenterImpl(UserAlcoState userState, Context context) {
        this.userState = userState;
        this.context = context;
    }

    @Override
    public void setMVPView(StatusView mvpView) {
        statusView = mvpView;
    }

    @Override
    public void updateStatus(UserAlcoState userAlcoState) {
        if(userState != null) {
            userState = userAlcoState;
        }
        if(statusView != null) {
            update();
        }
    }

    private void update() {
        Log.d(TAG, "update()");
        updatePerMileInfo();
        updateTimeToSoberInfo();
    }

    private void updateTimeToSoberInfo() {
        DateTime dateToSober = getDateToSober();
        DateTime now = new DateTime(new Date());

        displaySoberClockTime(dateToSober, now);
        displaySoberTextTime(dateToSober, now);
    }

    private void displaySoberTextTime(DateTime dateToSober, DateTime now) {
        int daysBetween = Days.daysBetween(now.withTimeAtStartOfDay(), dateToSober.withTimeAtStartOfDay()).getDays();
        if(daysBetween == 0) {
            statusView.displayTodayTimeToSoberText(dateToSober.toLocalTime().toString(DISPLAYED_SOBER_TIME_FORMAT));
        } else if(daysBetween == 1) {
            statusView.displayTomorrowTimeToSoberText(dateToSober.toLocalTime().toString(DISPLAYED_SOBER_TIME_FORMAT));
        } else {
            statusView.displayLaterThanTomorrowTimeToSoberText(dateToSober.toLocalTime().toString(DISPLAYED_SOBER_TIME_FORMAT));
        }
    }

    private void displaySoberClockTime(DateTime dateToSober, DateTime now) {
        if(willSoberLaterThanTomorrow(dateToSober, now)) {
            statusView.displayOver24hSoberTime();
        } else {
            int soberHour = dateToSober.get(DateTimeFieldType.hourOfDay());
            int soberMinute = dateToSober.get(DateTimeFieldType.minuteOfHour());
            statusView.displaySoberTime(soberHour, soberMinute);
        }
    }

    private boolean willSoberLaterThanTomorrow(DateTime dateToSober, DateTime now) {
        int daysBetween = Days.daysBetween(now.withTimeAtStartOfDay(), dateToSober
                .withTimeAtStartOfDay()).getDays();
        if(daysBetween <= 1) {
            return false;
        } else {
            return true;
        }
    }

    private void updatePerMileInfo() {
        statusView.displayPerMile(userState.getCurrentPerMile());
    }

    @NonNull
    private DateTime getDateToSober() {
        Date date = new Date();
        date.setTime(date.getTime() + userState.getTimeToSoberInMs());
        return new DateTime(date);
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
