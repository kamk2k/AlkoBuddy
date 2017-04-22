package com.corellidev.alcotester.presenter.dagger;

import com.corellidev.alcotester.view.EditDrinkFragment;
import com.corellidev.alcotester.view.MainActivity;
import com.corellidev.alcotester.view.PickerFragment;
import com.corellidev.alcotester.view.StatusFragment;

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
    void inject(EditDrinkFragment editDrinkFragment);
    MainActivityComponent plus(MainActivityModule mainActivityModule);

}
