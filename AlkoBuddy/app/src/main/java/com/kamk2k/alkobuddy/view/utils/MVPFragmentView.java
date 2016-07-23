package com.kamk2k.alkobuddy.view.utils;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;

/**
 * Created by PC on 2015-05-01.
 */
public abstract class MVPFragmentView extends Fragment implements MVPView{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectFragment(App.getApplicationComponent());
    }

    public abstract void injectFragment(ApplicationComponent component);

}
