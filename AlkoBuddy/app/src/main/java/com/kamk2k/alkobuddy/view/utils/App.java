package com.kamk2k.alkobuddy.view.utils;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.PresentersModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by PC on 2015-11-21.
 */
public class App extends Application {

    private static Application INSTANCE;
    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        // TODO: 24.07.16 initialize stetho only in debug builds
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .presentersModule(new PresentersModule(this))
                .build();
    }

    public static Application getApplication() {
        if(INSTANCE == null) {
            throw new AssertionError("Application instance is null");
        }
        return INSTANCE;
    }

    public static ApplicationComponent getApplicationComponent() {
        if(mApplicationComponent == null) {
            throw new AssertionError("ApplicationComponent instance is null");
        }
        return mApplicationComponent;
    }
}
