package com.kamk2k.alkobuddy.view;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.StatusFragmentPresenter;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.view.utils.CircleProgressColorAnimationListener;
import com.kamk2k.alkobuddy.view.utils.IntroTipsViewDelegate;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;
import com.robinhood.ticker.TickerView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jonathanfinerty.once.Once;
import rm.com.clocks.ClockImageView;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusFragment extends MVPFragmentView implements StatusView {

    public static final String TAG = StatusFragment.class.getSimpleName();
    public static final float MAX_CIRCLE_PROGRESS_VALUE = 5f;
    public static final String SHOW_STATUS_FRAGMENT_INTRO = "SHOW_STATUS_FRAGMENT_INTRO";

    @BindView(R.id.promil_text_field)
    TickerView perMileTextView;
    @BindView(R.id.time_to_sober_text_field)
    TickerView timeToSoberTextView;
    @BindView(R.id.clock_view)
    ClockImageView clockImageView;
    @BindView(R.id.circle_progress)
    CircleProgress circleProgress;
    @BindView(R.id.time_to_sober_additional_text)
    TextView timeToSoberAdditionalTextView;
    @BindView(R.id.tooltip_button)
    ImageView tooltipButton;

    @Inject
    StatusFragmentPresenter presenter;
    @Inject
    IntroTipsViewDelegate introTipsViewDelegate;

    private ValueAnimator.AnimatorUpdateListener circleProgressAnimationListener;
    private View rootView;

    public StatusFragment() {
    }

    @Override
    public void displayPerMile(float perMile) {
        int progress = (int) Math.min((perMile * 100) / MAX_CIRCLE_PROGRESS_VALUE, 100);
        changeCircleProgressWithAnimation(progress);
        perMileTextView.setText(String.format("%.2f ", perMile) + getString(R.string.promil));
    }

    @Override
    public void displayTodayTimeToSoberText(String timeToSoberText) {
        timeToSoberAdditionalTextView.setVisibility(View.GONE);
        timeToSoberTextView.setText(timeToSoberText);
    }

    @Override
    public void displayTomorrowTimeToSoberText(String timeToSoberText) {
        timeToSoberAdditionalTextView.setVisibility(View.VISIBLE);
        timeToSoberAdditionalTextView.setText(R.string.tomorrow);
        timeToSoberTextView.setText(timeToSoberText);
    }

    @Override
    public void displayLaterThanTomorrowTimeToSoberText(String timeToSoberText) {
        timeToSoberAdditionalTextView.setVisibility(View.VISIBLE);
        timeToSoberAdditionalTextView.setText(R.string.more_than_a_day);
        timeToSoberTextView.setText("--:--");
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
        rootView = inflater.inflate(R.layout.status_fragment, container, false);
        ButterKnife.bind(this, rootView);
        circleProgressAnimationListener = new CircleProgressColorAnimationListener(circleProgress);
        perMileTextView.setCharacterList(getListForPerMiles());
        timeToSoberTextView.setCharacterList(getListForPerMiles());
        if (!Once.beenDone(Once.THIS_APP_INSTALL, SHOW_STATUS_FRAGMENT_INTRO)) {
            showIntroTooltips();
            Once.markDone(SHOW_STATUS_FRAGMENT_INTRO);
        }
        tooltipButton.setOnClickListener(view -> showIntroTooltips());
        return rootView;
    }

    private void showIntroTooltips() {
        View[] anchorsArray = {timeToSoberTextView, perMileTextView, rootView, rootView};
        List<View> anchors = Arrays.asList(anchorsArray);

        Integer[] textsArray = {R.string.tooltip_time_to_sober, R.string.tooltip_per_mile,
                R.string.tooltip_tab_bar, R.string.tooltip_picker_in_status};
        List<Integer> texts = Arrays.asList(textsArray);

        Integer[] gravityArray = {Gravity.BOTTOM, Gravity.BOTTOM, Gravity.TOP, Gravity.BOTTOM};
        List<Integer> gravity = Arrays.asList(gravityArray);

        introTipsViewDelegate.viewIntroTips(getContext(), anchors, texts, gravity);
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