package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.RelultCheckoutContract;
import com.color.card.mvp.model.ResultCheckoutModel;


import card.color.basemoudle.util.SPCacheUtils;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/4
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultCheckoutPresenter extends BasePresenterImpl<RelultCheckoutContract.View> implements RelultCheckoutContract.Presenter {

    private ResultCheckoutModel resultCheckoutModel;

    public ResultCheckoutPresenter(RelultCheckoutContract.View view) {
        super(view);
        resultCheckoutModel = new ResultCheckoutModel();
    }


    @Override
    public void detach() {
        super.detach();
        resultCheckoutModel = null;
    }

    @Override
    public void creatEvent() {
        resultCheckoutModel.creatEvent(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                JSONObject data = JSON.parseObject(result);
                if (data != null) {
                    String eventId = data.getString("id");
                    view.creatEventSuccess(eventId);
                }

            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void postUrine(String eventId, String detectionType, String value, String valueLevel, long dateTime) {
        resultCheckoutModel.postUrineData(eventId, detectionType, value, valueLevel, dateTime, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                view.postUrineSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }
}



