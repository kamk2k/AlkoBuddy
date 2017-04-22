package com.corellidev.alcotester.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.corellidev.alcotester.R;
import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.presenter.EditDrinkPresenter;
import com.corellidev.alcotester.presenter.dagger.ApplicationComponent;
import com.corellidev.alcotester.presenter.utils.AnalyticsLogger;
import com.corellidev.alcotester.view.utils.ImagePickerDelegate;
import com.corellidev.alcotester.view.utils.IntroTipsViewDelegate;
import com.corellidev.alcotester.view.utils.MVPFragmentView;
import com.robinhood.ticker.TickerView;
import com.squareup.picasso.Picasso;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import jonathanfinerty.once.Once;

/**
 * Created by PC on 2015-02-23.
 */
public class EditDrinkFragment extends MVPFragmentView implements EditDrinkView {

    private static final String TAG = "EditDrinkFragment";
    public static final String SHOW_EDIT_FRAGMENT_INTRO = "SHOW_EDIT_FRAGMENT_INTRO";

    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.content_view)
    ScrollView contentView;
    @BindView(R.id.add_photo)
    ImageView addPhotoView;
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
    @BindView(R.id.tooltip_button)
    ImageView tooltipButton;
    private View rootView;

    DiscreteSeekBarToTickerViewConnector beerSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector wineSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector vodkaSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector customVolumeSeekBarConnector;
    DiscreteSeekBarToTickerViewConnector customPercentageSeekBarConnector;

    @Inject
    EditDrinkPresenter presenter;
    @Inject
    ImagePickerDelegate imagePickerDelegate;
    @Inject
    AnalyticsLogger analyticsLogger;

    @Inject
    IntroTipsViewDelegate introTipsViewDelegate;
    private ImagePickerDelegate.OnCompleteListener onImagePickerCompleteListener = new
            ImagePickerDelegate.OnCompleteListener() {
        @Override
        public void onImageReturned(Uri imageUri) {
            String imagePath = imageUri.getPath();
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            presenter.getCurrentDrinkItem().setImagePath(imagePath);
            realm.commitTransaction();
            realm.close();
            Picasso.with(getContext()).load(new File(imagePath)).error(R.drawable.ic_add_photo).into
                    (addPhotoView);
        }

        @Override
        public void onError() {
            Log.e(TAG, "ImagePickerDelegate.OnCompleteListener error");
        }
    };

    public EditDrinkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edit_drink_fragment, container, false);
        ButterKnife.bind(this, rootView);
        if(presenter.getCurrentDrinkItem() == null) {
            showEmptyView();
        } else {
            showContentView();
        }
        initializeSeekBarConnectors();
        setNameChangeListener();
        addPhotoView.setOnClickListener(view -> {
            imagePickerDelegate
                    .addOnCompleteListener(onImagePickerCompleteListener)
                    .startImagePicker(this);
        });
        tooltipButton.setOnClickListener(view -> {
            showIntroTooltips();
            analyticsLogger.logEvent(AnalyticsLogger.TOOLTIP_CLICKED_EVENT);
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            if (!Once.beenDone(Once.THIS_APP_INSTALL, SHOW_EDIT_FRAGMENT_INTRO)) {
                showIntroTooltips();
                Once.markDone(SHOW_EDIT_FRAGMENT_INTRO);
            }
        }
    }

    private void showIntroTooltips() {
        View[] anchorsArray = {drinkName, addPhotoView, wineVolumeSeekBar, rootView};
        List<View> anchors = Arrays.asList(anchorsArray);

        Integer[] textsArray = {R.string.tooltip_drink_name, R.string.tooltip_drink_image,
                R.string.tooltip_drink_edit_seekbars, R.string.tooltip_picker_in_edit};
        List<Integer> texts = Arrays.asList(textsArray);

        Integer[] gravityArray = {Gravity.RIGHT, Gravity.BOTTOM, Gravity.BOTTOM, Gravity.BOTTOM};
        List<Integer> gravity = Arrays.asList(gravityArray);

        introTipsViewDelegate.viewIntroTips(getContext(), anchors, texts, gravity);
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
        beerSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(beerVolumeSeekBar,
                beerVolume, 50.0f, 500);
        wineSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(wineVolumeSeekBar,
                wineVolume, 25.0f, 200);
        vodkaSeekBarConnector = new DiscreteSeekBarToTickerViewConnector(vodkaVolumeSeekBar,
                vodkaVolume, 10.0f, 100);
        customVolumeSeekBarConnector = new DiscreteSeekBarToTickerViewConnector
                (customVolumeSeekBar, customVolume, 10.0f, 500);
        customPercentageSeekBarConnector = new DiscreteSeekBarToTickerViewConnector
                (customPerCentSeekBar, customPerCent, 5.0f, 100);

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
                presenter.getCurrentDrinkItem().setCustomPercentage((float) value / 100f);
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
        showContentView();
        drinkName.setText(drinkItem.getName());
        Picasso.with(getContext()).load(new File(drinkItem.getImagePath()))
                .error(R.drawable.ic_add_photo).placeholder(R.drawable.ic_add_photo).into(addPhotoView);

        beerSeekBarConnector.setCurrentValue(drinkItem.getBeerVolume());
        wineSeekBarConnector.setCurrentValue(drinkItem.getWineVolume());
        vodkaSeekBarConnector.setCurrentValue(drinkItem.getVodkaVolume());
        customVolumeSeekBarConnector.setCurrentValue(drinkItem.getCustomVolume());
        customPercentageSeekBarConnector.setCurrentValue((int) (100 * drinkItem
                .getCustomPercentage()));
    }

    private void showContentView() {
        emptyView.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(presenter.getCurrentDrinkItem() != null) {
            imagePickerDelegate.handleActivityResult(getActivity(), this, requestCode, resultCode, data,
                    presenter.getCurrentDrinkItem().getId());
        }
    }
}
