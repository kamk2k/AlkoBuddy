package com.corellidev.alcotester.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by PC on 2015-02-23.
 */
@RealmClass
public class DrinkItem implements RealmModel, Parcelable {

    public static final float DEFAULT_BEER_PERCENTAGE = 0.06f;
    public static final float DEFAULT_WINE_PERCENTAGE = 0.14f;
    public static final float DEFAULT_VODKA_PERCENTAGE = 0.40f;
    public static final String DEFAULT_IMAGE_PATH = "";

    public static final String ID_FIELD_NAME = "id";

    @PrimaryKey
    private int id;
    private String name;
    private String imagePath;
    private int beerVolume;
    private float beerPercentage;
    private int wineVolume;
    private float winePercentage;
    private int vodkaVolume;
    private float vodkaPercentage;
    private int customVolume;
    private float customPercentage;

    public static DrinkItem generateMock() {
        return new DrinkItem(1, "Piwo", DEFAULT_IMAGE_PATH, 500, 0.08f, 0, 0, 0, 0, 0, 0);
    }

    public static DrinkItem getDefaultItem(int id) {
        return new DrinkItem(id, "Drink", DEFAULT_IMAGE_PATH, 0, DEFAULT_BEER_PERCENTAGE, 0, DEFAULT_WINE_PERCENTAGE, 0,
                DEFAULT_VODKA_PERCENTAGE, 0, 0.20f);
    }

    public DrinkItem() {
    }

    public DrinkItem(int id, String name, String imagePath, int beerVolume, float beerPercentage, int wineVolume,
                     float winePercentage, int vodkaVolume, float vodkaPercentage, int customVolume,
                     float customPercentage) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.beerVolume = beerVolume;
        this.beerPercentage = beerPercentage;
        this.wineVolume = wineVolume;
        this.winePercentage = winePercentage;
        this.vodkaVolume = vodkaVolume;
        this.vodkaPercentage = vodkaPercentage;
        this.customVolume = customVolume;
        this.customPercentage = customPercentage;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DrinkItem && id == ((DrinkItem) o).id;
    }

    public int getBeerVolume() {
        return beerVolume;
    }

    public void setBeerVolume(int beerVolume) {
        this.beerVolume = beerVolume;
    }

    public float getBeerPercentage() {
        return beerPercentage;
    }

    public void setBeerPercentage(float beerPercentage) {
        this.beerPercentage = beerPercentage;
    }

    public int getWineVolume() {
        return wineVolume;
    }

    public void setWineVolume(int wineVolume) {
        this.wineVolume = wineVolume;
    }

    public float getWinePercentage() {
        return winePercentage;
    }

    public void setWinePercentage(float winePercentage) {
        this.winePercentage = winePercentage;
    }

    public int getVodkaVolume() {
        return vodkaVolume;
    }

    public void setVodkaVolume(int vodkaVolume) {
        this.vodkaVolume = vodkaVolume;
    }

    public float getVodkaPercentage() {
        return vodkaPercentage;
    }

    public void setVodkaPercentage(float vodkaPercentage) {
        this.vodkaPercentage = vodkaPercentage;
    }

    public int getCustomVolume() {
        return customVolume;
    }

    public void setCustomVolume(int customVolume) {
        this.customVolume = customVolume;
    }

    public float getCustomPercentage() {
        return customPercentage;
    }

    public void setCustomPercentage(float customPercentage) {
        this.customPercentage = customPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getDrinkStrength() {
        int volume = beerVolume + wineVolume + vodkaVolume + customVolume;
        float alcoVolume = (beerVolume * beerPercentage) + (wineVolume * winePercentage)
                + (vodkaVolume * vodkaPercentage) + (customVolume * customPercentage);
        return alcoVolume / (float) volume;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.beerVolume);
        dest.writeFloat(this.beerPercentage);
        dest.writeInt(this.wineVolume);
        dest.writeFloat(this.winePercentage);
        dest.writeInt(this.vodkaVolume);
        dest.writeFloat(this.vodkaPercentage);
        dest.writeInt(this.customVolume);
        dest.writeFloat(this.customPercentage);
    }

    private DrinkItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.beerVolume = in.readInt();
        this.beerPercentage = in.readFloat();
        this.wineVolume = in.readInt();
        this.winePercentage = in.readFloat();
        this.vodkaVolume = in.readInt();
        this.vodkaPercentage = in.readFloat();
        this.customVolume = in.readInt();
        this.customPercentage = in.readFloat();
    }

    public static final Parcelable.Creator<DrinkItem> CREATOR = new Parcelable.Creator<DrinkItem>() {
        public DrinkItem createFromParcel(Parcel source) {
            return new DrinkItem(source);
        }

        public DrinkItem[] newArray(int size) {
            return new DrinkItem[size];
        }
    };
}
