package com.kamk2k.alkobuddy.presenter.dagger;

import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PC on 2015-11-11.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    StatusToCreatePagerAdapter provideStatusToCreatePagerAdapter();
}
