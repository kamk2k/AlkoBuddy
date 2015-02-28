package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.content.Context;

import com.google.gson.Gson;
import com.kamk2k.alkobuddy.Constants;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.UserAlcoState;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityController {

    private static UserAlcoState mUserAlcoState;

    private static DrinkClickListener mDrinkClickListener;
    private static PerMileCalculator mPerMileCalculator;
    private static StatusDisplayer mStatusDisplayer;
    private static StorageControler mStorageControler;

    public static void init(Context context) {
        mStorageControler = new StorageControler(context);
        mUserAlcoState = loadUserStateFromFile();
        // TODO Add proper constructor for UserAlcoState
        if(mUserAlcoState == null)  mUserAlcoState = UserAlcoState.generateMock();
        mPerMileCalculator = new PerMileCalculator(mUserAlcoState);
        mStatusDisplayer = new StatusDisplayer(mUserAlcoState);
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

    public static UserAlcoState loadUserStateFromFile() {
        Gson gson = new Gson();
        UserAlcoState state = gson.fromJson((String)mStorageControler.readObjectData(Constants.USER_STATE_STORAGE_FILE), UserAlcoState.class);
        return state;
    }

    public static void saveUserStateFromFile() {
        Gson gson = new Gson();
        String json = gson.toJson(mUserAlcoState);
        mStorageControler.saveObjectData(json, Constants.USER_STATE_STORAGE_FILE);
    }
}
