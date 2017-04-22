package com.corellidev.alcotester.presenter;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.presenter.utils.MVPFragmentPresenter;
import com.corellidev.alcotester.view.EditDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface EditDrinkPresenter extends MVPFragmentPresenter{
    void setMVPView(EditDrinkView mvpView);
    void changeSelectedDrink(DrinkItem drinkItem);
    void selectNewDrink(DrinkItem drinkItem);
    DrinkItem getCurrentDrinkItem();
}
