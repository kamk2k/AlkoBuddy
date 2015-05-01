package com.kamk2k.alkobuddy.model.events;

/**
 * Created by PC on 2015-04-12.
 */
public class ChangeStatusTextEvent {
    private String perMileText;
    private String timeToSoberText;

    public ChangeStatusTextEvent(String perMileText, String timeToSoberText) {
        this.perMileText = perMileText;
        this.timeToSoberText = timeToSoberText;
    }

    public String getPerMileText() {
        return perMileText;
    }

    public void setPerMileText(String perMileText) {
        this.perMileText = perMileText;
    }

    public String getTimeToSoberText() {
        return timeToSoberText;
    }

    public void setTimeToSoberText(String timeToSoberText) {
        this.timeToSoberText = timeToSoberText;
    }
}
