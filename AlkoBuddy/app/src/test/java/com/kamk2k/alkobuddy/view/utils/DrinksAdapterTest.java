package com.kamk2k.alkobuddy.view.utils;

import android.content.Context;

import com.kamk2k.alkobuddy.BuildConfig;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.utils.MockRealm;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by kksiazek on 07.08.16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class})
public class DrinksAdapterTest {

    @Mock
    Context context;
    @Mock
    MainActivityPresenter mainActivityPresenter;
    @Mock
    RealmResults<DrinkItem> realmResults;
    DrinkItem drinkItem = new DrinkItem();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    // TODO: 07.08.16  find a better way to mock realm or wrap it

//    @Before
//    public void setup() throws Exception {
//
//        // Setup Realm to be mocked
//        mockStatic(Realm.class);
//        mockStatic(RealmConfiguration.class);
//        mockStatic(RealmCore.class);
//
//        // Create the mock
//        final Realm mockRealm = mock(Realm.class);
//        final RealmConfiguration mockRealmConfig = mock(RealmConfiguration.class);
//
//        // TODO: Better solution would be just mock the RealmConfiguration.Builder class. But it seems there is some
//        // problems for powermock to mock it (static inner class). We just mock the RealmCore.loadLibrary(Context) which
//        // will be called by RealmConfiguration.Builder's constructor.
//        doNothing().when(RealmCore.class);
//        RealmCore.loadLibrary(any(Context.class));
//
//        // TODO: Mock the RealmConfiguration's constructor. If the RealmConfiguration.Builder.build can be mocked, this
//        // is not necessary anymore.
//        whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);
//
//        // Anytime getInstance is called with any configuration, then return the mockRealm
//        when(Realm.getInstance(any(RealmConfiguration.class))).thenReturn(mockRealm);
//    }
//
//    @Test
//    public void testAddItem() throws Exception {
//        DrinksAdapter drinksAdapter = new DrinksAdapter(context, mainActivityPresenter, realm);
//        drinksAdapter.addItem(drinkItem);
//        verify(realm).beginTransaction();
//        verify(realm).copyToRealm(drinkItem);
//        verify(realm).commitTransaction();
//    }
//
//    @Test
//    public void testGetItem() throws Exception {
//        int itemLocation = (int) (Math.random() * 100000);
//        when(realmResults.get(itemLocation)).thenReturn(drinkItem);
//        when(realm.where(DrinkItem.class).findAll()).thenReturn(realmResults);
//        DrinksAdapter drinksAdapter = new DrinksAdapter(context, mainActivityPresenter, realm);
//        assertEquals(drinkItem, drinksAdapter.getItem(itemLocation));
//    }
//
//    @Test
//    public void testGetItemCount() throws Exception {
//        long itemCount = (int) (Math.random() * 100000);
//        when(realm.where(DrinkItem.class).count()).thenReturn(itemCount);
//        DrinksAdapter drinksAdapter = new DrinksAdapter(context, mainActivityPresenter, realm);
//        assertEquals(itemCount, drinksAdapter.getItemCount());
//    }
}