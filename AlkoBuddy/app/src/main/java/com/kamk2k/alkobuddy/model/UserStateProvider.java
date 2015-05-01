package com.kamk2k.alkobuddy.model;

/**
 * Created by PC on 2015-05-01.
 */
public class UserStateProvider {

    private static UserAlcoState userState;

    public static UserAlcoState getUserState() {
        if(userState == null)
            userState = UserAlcoState.generateMock();
        return userState;
    }

    public static void setUserState(UserAlcoState userState) {
        UserStateProvider.userState = userState;
    }
}
