package com.kamk2k.alkobuddy.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;

import com.kamk2k.alkobuddy.presenter.logic.AlcoholGramsToPerMileConverter;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by PC on 2015-02-25.
 */
@RealmClass
public class UserAlcoState implements RealmModel, Parcelable {

    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    public static UserAlcoState generateMock() {
        return new UserAlcoState(SEX_MALE, 70, new Date(), 0, 0);
    }

    public UserAlcoState() {}

    public UserAlcoState(int sex, int weight, Date lastUpdate, float currentPerMile, long timeToSoberInMs) {
        this.sex = sex;
        this.weight = weight;
        this.lastUpdate = lastUpdate;
        this.currentPerMile = currentPerMile;
        this.timeToSoberInMs = timeToSoberInMs;
        this.ethanolGramsInBlood = AlcoholGramsToPerMileConverter.alcoholGramsForPerMile(currentPerMile, this);
    }

    // User info
    //// TODO: 24.07.16 write test for intRange condition
    @IntRange(from = 0, to = 1)
    private int sex;
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

    @IntRange(from = 0, to = 1)
    public int getSex() {
        return sex;
    }

    public void setSex(@IntRange(from = 0, to = 1) int sex) {
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
        dest.writeInt(this.sex);
        dest.writeInt(this.weight);
        dest.writeLong(lastUpdate != null ? lastUpdate.getTime() : -1);
        dest.writeFloat(this.ethanolGramsInBlood);
        dest.writeFloat(this.currentPerMile);
        dest.writeLong(this.timeToSoberInMs);
    }

    private UserAlcoState(Parcel in) {
        this.sex = in.readInt();
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
