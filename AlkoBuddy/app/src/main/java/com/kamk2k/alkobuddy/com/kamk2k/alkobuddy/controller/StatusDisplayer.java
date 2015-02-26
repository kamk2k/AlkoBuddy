package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.util.Log;

import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.UserAlcoState;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by PC on 2015-02-25.
 */
public class StatusDisplayer {

    private static final String TAG = StatusDisplayer.class.getSimpleName();

    private UserAlcoState userState;
    private StatusTextContainer statusTextContainer;

    public StatusDisplayer(UserAlcoState userState) {
        this.userState = userState;
    }

    //TODO text formattin
    public void update() {
        Log.d(TAG, "update()");
        String perMileText = String.format("%.2f", userState.getCurrentPerMile());
        Date date = new Date();
        date.setTime(date.getTime() + userState.getTimeToSoberInMs());
        DateFormat df = DateFormat.getDateTimeInstance();
        String timeToSoberText = df.format(date);

        statusTextContainer.setPerMileText(perMileText);
        statusTextContainer.setTimeToSober(timeToSoberText);
    }

    public UserAlcoState getUserState() {
        return userState;
    }

    public void setUserState(UserAlcoState userState) {
        this.userState = userState;
    }

    public StatusTextContainer getStatusTextContainer() {
        return statusTextContainer;
    }

    public void setStatusTextContainer(StatusTextContainer statusTextContainer) {
        this.statusTextContainer = statusTextContainer;
    }
}
