package com.kamk2k.alkobuddy.view.utils;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.PresentersModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // TODO: 09.08.16 make generator for default drinks
                        realm.copyToRealm(DrinkItem.generateMock());
                    }
                })
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
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
