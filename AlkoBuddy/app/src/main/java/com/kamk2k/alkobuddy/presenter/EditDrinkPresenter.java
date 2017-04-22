package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;
import com.kamk2k.alkobuddy.view.EditDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface EditDrinkPresenter extends MVPFragmentPresenter{
    void setMVPView(EditDrinkView mvpView);
    void changeSelectedDrink(DrinkItem drinkItem);
    void selectNewDrink(DrinkItem drinkItem);
    DrinkItem getCurrentDrinkItem();
}
