package com.kamk2k.alkobuddy.presenter.dagger;

import com.kamk2k.alkobuddy.view.MainActivity;
import com.kamk2k.alkobuddy.view.PickerFragment;
import com.kamk2k.alkobuddy.view.StatusFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PC on 2015-11-11.
 */
@Singleton
@Component(modules = {
        PresentersModule.class
    })
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(StatusFragment statusFragment);
    void inject(PickerFragment pickerFragment);
    MainActivityComponent plus(MainActivityModule mainActivityModule);

}
