package com.kamk2k.alkobuddy.presenter.dagger;

import android.support.v4.app.FragmentActivity;

import com.kamk2k.alkobuddy.view.MainActivity;
import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by PC on 2015-11-21.
 */
@ActivityScope
@Subcomponent(modules = {ApplicationModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
