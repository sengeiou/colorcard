package com.color.card.mvp.presenter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.color.card.entity.CheckData;
import com.color.card.entity.DataListEntity;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.MainActivityContract;
import com.color.card.mvp.model.MainActivityModel;
import com.color.card.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import card.color.basemoudle.util.ParameterUtils;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/1
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class MainActivityPresenter extends BasePresenterImpl<MainActivityContract.View> implements MainActivityContract.Presenter {

    private MainActivityModel mainActivityModel;

    private int currentIndex;

    public MainActivityPresenter(MainActivityContract.View view) {
        super(view);
        mainActivityModel = new MainActivityModel();
    }


    @Override
    public void detach() {
        super.detach();
        mainActivityModel = null;
    }


    @Override
    public void showFragment(FragmentManager fragmentManager, int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        view.hideFragment(fragmentManager);
        //注意这里设置位置
        currentIndex = index;
        switch (index) {
            case ParameterUtils.FRAGMENT_ONE:
                view.showCheckFragment(ft);
                break;
            case ParameterUtils.FRAGMENT_TWO:
                view.showMyQaFragment(ft);
                break;
            default:
                view.showCheckFragment(ft);
                break;
        }
        ft.commitAllowingStateLoss();
        fragmentManager = null;
        ft = null;
    }

    @Override
    public int currentIndex() {
        return currentIndex;
    }
}