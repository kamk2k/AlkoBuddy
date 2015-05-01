package com.kamk2k.alkobuddy.model.events;

import com.kamk2k.alkobuddy.model.DrinkItem;

/**
 * Created by PC on 2015-04-12.
 */
public class DrinkEvent {
    private DrinkItem drink;

    public DrinkEvent(DrinkItem drink) {
        this.drink = drink;
    }

    public DrinkItem getDrink() {
        return drink;
    }

    public void setDrink(DrinkItem drink) {
        this.drink = drink;
    }
}
