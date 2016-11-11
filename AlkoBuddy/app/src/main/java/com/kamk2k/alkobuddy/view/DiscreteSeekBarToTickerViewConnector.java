package com.kamk2k.alkobuddy.view;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by kksiazek on 11.11.16.
 */

// TODO: 11.11.16 set progress step
public class DiscreteSeekBarToTickerViewConnector {
    public static final String START_VALUE = "0";
    final DiscreteSeekBar discreteSeekBar;
    final TickerView tickerView;
    int currentValue;

    private DiscreteSeekBar.OnProgressChangeListener seekbarChangeListener = new DiscreteSeekBar
            .OnProgressChangeListener() {
        @Override
        public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
            currentValue = value;
            DiscreteSeekBarToTickerViewConnector.this.tickerView.setText(Integer.toString
                    (value));
        }

        @Override
        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

        }
    };

    public DiscreteSeekBarToTickerViewConnector(DiscreteSeekBar discreteSeekBar, TickerView
            tickerView) {
        this.discreteSeekBar = discreteSeekBar;
        this.tickerView = tickerView;

        init();
    }

    private void init() {
        tickerView.setCharacterList(TickerUtils.getDefaultNumberList());
        tickerView.setText(START_VALUE);
        discreteSeekBar.setOnProgressChangeListener(seekbarChangeListener);
    }

    public int getCurrentValue() {
        return currentValue;
    }
}
