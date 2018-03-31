package com.color.card.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.color.card.R;
import com.color.card.mvp.contract.UserCreateContract;
import com.color.card.mvp.presenter.UserCreatePresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.util.ToastUtils;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class UserCreateActivity extends BaseActivity<UserCreateContract.Presenter> implements UserCreateContract.View {

    private EditText et_name;

    private EditText et_password;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

    }

    @Override
    public UserCreateContract.Presenter initPresenter() {
        return new UserCreatePresenter(this);
    }

    @Override
    public void initView() {
        phone = getIntent().getStringExtra("phone");
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        setLeftImgVisible(View.GONE);
        setRightTxt("完成");
        setTopdefaultRighttextVisible(View.VISIBLE);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        topdefaultRighttext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.topdefault_righttext:
                presenter.createUser(getUserName(), phone, getPwd());
                break;
            default:
                break;
        }
    }


    private String getUserName() {
        return et_name.getText().toString();
    }

    private String getPwd() {
        return et_password.getText().toString();
    }


    @Override
    public void createUserSuccess() {
        ToastUtils.shortToast(this, "注册成功");
        finish();
    }
}
