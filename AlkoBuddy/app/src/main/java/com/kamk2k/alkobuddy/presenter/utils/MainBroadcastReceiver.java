package com.kamk2k.alkobuddy.presenter.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kamk2k.alkobuddy.model.events.ProcessAlkoEvent;

import de.greenrobot.event.EventBus;

public class MainBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = MainBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() : " + intent.getAction());
        EventBus.getDefault().post(new ProcessAlkoEvent());
    }
}