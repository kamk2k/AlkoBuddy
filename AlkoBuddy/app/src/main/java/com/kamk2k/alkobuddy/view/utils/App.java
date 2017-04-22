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
                .initialData(realm -> {
                    // TODO: 2017-04-22 provide drink photos
                    generateInitialDrinks(realm);
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

    private void generateInitialDrinks(Realm realm) {
        String largeBeerImgPath = "";
        String smallBeerImgPath = "";
        String wineGlassImgPath = "";
        String vodkaShotImgPath = "";
        DrinkItem largeBeer = new DrinkItem(0, "Large beer", largeBeerImgPath, 500,
                DrinkItem.DEFAULT_BEER_PERCENTAGE, 0, DrinkItem.DEFAULT_WINE_PERCENTAGE,
                0, DrinkItem.DEFAULT_VODKA_PERCENTAGE, 0, 0f);
        DrinkItem smallBeer = new DrinkItem(1, "Small beer", smallBeerImgPath, 330,
                DrinkItem.DEFAULT_BEER_PERCENTAGE, 0, DrinkItem.DEFAULT_WINE_PERCENTAGE,
                1, DrinkItem.DEFAULT_VODKA_PERCENTAGE, 0, 0f);
        DrinkItem wineGlass = new DrinkItem(2, "Glass of wine", wineGlassImgPath, 0,
                DrinkItem.DEFAULT_BEER_PERCENTAGE, 200, DrinkItem.DEFAULT_WINE_PERCENTAGE,
                0, DrinkItem.DEFAULT_VODKA_PERCENTAGE, 0, 0f);
        DrinkItem vodkaShot = new DrinkItem(3, "Vodka shot", vodkaShotImgPath, 0,
                DrinkItem.DEFAULT_BEER_PERCENTAGE, 0, DrinkItem.DEFAULT_WINE_PERCENTAGE,
                50, DrinkItem.DEFAULT_VODKA_PERCENTAGE, 0, 0f);
        realm.copyToRealmOrUpdate(largeBeer);
        realm.copyToRealmOrUpdate(smallBeer);
        realm.copyToRealmOrUpdate(wineGlass);
        realm.copyToRealmOrUpdate(vodkaShot);
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
