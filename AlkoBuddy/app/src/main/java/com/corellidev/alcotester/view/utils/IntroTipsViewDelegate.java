package com.corellidev.alcotester.view.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.github.xizzhu.simpletooltip.ToolTip;
import com.github.xizzhu.simpletooltip.ToolTipView;
import com.corellidev.alcotester.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by kksiazek on 25.03.17.
 */

public class IntroTipsViewDelegate {

    List<ToolTipView> toolTipViews = new ArrayList<>();

    @Inject
    public IntroTipsViewDelegate() {
    }

    public void viewIntroTips(Context context, @NonNull List<View> anchors, @NonNull
            List<Integer> tipsTextsIds, @NonNull List<Integer> gravity) {
        if(anchors.size() != tipsTextsIds.size() || tipsTextsIds.size() != gravity.size()) {
            throw new IllegalArgumentException("You have to provide same number of anchors, texts" +
                    " and gravity values");
        }
        for(int i = 0; i < anchors.size(); i++) {
            View anchor = anchors.get(i);
            String text = context.getString(tipsTextsIds.get(i));
            int numberOfLines = StringUtils.countMatches(text, "\n") + 1;
            ToolTip toolTip = new ToolTip.Builder()
                    .withText(text)
                    .withTextSize(50)
                    .withCornerRadius(40f)
                    .withPadding(16, 16, 16, 16)
                    .withTextGravity(Gravity.CENTER)
                    .withLines(numberOfLines)
                    .withBackgroundColor(ContextCompat.getColor(context, R.color.accent))
                    .build();
            ToolTipView toolTipView = new ToolTipView.Builder(context)
                    .withAnchor(anchor)
                    .withToolTip(toolTip)
                    .withGravity(gravity.get(i))
                    .build();
            toolTipView.show();
            toolTipViews.add(toolTipView);
            toolTipView.setOnToolTipClickedListener(toolTipView1 -> {
                for(ToolTipView tipView : toolTipViews) {
                    tipView.remove();
                }
            });
        }
    }

}
