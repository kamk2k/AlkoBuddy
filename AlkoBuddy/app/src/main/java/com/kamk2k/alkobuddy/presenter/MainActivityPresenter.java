package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;

/**
 * Created by kksiazek on 29.11.15.
 */
public interface MainActivityPresenter extends MVPActivityPresenter {
    void drinkClicked(DrinkItem drinkItem);
    void resetDrinkState();
}
