package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller.MainActivityController;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller.StatusToCreatePagerAdapter;
import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller.StatusUpdateService;

import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.pager) ViewPager mViewPager;
    StatusToCreatePagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityController.init(this);
        setContentView(R.layout.activity_main);
        mFragmentPagerAdapter = new StatusToCreatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        startUpdateService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopUpdateService();
        MainActivityController.saveUserStateFromFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startUpdateService() {
        Intent intent = new Intent(this, StatusUpdateService.class);
        startService(intent);
    }

    private void stopUpdateService() {
        Intent intent = new Intent(this, StatusUpdateService.class);
        stopService(intent);
    }

}
