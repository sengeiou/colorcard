package com.color.card.mvp.contract;

import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface RegisterContract {
    interface View extends BaseView {

        void getPhoneCodeSuccess();

        void checkPhoneCodeSuccess();
    }

    interface Presenter extends BasePresenter {

        void getPhoneCode(String phone);

        void checkPhoneCode(String phone, String code);

    }
}
