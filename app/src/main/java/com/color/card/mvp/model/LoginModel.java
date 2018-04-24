package com.color.card.mvp.model;

import android.util.Log;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BaseModel;
import com.color.card.util.TokenUtil;

import java.util.HashMap;
import java.util.Map;

import card.color.basemoudle.util.HttpUrlUtils;
import card.color.basemoudle.util.SPCacheUtils;
import card.color.httpmoudle.api.ApiManager;

/**
 * @author yqy
 * @date on 2018/3/27
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class LoginModel extends BaseModel {
    public void phoneCodeLogin(String phone, String code, String password, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_USER_LOGIN);
        params.put("username", phone);
        params.put("password", password);
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void getUserInfo(ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_USER_PROFILE);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void getLoginaAutomatic(ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap2();
        params.put("_api", HttpUrlUtils.URL_LOGIN_AUTOMATIC);
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
