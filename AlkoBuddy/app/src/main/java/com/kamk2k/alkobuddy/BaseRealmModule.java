package com.kamk2k.alkobuddy;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;

import io.realm.annotations.RealmModule;

/**
 * Created by kksiazek on 02.10.16.
 */

@RealmModule(classes = {DrinkItem.class, UserAlcoState.class})
public class BaseRealmModule {
}
