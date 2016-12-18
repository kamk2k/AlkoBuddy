package com.kamk2k.alkobuddy.view;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.view.utils.MVPView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface CreateDrinkView extends MVPView {
    void showLoading();
    void showDrink(DrinkItem drinkItem);
    void showEmptyView();
}
