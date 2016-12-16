package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.view.CreateDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public class CreateDrinkPresenterImpl implements CreateDrinkPresenter {

    private DrinkItem selectedDrink;

    private CreateDrinkView createDrinkView;

    public CreateDrinkPresenterImpl() {
    }

    @Override
    public void setMVPView(CreateDrinkView mvpView) {
        this.createDrinkView = mvpView;
    }

    @Override
    public void changeSelectedDrink(DrinkItem drinkItem) {
        selectedDrink = drinkItem;
        createDrinkView.showDrink(drinkItem);
    }

    @Override
    public DrinkItem getCurrentDrinkItem() {
        return selectedDrink;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
