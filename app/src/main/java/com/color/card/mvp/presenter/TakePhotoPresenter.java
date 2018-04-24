package com.color.card.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.TakePhotoContract;
import com.color.card.mvp.model.TakePhotoModel;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/18
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class TakePhotoPresenter extends BasePresenterImpl<TakePhotoContract.View> implements TakePhotoContract.Presenter {

    private TakePhotoModel takePhotoModel;

    public TakePhotoPresenter(TakePhotoContract.View view) {
        super(view);
        takePhotoModel = new TakePhotoModel();
    }


    @Override
    public void detach() {
        super.detach();
        takePhotoModel = null;
    }

    @Override
    public void creatEvent() {
        takePhotoModel.creatEvent(new ObserverListener<String>() {
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
        takePhotoModel.postUrineData(eventId, detectionType, value, valueLevel, dateTime, new ObserverListener<String>() {
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



