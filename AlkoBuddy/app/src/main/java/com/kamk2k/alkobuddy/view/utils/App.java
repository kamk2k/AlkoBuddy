package com.kamk2k.alkobuddy.view.utils;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.kamk2k.alkobuddy.BaseRealmModule;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.PresentersModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import jonathanfinerty.once.Once;

/**
 * Created by PC on 2015-11-21.
 */
public class App extends Application {

    public static final String BASE_REALM_NAME = "BASE_REALM";
    public static final int REALM_SCHEMA_VERSION = 0;
    private static Application INSTANCE;
    private static ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        INSTANCE = this;
        super.onCreate();
        Once.initialise(this);
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(BASE_REALM_NAME)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(REALM_SCHEMA_VERSION)
                .modules(new BaseRealmModule())
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
        Fabric.with(this, new Crashlytics());
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
