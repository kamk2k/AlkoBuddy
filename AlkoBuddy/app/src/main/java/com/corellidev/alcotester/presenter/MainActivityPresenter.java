package com.corellidev.alcotester.presenter;

import android.content.SharedPreferences;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.presenter.utils.MVPActivityPresenter;
import com.corellidev.alcotester.view.MainActivityView;
import com.corellidev.alcotester.view.utils.DrinkListItemSelector;

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
