package com.color.card.mvp.base;


import android.text.TextUtils;
import android.util.Log;

import com.color.card.listener.ObserverListener;

import java.util.HashMap;
import java.util.Map;

import card.color.httpmoudle.entity.ResponseInfo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BaseModel {

    protected void apiSubscribe(Observable observable, final ObserverListener listener) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.onSubscribe(d);
                    }

                    @Override
                    public void onNext(ResponseInfo responseInfo) {
                        if (responseInfo != null) {
                            Log.d("result======", responseInfo.toString());
                        }
                        if (responseInfo.isSuccess()) {
                            listener.onNext(responseInfo.getModel());
                        } else {
                            listener.onError(responseInfo.getMsgInfo());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!TextUtils.isEmpty(e.getMessage())) {
                            listener.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.d("complete=====", "");
                    }
                });
    }

    protected Map<String, String> getHeadersMap() {
        Map<String, String> params = new HashMap<>();
//        params.put("User-Agent", AppUtils.getUserAgent(AppUtils.getAndroidUserAgent(BaseApplication.getInstance())) + " Store/"
//                + "xxd");
//        params.put("x-smartsa-uid", DeviceUtils.getIdentifier());
//        params.put("x-smartsa-push-reg-id", JPushInterface.getRegistrationID(BaseApplication.appContext));
//        String ticket = (String) SPCacheUtils.get("ticket", ConstantUtils.CACHE_NULL);
//        if (!TextUtils.isEmpty(ticket) && !ConstantUtils.CACHE_NULL.equals(ticket)) {
//            params.put("x-smartsa-counsellor-ticket", ticket);
//        }
        return params;
    }

}
