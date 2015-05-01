package com.kamk2k.alkobuddy.presenter.utils;

/**
 * Created by PC on 2015-05-01.
 */
public interface MVPActivityPresenter extends MVPPresenter {
    void onCreate();
    void onDestroy();
    void onStart();
    void onStop();
    void onPause();
    void onResume();
}
