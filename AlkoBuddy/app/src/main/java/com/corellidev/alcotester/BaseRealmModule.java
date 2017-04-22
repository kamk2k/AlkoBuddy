package com.corellidev.alcotester;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.model.UserAlcoState;

import io.realm.annotations.RealmModule;

/**
 * Created by kksiazek on 02.10.16.
 */

@RealmModule(classes = {DrinkItem.class, UserAlcoState.class})
public class BaseRealmModule {
}
