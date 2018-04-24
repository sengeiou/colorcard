package com.color.card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.color.card.entity.CheckData;
import com.color.card.entity.DataListEntity;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.contract.MainActivityContract;
import com.color.card.mvp.presenter.MainActivityPresenter;
import com.color.card.ui.activity.GuideActivity;
import com.color.card.ui.activity.ResultCheckActivity;
import com.color.card.ui.activity.ResultDetailActivity;
import com.color.card.ui.activity.TakePhotoActivity;
import com.color.card.ui.adapter.base.CommonAdapter;
import com.color.card.ui.adapter.base.ViewHolder;
import com.color.card.ui.adapter.wrapper.HeaderAndFooterWrapper;
import com.color.card.ui.adapter.wrapper.LoadMoreWrapper;
import com.color.card.ui.base.BaseActivity;
import com.color.card.ui.fragment.CheckDataFragment;
import com.color.card.ui.fragment.MyFragment;
import com.color.card.ui.widget.HealthPointView;
import com.color.card.ui.widget.LoadMoreRecyclerView;
import com.color.card.ui.widget.NoScrollLinearLayoutManager;
import com.color.card.util.BloodRuleUtil;
import com.color.card.util.DateUtil;
import com.color.card.util.HandleUtil;

import java.util.ArrayList;
import java.util.List;

import card.color.basemoudle.util.ParameterUtils;
import card.color.basemoudle.util.SPCacheUtils;

/**
 * @author yqy
 * @date on 2018/3/15
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class MainActivity extends BaseActivity<MainActivityContract.Presenter> implements MainActivityContract.View {

    private TextView tv_qick_check;

    private TextView tv_mine;

    private LinearLayout ll_title;

    private Bundle state;
    private CheckDataFragment checkDataFragment;
    private FragmentManager mfragmentManager;
    private MyFragment myFragment;

    @Override
    public MainActivityContract.Presenter initPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = savedInstanceState;
        setContentView(R.layout.activity_main);
        getHeadView().setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        if (TextUtils.isEmpty(SPCacheUtils.get("sessionId", "").toString())) {
            startActivity(new Intent(this, GuideActivity.class));
            finish();
        }
        tv_qick_check = findViewById(R.id.tv_qick_check);
        tv_mine = findViewById(R.id.tv_mine);
        ll_title=findViewById(R.id.ll_title);
        tv_qick_check.setSelected(true);
        mfragmentManager = getSupportFragmentManager();
        hideFragment(mfragmentManager);
        if (state == null) {
            presenter.showFragment(mfragmentManager, ParameterUtils.FRAGMENT_ONE);
        }
    }


    @Override
    public void initEvent() {
        super.initEvent();
        tv_qick_check.setOnClickListener(this);
        tv_mine.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qick_check:
                presenter.showFragment(mfragmentManager, ParameterUtils.FRAGMENT_ONE);
                break;
            case R.id.tv_mine:
                presenter.showFragment(mfragmentManager, ParameterUtils.FRAGMENT_TWO);
                break;
            default:
                break;
        }
    }


    /**
     * 保存fragment状态
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ParameterUtils.FRAGMENT_TAG, presenter.currentIndex());
        super.onSaveInstanceState(outState);
    }


    /**
     * 复位fragment状态
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            hideFragment(mfragmentManager);
            presenter.showFragment(mfragmentManager, savedInstanceState.getInt(ParameterUtils.FRAGMENT_TAG));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void hideFragment(FragmentManager fragmentManager) {
        //如果不为空，就先隐藏起来
        if (fragmentManager != null && fragmentManager.getFragments().size() > 0) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                fragment.setUserVisibleHint(false);
                if (fragment.isAdded()) {
                    fragmentManager.beginTransaction().hide(fragment)
                            .commitAllowingStateLoss();
                }
            }
        }
        tv_qick_check.setSelected(false);
        tv_mine.setSelected(false);
    }

    @Override
    public void showCheckFragment(FragmentTransaction ft) {
        tv_qick_check.setSelected(true);
        if (checkDataFragment == null) {
            checkDataFragment = new CheckDataFragment();
            ft.add(R.id.flyt_qa, checkDataFragment);
        } else {
            ft.show(checkDataFragment);
        }
        checkDataFragment.setUserVisibleHint(true);
        ft = null;
    }


    @Override
    public void showMyQaFragment(FragmentTransaction ft) {
        tv_mine.setSelected(true);
        /**
         * 如果Fragment为空，就新建一个实例
         * 如果不为空，就将它从栈中显示出来
         */
        if (myFragment == null) {
            myFragment = new MyFragment();
            ft.add(R.id.flyt_qa, myFragment);
        } else {
            ft.show(myFragment);
        }
        myFragment.setUserVisibleHint(true);
        ft = null;
    }


    public TextView getTv_qick_check() {
        return tv_qick_check;
    }

    public TextView getTv_mine() {
        return tv_mine;
    }

    public View getLl_title() {
        return ll_title;
    }
}
