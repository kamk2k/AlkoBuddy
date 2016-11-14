package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.view.CreateDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public class CreateDrinkPresenterImpl implements CreateDrinkPresenter {

    private CreateDrinkView createDrinkView;

    @Override
    public void setMVPView(CreateDrinkView mvpView) {
        this.createDrinkView = mvpView;
    }

    @Override
    public void onDrinkContentChanged(DrinkItem drinkItem) {

    }


}
