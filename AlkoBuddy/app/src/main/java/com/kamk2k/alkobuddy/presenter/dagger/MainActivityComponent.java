package com.kamk2k.alkobuddy.presenter.dagger;

import com.kamk2k.alkobuddy.view.MainActivity;
import com.kamk2k.alkobuddy.view.utils.StatusToEditPagerAdapter;

import dagger.Subcomponent;

/**
 * Created by PC on 2015-11-21.
 */
@ActivityScope
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
    StatusToEditPagerAdapter getStatusToEditPagerAdapter();
}
