package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model;

import java.util.Date;

/**
 * Created by PC on 2015-02-25.
 */
public class UserAlcoState {

    public enum Sex{MALE, FEMALE};

    public static UserAlcoState generateMock() {
        return new UserAlcoState(Sex.MALE, 70, new Date(), 0, 0);
    }

    public UserAlcoState(Sex sex, int weight, Date lastUpdate, float currentPerMile, long timeToSoberInMs) {
        this.sex = sex;
        this.weight = weight;
        this.lastUpdate = lastUpdate;
        this.currentPerMile = currentPerMile;
        this.timeToSoberInMs = timeToSoberInMs;
    }

    // User info
    private Sex sex;
    private int weight;

    //User state
    private Date lastUpdate;
    private float ethanolGramsInBlood;
    private float currentPerMile;
    private long timeToSoberInMs;

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public float getEthanolGramsInBlood() {
        return ethanolGramsInBlood;
    }

    public void setEthanolGramsInBlood(float ethanolGramsInBlood) {
        this.ethanolGramsInBlood = ethanolGramsInBlood;
    }

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
