package com.color.card.mvp.model;

import android.util.Log;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BaseModel;
import com.color.card.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

import card.color.basemoudle.util.HttpUrlUtils;
import card.color.httpmoudle.api.ApiManager;

/**
 * @author yqy
 * @date on 2018/3/30
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class RegisterModel extends BaseModel {
    public void getPhoneCode(String phone, final ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_PHONE_CODE);
        params.put("mobile", phone);
        params.put("sign", "login");
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", String.valueOf(at));
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }

    public void checkPhoneCode(String phone, String code, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_PHONE_CODE_CHECK);
        params.put("code", code);
        params.put("mobile", phone);
        params.put("sign", "login");
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
