package com.corellidev.alcotester.presenter.utils;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

/**
 * Created by Kamil on 2017-04-21.
 */

public class AnalyticsLogger {

    public static final String STATUS_SCREEN_DISPLAYED_EVENT = "status_screen_displayed";
    public static final String EDIT_SCREEN_DISPLAYED_EVENT = "edit_screen_displayed";
    public static final String RESET_OPTION_CLICKED_EVENT = "reset_option_clicked";
    public static final String REMOVE_OPTION_CLICKED_EVENT = "remove_option_clicked";
    public static final String SETTINGS_OPTION_CLICKED_EVENT = "settings_option_clicked";
    public static final String DRINK_CLICKED_EVENT = "drink_clicked";
    public static final String ADD_NEW_DRINK_CLICKED_EVENT = "add_new_drink_clicked";
    public static final String TOOLTIP_CLICKED_EVENT = "tooltip_clicked";

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Inject
    public AnalyticsLogger() {
    }

    public void logEvent(String eventName) {
        firebaseAnalytics.logEvent(eventName, new Bundle());
    }
}
