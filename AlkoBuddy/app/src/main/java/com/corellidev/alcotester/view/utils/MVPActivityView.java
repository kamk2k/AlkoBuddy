package com.corellidev.alcotester.view.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.corellidev.alcotester.presenter.dagger.ApplicationComponent;

/**
 * Created by PC on 2015-05-01.
 */
public abstract class MVPActivityView extends AppCompatActivity implements MVPView{

    @Override
    public void onCreate(Bundle saved){
        super.onCreate(saved);
        injectActivity(App.getApplicationComponent());
    }

    public abstract void injectActivity(ApplicationComponent component);

}
