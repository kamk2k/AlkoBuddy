package com.kamk2k.alkobuddy.view.utils;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;

/**
 * Created by PC on 2015-05-01.
 */
public class MVPActivityView extends ActionBarActivity {

    protected MVPActivityPresenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
