package com.kamk2k.alkobuddy.view.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = MainBroadcastReceiver.class.getSimpleName();

    public MainBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + intent.getAction());
        //TODO PerMileCalculator.process
    }
}