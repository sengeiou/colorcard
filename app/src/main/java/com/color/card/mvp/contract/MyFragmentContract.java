package com.color.card.mvp.contract;

import com.color.card.entity.BindInfo;
import com.color.card.entity.TeamEntity;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/8
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface MyFragmentContract {
    interface View extends BaseView {

        void bindHave(BindInfo bindInfo);

        void agentSuccess(String id, String mobilePhone);

        void bindAgentSuccess(String mobile);

        void getUserListSuccess(List<TeamEntity.User> users, int action);

        void updateUserInfoSuccess();
    }

    interface Presenter extends BasePresenter {
        void getBind();

        void getAgent(String phone);

        void bindAgent(String id, String mobile);

        void getUserList(String page, String employeeMobile, int action);

        void updateUserInfo(String userId, String name, String mobile, String avatar);
    }
}
