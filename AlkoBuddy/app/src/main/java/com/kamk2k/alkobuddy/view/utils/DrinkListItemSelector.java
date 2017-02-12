package com.kamk2k.alkobuddy.view.utils;

import com.kamk2k.alkobuddy.model.DrinkItem;

/**
 * Created by kksiazek on 12.02.17.
 */

public interface DrinkListItemSelector {
    void selectItem(DrinkItem item);
    void clearSelection();
}
