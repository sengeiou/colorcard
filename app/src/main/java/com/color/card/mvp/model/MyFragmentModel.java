package com.color.card.mvp.model;

import android.text.TextUtils;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BaseModel;
import com.color.card.util.TokenUtil;

import java.util.Map;

import card.color.basemoudle.util.HttpUrlUtils;
import card.color.basemoudle.util.SPCacheUtils;
import card.color.httpmoudle.api.ApiManager;

/**
 * @author yqy
 * @date on 2018/4/8
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class MyFragmentModel extends BaseModel {
    public void getBind(ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_CHECK_BIND);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void getAgent(String phone, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_GET_AGENT);
        params.put("phone", phone);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void bindAgent(String id, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_BIND_AGENT);
        params.put("clerkId", id);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }

    public void getUserList(String page, String employeeMobile, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_USER_LIST);
        params.put("vendorId", "1");
        params.put("limit", "10");
        params.put("offset", (Integer.parseInt(page)) * 10 + "");
        params.put("employeeMobile", employeeMobile);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void updateUserInfo(String userId, String name, String mobile, String avatar, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_UPDATE_USER);
        params.put("vendorId", "1");
        params.put("userId", userId);
        if (!TextUtils.isEmpty(name)) {
            params.put("username", name);
        }
        if (!TextUtils.isEmpty(mobile)) {
            params.put("mobile", mobile);
        }
        if (!TextUtils.isEmpty(avatar)) {
            params.put("avatar", avatar);
        }
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
