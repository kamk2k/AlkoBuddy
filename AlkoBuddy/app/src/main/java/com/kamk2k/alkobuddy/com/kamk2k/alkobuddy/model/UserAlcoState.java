package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model;

/**
 * Created by PC on 2015-02-25.
 */
public class UserAlcoState {

    public enum Sex{MALE, FEMALE};

    public static UserAlcoState generateMock() {
        return new UserAlcoState(Sex.MALE, 70, 0, 0);
    }

    public UserAlcoState(Sex sex, int weight, float currentPerMile, long timeToSoberInMs) {
        this.sex = sex;
        this.weight = weight;
        this.currentPerMile = currentPerMile;
        this.timeToSoberInMs = timeToSoberInMs;
    }

    // User info
    private Sex sex;
    private int weight;

    //User state
    private float currentPerMile;
    private long timeToSoberInMs;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getCurrentPerMile() {
        return currentPerMile;
    }

    public void setCurrentPerMile(float currentPerMile) {
        this.currentPerMile = currentPerMile;
    }

    public long getTimeToSoberInMs() {
        return timeToSoberInMs;
    }

    public void setTimeToSoberInMs(long timeToSoberInMs) {
        this.timeToSoberInMs = timeToSoberInMs;
    }
}
