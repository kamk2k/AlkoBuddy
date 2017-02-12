package com.kamk2k.alkobuddy.presenter;

import android.content.SharedPreferences;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;
import com.kamk2k.alkobuddy.view.MainActivityView;
import com.kamk2k.alkobuddy.view.utils.DrinkListItemSelector;

/**
 * Created by kksiazek on 29.11.15.
 */
public interface MainActivityPresenter extends MVPActivityPresenter {
    void setMVPView(MainActivityView mainActivityView);
    void setDrinkListItemSelector(DrinkListItemSelector drinkListItemSelector);

    void drinkClicked(DrinkItem drinkItem);
    void drinkRemoveClicked(DrinkItem drinkItem);
    void resetDrinkState();
    void addNewDrinkClicked();

    void clearDrinkListSelection();
    void selectDisplayedDrinkOnList();
    void updateUserDataFromPreferences(SharedPreferences sharedPreferences);
}
