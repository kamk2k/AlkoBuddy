package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.UserAlcoState;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityController {

    private static DrinkClickListener mDrinkClickListener;
    private static PerMileCalculator mPerMileCalculator;
    private static StatusDisplayer mStatusDisplayer;

    public static void init(UserAlcoState userState) {
        mPerMileCalculator = new PerMileCalculator(userState);
        mStatusDisplayer = new StatusDisplayer(userState);
        mDrinkClickListener = new DrinkClickListener(mPerMileCalculator, mStatusDisplayer);
    }

    public static DrinkClickListener getDrinkClickListenerInstance() {
        return mDrinkClickListener;
    }

    public static StatusDisplayer getStatusDisplayerInstance() {
        return mStatusDisplayer;
    }

    public static void processStatusUpdate() {
        if(mPerMileCalculator != null && mStatusDisplayer != null) {
            mPerMileCalculator.processAlco();
            mStatusDisplayer.update();
        }
    }
}
