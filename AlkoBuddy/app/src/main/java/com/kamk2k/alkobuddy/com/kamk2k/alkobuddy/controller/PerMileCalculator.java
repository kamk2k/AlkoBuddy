package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.UserAlcoState;

/**
 * Created by PC on 2015-02-25.
 */
public class PerMileCalculator {

    private static final float WATER_K_FACTOR_FOR_MEN = 0.7f;
    private static final float WATER_K_FACTOR_FOR_WOMEN = 0.6f;
    private static final float ALCOHOL_VOLUME_TO_WEIGHT_RATIO = 1.25f;  // ml to grams
    private static final float ALCOHOL_DECAY_RATE_FOR_MEN = 11f;    //in grams for hour
    private static final float ALCOHOL_DECAY_RATE_FOR_WOMEN = 9f;    //in grams for hour
    private static final int MS_IN_ONE_HOUR = 60*60*1000;    //in grams for hour


    private UserAlcoState userState;

    public PerMileCalculator(UserAlcoState userState) {
        this.userState = userState;
    }

    public void drink(DrinkItem drink) {
        // implementacja wzoru Erika Widmarka

        float alcoholWeight = calculateAlcoholWeight(drink);
        float perMile = 0;
        long timeToSober = 0;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            perMile = alcoholWeight / (WATER_K_FACTOR_FOR_MEN * userState.getWeight());
            timeToSober = (long)((alcoholWeight * MS_IN_ONE_HOUR) / ALCOHOL_DECAY_RATE_FOR_MEN);
        } else {
            perMile = alcoholWeight / (WATER_K_FACTOR_FOR_WOMEN * userState.getWeight());
            timeToSober = (long)(alcoholWeight * ALCOHOL_DECAY_RATE_FOR_WOMEN * MS_IN_ONE_HOUR);
        }
        userState.setCurrentPerMile(perMile);
        userState.setTimeToSoberInMs(timeToSober);
    }

    private float calculateAlcoholWeight(DrinkItem drink) {
        return (drink.getBeerVolume() * drink.getBeerPercentage() +
                drink.getWineVolume() * drink.getWinePercentage() +
                drink.getVodkaVolume() * drink.getVodkaPercentage() +
                drink.getCustomVolume() * drink.getCustomPercentage()) /
                ALCOHOL_VOLUME_TO_WEIGHT_RATIO;
    }
}
