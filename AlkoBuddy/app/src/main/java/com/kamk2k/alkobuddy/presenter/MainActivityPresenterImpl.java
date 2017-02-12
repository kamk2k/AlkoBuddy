package com.kamk2k.alkobuddy.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;
import com.kamk2k.alkobuddy.view.MainActivityView;

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
    private MainActivityView mainActivityView;
    private CreateDrinkPresenter createDrinkPresenter;
    private Context context;

    @Override
    public void setMVPView(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void drinkClicked(DrinkItem drinkItem) {
        if(mainActivityView.isStatusFragmentDisplayed()) {
            userStateChangeHandler.onDrink(drinkItem, new Date());
        } else if(mainActivityView.isCreateDrinkFragmentDisplayed()) {
            createDrinkPresenter.changeSelectedDrink(drinkItem);
        }
    }

    @Override
    public void drinkRemoveClicked(DrinkItem drinkItem) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(DrinkItem.class)
                .equalTo(DrinkItem.ID_FIELD_NAME, drinkItem.getId())
                .findAll()
                .deleteAllFromRealm();
        realm.commitTransaction();
        if(realm.where(DrinkItem.class).findAll().isEmpty()) {
            createDrinkPresenter.changeSelectedDrink(null);
        } else {
            createDrinkPresenter.changeSelectedDrink(realm.where(DrinkItem.class).findFirst());
        }
        realm.close();
    }

    @Override
    public void resetDrinkState() {
        userStateChangeHandler.resetUserState();
    }

    @Override
    public void addNewDrinkClicked() {
        if(mainActivityView.isStatusFragmentDisplayed()) {
            mainActivityView.switchToCreateFragment();
            DrinkItem drinkItem = createEmptyDrinkItem();
            createDrinkPresenter.selectNewDrink(drinkItem);
        } else if(mainActivityView.isCreateDrinkFragmentDisplayed()) {
            DrinkItem drinkItem = createEmptyDrinkItem();
            createDrinkPresenter.selectNewDrink(drinkItem);
        }
    }

    @Override
    public void updateUserDataFromPreferences(SharedPreferences sharedPreferences) {
        userStateChangeHandler.getUserState().setWeight(Integer.parseInt(sharedPreferences.getString(context.getString(R.string.weight_preference_key), "70")));
        String sex = sharedPreferences.getString(context.getString(R.string.sex_preference_key), context.getString(R.string.male));
        if(sex.equals(context.getString(R.string.male))) {
            userStateChangeHandler.getUserState().setSex(UserAlcoState.SEX_MALE);
        } else if(sex.equals(context.getString(R.string.female))) {
            userStateChangeHandler.getUserState().setSex(UserAlcoState.SEX_FEMALE);
        }
        userStateChangeHandler.processAlcohol(new Date());
    }

    @NonNull
    private DrinkItem createEmptyDrinkItem() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        int nextID = 1;
        if(!realm.where(DrinkItem.class).findAll().isEmpty()) {
            nextID = (realm.where(DrinkItem.class).max("id").intValue()) + 1;
        }
        DrinkItem drinkItem = DrinkItem.getDefaultItem(nextID);
        realm.copyToRealmOrUpdate(drinkItem);
        realm.commitTransaction();
        realm.close();
        return drinkItem;
    }

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            updateUserStateData();
        }
    }

    @Inject
    public MainActivityPresenterImpl(UserStateChangeHandler userStateChangeHandler, Handler updateHandler,
                                     CreateDrinkPresenter createDrinkPresenter, Context context) {
        this.userStateChangeHandler = userStateChangeHandler;
        this.updateHandler = updateHandler;
        this.createDrinkPresenter = createDrinkPresenter;
        this.context = context;
        updateRunnable = new UpdateRunnable();
        userStateChangeHandler.setUserState(UserStateProvider.getUserState());
    }

    public void loadUserStateFromRealm() {
        Realm realm = Realm.getDefaultInstance();
        UserAlcoState state = realm.where(UserAlcoState.class).findFirst();
        if(state != null) {
            userStateChangeHandler.setUserState(realm.copyFromRealm(state));
        }
        realm.close();
    }

    public void saveUserStateToRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(UserAlcoState.class);
        realm.copyToRealm(userStateChangeHandler.getUserState());
        realm.commitTransaction();
        realm.close();
    }

    private void updateUserStateData() {
        userStateChangeHandler.processAlcohol(new Date());
        updateHandler.postDelayed(updateRunnable, UPDATE_DELAY);
    }

    private void stopUpdatingUserState() {
        updateHandler.removeCallbacks(updateRunnable);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
        loadUserStateFromRealm();
        updateUserStateData();
    }

    @Override
    public void onStop() {
        saveUserStateToRealm();
        stopUpdatingUserState();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
