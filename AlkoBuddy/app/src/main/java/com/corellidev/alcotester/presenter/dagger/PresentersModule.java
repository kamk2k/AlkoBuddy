package com.corellidev.alcotester.presenter.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.corellidev.alcotester.model.UserAlcoState;
import com.corellidev.alcotester.model.UserStateProvider;
import com.corellidev.alcotester.presenter.EditDrinkPresenter;
import com.corellidev.alcotester.presenter.EditDrinkPresenterImpl;
import com.corellidev.alcotester.presenter.MainActivityPresenter;
import com.corellidev.alcotester.presenter.MainActivityPresenterImpl;
import com.corellidev.alcotester.presenter.StatusFragmentPresenter;
import com.corellidev.alcotester.presenter.StatusFragmentPresenterImpl;
import com.corellidev.alcotester.presenter.logic.UserStateChangeHandler;

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
                                                       Handler updateHandler, EditDrinkPresenter editDrinkPresenter,
                                                       Context context) {
        return new MainActivityPresenterImpl(userStateChangeHandler, updateHandler, editDrinkPresenter, context);
    }

    @Singleton
    @Provides
    EditDrinkPresenter provideCreateDrinkPresenter() {
        return new EditDrinkPresenterImpl();
    }

}
