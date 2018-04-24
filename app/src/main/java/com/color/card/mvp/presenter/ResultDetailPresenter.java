package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.RegisterContract;
import com.color.card.mvp.contract.ResultDetailContract;
import com.color.card.mvp.model.RegisterModel;
import com.color.card.mvp.model.ResultDetailModel;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/3
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultDetailPresenter extends BasePresenterImpl<ResultDetailContract.View> implements ResultDetailContract.Presenter {

    private ResultDetailModel resultDetailModel;

    public ResultDetailPresenter(ResultDetailContract.View view) {
        super(view);
        resultDetailModel = new ResultDetailModel();
    }


    @Override
    public void detach() {
        super.detach();
        resultDetailModel = null;
    }

    @Override
    public void creatEvent() {
        resultDetailModel.creatEvent(new ObserverListener<String>() {
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
        resultDetailModel.postUrineData(eventId, detectionType, value, valueLevel, dateTime, new ObserverListener<String>() {
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

    @Override
    public void postTag(String tag, String eventId) {
        resultDetailModel.postUriTag(tag, eventId, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                view.postTagSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }
}


