package com.color.card.mvp.contract;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.color.card.entity.CheckData;
import com.color.card.entity.DataListEntity;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.base.BaseView;

import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/1
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public interface MainActivityContract {
    interface View extends BaseView {

        void hideFragment(FragmentManager fragmentManager);

        void showCheckFragment(FragmentTransaction ft);

        void showMyQaFragment(FragmentTransaction ft);

    }

    interface Presenter extends BasePresenter {

        void showFragment(FragmentManager fragmentManager, int index);

        int currentIndex();
    }
}
