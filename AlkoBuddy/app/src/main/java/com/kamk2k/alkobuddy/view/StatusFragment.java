package com.kamk2k.alkobuddy.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.view.utils.CircleProgressColorAnimationListener;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;
import com.robinhood.ticker.TickerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rm.com.clocks.ClockImageView;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusFragment extends MVPFragmentView implements StatusView {

    public static final String TAG = StatusFragment.class.getSimpleName();
    public static final float MAX_CIRCLE_PROGRESS_VALUE = 5f;

    @BindView(R.id.promil_text_field)
    TickerView perMileTextView;
    @BindView(R.id.time_to_sober_text_field)
    TickerView timeToSoberTextView;
    @BindView(R.id.clock_view)
    ClockImageView clockImageView;
    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;

    @Inject
    StatusFragmentPresenter presenter;

    private ValueAnimator.AnimatorUpdateListener circleProgressAnimationListener;

    public StatusFragment() {
    }

    @Override
    public void displayPerMile(float perMile) {
        int progress = (int) Math.min((perMile * 100) / MAX_CIRCLE_PROGRESS_VALUE, 100);
        changeCircleProgressWithAnimation(progress);
        perMileTextView.setText(String.format("%.2f ", perMile) + getString(R.string.promil));
    }

    @Override
    public void displayTimeToSoberText(String timeToSoberText) {
        timeToSoberTextView.setText(timeToSoberText);
    }

    @Override
    public void displaySoberTime(int hours, int minutes) {
        clockImageView.animateToTime(hours, minutes);
    }

    @Override
    public void displayOver24hSoberTime() {
        clockImageView.animateIndeterminate();
    }

    private void changeCircleProgressWithAnimation(int progress) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(circleProgress.getProgress(), progress);
        valueAnimator.setInterpolator(new AnticipateOvershootInterpolator(5f));
        valueAnimator.addUpdateListener(circleProgressAnimationListener);
        valueAnimator.setDuration(600);
        valueAnimator.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.bind(this, root);
        circleProgressAnimationListener = new CircleProgressColorAnimationListener(circleProgress);
        perMileTextView.setCharacterList(getListForPerMiles());
        timeToSoberTextView.setCharacterList(getListForPerMiles());
        return root;
    }

    public static char[] getListForPerMiles() {
        final int indexOf0 = (int) '0';
        final int start = 32;
        final int end = 256;
        final char[] charList = new char[end - start + 2];
        for (int i = start; i < indexOf0; i++) {
            charList[i - start] = (char) i;
        }
        charList[indexOf0 - start] = 0;
        for (int i = indexOf0 + 1; i < end + 1; i++) {
            charList[i - start] = (char) (i - 1);
        }
        charList[charList.length - 1] = '‰';
        return charList;
    }

    @Override
    public void injectFragment(ApplicationComponent component) {
        component.inject(this);
        presenter.setMVPView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}