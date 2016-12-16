package com.kamk2k.alkobuddy.view.utils;

import android.content.Context;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by PC on 2015-02-23.
 */
public class DrinksAdapter extends RealmRecyclerViewAdapter<DrinkItem, RecyclerView.ViewHolder> {

    private static final String TAG = "DrinksAdapter";

    public static final int DRINK_ITEM_VIEW_TYPE = 1;
    public static final int ADD_NEW_ITEM_VIEW_TYPE = 2;

    Context context;
    MainActivityPresenter mainActivityPresenter;

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.drink_image)
        ImageView drinkImage;
        @BindView(R.id.drink_name)
        TextView drinkName;
        @BindView(R.id.drink_strength_rating_bar)
        RatingBar drinkStrengthRatingBar;

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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch(viewType) {
            case DRINK_ITEM_VIEW_TYPE :
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.picker_drink_item, parent, false);
                return new DrinkViewHolder(view);
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
            drinkViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrinkItem clickedDrink = getItem(position);
                    mainActivityPresenter.drinkClicked(clickedDrink);
                }
            });
        } else if(holder instanceof AddNewViewHolder) {
            AddNewViewHolder addNewViewHolder = (AddNewViewHolder) holder;
            addNewViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivityPresenter.addNewDrinkClicked();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1) {
            return ADD_NEW_ITEM_VIEW_TYPE;
        } else {
            return DRINK_ITEM_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }
}
