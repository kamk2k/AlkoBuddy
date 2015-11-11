package com.kamk2k.alkobuddy.model;

import java.util.Date;

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

    public static void resetUserState(UserAlcoState state) {
        state.setEthanolGramsInBlood(0);
        state.setTimeToSoberInMs(0);
        state.setCurrentPerMile(0);
        state.setLastUpdate(new Date());
    }
}
