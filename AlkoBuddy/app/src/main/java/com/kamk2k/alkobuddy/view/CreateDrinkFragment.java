package com.kamk2k.alkobuddy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.robinhood.ticker.TickerView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PC on 2015-02-23.
 */
public class CreateDrinkFragment extends Fragment {


    @BindView(R.id.beerView)
    ImageView beerView;
    @BindView(R.id.beer_title)
    TextView beerTitle;
    @BindView(R.id.wine_title)
    TextView wineTitle;
    @BindView(R.id.vodka_title)
    TextView vodkaTitle;
    @BindView(R.id.custom_title)
    TextView customTitle;
    @BindView(R.id.drink_name)
    EditText drinkName;
    @BindView(R.id.beer_volume)
    TickerView beerVolume;
    @BindView(R.id.wine_volume)
    TickerView wineVolume;
    @BindView(R.id.vodka_volume)
    TickerView vodkaVolume;
    @BindView(R.id.custom_volume)
    TickerView customVolume;
    @BindView(R.id.custom_per_cent)
    TickerView customPerCent;
    @BindView(R.id.beer_volume_seek_bar)
    DiscreteSeekBar beerVolumeSeekBar;
    @BindView(R.id.wine_volume_seek_bar)
    DiscreteSeekBar wineVolumeSeekBar;
    @BindView(R.id.vodka_volume_seek_bar)
    DiscreteSeekBar vodkaVolumeSeekBar;
    @BindView(R.id.custom_volume_seek_bar)
    DiscreteSeekBar customVolumeSeekBar;
    @BindView(R.id.custom_per_cent_seek_bar)
    DiscreteSeekBar customPerCentSeekBar;

    DiscreteSeekBarToTickerViewConnector beerSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector wineSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector vodkaSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector customVolumeSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector customPercentageSeekBarConnector;

    public CreateDrinkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_drink_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initializeSeekBarConnectors();
        return rootView;
    }
    private void initializeSeekBarConnectors() {
        beerSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(beerVolumeSeekBar, beerVolume, 50.0f, 500);
        wineSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(wineVolumeSeekBar, wineVolume, 25.0f, 200);
        vodkaSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(vodkaVolumeSeekBar, vodkaVolume, 10.0f, 100);
        customVolumeSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(customVolumeSeekBar, customVolume, 10.0f, 500);
        customPercentageSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(customPerCentSeekBar, customPerCent, 5.0f, 100);
    }
}
