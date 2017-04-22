package com.corellidev.alcotester.view.utils;

import com.corellidev.alcotester.model.DrinkItem;

/**
 * Created by kksiazek on 12.02.17.
 */

public interface DrinkListItemSelector {
    void selectItem(DrinkItem item);
    void clearSelection();
}
