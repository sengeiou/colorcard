package com.color.card.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.color.card.MainActivity;
import com.color.card.R;
import com.color.card.entity.CheckData;
import com.color.card.entity.CheckDataByDateEntity;
import com.color.card.entity.DataListEntity;
import com.color.card.mvp.contract.CheckListContract;
import com.color.card.mvp.presenter.ChekDataListPresenter;
import com.color.card.ui.activity.ResultCheckActivity;
import com.color.card.ui.activity.TakePhotoActivity;
import com.color.card.ui.adapter.CheckDataAdapter;
import com.color.card.ui.adapter.base.CommonAdapter;
import com.color.card.ui.adapter.base.ViewHolder;
import com.color.card.ui.adapter.wrapper.HeaderAndFooterWrapper;
import com.color.card.ui.adapter.wrapper.LoadMoreWrapper;
import com.color.card.ui.base.UIFragment;
import com.color.card.ui.widget.HealthPointView;
import com.color.card.ui.widget.LoadMoreRecyclerView;
import com.color.card.ui.widget.NoScrollLinearLayoutManager;
import com.color.card.ui.widget.decoration.HorizontalDividerItemDecoration;
import com.color.card.util.BloodRuleUtil;
import com.color.card.util.DateUtil;
import com.color.card.util.DensityUtils;
import com.color.card.util.ToastUtils;
import com.example.lyr.camera.OpencvCameraActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import card.color.basemoudle.util.ParameterUtils;

/**
 * @author yqy
 * @date on 2018/4/7
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class CheckDataFragment extends UIFragment<CheckListContract.Presenter> implements CheckListContract.View {
    private HealthPointView healthPointView;

    private CardView civ_camera;

    private LoadMoreRecyclerView rv_blood;

    private LoadMoreWrapper<CheckDataByDateEntity> loadMoreWrapper;

    List<CheckDataByDateEntity> checkDataByDateEntities;

    List<CheckData> checkDataList;

    private NoScrollLinearLayoutManager mLayoutManager;

    private CommonAdapter<CheckDataByDateEntity> mAdapter;

    private LinearLayout ll_no_data;

    private HeaderAndFooterWrapper mHeader;

    private View headView;

    private SwipeRefreshLayout srlt_qa;

    private int mPage = 0;

    private LinearLayout ll_title;

    private TextView tv_handle;

    private TextView tv_count;

    private MainActivity mainActivity;

    private String showColor;

    private TextView tv_check;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    protected View getLayoutView() {
        return mActivity.getLayoutInflater().inflate(R.layout.fragment_check_list, null);
    }


    @Override
    public void onUserVisible() {
        super.onUserVisible();
        presenter.getRecentCheck();
        if (!TextUtils.isEmpty(showColor)) {
            mainActivity.getLl_title().setBackgroundColor(Color.parseColor(showColor));
            mainActivity.getTv_qick_check().setTextColor(Color.parseColor(showColor));
            ll_title.setBackgroundColor(Color.parseColor(showColor));
        }
        if (rv_blood != null) {
            rv_blood.setFocusable(false);
        }
    }


    @Override
    protected void initView(View rootView) {
        rv_blood = (LoadMoreRecyclerView) rootView.findViewById(R.id.rv_blood);
        srlt_qa = rootView.findViewById(R.id.srlt_qa);
        srlt_qa.setColorSchemeColors(getResources().getColor(R.color.app_main_color));
        mLayoutManager = new NoScrollLinearLayoutManager(getActivity());
        mLayoutManager = new NoScrollLinearLayoutManager(getActivity());
        mLayoutManager.setScrollEnabled(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_blood.setLayoutManager(mLayoutManager);
        ll_no_data = rootView.findViewById(R.id.ll_no_data);
        rv_blood.setFocusable(false);
        initAdapter();
        initHeaderAndFooter();

        presenter.getUserDataList(mPage, ParameterUtils.PULL_DOWN);
        srlt_qa.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage =0;
                presenter.getUserDataList(mPage, ParameterUtils.PULL_DOWN);
            }
        });

    }


    private void initHeaderAndFooter() {
        headView = getActivity().getLayoutInflater().inflate(R.layout.layout_first_page, null, false);
        healthPointView = headView.findViewById(R.id.hpv);
        civ_camera = headView.findViewById(R.id.civ_camera);
        civ_camera.setBackgroundResource(R.drawable.ic_check_backgroup);
        ll_title = headView.findViewById(R.id.ll_title);
        tv_check = headView.findViewById(R.id.tv_check);
        mHeader = new HeaderAndFooterWrapper(mAdapter);
        mHeader.addHeaderView(headView);
        tv_count = headView.findViewById(R.id.tv_count);
        tv_handle = headView.findViewById(R.id.tv_handle);
        loadMoreWrapper = new LoadMoreWrapper<>(mHeader);
        rv_blood.setAdapter(loadMoreWrapper);
        rv_blood.SetOnLoadMoreLister(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void OnLoad() {
                if (srlt_qa.isRefreshing()) {
                    rv_blood.loadComplete(true);
                    return;
                }
                mPage = mPage + 1;
                presenter.getUserDataList(mPage, ParameterUtils.PULL_UP);
            }
        });
//        tv_check.setText("立即检测");
    }

    private void initAdapter() {
        checkDataByDateEntities = new ArrayList<>();
        checkDataList = new ArrayList<>();
        mAdapter = new CommonAdapter<CheckDataByDateEntity>(getActivity(), R.layout.item_checdata, checkDataByDateEntities) {
            @Override
            protected void convert(ViewHolder holder, CheckDataByDateEntity checkData, int position) {
                try {
                    if (DateUtil.IsToday(checkData.getShowDate())) {
                        holder.setText(R.id.tv_date, "今天");
                    } else if (DateUtil.IsYesterday(checkData.getShowDate())) {
                        holder.setText(R.id.tv_date, "昨日");
                    } else {
                        if (!TextUtils.isEmpty(checkData.getShowDate())) {
                            holder.setText(R.id.tv_date, checkData.getShowDate().substring(8, 10) + "/");
                            holder.setText(R.id.tv_month, checkData.getShowDate().substring(6, 7) + "月");
                        }

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                RecyclerView rv = holder.getView(R.id.rv_blood);
                // 关键代码
                //////////////////////////////////////////////////
                CheckDataAdapter checkDataAdapter = new CheckDataAdapter(mContext);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv.setLayoutManager(linearLayoutManager);
                rv.setAdapter(checkDataAdapter);
                if (checkDataByDateEntities.get(position - 1).getCheckData() != null && checkDataByDateEntities.get(position - 1).getCheckData().size() > 0) {
                    checkDataAdapter.setComments(checkDataByDateEntities.get(position - 1).getCheckData());
                }
            }
        };
    }

    @Override
    protected void initEvent() {
        civ_camera.setOnClickListener(this);
        tv_handle.setOnClickListener(this);
    }

    @Override
    public CheckListContract.Presenter initPresenter() {
        return new ChekDataListPresenter(this);
    }


    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        presenter.getUserDataList(mPage, ParameterUtils.PULL_UP);
        presenter.getRecentCheck();
    }

    @Override
    public void onUserInvisible() {
        if (srlt_qa != null && srlt_qa.isRefreshing()) {
            srlt_qa.setRefreshing(false);
        }
        super.onUserInvisible();
    }

    @Override
    public void onFirstUserInvisible() {
        if (srlt_qa != null && srlt_qa.isRefreshing()) {
            srlt_qa.setRefreshing(false);
        }
        super.onFirstUserInvisible();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.getCheckCount();
        mPage = 0;
        presenter.getUserDataList(mPage, ParameterUtils.PULL_DOWN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qick_check:
                break;
            case R.id.tv_mine:
                break;
            case R.id.civ_camera:
                startActivity(new Intent(getActivity(), OpencvCameraActivity.class));
                break;
            case R.id.tv_handle:
                startActivity(new Intent(getActivity(), ResultCheckActivity.class));
            default:
                break;
        }
    }


    @Override
    public void getUserDataSuccess(List<CheckData> mlist, int request_state) {
        if (presenter != null) {
            mLayoutManager.setScrollEnabled(true);
            int len = mlist.size();
            if (request_state == ParameterUtils.PULL_DOWN) {
                //下拉刷新
                if (len <= 0) {
                    rv_blood.loadComplete(true);
                    mLayoutManager.setScrollEnabled(false);
                }
                checkDataList.clear();
                checkDataList.addAll(mlist);
                checkDataByDateEntities.clear();
                checkDataByDateEntities.addAll(CheckDataByDateEntity.sortOutByDate(checkDataList));
                srlt_qa.setRefreshing(false);
                loadMoreWrapper.notifyDataSetChanged();
            } else if (request_state == ParameterUtils.PULL_UP) {
                //上拉加载
                if (len <= 0) {
                    //没有更多内容
                    if (mPage > 1) {
                        mPage = mPage - 1;
                    }
                    rv_blood.loadComplete(false);
                } else {
                    rv_blood.loadComplete(true);
                    checkDataList.addAll(mlist);
                    checkDataByDateEntities.clear();
                    checkDataByDateEntities.addAll(CheckDataByDateEntity.sortOutByDate(checkDataList));
                    loadMoreWrapper.notifyDataSetChanged();
                }
            }
        }
        mlist = null;
    }

    @Override
    public void getUserDataFail() {
        ll_no_data.setVisibility(View.VISIBLE);
        rv_blood.loadComplete(true);
    }


    @Override
    public void getRecentCheckSuccess(DataListEntity dataListEntity) {
        if (dataListEntity.getBloodglucoses() != null && dataListEntity.getBloodglucoses().size() > 0) {
            if (!TextUtils.isEmpty(dataListEntity.getBloodglucoses().get(0).getValue())) {
                String value = dataListEntity.getBloodglucoses().get(0).getValue();
                healthPointView.changeAngle((Float.valueOf(value) / 40) * 270, value, BloodRuleUtil.getBloodLevelText(Double.valueOf(value)));
                if (BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.LOW || BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.HIGH) {
                    showColor = "#F04F41";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#F04F41"));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#F04F41"));
                    ll_title.setBackgroundColor(Color.parseColor("#F04F41"));

                } else if (BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.CRITICAL) {
                    showColor = "#FD792F";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#FD792F"));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#FD792F"));
                    ll_title.setBackgroundColor(Color.parseColor("#FD792F"));

                } else {
                    showColor = "#2ECE63";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#2ECE63"));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#2ECE63"));
                    ll_title.setBackgroundColor(Color.parseColor("#2ECE63"));

                }
                return;
            }
        }

        if (dataListEntity.getUrines() != null && dataListEntity.getUrines().size() > 0) {
            if (!TextUtils.isEmpty(dataListEntity.getUrines().get(0).getValue())) {
                String value = dataListEntity.getUrines().get(0).getValue();
                healthPointView.changeAngle((Float.valueOf(value) / 40) * 270, value, BloodRuleUtil.getBloodLevelText(Double.valueOf(value)));
                if (BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.LOW || BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.HIGH) {
                    showColor = "#F04F41";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor(showColor));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor(showColor));
                    ll_title.setBackgroundColor(Color.parseColor(showColor));
                } else if (BloodRuleUtil.getBloodLevel(Double.valueOf(value)) == BloodRuleUtil.CRITICAL) {
                    showColor = "#FD792F";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor(showColor));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor(showColor));
                    ll_title.setBackgroundColor(Color.parseColor(showColor));

                } else {
                    showColor = "#2ECE63";
                    mainActivity.getLl_title().setBackgroundColor(Color.parseColor(showColor));
                    mainActivity.getTv_qick_check().setTextColor(Color.parseColor(showColor));
                    ll_title.setBackgroundColor(Color.parseColor(showColor));

                }
                return;
            }
        }
    }

    @Override
    public void getCheckCountSuccess(String count) {
        tv_count.setText("今日检测血糖 " + count + " 次");
    }

    @Override
    public void showTip(String message) {
        if (presenter != null) {
            srlt_qa.setRefreshing(false);
            rv_blood.loadComplete(true);
            ToastUtils.shortToast(mActivity, message);
        }
    }

}
