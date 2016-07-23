package com.kamk2k.alkobuddy.presenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.kamk2k.alkobuddy.Constants;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;
import com.kamk2k.alkobuddy.presenter.utils.StorageControler;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final int UPDATE_DELAY = 10000;

    protected UserStateChangeHandler userStateChangeHandler;
    protected StorageControler storageControler;
    // TODO: 23.07.16 delegate update handling to external class
    protected Handler updateHandler;
    private UpdateRunnable updateRunnable;

    @Override
    public void drinkClicked(DrinkItem drinkItem) {
        userStateChangeHandler.onDrink(drinkItem, new Date());
    }

    @Override
    public void resetDrinkState() {
        userStateChangeHandler.resetUserState();
    }

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            updateUserStateData();
        }
    }

    @Inject
    public MainActivityPresenterImpl(StorageControler storageControler,
                                     UserStateChangeHandler userStateChangeHandler, Handler updateHandler) {
        this.storageControler = storageControler;
        this.userStateChangeHandler = userStateChangeHandler;
        this.updateHandler = updateHandler;
        updateRunnable = new UpdateRunnable();
        userStateChangeHandler.setUserState(UserStateProvider.getUserState());
    }

    public void loadUserStateFromFile() {
        Gson gson = new Gson();
        UserAlcoState state = gson.fromJson((String) storageControler.readObjectData(Constants.USER_STATE_STORAGE_FILE), UserAlcoState.class);
        if(state != null) {
            userStateChangeHandler.setUserState(state);
        }
    }

    public void saveUserStateToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(userStateChangeHandler.getUserState());
        storageControler.saveObjectData(json, Constants.USER_STATE_STORAGE_FILE);
    }

    private void updateUserStateData() {
        userStateChangeHandler.processAlcohol(new Date());
        updateHandler.postDelayed(updateRunnable, UPDATE_DELAY);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
        loadUserStateFromFile();
        updateUserStateData();
    }

    @Override
    public void onStop() {
        saveUserStateToFile();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
