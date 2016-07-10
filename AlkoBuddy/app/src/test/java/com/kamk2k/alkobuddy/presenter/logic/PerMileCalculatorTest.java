package com.kamk2k.alkobuddy.presenter.logic;

import android.test.suitebuilder.annotation.SmallTest;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.UserAlcoState;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by kksiazek on 09.07.16.
 */

public class PerMileCalculatorTest {

    @Mock
    private UserAlcoState userAlcoState;
    @Mock
    private DrinkItem drinkItem;

    @Test
    public void testSoberMaleDrink() {

        Date currentTime = new Date();
        UserAlcoState userAlcoState = new UserAlcoState(UserAlcoState.Sex.MALE, 80, currentTime, 0f, 0);
//        userAlcoState.setCurrentPerMile(0f);
//        userAlcoState.setSex(UserAlcoState.Sex.MALE);
//        userAlcoState.setEthanolGramsInBlood(0f);
//        userAlcoState.setLastUpdate(currentTime);
//        userAlcoState.setWeight(80);

        DrinkItem drinkItem = Mockito.mock(DrinkItem.class);
        when(drinkItem.getBeerPercentage()).thenReturn(0.05f);
        when(drinkItem.getBeerVolume()).thenReturn(750);
        when(drinkItem.getCustomPercentage()).thenReturn(0f);
        when(drinkItem.getVodkaPercentage()).thenReturn(0f);
        when(drinkItem.getWinePercentage()).thenReturn(0f);
        when(drinkItem.getCustomVolume()).thenReturn(0);
        when(drinkItem.getWineVolume()).thenReturn(0);
        when(drinkItem.getVodkaVolume()).thenReturn(0);

        new PerMileCalculator(userAlcoState).drink(drinkItem, currentTime);

//        verify(userAlcoState).setEthanolGramsInBlood(0);
//        verify(userAlcoState).setLastUpdate(currentTime);
//        verify(userAlcoState).setEthanolGramsInBlood(30);
        assertEquals(currentTime, userAlcoState.getLastUpdate());
        assertEquals(30, userAlcoState.getEthanolGramsInBlood(), 0.1f);
        assertEquals(0.535714286f, userAlcoState.getCurrentPerMile(), 0.01f);
        assertEquals(9818181, userAlcoState.getTimeToSoberInMs(), 60000);
    }

    @Test
    public void testSoberFealeDrink() {

        Date currentTime = new Date();
        UserAlcoState userAlcoState = new UserAlcoState(UserAlcoState.Sex.FEMALE, 60, currentTime, 0f, 0);
//        userAlcoState.setCurrentPerMile(0f);
//        userAlcoState.setSex(UserAlcoState.Sex.MALE);
//        userAlcoState.setEthanolGramsInBlood(0f);
//        userAlcoState.setLastUpdate(currentTime);
//        userAlcoState.setWeight(80);

        DrinkItem drinkItem = Mockito.mock(DrinkItem.class);
        when(drinkItem.getBeerPercentage()).thenReturn(0f);
        when(drinkItem.getBeerVolume()).thenReturn(0);
        when(drinkItem.getCustomPercentage()).thenReturn(0f);
        when(drinkItem.getVodkaPercentage()).thenReturn(0f);
        when(drinkItem.getWinePercentage()).thenReturn(0.1f);
        when(drinkItem.getCustomVolume()).thenReturn(0);
        when(drinkItem.getWineVolume()).thenReturn(100);
        when(drinkItem.getVodkaVolume()).thenReturn(0);

        new PerMileCalculator(userAlcoState).drink(drinkItem, currentTime);

//        verify(userAlcoState).setEthanolGramsInBlood(0);
//        verify(userAlcoState).setLastUpdate(currentTime);
//        verify(userAlcoState).setEthanolGramsInBlood(30);
        assertEquals(currentTime, userAlcoState.getLastUpdate());
        assertEquals(8, userAlcoState.getEthanolGramsInBlood(), 0.1f);
        assertEquals(0.222222222f, userAlcoState.getCurrentPerMile(), 0.01f);
        assertEquals(3200000, userAlcoState.getTimeToSoberInMs(), 60000);
    }

    @Test
    public void testDrunkMaleDrink() {

        Date currentTime = new Date();
        UserAlcoState userAlcoState = new UserAlcoState(UserAlcoState.Sex.MALE, 80, currentTime, 2f, 0);
        assertEquals(112, userAlcoState.getEthanolGramsInBlood(), 0.1f);
//        userAlcoState.setCurrentPerMile(0f);
//        userAlcoState.setSex(UserAlcoState.Sex.MALE);
//        userAlcoState.setEthanolGramsInBlood(0f);
//        userAlcoState.setLastUpdate(currentTime);
//        userAlcoState.setWeight(80);

        DrinkItem drinkItem = Mockito.mock(DrinkItem.class);
        when(drinkItem.getBeerPercentage()).thenReturn(0f);
        when(drinkItem.getBeerVolume()).thenReturn(0);
        when(drinkItem.getCustomPercentage()).thenReturn(0f);
        when(drinkItem.getVodkaPercentage()).thenReturn(0.4f);
        when(drinkItem.getWinePercentage()).thenReturn(0f);
        when(drinkItem.getCustomVolume()).thenReturn(0);
        when(drinkItem.getWineVolume()).thenReturn(0);
        when(drinkItem.getVodkaVolume()).thenReturn(100);

        new PerMileCalculator(userAlcoState).drink(drinkItem, currentTime);

//        verify(userAlcoState).setEthanolGramsInBlood(0);
//        verify(userAlcoState).setLastUpdate(currentTime);
//        verify(userAlcoState).setEthanolGramsInBlood(30);
        assertEquals(currentTime, userAlcoState.getLastUpdate());
        assertEquals(144, userAlcoState.getEthanolGramsInBlood(), 0.1f);
        assertEquals(2.5714285714f, userAlcoState.getCurrentPerMile(), 0.01f);
        assertEquals(47127272, userAlcoState.getTimeToSoberInMs(), 60000);
    }

    @Test
    public void testAlcoholGramsToPerMilesForMen() {
        UserAlcoState userAlcoState = Mockito.mock(UserAlcoState.class);
        when(userAlcoState.getSex()).thenReturn(UserAlcoState.Sex.MALE);
        when(userAlcoState.getWeight()).thenReturn(80);

        float perMile = AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams(10, userAlcoState);

        assertEquals(0.178571429f, perMile, 0.01f);
    }

    @Test
    public void testAlcoholGramsToPerMilesForWomen() {
        UserAlcoState userAlcoState = Mockito.mock(UserAlcoState.class);
        when(userAlcoState.getSex()).thenReturn(UserAlcoState.Sex.FEMALE);
        when(userAlcoState.getWeight()).thenReturn(60);

        float perMile = AlcoholGramsToPerMileConverter.alcoholPerMilesForGrams(10, userAlcoState);

        assertEquals(0.277777778f, perMile, 0.01f);
    }

    @Test
    public void testPerMilesToAlcoholGramsForMen() {
        UserAlcoState userAlcoState = Mockito.mock(UserAlcoState.class);
        when(userAlcoState.getSex()).thenReturn(UserAlcoState.Sex.MALE);
        when(userAlcoState.getWeight()).thenReturn(80);

        float perMile = AlcoholGramsToPerMileConverter.alcoholGramsForPerMile(1, userAlcoState);

        assertEquals(56, perMile, 0.01f);
    }

    @Test
    public void testPerMilesToAlcoholGramsForWomen() {
        UserAlcoState userAlcoState = Mockito.mock(UserAlcoState.class);
        when(userAlcoState.getSex()).thenReturn(UserAlcoState.Sex.FEMALE);
        when(userAlcoState.getWeight()).thenReturn(60);

        float perMile = AlcoholGramsToPerMileConverter.alcoholGramsForPerMile(1, userAlcoState);

        assertEquals(36, perMile, 0.01f);
    }

    @Test
    public void testCalculateAlcoholWeightInDrink() {
        DrinkItem drinkItem = Mockito.mock(DrinkItem.class);

        when(drinkItem.getBeerPercentage()).thenReturn(0.045f);
        when(drinkItem.getBeerVolume()).thenReturn(330);

        float alcoholGrams = AlcoholInDrinkCalculator.calculateAlcoholWeightInDrink(drinkItem);

        assertEquals(11.9f, alcoholGrams, 0.1f);
    }
}
