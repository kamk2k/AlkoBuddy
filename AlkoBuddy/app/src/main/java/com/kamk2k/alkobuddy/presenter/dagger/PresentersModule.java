package com.kamk2k.alkobuddy.presenter.dagger;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;
import com.kamk2k.alkobuddy.model.UserStateProvider;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenterImpl;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenterImpl;
import com.kamk2k.alkobuddy.presenter.logic.UserStateChangeHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

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
    StatusFragmentPresenter provideStatusFragmentPresenter(Context context, UserAlcoState userAlcoState) {
        return new StatusFragmentPresenterImpl(context, userAlcoState);
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

    @Provides
    OrderedRealmCollection<DrinkItem> provideDrinkItemsData() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<DrinkItem> results = realm.where(DrinkItem.class).findAll();
        realm.close();
        return results;
    }

    @Singleton
    @Provides
    MainActivityPresenter provideMainActivityPresenter(UserStateChangeHandler userStateChangeHandler,
                                                       Handler updateHandler) {
        return new MainActivityPresenterImpl(userStateChangeHandler, updateHandler);
    }
}
