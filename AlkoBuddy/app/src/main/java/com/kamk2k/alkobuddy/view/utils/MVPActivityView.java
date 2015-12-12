package com.kamk2k.alkobuddy.view.utils;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;

/**
 * Created by PC on 2015-05-01.
 */
public abstract class MVPActivityView extends ActionBarActivity {

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        injectActivity(App.getApplicationComponent());
    }

    public abstract void injectActivity(ApplicationComponent component);

}
