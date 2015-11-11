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
public class ApplicationModule {

    private FragmentActivity mFragmentActivity;

    public ApplicationModule(FragmentActivity fragmentActivity) {
        mFragmentActivity = fragmentActivity;
    }

    @Provides
    StatusToCreatePagerAdapter provideStatusToCreatePagerAdapter(FragmentManager fragmentManager) {
        return new StatusToCreatePagerAdapter(fragmentManager);
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mFragmentActivity.getSupportFragmentManager();
    }
}
