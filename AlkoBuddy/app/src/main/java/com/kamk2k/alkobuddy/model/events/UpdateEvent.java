package com.kamk2k.alkobuddy.model.events;

import com.kamk2k.alkobuddy.model.UserAlcoState;

/**
 * Created by PC on 2015-04-12.
 */
public class UpdateEvent {

    private UserAlcoState userAlcoState;

    public UpdateEvent(UserAlcoState userAlcoState) {
        this.userAlcoState = userAlcoState;
    }

    public UserAlcoState getUserAlcoState() {
        return userAlcoState;
    }

    public void setUserAlcoState(UserAlcoState userAlcoState) {
        this.userAlcoState = userAlcoState;
    }
}
