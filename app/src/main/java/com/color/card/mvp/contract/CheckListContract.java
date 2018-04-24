package com.color.card.mvp.contract;

import com.color.card.entity.CheckData;
import com.color.card.entity.DataListEntity;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/7
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface CheckListContract {
    interface View extends BaseView {

        void getUserDataSuccess(List<CheckData> mlist, int request_code);

        void getUserDataFail();

        void getRecentCheckSuccess(DataListEntity dataListEntity);

        void getCheckCountSuccess(String count);

    }

    interface Presenter extends BasePresenter {

        void getUserDataList(int page, int action);

        void getRecentCheck();

        void getCheckCount();
    }
}
