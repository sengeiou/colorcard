package com.color.card.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.color.card.MainActivity;
import com.color.card.R;
import com.color.card.handler.WeakHandler;
import com.color.card.listener.SoftKeyBoardListener;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.mvp.contract.LoginContract;
import com.color.card.mvp.presenter.LoginPresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.ui.widget.EditTextWithClear;
import com.color.card.util.DensityUtils;
import com.color.card.util.HandleUtil;
import com.color.card.util.KeyBoardUtils;
import com.color.card.util.ToastUtils;

import java.util.List;

import card.color.basemoudle.util.AppUtils;
import card.color.basemoudle.util.ConstantUtils;
import card.color.basemoudle.util.SPCacheUtils;

/**
 * @author yqy
 * @date on 2018/3/27
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    private EditTextWithClear etcmobile;

    private EditText etc_password;
    private EditText etc_code;
    private Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public LoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }


    @Override
    public void initView() {
        presenter.loginAutomatic();
        setHeadVisible(View.GONE);
        btn_login = _id(R.id.btn_login);
        etcmobile = _id(R.id.etc_mobile);
        etc_password = _id(R.id.etc_password);
        etc_code = _id(R.id.etc_code);
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
                KeyBoardUtils.openKeybord(etcmobile.getMyEditText(), LoginActivity.this);
            }
        }, 300);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(etcmobile.getText()) || etcmobile.getText().length() != 11) {
                    ToastUtils.shortToast(LoginActivity.this, "请输入手机号");
                    return;
                } else if (TextUtils.isEmpty(etc_password.getText())) {
                    ToastUtils.shortToast(LoginActivity.this, "请输入密码");
                }
                presenter.login(etcmobile.getText().toString(), etc_code.getText().toString(), etc_password.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        KeyBoardUtils.closeKeybord(etcmobile.getMyEditText(), this);
        super.onBackPressed();

    }

    @Override
    public void showTip(String message) {
        super.showTip(message);
    }

    @Override
    public void phoneCodeLoginSuccess() {
        presenter.getUserInfo();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void getUserInfoSuccess() {

    }

    @Override
    public void loginAutomaticSuccess() {

    }
}
