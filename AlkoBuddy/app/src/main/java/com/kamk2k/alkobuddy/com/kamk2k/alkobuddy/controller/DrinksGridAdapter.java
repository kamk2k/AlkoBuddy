package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.DrinkItem;

import java.util.ArrayList;

/**
 * Created by PC on 2015-02-23.
 */
public class DrinksGridAdapter extends BaseAdapter {

    ArrayList<DrinkItem> drinksListContent = new ArrayList<DrinkItem>();
    Context mContext;

    public DrinksGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addItem(DrinkItem drink) {
        drinksListContent.add(drink);
    }

    @Override
    public int getCount() {
        return drinksListContent.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < drinksListContent.size()) {
            return drinksListContent.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.picker_item, parent, false);
        } else {
            return convertView;
        }
    }
}
