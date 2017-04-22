package com.corellidev.alcotester.presenter.dagger;

import com.corellidev.alcotester.view.MainActivity;
import com.corellidev.alcotester.view.utils.StatusToEditPagerAdapter;

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
