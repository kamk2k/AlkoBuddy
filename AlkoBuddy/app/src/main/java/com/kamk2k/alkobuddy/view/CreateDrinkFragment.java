package com.kamk2k.alkobuddy.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.CreateDrinkPresenter;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.view.utils.ImagePickerDelegate;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;
import com.robinhood.ticker.TickerView;
import com.squareup.picasso.Picasso;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by PC on 2015-02-23.
 */
public class CreateDrinkFragment extends MVPFragmentView implements CreateDrinkView {

    private static final String TAG = "CreateDrinkFragment";

    // TODO: 15.11.16 add new drink and delete button
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

    @Inject
    CreateDrinkPresenter presenter;
    @Inject
    ImagePickerDelegate imagePickerDelegate;

    private ImagePickerDelegate.OnCompleteListener onImagePickerCompleteListener = new ImagePickerDelegate.OnCompleteListener() {
        @Override
        public void onImageReturned(Uri imageUri) {
            String imagePath = imageUri.getPath();
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            presenter.getCurrentDrinkItem().setImagePath(imagePath);
            realm.commitTransaction();
            realm.close();
            Picasso.with(getContext()).load(new File(imagePath)).error(R.drawable.beer_icon).into(beerView);
        }

        @Override
        public void onError() {
            Log.e(TAG, "ImagePickerDelegate.OnCompleteListener error");
        }
    };

    public CreateDrinkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_drink_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initializeSeekBarConnectors();
        setNameChangeListener();
        beerView.setOnClickListener(view -> {
            imagePickerDelegate
                    .addOnCompleteListener(onImagePickerCompleteListener)
                    .startImagePicker(this);
        });
        return rootView;
    }

    private void setNameChangeListener() {
        drinkName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    presenter.getCurrentDrinkItem().setName(charSequence.toString());
                    realm.commitTransaction();
                    realm.close();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initializeSeekBarConnectors() {
        beerSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(beerVolumeSeekBar, beerVolume, 50.0f, 500);
        wineSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(wineVolumeSeekBar, wineVolume, 25.0f, 200);
        vodkaSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(vodkaVolumeSeekBar, vodkaVolume, 10.0f, 100);
        customVolumeSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(customVolumeSeekBar, customVolume, 10.0f, 500);
        customPercentageSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(customPerCentSeekBar, customPerCent, 5.0f, 100);

        beerSeekBarConnector.setOnValueChangedListener(value -> {
            if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                presenter.getCurrentDrinkItem().setBeerVolume(value);
                realm.commitTransaction();
                realm.close();
            }
        });
        wineSeekBarConnector.setOnValueChangedListener(value -> {
            if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                presenter.getCurrentDrinkItem().setWineVolume(value);
                realm.commitTransaction();
                realm.close();
            }
        });
        vodkaSeekBarConnector.setOnValueChangedListener(value -> {
            if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                presenter.getCurrentDrinkItem().setVodkaVolume(value);
                realm.commitTransaction();
                realm.close();
            }
        });
        customVolumeSeekBarConnector.setOnValueChangedListener(value -> {
            if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                presenter.getCurrentDrinkItem().setCustomVolume(value);
                realm.commitTransaction();
                realm.close();
            }
        });
        customPercentageSeekBarConnector.setOnValueChangedListener(value -> {
            if(presenter != null && presenter.getCurrentDrinkItem() != null) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                presenter.getCurrentDrinkItem().setCustomPercentage((float)value/100f);
                realm.commitTransaction();
                realm.close();
            }
        });
        showDrink(DrinkItem.generateMock());
    }

    @Override
    public void injectFragment(ApplicationComponent component) {
        component.inject(this);
        presenter.setMVPView(this);
    }

    @Override
    public void showLoading() {
        // TODO: 14.11.16 implement loading
    }

    @Override
    public void showDrink(DrinkItem drinkItem) {
        drinkName.setText(drinkItem.getName());
        Picasso.with(getContext()).load(new File(drinkItem.getImagePath()))
                .error(R.drawable.beer_icon).placeholder(R.drawable.beer_icon).into(beerView);

        beerSeekBarConnector.setCurrentValue(drinkItem.getBeerVolume());
        wineSeekBarConnector.setCurrentValue(drinkItem.getWineVolume());
        vodkaSeekBarConnector.setCurrentValue(drinkItem.getVodkaVolume());
        customVolumeSeekBarConnector.setCurrentValue(drinkItem.getCustomVolume());
        customPercentageSeekBarConnector.setCurrentValue((int) (100 * drinkItem.getCustomPercentage()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerDelegate.handleActivityResult(getActivity(), this, requestCode, resultCode, data,
                presenter.getCurrentDrinkItem().getId());
    }
}
