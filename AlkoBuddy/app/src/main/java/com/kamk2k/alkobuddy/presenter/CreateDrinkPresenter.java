package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.utils.MVPPresenter;
import com.kamk2k.alkobuddy.view.CreateDrinkView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface CreateDrinkPresenter extends MVPPresenter {
    void setMVPView(CreateDrinkView mvpView);
    void onDrinkContentChanged(DrinkItem drinkItem);
}
