package com.color.card.mvp.presenter;

import android.util.Log;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.UserCreateContract;
import com.color.card.mvp.model.UserCreateModel;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class UserCreatePresenter extends BasePresenterImpl<UserCreateContract.View> implements UserCreateContract.Presenter {

    private UserCreateModel userCreateModel;

    public UserCreatePresenter(UserCreateContract.View view) {
        super(view);
        userCreateModel = new UserCreateModel();
    }


    @Override
    public void detach() {
        super.detach();
        userCreateModel = null;
    }

    @Override
    public void createUser(String name, String mobile_ex, String pwd) {
        userCreateModel.createUser(name, mobile_ex, pwd, new ObserverListener() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(Object result) {
                view.createUserSuccess();
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });

    }
}


