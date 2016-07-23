package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;

import java.util.Date;

/**
 * Created by PC on 2015-02-25.
 */
public class UserStateChangeHandler {

    private UserAlcoState userState;
    private StatusFragmentPresenter statusFragmentPresenter;

    public UserStateChangeHandler(UserAlcoState userState, StatusFragmentPresenter statusFragmentPresenter) {
        this.userState = userState;
        this.statusFragmentPresenter = statusFragmentPresenter;
    }

    public void onDrink(DrinkItem drink, Date currentTime) {
        subtractProcessedAlcohol(currentTime);
        addAlcoholGramsFromDrink(drink);
        updateAlcoholPerMiles();
        updateTimeToSober();
        statusFragmentPresenter.updateStatus(userState);
    }

    public UserAlcoState getUserState() {
        return userState;
    }

    public void setUserState(UserAlcoState userState) {
        this.userState = userState;
    }

    public void resetUserState() {
        UserStateProvider.resetUserState(userState);
        statusFragmentPresenter.updateStatus(userState);
    }

    private void addAlcoholGramsFromDrink(DrinkItem drink) {
        float alcoholWeight = userState.getEthanolGramsInBlood() + AlcoholInDrinkCalculator
                .calculateAlcoholWeightInDrink
                (drink);
        userState.setEthanolGramsInBlood(alcoholWeight);
    }

    public void processAlcohol(Date currentTime) {
        subtractProcessedAlcohol(currentTime);
        updateAlcoholPerMiles();
        updateTimeToSober();
        statusFragmentPresenter.updateStatus(userState);
    }

    private void subtractProcessedAlcohol(Date currentTime) {
        long timeSinceLastUpdate = currentTime.getTime() - userState.getLastUpdate().getTime();
        float newEthanolsInBloodValue = UserStateCalculator.getAlcoholWeightAfterTime(timeSinceLastUpdate, userState);
        userState.setEthanolGramsInBlood(newEthanolsInBloodValue);
        userState.setLastUpdate(currentTime);
    }

    private void updateAlcoholPerMiles() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        userState.setCurrentPerMile((AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams(alcoholWeight, userState)));
    }

    private void updateTimeToSober() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        long timeToSober = UserStateCalculator.getTimePeriodToProcessAlcohol(alcoholWeight, userState);
        userState.setTimeToSoberInMs(timeToSober);
    }

}
