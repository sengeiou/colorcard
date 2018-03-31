package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.LoginContract;
import com.color.card.mvp.model.LoginModel;

import card.color.basemoudle.util.SPCacheUtils;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/3/27
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View view) {
        super(view);
        loginModel = new LoginModel();
    }

    @Override
    public void detach() {
        super.detach();
        loginModel = null;
    }

    @Override
    public void login(String phone, String code, String password) {
        loginModel.phoneCodeLogin(phone, code, password, new ObserverListener<String>() {
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
                view.phoneCodeLoginSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }
}
