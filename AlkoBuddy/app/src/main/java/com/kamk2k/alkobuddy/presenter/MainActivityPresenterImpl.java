package com.kamk2k.alkobuddy.presenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.kamk2k.alkobuddy.Constants;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.model.events.DrinkEvent;
import com.kamk2k.alkobuddy.model.events.ProcessAlkoEvent;
import com.kamk2k.alkobuddy.model.events.ResetDrinkState;
import com.kamk2k.alkobuddy.model.events.UpdateEvent;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;
import com.kamk2k.alkobuddy.presenter.utils.StorageControler;

import java.util.Date;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final int UPDATE_DELAY = 10000;
    private static UserAlcoState mUserAlcoState;

    protected UserStateChangeHandler userStateChangeHandler;
    protected StorageControler storageControler;
    protected Handler updateHandler;
    private UpdateRunnable updateRunnable;

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
        mUserAlcoState = UserStateProvider.getUserState();
    }

    public void loadUserStateFromFile() {
        Gson gson = new Gson();
        UserAlcoState state = gson.fromJson((String) storageControler.readObjectData(Constants.USER_STATE_STORAGE_FILE), UserAlcoState.class);
        if(state != null) {
            UserStateProvider.setUserState(state);
        }
    }

    public void saveUserStateToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(mUserAlcoState);
        storageControler.saveObjectData(json, Constants.USER_STATE_STORAGE_FILE);
    }

    private void updateUserStateData() {
        userStateChangeHandler.processAlcohol(new Date());
        EventBus.getDefault().post(new UpdateEvent(mUserAlcoState));
        updateHandler.postDelayed(updateRunnable, UPDATE_DELAY);
    }

    public void onEvent(DrinkEvent event){
        userStateChangeHandler.onDrink(event.getDrink(), new Date());
    }

    public void onEvent(ResetDrinkState event){
        UserStateProvider.resetUserState(mUserAlcoState);
        EventBus.getDefault().post(new UpdateEvent(mUserAlcoState));
    }

    @Override
    public void onCreate() {

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
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
