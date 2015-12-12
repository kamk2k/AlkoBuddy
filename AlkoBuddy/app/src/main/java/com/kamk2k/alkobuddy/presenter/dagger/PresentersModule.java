package com.kamk2k.alkobuddy.presenter.dagger;

import android.app.Application;
import android.content.Context;

import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenterImpl;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenterImpl;
import com.kamk2k.alkobuddy.presenter.logic.PerMileCalculator;
import com.kamk2k.alkobuddy.presenter.utils.StorageControler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PC on 2015-11-21.
 */
@Module
public class PresentersModule {

    Application application;

    public PresentersModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    UserAlcoState provideUserAlcoState() {
        return UserStateProvider.getUserState();
    }

    @Singleton
    @Provides
    StorageControler provideStorageControler(Context context) {
        return new StorageControler(context);
    }

    @Singleton
    @Provides
    StatusFragmentPresenter provideStatusFragmentPresenter(Context context, UserAlcoState userAlcoState) {
        return new StatusFragmentPresenterImpl(context, userAlcoState);
    }

    @Singleton
    @Provides
    PerMileCalculator providePerMileCalculator(UserAlcoState userAlcoState) {
        return new PerMileCalculator(userAlcoState);
    }

    @Singleton
    @Provides
    MainActivityPresenter provideMainActivityPresenter(Context context) {
        return new MainActivityPresenterImpl(context);
    }
}
