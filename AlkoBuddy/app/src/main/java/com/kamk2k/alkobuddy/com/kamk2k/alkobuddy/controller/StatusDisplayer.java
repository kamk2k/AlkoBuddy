package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.util.Log;

import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.UserAlcoState;

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
        statusTextContainer.setPerMileText(Float.toString(userState.getCurrentPerMile()));
        statusTextContainer.setTimeToSober(Long.toString(userState.getTimeToSoberInMs()));
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
