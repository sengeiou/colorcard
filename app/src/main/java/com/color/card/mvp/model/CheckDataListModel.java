package com.color.card.mvp.model;

import android.util.Log;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BaseModel;
import com.color.card.util.DateUtil;
import com.color.card.util.TokenUtil;

import java.util.Map;

import card.color.basemoudle.util.HttpUrlUtils;
import card.color.basemoudle.util.SPCacheUtils;
import card.color.httpmoudle.api.ApiManager;

/**
 * @author yqy
 * @date on 2018/4/7
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class CheckDataListModel  extends BaseModel {
    public void getUserDataList(int page, ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_USER_DATA);
        params.put("offset", (page *5)+"");
        params.put("limit", "5");
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
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


    public void getRecentCheck(ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_RECENT_CHECK);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }

    public void getCheckCount(ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_CHECK_COUNT);
        params.put("begin", String.valueOf(DateUtil.getYearMonthDay()));
        params.put("end", String.valueOf(System.currentTimeMillis()));
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
