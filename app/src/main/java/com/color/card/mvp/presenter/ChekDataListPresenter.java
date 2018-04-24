package com.color.card.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.color.card.entity.CheckData;
import com.color.card.entity.DataListEntity;
import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BasePresenterImpl;
import com.color.card.mvp.contract.CheckListContract;
import com.color.card.mvp.contract.MainActivityContract;
import com.color.card.mvp.model.CheckDataListModel;
import com.color.card.mvp.model.MainActivityModel;
import com.color.card.util.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @date on 2018/4/7
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ChekDataListPresenter extends BasePresenterImpl<CheckListContract.View> implements CheckListContract.Presenter {

    private CheckDataListModel checkDataListModel;

    public ChekDataListPresenter(CheckListContract.View view) {
        super(view);
        checkDataListModel = new CheckDataListModel();
    }


    @Override
    public void detach() {
        super.detach();
        checkDataListModel = null;
    }


    @Override
    public void getUserDataList(int page, final int action) {
        checkDataListModel.getUserDataList(page, new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                List<DataListEntity> listEntities = JSON.parseArray(result, DataListEntity.class);
                if (listEntities != null && listEntities.size() > 0) {
                    List<CheckData> checkData = new ArrayList<>();
                    for (int i = 0; i < listEntities.size(); i++) {
                        if (listEntities.get(i).getBloodglucoses() != null && listEntities.get(i).getBloodglucoses().size() > 0) {
                            for (DataListEntity.Bloodglucoses bloodglucoses : listEntities.get(i).getBloodglucoses()) {
                                CheckData data = new CheckData();
                                data.setValue(bloodglucoses.getValue());
                                data.setValueLevel(bloodglucoses.getValueLevel());
                                data.setTakeTime(listEntities.get(i).getEvent().getTag());
                                data.setTime(DateUtil.getDateByLong(bloodglucoses.getTakeTime().getTime()));
                                checkData.add(data);
                            }

                        }

                        if (listEntities.get(i).getUrines() != null && listEntities.get(i).getUrines().size() > 0) {
                            for (DataListEntity.Urines urines : listEntities.get(i).getUrines()) {
                                CheckData data = new CheckData();
                                data.setValue(urines.getValue());
                                data.setValueLevel(urines.getValueLevel());
                                data.setTakeTime(listEntities.get(i).getEvent().getTag());
                                data.setTime(DateUtil.getDateByLong(urines.getTakeTime().getTime()));
                                checkData.add(data);
                            }
                        }
                    }
                    view.getUserDataSuccess(sortData(checkData), action);
                } else {
                    view.getUserDataFail();
                }
            }

            @Override
            public void onError(String msg) {
                view.getUserDataFail();
                view.showTip(msg);
            }
        });
    }


    @Override
    public void getRecentCheck() {
        checkDataListModel.getRecentCheck(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                DataListEntity dataListEntity = JSON.parseObject(result, DataListEntity.class);
                if (dataListEntity != null) {
                    view.getRecentCheckSuccess(dataListEntity);
                }
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }

    @Override
    public void getCheckCount() {
        checkDataListModel.getCheckCount(new ObserverListener<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                addDisposable(disposable);
            }

            @Override
            public void onNext(String result) {
                if (result == null) {
                    view.getCheckCountSuccess("0");
                } else {
                    view.getCheckCountSuccess(result);
                }
            }

            @Override
            public void onError(String msg) {
                view.showTip(msg);
            }
        });
    }


    private List<CheckData> sortData(List<CheckData> mList) {
        Collections.sort(mList, new Comparator<CheckData>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(CheckData lhs, CheckData rhs) {
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (lhs.getTime().before(rhs.getTime())) {
                    return 1;
                }
                return -1;
            }
        });
        return mList;
    }
}