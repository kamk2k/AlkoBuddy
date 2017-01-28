package com.kamk2k.alkobuddy.view;

import com.kamk2k.alkobuddy.view.utils.MVPView;

/**
 * Created by kksiazek on 22.07.16.
 */
public interface StatusView extends MVPView{
    void displayPerMile(float perMile);
    void displayTimeToSoberText(String timeToSoberText);
    void displaySoberTime(int hours, int minutes);
    void displayOver24hSoberTime();
}
