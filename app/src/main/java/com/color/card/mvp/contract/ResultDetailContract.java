package com.color.card.mvp.contract;

import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

/**
 * @author yqy
 * @date on 2018/4/3
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface ResultDetailContract {
    interface View extends BaseView {

        void creatEventSuccess(String eventId);

        void postUrineSuccess();

        void postTagSuccess();
    }

    interface Presenter extends BasePresenter {

        void creatEvent();

        void postUrine(String eventId, String detectionType, String value, String valueLevel, long dateTime);

        void postTag(String tag, String eventId);

    }
}
