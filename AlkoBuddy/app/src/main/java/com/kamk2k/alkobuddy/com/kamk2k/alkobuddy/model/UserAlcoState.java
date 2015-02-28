package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by PC on 2015-02-25.
 */
public class UserAlcoState implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.sex == null ? -1 : this.sex.ordinal());
        dest.writeInt(this.weight);
        dest.writeLong(lastUpdate != null ? lastUpdate.getTime() : -1);
        dest.writeFloat(this.ethanolGramsInBlood);
        dest.writeFloat(this.currentPerMile);
        dest.writeLong(this.timeToSoberInMs);
    }

    private UserAlcoState(Parcel in) {
        int tmpSex = in.readInt();
        this.sex = tmpSex == -1 ? null : Sex.values()[tmpSex];
        this.weight = in.readInt();
        long tmpLastUpdate = in.readLong();
        this.lastUpdate = tmpLastUpdate == -1 ? null : new Date(tmpLastUpdate);
        this.ethanolGramsInBlood = in.readFloat();
        this.currentPerMile = in.readFloat();
        this.timeToSoberInMs = in.readLong();
    }

    public static final Parcelable.Creator<UserAlcoState> CREATOR = new Parcelable.Creator<UserAlcoState>() {
        public UserAlcoState createFromParcel(Parcel source) {
            return new UserAlcoState(source);
        }

        public UserAlcoState[] newArray(int size) {
            return new UserAlcoState[size];
        }
    };
}
