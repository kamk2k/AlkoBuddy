package com.kamk2k.alkobuddy.view.utils;

import android.app.Application;

import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.PresentersModule;

import junit.framework.Assert;

/**
 * Created by PC on 2015-11-21.
 */
public class App extends Application {

    private static Application INSTANCE;
    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .presentersModule(new PresentersModule(this))
                .build();
    }

    public static Application getApplication() {
        if(INSTANCE == null) {
            throw new AssertionError("Application instance is null");
        }
        return INSTANCE;
    }

    public static ApplicationComponent getApplicationComponent() {
        if(mApplicationComponent == null) {
            throw new AssertionError("ApplicationComponent instance is null");
        }
        return mApplicationComponent;
    }
}
