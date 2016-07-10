package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.UserAlcoState;

import java.util.Date;

public class UserStateCalculator {

    // based on: https://pl.wikipedia.org/wiki/Zawarto%C5%9B%C4%87_alkoholu_we_krwi#Wz.C3.B3r_Erika_Widmarka
    // https://pl.wikipedia.org/wiki/Standardowa_dawka_alkoholu

    private static final float ALCOHOL_DECAY_RATE_FOR_MEN = 11f;    //in grams for hour
    private static final float ALCOHOL_DECAY_RATE_FOR_WOMEN = 9f;    //in grams for hour
    private static final int MS_IN_ONE_HOUR = 60*60*1000;

    public static long timeMsToPerMile(float desiredPerMile, UserAlcoState userState) {
        float alcoholWeight = userState.getEthanolGramsInBlood();
        float desiredAlcoholWeight = AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams
                (desiredPerMile, userState);
        long timeToPerMile = getTimeLengthToProcessAlcohol((alcoholWeight - desiredAlcoholWeight), userState);
        if(timeToPerMile < 0)
            timeToPerMile = 0;
        return timeToPerMile;
    }

    public static float perMilesAtTime(Date when, UserAlcoState userState) {
        Date currentTime = new Date();
        long timeDiff = when.getTime() - currentTime.getTime();
        float newEthanolInBloodValue = getAlcoholWeightAfterDecay(timeDiff, userState);
        if(newEthanolInBloodValue < 0) newEthanolInBloodValue = 0;
        return AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams(newEthanolInBloodValue, userState);
    }

    public static float drinkLimitInGrams(Date timeLimit, float perMileLimit, UserAlcoState userState) {
        float gramLimit = AlcoholGramsToPerMileConverter.alcoholGramsForPerMile(perMileLimit, userState);
        float alcoholDecayed = 0;
        long timeDiff = timeLimit.getTime() - new Date().getTime();
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            alcoholDecayed = timeDiff * ALCOHOL_DECAY_RATE_FOR_MEN / MS_IN_ONE_HOUR;
        } else {
            alcoholDecayed = timeDiff * ALCOHOL_DECAY_RATE_FOR_WOMEN / MS_IN_ONE_HOUR;
        }
        return gramLimit + alcoholDecayed - userState.getEthanolGramsInBlood();
    }

    public static long getTimeLengthToProcessAlcohol(float alcoholWeight, UserAlcoState userState) {
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            return (long)(alcoholWeight * MS_IN_ONE_HOUR / ALCOHOL_DECAY_RATE_FOR_MEN);
        } else {
            return (long)(alcoholWeight * MS_IN_ONE_HOUR / ALCOHOL_DECAY_RATE_FOR_WOMEN);
        }
    }

    public static float getAlcoholWeightAfterDecay(long timeSinceLastUpdate, UserAlcoState userState) {
        float currentAlcoholWeight;
        if(userState.getSex() == UserAlcoState.Sex.MALE) {
            currentAlcoholWeight = userState.getEthanolGramsInBlood() - (timeSinceLastUpdate * ALCOHOL_DECAY_RATE_FOR_MEN / MS_IN_ONE_HOUR);
        } else {
            currentAlcoholWeight = userState.getEthanolGramsInBlood() - (timeSinceLastUpdate * ALCOHOL_DECAY_RATE_FOR_WOMEN / MS_IN_ONE_HOUR);
        }
        if(currentAlcoholWeight < 0) currentAlcoholWeight = 0;
        return currentAlcoholWeight;
    }
}