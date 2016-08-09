package com.kamk2k.alkobuddy.utils;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;

import org.powermock.api.mockito.PowerMockito;

import io.realm.Realm;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class MockRealm {

    public static Realm mockRealm() {
        mockStatic(Realm.class);

        Realm mockRealm = PowerMockito.mock(Realm.class);

        when(mockRealm.createObject(DrinkItem.class)).thenReturn(new DrinkItem());
        when(mockRealm.createObject(UserAlcoState.class)).thenReturn(new UserAlcoState());

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        return mockRealm;
    }
}