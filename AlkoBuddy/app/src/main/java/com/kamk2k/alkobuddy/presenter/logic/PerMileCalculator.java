package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.events.DrinkEvent;
import com.kamk2k.alkobuddy.model.events.ProcessAlkoEvent;

import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class PerMileCalculator {

    // based on: http://en.wikipedia.org/wiki/Blood_alcohol_content

    private static final float WATER_K_FACTOR_FOR_MEN = 0.7f;
    private static final float WATER_K_FACTOR_FOR_WOMEN = 0.6f;
    private static final float ALCOHOL_VOLUME_TO_WEIGHT_RATIO = 1.25f;  // ml to grams
    private static final float ALCOHOL_DECAY_RATE_FOR_MEN = 11f;    //in grams for hour
    private static final float ALCOHOL_DECAY_RATE_FOR_WOMEN = 9f;    //in grams for hour
    private static final int MS_IN_ONE_HOUR = 60*60*1000;


    private UserAlcoState userState;

    public PerMileCalculator(UserAlcoState userState) {
        this.userState = userState;
        EventBus.getDefault().registerSticky(this);
    }

    public long timeMsToPerMile(float desiredPerMile) {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        float desiredAlcoholWeight = alcoholPerMilesForGrams(desiredPerMile);
        long timeToPerMile = 0;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            timeToPerMile = (long)((alcoholWeight - desiredAlcoholWeight) * MS_IN_ONE_HOUR * ALCOHOL_DECAY_RATE_FOR_MEN);
        } else {
            timeToPerMile = (long)((alcoholWeight - desiredAlcoholWeight) * ALCOHOL_DECAY_RATE_FOR_WOMEN * MS_IN_ONE_HOUR);
        }
        if(timeToPerMile < 0)
            timeToPerMile = 0;
        return timeToPerMile;
    }

    public float perMilesAtTime(Date when) {
        Date currentTime = new Date();
        long timeDiff = when.getTime() - currentTime.getTime();
        float newEthanolInBloodValue;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            newEthanolInBloodValue = userState.getEthanolGramsInBlood() - (timeDiff * ALCOHOL_DECAY_RATE_FOR_MEN / MS_IN_ONE_HOUR);
        } else {
            newEthanolInBloodValue = userState.getEthanolGramsInBlood() - (timeDiff * ALCOHOL_DECAY_RATE_FOR_WOMEN / MS_IN_ONE_HOUR);
        }
        //to avoid negative values
        if(newEthanolInBloodValue < 0) newEthanolInBloodValue = 0;
        return alcoholPerMilesForGrams(newEthanolInBloodValue);
    }

    public float drinkLimitInGrams(Date timeLimit, float perMileLimit) {
        float gramLimit = alcoholGramsForPerMile(perMileLimit);
        float alcoholDecayed = 0;
        long timeDiff = timeLimit.getTime() - new Date().getTime();
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            alcoholDecayed = timeDiff * ALCOHOL_DECAY_RATE_FOR_MEN / MS_IN_ONE_HOUR;
        } else {
            alcoholDecayed = timeDiff * ALCOHOL_DECAY_RATE_FOR_WOMEN / MS_IN_ONE_HOUR;
        }
        return gramLimit + alcoholDecayed - userState.getEthanolGramsInBlood();
    }

    private void drink(DrinkItem drink) {
        calculateAlcoholDecay();
        float alcoholWeight = userState.getEthanolGramsInBlood() + calculateAlcoholWeightInDrink(drink);
        userState.setEthanolGramsInBlood(alcoholWeight);
        calculateCurrentPerMiles();
        calculateCurrentTimeToSober();
    }

    private void processAlco() {
        calculateAlcoholDecay();
        calculateCurrentPerMiles();
        calculateCurrentTimeToSober();
    }

    private void calculateAlcoholDecay() {
        Date currentTime = new Date();
        long timeSinceLastUpdate = currentTime.getTime() - userState.getLastUpdate().getTime();
        float newEthanolsInBloodValue;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            newEthanolsInBloodValue = userState.getEthanolGramsInBlood() - (timeSinceLastUpdate * ALCOHOL_DECAY_RATE_FOR_MEN / MS_IN_ONE_HOUR);
        } else {
            newEthanolsInBloodValue = userState.getEthanolGramsInBlood() - (timeSinceLastUpdate * ALCOHOL_DECAY_RATE_FOR_WOMEN / MS_IN_ONE_HOUR);
        }
        //to avoid negative values
        if(newEthanolsInBloodValue < 0) newEthanolsInBloodValue = 0;
        userState.setEthanolGramsInBlood(newEthanolsInBloodValue);
        userState.setLastUpdate(currentTime);
    }

    private void calculateCurrentPerMiles() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        userState.setCurrentPerMile((alcoholPerMilesForGrams(alcoholWeight)));
    }

    private void calculateCurrentTimeToSober() {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        long timeToSober = 0;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            timeToSober = (long)(alcoholWeight * MS_IN_ONE_HOUR * ALCOHOL_DECAY_RATE_FOR_MEN);
        } else {
            timeToSober = (long)(alcoholWeight * ALCOHOL_DECAY_RATE_FOR_WOMEN * MS_IN_ONE_HOUR);
        }
        userState.setTimeToSoberInMs(timeToSober);
    }

    private float calculateAlcoholWeightInDrink(DrinkItem drink) {
        return (drink.getBeerVolume() * drink.getBeerPercentage() +
                drink.getWineVolume() * drink.getWinePercentage() +
                drink.getVodkaVolume() * drink.getVodkaPercentage() +
                drink.getCustomVolume() * drink.getCustomPercentage()) /
                ALCOHOL_VOLUME_TO_WEIGHT_RATIO;
    }

    private float alcoholGramsForPerMile(float perMile) {
        float calculatedWeight = 0;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            calculatedWeight = WATER_K_FACTOR_FOR_MEN * userState.getWeight() * perMile;
        } else {
            calculatedWeight = WATER_K_FACTOR_FOR_WOMEN * userState.getWeight() * perMile;
        }
        return calculatedWeight;
    }

    private float alcoholPerMilesForGrams(float grams) {
        float perMile = 0;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            perMile = grams / (WATER_K_FACTOR_FOR_MEN * userState.getWeight());
        } else {
            perMile = grams / (WATER_K_FACTOR_FOR_WOMEN * userState.getWeight());
        }
        return perMile;
    }

    public void onEvent(DrinkEvent event){
        drink(event.getDrink());
    }

    public void onEvent(ProcessAlkoEvent event){
        processAlco();
    }
}
