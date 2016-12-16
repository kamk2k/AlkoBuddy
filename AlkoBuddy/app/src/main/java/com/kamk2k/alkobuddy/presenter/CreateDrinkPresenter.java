package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;
import com.kamk2k.alkobuddy.view.CreateDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface CreateDrinkPresenter extends MVPFragmentPresenter{
    void setMVPView(CreateDrinkView mvpView);
    void changeSelectedDrink(DrinkItem drinkItem);
    DrinkItem getCurrentDrinkItem();
}
