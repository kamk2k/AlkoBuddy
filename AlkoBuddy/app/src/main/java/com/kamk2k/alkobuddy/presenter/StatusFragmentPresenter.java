package com.kamk2k.alkobuddy.presenter;

import com.kamk2k.alkobuddy.presenter.utils.MVPFragmentPresenter;
import com.kamk2k.alkobuddy.view.StatusView;

/**
 * Created by kksiazek on 29.11.15.
 */
public interface StatusFragmentPresenter extends MVPFragmentPresenter {
    void setMVPView(StatusView mvpView);
}
