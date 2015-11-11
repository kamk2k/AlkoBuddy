package com.kamk2k.alkobuddy.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.kamk2k.alkobuddy.Constants;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.model.events.ResetDrinkState;
import com.kamk2k.alkobuddy.model.events.UpdateEvent;
import com.kamk2k.alkobuddy.presenter.logic.PerMileCalculator;
import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;
import com.kamk2k.alkobuddy.presenter.utils.StorageControler;
import com.kamk2k.alkobuddy.view.utils.MVPRetainWorkerFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityPresenter implements MVPActivityPresenter {

    private static UserAlcoState mUserAlcoState;

    private PerMileCalculator mPerMileCalculator;
    private StorageControler mStorageControler;

    public MainActivityPresenter(Context context) {
        mStorageControler = new StorageControler(context);
        mUserAlcoState = UserStateProvider.getUserState();
        mPerMileCalculator = new PerMileCalculator(mUserAlcoState);
    }

    public void loadUserStateFromFile() {
        Gson gson = new Gson();
        UserAlcoState state = gson.fromJson((String)mStorageControler.readObjectData(Constants.USER_STATE_STORAGE_FILE), UserAlcoState.class);
        if(state != null) {
            UserStateProvider.setUserState(state);
        }
    }

    public void saveUserStateToFile() {
        Gson gson = new Gson();
        String json = gson.toJson(mUserAlcoState);
        mStorageControler.saveObjectData(json, Constants.USER_STATE_STORAGE_FILE);
    }

    public void onEvent(ResetDrinkState event){
        UserStateProvider.resetUserState(mUserAlcoState);
        EventBus.getDefault().post(new UpdateEvent());
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
