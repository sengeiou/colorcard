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
public interface UserCreateContract {
    interface View extends BaseView {

        void createUserSuccess();

    }

    interface Presenter extends BasePresenter {

        void createUser(String name, String mobile_ex, String pwd);

    }
}
