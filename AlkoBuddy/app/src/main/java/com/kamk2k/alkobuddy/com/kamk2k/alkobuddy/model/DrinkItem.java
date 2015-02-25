package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model;

/**
 * Created by PC on 2015-02-23.
 */
public class DrinkItem {

    private int id;
    private String name;
    private int beerVolume;
    private float beerPercentage;
    private int wineVolume;
    private float winePercentage;
    private int vodkaVolume;
    private float vodkaPercentage;
    private int customVolume;
    private float customPercentage;

    public static DrinkItem generateMock() {
        return new DrinkItem(1, "Piwo", 500, 0.08f, 0, 0, 0, 0, 0, 0);
    }

    public DrinkItem(int id, String name, int beerVolume, float beerPercentage, int wineVolume,
                     float winePercentage, int vodkaVolume, float vodkaPercentage, int customVolume,
                     float customPercentage) {
        this.id = id;
        this.name = name;
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
        if(o instanceof DrinkItem) {
            return id == ((DrinkItem)o).id;
        } else {
            return false;
        }
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
}
