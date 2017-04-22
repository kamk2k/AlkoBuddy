package com.corellidev.alcotester.presenter;

import com.corellidev.alcotester.model.UserAlcoState;
import com.corellidev.alcotester.presenter.utils.MVPFragmentPresenter;
import com.corellidev.alcotester.view.StatusView;

/**
 * Created by kksiazek on 29.11.15.
 */
public interface StatusFragmentPresenter extends MVPFragmentPresenter {
    void setMVPView(StatusView mvpView);
    void updateStatus(UserAlcoState userAlcoState);
}
