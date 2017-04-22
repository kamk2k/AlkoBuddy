package com.corellidev.alcotester.view;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.view.utils.MVPView;

/**
 * Created by kksiazek on 14.11.16.
 */

public interface EditDrinkView extends MVPView {
    void showLoading();
    void showDrink(DrinkItem drinkItem);
    void showEmptyView();
}
