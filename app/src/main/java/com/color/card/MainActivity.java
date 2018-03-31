package com.color.card;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.color.card.mvp.base.BasePresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.ui.widget.HealthPointView;

/**
 * @author yqy
 * @date on 2018/3/15
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class MainActivity extends BaseActivity {
    private HealthPointView healthPointView;

    private TextView tv_qick_check;

    private TextView tv_mine;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHeadView().setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        tv_qick_check = findViewById(R.id.tv_qick_check);
        tv_mine = findViewById(R.id.tv_mine);
        tv_qick_check.setSelected(true);
        healthPointView = findViewById(R.id.hpv);
        healthPointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件中，调用动的方法
                healthPointView.changeAngle(200);
            }
        });
    }


    @Override
    public void initEvent() {
        super.initEvent();
        tv_qick_check.setOnClickListener(this);
        tv_mine.setOnClickListener(this);
    }

}
