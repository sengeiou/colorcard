package com.color.card.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.color.card.R;
import com.color.card.handler.WeakHandler;
import com.color.card.mvp.contract.RegisterContract;
import com.color.card.mvp.presenter.RegisterPresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.ui.widget.EditTextWithClear;
import com.color.card.util.KeyBoardUtils;
import com.color.card.util.ToastUtils;

import card.color.basemoudle.util.ConstantUtils;
import card.color.basemoudle.util.SPCacheUtils;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {
    private EditTextWithClear etcmobile;
    private EditText etc_yzm;
    private TextView tv_yzm;
    private Button btn_next;

    private CountDownTimer countDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void initView() {
        setHeadVisible(View.GONE);
        tv_yzm = _id(R.id.tv_yzm);
        btn_next = _id(R.id.btn_next);
        etcmobile = _id(R.id.etc_mobile);
        etc_yzm = _id(R.id.etc_yzm);
        etc_yzm.setInputType(InputType.TYPE_CLASS_NUMBER);
        etc_yzm.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etc_yzm.setHint("请输入验证码");
        etcmobile.getMyEditText().setHint("请输入您的手机号");
        etcmobile.getMyEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        etcmobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        String account = (String) SPCacheUtils.get("phone", ConstantUtils.CACHE_NULL);
        if (!ConstantUtils.CACHE_NULL.equals(account)) {
            etcmobile.setText(account);
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        new WeakHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.openKeybord(etcmobile.getMyEditText(), RegisterActivity.this);
            }
        }, 300);
        etc_yzm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (TextUtils.isEmpty(etcmobile.getText()) || etcmobile.getText().length() != 11) {
                        ToastUtils.shortToast(RegisterActivity.this, "请输入手机号");
                    } else if (TextUtils.isEmpty(etc_yzm.getText())) {
                        ToastUtils.shortToast(RegisterActivity.this, String.format(getString(R.string.not_null), "验证码"));
                    } else {
                        presenter.checkPhoneCode(etcmobile.getText(), etc_yzm.getText().toString());
                    }
                }
                return true;
            }
        });
        btn_next.setOnClickListener(this);
        tv_yzm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_next:
                if (TextUtils.isEmpty(etcmobile.getText()) || etcmobile.getText().length() != 11) {
                    ToastUtils.shortToast(RegisterActivity.this, "请输入手机号");
                    return;
                } else if (TextUtils.isEmpty(etc_yzm.getText())) {
                    ToastUtils.shortToast(RegisterActivity.this, String.format(getString(R.string.not_null), "验证码"));
                    return;
                }
                presenter.checkPhoneCode(etcmobile.getText(), etc_yzm.getText().toString());
                break;
            case R.id.tv_yzm:
                tv_yzm.setEnabled(false);
                //获取验证码,手机号加密
                if (TextUtils.isEmpty(etcmobile.getText()) || etcmobile.getText().length() != 11) {
                    ToastUtils.shortToast(RegisterActivity.this, "请输入手机号");
                    tv_yzm.setEnabled(true);
                    return;
                }
                presenter.getPhoneCode(etcmobile.getText());
                break;
            case R.id.topdefault_rightbutton:
                KeyBoardUtils.closeKeybord(etcmobile.getMyEditText(), this);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        KeyBoardUtils.closeKeybord(etcmobile.getMyEditText(), this);
        super.onBackPressed();

    }

    @Override
    public void getPhoneCodeSuccess() {
        startTimer();
    }

    @Override
    public void checkPhoneCodeSuccess() {
        Intent intent = new Intent();
        intent.putExtra("phone", etcmobile.getText().toString());
        intent.setClass(this, UserCreateActivity.class);
        startActivity(intent);
        finish();
    }


    protected void startTimer() {
        countDownTimer = new CountDownTimer(ConstantUtils.SMS_TIMEOUT, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv_yzm.setTextColor(getResources().getColor(R.color.app_text_color));
                tv_yzm.setText(String.format(getString(R.string.get_code_again), millisUntilFinished / 1000 + "s"));
                //防止重复点击
                tv_yzm.setEnabled(false);
            }

            @Override
            public void onFinish() {
                //可以在这做一些操作,如果没有获取到验证码再去请求服务器
                //防止重复点击
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(getResources().getColor(R.color.app_main_color));
                tv_yzm.setText(getString(R.string.send_again));
            }
        };
        countDownTimer.start();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
    }

    @Override
    public void showTip(String message) {
        super.showTip(message);
        tv_yzm.setEnabled(true);
    }
}
