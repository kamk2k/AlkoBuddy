package com.kamk2k.alkobuddy.view.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.logic.AlcoholInDrinkCalculator;
import com.kamk2k.alkobuddy.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by PC on 2015-02-23.
 */
public class DrinksAdapter extends RealmRecyclerViewAdapter<DrinkItem, RecyclerView.ViewHolder> implements DrinkListItemSelector {

    private static final String TAG = "DrinksAdapter";

    public static final int DRINK_ITEM_VIEW_TYPE = 1;
    public static final int SELECTED_DRINK_VIEW_TYPE = 2;
    public static final int ADD_NEW_ITEM_VIEW_TYPE = 3;
    public static final float MAX_DRINK_STRENGTH_ALCO_WEIGHT_IN_G = 32f;

    Context context;
    MainActivityPresenter mainActivityPresenter;
    private int selectedDrinkPosition = -1;

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = (sharedPreferences, key) -> {
        if(key.equals(MainActivity.SHARED_PREF_IS_IN_REMOVE_MODE)) {
            notifyDataSetChanged();
        }
    };

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.drink_image)
        ImageView drinkImage;
        @BindView(R.id.drink_name)
        TextView drinkName;
        @BindView(R.id.drink_strength_rating_bar)
        RatingBar drinkStrengthRatingBar;
        @BindView(R.id.remove_icon)
        ImageView removeIcon;

        public DrinkViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class AddNewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.add_new_item)
        LinearLayout addNewItem;
        @BindView(R.id.add_new_icon)
        ImageView addNewIcon;
        @BindView(R.id.add_new_text)
        TextView addNewText;

        public AddNewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public DrinksAdapter(Context context, MainActivityPresenter mainActivityPresenter, OrderedRealmCollection<DrinkItem> data) {
        super(context, data, true);
        this.context = context;
        this.mainActivityPresenter = mainActivityPresenter;
        mainActivityPresenter.setDrinkListItemSelector(this);
        registerRemoveModeChangeListener(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch(viewType) {
            case DRINK_ITEM_VIEW_TYPE :
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_drink_item, parent, false);
                return new DrinkViewHolder(v);
            case SELECTED_DRINK_VIEW_TYPE :
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_selected_drink_item, parent, false);
                return new DrinkViewHolder(v);
            case ADD_NEW_ITEM_VIEW_TYPE :
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_add_new_item, parent, false);
                return new AddNewViewHolder(v);
            default :
                Log.e(TAG, "Wrong view type. Using default view");
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_drink_item, parent, false);
                return new DrinkViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof DrinkViewHolder) {
            DrinkViewHolder drinkViewHolder = (DrinkViewHolder)holder;
            DrinkItem itemToDisplay = getItem(position);
            drinkViewHolder.drinkName.setText(itemToDisplay.getName());
            drinkViewHolder.drinkStrengthRatingBar.setProgress(Math.round(drinkViewHolder
                    .drinkStrengthRatingBar.getMax() * (AlcoholInDrinkCalculator.calculateAlcoholWeightInDrink(itemToDisplay) /
                    MAX_DRINK_STRENGTH_ALCO_WEIGHT_IN_G)));
            Picasso.with(context).load(new File(itemToDisplay.getImagePath()))
                    .error(R.drawable.beer_icon).placeholder(R.drawable.beer_icon)
                    .into(drinkViewHolder.drinkImage);
            if(isInRemoveMode()) {
                drinkViewHolder.removeIcon.setVisibility(View.VISIBLE);
            } else {
                drinkViewHolder.removeIcon.setVisibility(View.GONE);
            }
            drinkViewHolder.cardView.setOnClickListener(v -> {
                if(isInRemoveMode()) {
                    mainActivityPresenter.drinkRemoveClicked(itemToDisplay);
                } else {
                    mainActivityPresenter.drinkClicked(itemToDisplay);
                }
            });
        } else if(holder instanceof AddNewViewHolder) {
            AddNewViewHolder addNewViewHolder = (AddNewViewHolder) holder;
            addNewViewHolder.cardView.setOnClickListener(view -> mainActivityPresenter.addNewDrinkClicked());
        }
    }

    private boolean isInRemoveMode() {
        return context.getSharedPreferences(MainActivity.OPTIONS_SHARED_PREFERENCES, 0)
                .getBoolean(MainActivity.SHARED_PREF_IS_IN_REMOVE_MODE, false);
    }

    private void registerRemoveModeChangeListener(Context context) {
        context.getSharedPreferences(MainActivity.OPTIONS_SHARED_PREFERENCES, 0)
                .registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void selectItem(DrinkItem drinkItem) {
        selectedDrinkPosition = getData().indexOf(drinkItem);
        notifyItemChanged(selectedDrinkPosition);
    }

    @Override
    public void clearSelection() {
        selectedDrinkPosition = -1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1) {
            return ADD_NEW_ITEM_VIEW_TYPE;
        } else if(position == selectedDrinkPosition) {
            return SELECTED_DRINK_VIEW_TYPE;
        } else {
            return DRINK_ITEM_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
