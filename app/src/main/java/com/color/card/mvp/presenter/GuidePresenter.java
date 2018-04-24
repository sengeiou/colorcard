package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.GuideContract;
import com.color.card.mvp.contract.LoginContract;
import com.color.card.mvp.model.GuideModel;
import com.color.card.mvp.model.LoginModel;

import card.color.basemoudle.util.SPCacheUtils;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/18
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class GuidePresenter extends BasePresenterImpl<GuideContract.View> implements GuideContract.Presenter {

    private GuideModel guideModel;

    public GuidePresenter(GuideContract.View view) {
        super(view);
        guideModel = new GuideModel();
    }

    @Override
    public void detach() {
        super.detach();
        guideModel = null;
    }


    @Override
    public void getUserInfo() {
        guideModel.getUserInfo(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                Log.w("kim", "--->" + result);
                JSONObject data = JSON.parseObject(result);
                if (data != null) {
                    String realname = data.getString("realname");
                    SPCacheUtils.put("realname", realname);
                    String nickname = data.getString("nickname");
                    SPCacheUtils.put("nickname", nickname);
                    String mobile = data.getString("mobile");
                    SPCacheUtils.put("mobile", mobile);
                    String avatar = data.getString("avatar");
                    SPCacheUtils.put("avatar", avatar);
                    String userId = data.getString("userId");
                    SPCacheUtils.put("userId", userId);
                }
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void loginAutomatic() {
        guideModel.getLoginaAutomatic(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                JSONObject data = JSON.parseObject(result);
                if (data != null) {
                    String sessionId = data.getString("sessionId");
                    SPCacheUtils.put("sessionId", sessionId);

                }
                view.loginAutomaticSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }
}
