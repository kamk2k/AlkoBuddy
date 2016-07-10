package com.kamk2k.alkobuddy.presenter.logic;

import com.kamk2k.alkobuddy.model.DrinkItem;

public class AlcoholInDrinkCalculator {

    private static final float ALCOHOL_DENSITY_G_PER_ML = 0.8f;  // g/ml

    public static float calculateAlcoholWeightInDrink(DrinkItem drink) {
        return (drink.getBeerVolume() * drink.getBeerPercentage() +
                drink.getWineVolume() * drink.getWinePercentage() +
                drink.getVodkaVolume() * drink.getVodkaPercentage() +
                drink.getCustomVolume() * drink.getCustomPercentage()) *
                ALCOHOL_DENSITY_G_PER_ML;
    }
}