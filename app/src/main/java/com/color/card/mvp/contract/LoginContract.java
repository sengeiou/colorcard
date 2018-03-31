package com.color.card.mvp.contract;

import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

/**
 * @author yqy
 * @date on 2018/3/27
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface LoginContract {
    interface View extends BaseView {
        void phoneCodeLoginSuccess();
    }

    interface Presenter extends BasePresenter {

        void login(String phone, String code, String password);

    }
}
