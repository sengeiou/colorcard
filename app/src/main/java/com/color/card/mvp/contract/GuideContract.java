package com.color.card.mvp.contract;

import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

/**
 * @author yqy
 * @date on 2018/4/18
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface GuideContract {
    interface View extends BaseView {
        void getUserInfoSuccess();

        void loginAutomaticSuccess();
    }


    interface Presenter extends BasePresenter {

        void getUserInfo();

        void loginAutomatic();

    }
}