package com.kamk2k.alkobuddy.presenter.dagger;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PC on 2015-11-11.
 */

@Module
public class MainActivityModule {

    protected FragmentActivity mFragmentActivity;
    protected FragmentManager mFragmentManager;

    public MainActivityModule(FragmentActivity fragmentActivity) {
        mFragmentActivity = fragmentActivity;
        mFragmentManager = mFragmentActivity.getSupportFragmentManager();
    }

    @ActivityScope
    @Provides
    StatusToCreatePagerAdapter provideStatusToCreatePagerAdapter() {
        return new StatusToCreatePagerAdapter(mFragmentManager);
    }
}
