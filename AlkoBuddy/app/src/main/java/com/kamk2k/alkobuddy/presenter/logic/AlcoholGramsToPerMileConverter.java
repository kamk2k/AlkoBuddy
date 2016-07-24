package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.UserAlcoState;

public class AlcoholGramsToPerMileConverter {

    private static final float WATER_K_FACTOR_FOR_MEN = 0.7f;
    private static final float WATER_K_FACTOR_FOR_WOMEN = 0.6f;

    public static float alcoholGramsForPerMile(float perMile, UserAlcoState userState) {
        if(userState.getSex() == UserAlcoState.SEX_MALE) {
            return WATER_K_FACTOR_FOR_MEN * userState.getWeight() * perMile;
        } else {
            return WATER_K_FACTOR_FOR_WOMEN * userState.getWeight() * perMile;
        }
    }

    public static float alcoholPerMilesForGrams(float grams, UserAlcoState userState) {
        if(userState.getSex() == UserAlcoState.SEX_MALE) {
            return grams / (WATER_K_FACTOR_FOR_MEN * userState.getWeight());
        } else {
            return grams / (WATER_K_FACTOR_FOR_WOMEN * userState.getWeight());
        }
    }
}