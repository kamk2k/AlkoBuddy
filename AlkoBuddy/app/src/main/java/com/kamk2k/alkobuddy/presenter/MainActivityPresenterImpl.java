package com.kamk2k.alkobuddy.presenter;

import android.os.Handler;
import android.util.Log;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;

import java.util.Date;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by PC on 2015-02-25.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final int UPDATE_DELAY = 10000;

    protected UserStateChangeHandler userStateChangeHandler;
    // TODO: 23.07.16 delegate update handling to external class
    protected Handler updateHandler;
    private UpdateRunnable updateRunnable;
    private Realm realm;

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
    public MainActivityPresenterImpl(UserStateChangeHandler userStateChangeHandler, Handler updateHandler) {
        this.userStateChangeHandler = userStateChangeHandler;
        this.updateHandler = updateHandler;
        realm = Realm.getDefaultInstance();
        updateRunnable = new UpdateRunnable();
        userStateChangeHandler.setUserState(UserStateProvider.getUserState());
    }

    public void loadUserStateFromRealm() {
        UserAlcoState state = realm.where(UserAlcoState.class).findFirst();
        if(state != null) {
            userStateChangeHandler.setUserState(realm.copyFromRealm(state));
        }
    }

    public void saveUserStateToRealm() {
        realm.beginTransaction();
        realm.delete(UserAlcoState.class);
        realm.copyToRealm(userStateChangeHandler.getUserState());
        realm.commitTransaction();
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
        realm.close();
    }

    @Override
    public void onStart() {
        loadUserStateFromRealm();
        updateUserStateData();
    }

    @Override
    public void onStop() {
        saveUserStateToRealm();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
