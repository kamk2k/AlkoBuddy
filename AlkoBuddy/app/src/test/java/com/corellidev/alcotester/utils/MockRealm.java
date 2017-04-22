package com.corellidev.alcotester.utils;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.model.UserAlcoState;

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