package com.kamk2k.alkobuddy.presenter.dagger;

import android.app.Application;

import com.kamk2k.alkobuddy.view.MainActivity;
import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PC on 2015-11-11.
 */
@Singleton
@Component(modules = {
        PresentersModule.class,
    })
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    MainActivityComponent newMainActivityComponent(ApplicationModule applicationModule);
}
