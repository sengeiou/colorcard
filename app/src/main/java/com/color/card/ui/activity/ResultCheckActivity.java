package com.color.card.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.color.card.R;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.contract.RelultCheckoutContract;
import com.color.card.mvp.presenter.ResultCheckoutPresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.ui.widget.filter.BloodFilter;
import com.color.card.util.BloodRuleUtil;
import com.color.card.util.ToastUtils;
import com.donkingliang.labels.LabelsView;

import java.util.ArrayList;

/**
 * @author yqy
 * @date on 2018/4/3
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultCheckActivity extends BaseActivity<RelultCheckoutContract.Presenter> implements RelultCheckoutContract.View {
    private LabelsView labelsView;
    private TextView check_time;
    private String eventId;
    private TextView tv_submit;
    private EditText et_value;
    private long dateTime;
    private InputFilter[] inputFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_check);
    }

    @Override
    public RelultCheckoutContract.Presenter initPresenter() {
        return new ResultCheckoutPresenter(this);
    }

    @Override
    public void initView() {
        inputFilter = new InputFilter[]{new BloodFilter()};
        labelsView = findViewById(R.id.labels);
        labelsView.setLabels(getLabs());
        check_time = findViewById(R.id.check_time);
        setTopdefaultLefttextVisible(View.VISIBLE);
        tv_submit = findViewById(R.id.tv_submit);
        eventId = getIntent().getStringExtra("eventId");
        et_value = findViewById(R.id.et_value);
//        et_value.setFilters(inputFilter);
        setLeftTxt("首页");
        getTopdefaultLefttext().setTextColor(Color.parseColor("#ffffff"));
        setTitle("血糖录入");
        getTopdefaultCentertitle().setTextColor(Color.parseColor("#ffffff"));
        getHeadView().setBackgroundColor(Color.parseColor("#29C15C"));
        //标签的点击监听
        labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(View label, String labelText, boolean isSelect, int position) {
                //label是被点击的标签，labelText是标签的文字，isSelect是是否选中，position是标签的位置。
                check_time.setText(labelText);
            }
        });
        dateTime = System.currentTimeMillis();
    }


    @Override
    public void initEvent() {
        tv_submit.setOnClickListener(this);
        topdefaultLeftbutton.setOnClickListener(this);
        topdefaultLefttext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (TextUtils.isEmpty(et_value.getText().toString())) {
                    ToastUtils.shortToast(this, "请填写血糖值");
                    return;
                }
                if (TextUtils.isEmpty(check_time.getText().toString())) {
                    ToastUtils.shortToast(this, "请填写测量时间");
                    return;
                }
                if (TextUtils.isEmpty(eventId)) {
                    presenter.creatEvent();
                } else {
                    presenter.postUrine(eventId, "blood_glucose", et_value.getText().toString(), getLevel(), dateTime);
                }
                break;

            case R.id.topdefault_lefttext:
                finish();
                break;
            case R.id.topdefault_leftbutton:
                finish();
                break;
        }
    }

    private ArrayList<String> getLabs() {
        ArrayList<String> label = new ArrayList<>();
        label.add("凌晨");
        label.add("早餐前");
        label.add("早餐后");
        label.add("午餐前");
        label.add("午餐后");
        label.add("晚餐前");
        label.add("晚餐后");
        label.add("睡前");
        return label;
    }

    @Override
    public void creatEventSuccess(String eventId) {
        this.eventId = eventId;
        presenter.postUrine(eventId, "blood_glucose", et_value.getText().toString(), getLevel(), dateTime);
    }

    @Override
    public void postUrineSuccess() {
        ToastUtils.shortToast(this, "数据上传成功");
        finish();
    }


    private String getLevel() {
        if (!TextUtils.isEmpty(et_value.getText().toString())) {
            return BloodRuleUtil.getBloodLevel(Double.valueOf(et_value.getText().toString())) + "";
        }
        return BloodRuleUtil.NORMAL + "";
    }
}
