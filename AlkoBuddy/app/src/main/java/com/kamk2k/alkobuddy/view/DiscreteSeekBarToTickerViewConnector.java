package com.kamk2k.alkobuddy.view;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by kksiazek on 11.11.16.
 */

// TODO: 14.11.16 custom view
public class DiscreteSeekBarToTickerViewConnector {
    public static final String START_VALUE = "0";
    final DiscreteSeekBar discreteSeekBar;
    final TickerView tickerView;
    final float progressStep;
    final int maxValue;
    int currentValue;

    private DiscreteSeekBar.OnProgressChangeListener seekbarChangeListener = new DiscreteSeekBar
            .OnProgressChangeListener() {
        @Override
        public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            currentValue = (int) (value * progressStep);
            DiscreteSeekBarToTickerViewConnector.this.tickerView.setText(Integer.toString
                    (currentValue));
        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };

    public DiscreteSeekBarToTickerViewConnector(DiscreteSeekBar discreteSeekBar, TickerView
            tickerView, float progressStep, int maxValue) {
        this.discreteSeekBar = discreteSeekBar;
        this.tickerView = tickerView;
        this.progressStep = progressStep;
        this.maxValue = maxValue;

        init();
    }

    private void init() {
        tickerView.setCharacterList(TickerUtils.getDefaultNumberList());
        tickerView.setText(START_VALUE);
        discreteSeekBar.setOnProgressChangeListener(seekbarChangeListener);
        DiscreteSeekBar.NumericTransformer multiplyTransformer =
                new DiscreteSeekBar.NumericTransformer()  {
                    @Override
                    public int transform(int value) {
                        return (int) (value * progressStep);
                    }
                };
        discreteSeekBar.setNumericTransformer(multiplyTransformer);
        discreteSeekBar.setMax((int) (maxValue / progressStep));
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int value) {
        if(value < 0 || value > maxValue) {
            throw new IllegalArgumentException
                    ("Trying to set value " + value + " out of the SeekBar range = [0, " + maxValue + "]");
        } else {
            currentValue = value;
            discreteSeekBar.setProgress((int) (currentValue / progressStep));
        }
    }
}
