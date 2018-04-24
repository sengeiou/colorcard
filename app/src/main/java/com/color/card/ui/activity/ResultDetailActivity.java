package com.color.card.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.color.card.R;
import com.color.card.listener.OnMultiClickListener;
import com.color.card.mvp.contract.ResultDetailContract;
import com.color.card.mvp.presenter.ResultDetailPresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.util.BloodRuleUtil;
import com.color.card.util.ToastUtils;
import com.donkingliang.labels.LabelsView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author yqy
 * @date on 2018/4/2
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultDetailActivity extends BaseActivity<ResultDetailContract.Presenter> implements ResultDetailContract.View {
    private LabelsView labelsView;
    private TextView check_time;
    private ImageView iv_low_icon;
    private ImageView iv_normal_icon;
    private ImageView iv_critical_icon;
    private ImageView iv_high_icon;
    private TextView tv_value;
    private double value;
    private TextView tv_remind;
    private String eventId;
    private String level;
    private TextView tv_check_blood;

    private long dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
    }

    @Override
    public ResultDetailContract.Presenter initPresenter() {
        return new ResultDetailPresenter(this);
    }

    @Override
    public void initView() {
        value = new Double(getIntent().getStringExtra("value"));
        labelsView = findViewById(R.id.labels);
        labelsView.setLabels(getLabs());
        iv_low_icon = findViewById(R.id.iv_low_icon);
        iv_normal_icon = findViewById(R.id.iv_normal_icon);
        iv_high_icon = findViewById(R.id.iv_high_icon);
        iv_critical_icon = findViewById(R.id.iv_critical_icon);
        tv_value = findViewById(R.id.tv_value);
        setTopdefaultLefttextVisible(View.VISIBLE);
        tv_check_blood = findViewById(R.id.tv_check_blood);
        tv_remind = findViewById(R.id.tv_remind);
        setLeftTxt("首页");
        getTopdefaultLefttext().setTextColor(Color.parseColor("#ffffff"));
        setTitle("检测结果");
        getTopdefaultCentertitle().setTextColor(Color.parseColor("#ffffff"));
        getHeadView().setBackgroundColor(Color.parseColor("#29C15C"));
        //标签的点击监听
        dateTime = System.currentTimeMillis();
        tv_value.setText(value + "mmol/L");


        if (BloodRuleUtil.getBloodLevel(new Double(value)) == BloodRuleUtil.HIGH) {
            level = BloodRuleUtil.HIGH + "";
            iv_low_icon.setVisibility(View.INVISIBLE);
            iv_normal_icon.setVisibility(View.INVISIBLE);
            iv_critical_icon.setVisibility(View.INVISIBLE);
            iv_high_icon.setVisibility(View.VISIBLE);
            tv_remind.setText("你目前的血糖严重偏高，血糖偏高严重影响健康，请进行血糖校准");
            tv_remind.setTextColor(Color.parseColor("#D22C1F"));
        } else if (BloodRuleUtil.getBloodLevel(value) == BloodRuleUtil.CRITICAL) {
            level = BloodRuleUtil.CRITICAL + "";
            iv_low_icon.setVisibility(View.INVISIBLE);
            iv_normal_icon.setVisibility(View.INVISIBLE);
            iv_critical_icon.setVisibility(View.VISIBLE);
            iv_high_icon.setVisibility(View.INVISIBLE);
            tv_remind.setText("你目前的血糖处于临界值状态，请注意");
            tv_remind.setTextColor(Color.parseColor("#3A393E"));
        } else if (BloodRuleUtil.getBloodLevel(value) == BloodRuleUtil.NORMAL) {
            level = BloodRuleUtil.NORMAL + "";
            iv_low_icon.setVisibility(View.INVISIBLE);
            iv_normal_icon.setVisibility(View.VISIBLE);
            iv_critical_icon.setVisibility(View.INVISIBLE);
            iv_high_icon.setVisibility(View.INVISIBLE);
            tv_remind.setText("你目前的血糖处于临界值状态，请注意");
            tv_remind.setTextColor(Color.parseColor("#3A393E"));
        } else {
            level = BloodRuleUtil.LOW + "";
            iv_low_icon.setVisibility(View.VISIBLE);
            iv_normal_icon.setVisibility(View.INVISIBLE);
            iv_critical_icon.setVisibility(View.INVISIBLE);
            iv_high_icon.setVisibility(View.INVISIBLE);
        }

        check_time = findViewById(R.id.check_time);
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(View label, String labelText, int position) {
                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
            }
        });
        //标签的选中监听
        labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {

            @Override
            public void onLabelSelectChange(View label, String labelText, boolean isSelect, int position) {
                //label是被点击的标签，labelText是标签的文字，isSelect是是否选中，position是标签的位置。
                check_time.setText(labelText);
                if (!TextUtils.isEmpty(eventId)) {
                    String tag = "";
                    if (check_time.getText().toString().equals("凌晨")) {
                        tag = "0";
                    } else if (check_time.getText().toString().equals("早餐前")) {
                        tag = "1";
                    } else if (check_time.getText().toString().equals("早餐后")) {
                        tag = "2";
                    } else if (check_time.getText().toString().equals("午餐前")) {
                        tag = "3";
                    } else if (check_time.getText().toString().equals("午餐后")) {
                        tag = "4";
                    } else if (check_time.getText().toString().equals("晚餐前")) {
                        tag = "5";
                    } else if (check_time.getText().toString().equals("晚餐后")) {
                        tag = "6";
                    } else {
                        tag = "7";
                    }
                    presenter.postTag(eventId, tag);
                }

            }
        });

        iv_low_icon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_low_icon.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int icLowLeft = iv_low_icon.getLeft();
                        int icNormalLeft = iv_normal_icon.getLeft();
                        int icCritical = iv_critical_icon.getLeft();
                        int icHighRigh = iv_high_icon.getLeft();
                        if (iv_low_icon.isShown()) {

                        } else if (iv_normal_icon.isShown()) {
                            tv_value.setPadding(2 * icLowLeft, 0, 0, 0);
                        } else if (iv_critical_icon.isShown()) {
                            tv_value.setPadding(2 * icLowLeft + 2 * icNormalLeft, 0, 0, 0);
                        } else {
                            tv_value.setPadding(2 * icLowLeft + 2 * icNormalLeft + 2 * icCritical, 0, 0, 0);
                        }
                    }
                }, 300);
            }
        });

        presenter.creatEvent();
    }


    @Override
    public void initEvent() {

        tv_check_blood.setOnClickListener(this);
        topdefaultLeftbutton.setOnClickListener(this);
        topdefaultLefttext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check_blood:
                startActivity(new Intent(this, ResultCheckActivity.class).putExtra("eventId", eventId));
                break;
            case R.id.topdefault_leftbutton:
                finish();
                break;

            case R.id.topdefault_lefttext:
                finish();
                break;
            default:
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


    private double getBlood() {
        double a = (float) (Math.random() * 20);
        double numb = a;
        DecimalFormat decimalFormat = new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(numb);//format 返回的是字符串
        //数据类型转换
        double i = Double.parseDouble(p);
        return i;
    }

    @Override
    public void creatEventSuccess(String eventId) {
        this.eventId = eventId;
        presenter.postUrine(eventId, "urine", value + "", level, dateTime);
    }

    @Override
    public void postUrineSuccess() {
    }

    @Override
    public void postTagSuccess() {
        ToastUtils.shortToast(this, "标签更新成功");
        finish();
    }
}
