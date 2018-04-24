package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.color.card.entity.BindInfo;
import com.color.card.entity.TeamEntity;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.MyFragmentContract;
import com.color.card.mvp.model.MyFragmentModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/8
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyuinnobuddy.com
 */
public class MyFragmentPresenter extends BasePresenterImpl<MyFragmentContract.View> implements MyFragmentContract.Presenter {

    private MyFragmentModel myFragmentModel;

    public MyFragmentPresenter(MyFragmentContract.View view) {
        super(view);
        myFragmentModel = new MyFragmentModel();
    }


    @Override
    public void detach() {
        super.detach();
        myFragmentModel = null;
    }


    @Override
    public void getBind() {
        myFragmentModel.getBind(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {

                BindInfo bindInfo = JSON.parseObject(result, BindInfo.class);
                if (bindInfo != null) {
                    view.bindHave(bindInfo);
                }
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void getAgent(final String phone) {
        myFragmentModel.getAgent(phone, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                JSONObject data = JSON.parseObject(result);
                if (data != null) {
                    String clerkId = data.getString("clerkId");
                    view.agentSuccess(clerkId, phone);
                }
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void bindAgent(String id, final String mobile) {
        myFragmentModel.bindAgent(id, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                view.bindAgentSuccess(mobile);
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void getUserList(String page, String employeeMobile, final int action) {
        myFragmentModel.getUserList(page, employeeMobile, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                List<TeamEntity> teamEntity = JSON.parseArray(result, TeamEntity.class);
                List<TeamEntity.User> users = new ArrayList<>();
                if (teamEntity != null && teamEntity.size() > 0) {
                    for (int i = 0; i < teamEntity.size(); i++) {
                        teamEntity.get(i).getUser().setCreatedTime(teamEntity.get(i).getUserVendor().getCreatedTime());
                        users.add(teamEntity.get(i).getUser());
                    }
                }
                view.getUserListSuccess(users, action);
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void updateUserInfo(String userId, String name, String mobile, String avatar) {
        myFragmentModel.updateUserInfo(userId, name, mobile, avatar, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                Log.w("kim", "====-》" + result);
                view.updateUserInfoSuccess();
            }

            @Override
            public void onError(String msg) {
                Log.w("kim", "---》" + msg);
                view.showTip(msg);
            }
        });
    }
}



