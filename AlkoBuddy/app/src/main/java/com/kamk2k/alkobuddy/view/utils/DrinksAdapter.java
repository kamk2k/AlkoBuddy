package com.kamk2k.alkobuddy.view.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by PC on 2015-02-23.
 */
public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.ViewHolder> {

    // TODO: 09.01.16 Add persisting (Realm maybe?)
    ArrayList<DrinkItem> drinksListContent = new ArrayList<>();
    Context context;
    MainActivityPresenter mainActivityPresenter;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;

        public ViewHolder(ImageView imageView) {
            super(imageView);
            mImageView = imageView;
        }

    }

    @Inject
    public DrinksAdapter(Context context, MainActivityPresenter mainActivityPresenter) {
        this.context = context;
        this.mainActivityPresenter = mainActivityPresenter;
    }

    public void addItem(DrinkItem drink) {
        drinksListContent.add(drink);
    }

    public Object getItem(int position) {
        if(position < drinksListContent.size()) {
            return drinksListContent.get(position);
        } else {
            return null;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picker_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrinkItem clickedDrink = drinksListContent.get(position);
                mainActivityPresenter.drinkClicked(clickedDrink);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return drinksListContent.size();
    }
}
