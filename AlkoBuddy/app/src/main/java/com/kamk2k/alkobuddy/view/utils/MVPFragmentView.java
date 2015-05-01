package com.kamk2k.alkobuddy.view.utils;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;

/**
 * Created by PC on 2015-05-01.
 */
public class MVPFragmentView extends Fragment{

    protected MVPFragmentPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
