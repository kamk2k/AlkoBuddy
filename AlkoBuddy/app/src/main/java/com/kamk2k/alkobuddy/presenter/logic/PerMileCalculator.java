package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;

import java.util.Date;

/**
 * Created by PC on 2015-02-25.
 */
public class PerMileCalculator {

    private UserAlcoState userState;

    public PerMileCalculator(UserAlcoState userState) {
        this.userState = userState;
    }

    public void drink(DrinkItem drink, Date currentTime) {
        addAlcoholGramsFromDrink(drink);
        processAlco(currentTime);
    }

    private void addAlcoholGramsFromDrink(DrinkItem drink) {
        float alcoholWeight = userState.getEthanolGramsInBlood() + AlcoholInDrinkCalculator
                .calculateAlcoholWeightInDrink
                (drink);
        userState.setEthanolGramsInBlood(alcoholWeight);
    }

    public void processAlco(Date currentTime) {
        calculateAlcoholDecay(currentTime);
        updateAlcoholPerMiles();
        calculateCurrentTimeToSober();
    }

    public void calculateAlcoholDecay(Date currentTime) {
        long timeSinceLastUpdate = currentTime.getTime() - userState.getLastUpdate().getTime();
        float newEthanolsInBloodValue = UserStateCalculator.getAlcoholWeightAfterDecay(timeSinceLastUpdate, userState);
        //to avoid negative values
        userState.setEthanolGramsInBlood(newEthanolsInBloodValue);
        userState.setLastUpdate(currentTime);
    }

    public void updateAlcoholPerMiles() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        userState.setCurrentPerMile((AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams(alcoholWeight, userState)));
    }

    public void calculateCurrentTimeToSober() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        long timeToSober = UserStateCalculator.getTimeLengthToProcessAlcohol(alcoholWeight, userState);
        userState.setTimeToSoberInMs(timeToSober);
    }

}
