package com.kamk2k.alkobuddy.presenter.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.CreateDrinkPresenter;
import com.kamk2k.alkobuddy.presenter.CreateDrinkPresenterImpl;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenterImpl;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenterImpl;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;

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
    FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Singleton
    @Provides
    UserAlcoState provideUserAlcoState() {
        return UserStateProvider.getUserState();
    }

    @Singleton
    @Provides
    StatusFragmentPresenter provideStatusFragmentPresenter(UserAlcoState userAlcoState, Context context) {
        return new StatusFragmentPresenterImpl(userAlcoState, context);
    }

    @Singleton
    @Provides
    UserStateChangeHandler providePerMileCalculator(UserAlcoState userAlcoState, StatusFragmentPresenter statusFragmentPresenter) {
        return new UserStateChangeHandler(userAlcoState, statusFragmentPresenter);
    }

    @Singleton
    @Provides
    Handler provideHandler() {
        return new Handler();
    }

    @Singleton
    @Provides
    MainActivityPresenter provideMainActivityPresenter(UserStateChangeHandler userStateChangeHandler,
                                                       Handler updateHandler, CreateDrinkPresenter createDrinkPresenter,
                                                       Context context) {
        return new MainActivityPresenterImpl(userStateChangeHandler, updateHandler, createDrinkPresenter, context);
    }

    @Singleton
    @Provides
    CreateDrinkPresenter provideCreateDrinkPresenter() {
        return new CreateDrinkPresenterImpl();
    }

}
