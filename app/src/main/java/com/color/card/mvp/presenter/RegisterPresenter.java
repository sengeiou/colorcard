package com.color.card.mvp.presenter;

import android.util.Log;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.LoginContract;
import com.color.card.mvp.contract.RegisterContract;
import com.color.card.mvp.model.LoginModel;
import com.color.card.mvp.model.RegisterModel;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class RegisterPresenter extends BasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {

    private RegisterModel registerModel;

    public RegisterPresenter(RegisterContract.View view) {
        super(view);
        registerModel = new RegisterModel();
    }

    @Override
    public void getPhoneCode(String phone) {
        registerModel.getPhoneCode(phone, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String s) {
                view.getPhoneCodeSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void checkPhoneCode(String phone, String code) {
        registerModel.checkPhoneCode(phone, code, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                view.checkPhoneCodeSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }


    @Override
    public void detach() {
        super.detach();
        registerModel = null;
    }
}

