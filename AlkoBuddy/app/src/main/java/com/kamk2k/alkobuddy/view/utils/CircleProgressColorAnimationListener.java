package com.kamk2k.alkobuddy.view.utils;

import android.animation.ValueAnimator;
import android.graphics.Color;

import com.github.lzyzsd.circleprogress.CircleProgress;

/**
 * Created by kksiazek on 29.01.17.
 */
// TODO: 29.01.17 make a nice util class from it
public class CircleProgressColorAnimationListener implements ValueAnimator.AnimatorUpdateListener {

    public static final float START_COLOR_HUE = 100;
    public static final float END_COLOR_HUE = 0;
    public static final float COLOR_SATURATION = 1;
    public static final float COLOR_VALUE = 1;

    private CircleProgress circleProgress;

    public CircleProgressColorAnimationListener(CircleProgress circleProgress) {
        this.circleProgress = circleProgress;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int value = getNewProgressValue((int) valueAnimator.getAnimatedValue());
        float[] hsv = getNewProgressColorInHSV(value);
        circleProgress.setFinishedColor(Color.HSVToColor(hsv));
        circleProgress.setProgress(value);
    }

    private float[] getNewProgressColorInHSV(int value) {
        float[] hsv = new float[3];
        hsv[0] = START_COLOR_HUE - ((Math.abs(END_COLOR_HUE - START_COLOR_HUE) * value) / circleProgress
                .getMax());
        hsv[1] = COLOR_SATURATION;
        hsv[2] = COLOR_VALUE;
        return hsv;
    }

    private int getNewProgressValue(int currentProgressValue) {
        int value = currentProgressValue;
        if(value > circleProgress.getMax()) {
            value = circleProgress.getMax();
        } else if(value < 0) {
            value = 0;
        }
        return value;
    }
}
