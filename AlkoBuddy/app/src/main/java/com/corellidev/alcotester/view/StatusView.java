package com.corellidev.alcotester.view;

import com.corellidev.alcotester.view.utils.MVPView;

/**
 * Created by kksiazek on 22.07.16.
 */
public interface StatusView extends MVPView{
    void displayPerMile(float perMile);
    void displayTodayTimeToSoberText(String timeToSoberText);
    void displayTomorrowTimeToSoberText(String timeToSoberText);
    void displayLaterThanTomorrowTimeToSoberText(String timeToSoberText);
    void displaySoberTime(int hours, int minutes);
    void displayOver24hSoberTime();
}
