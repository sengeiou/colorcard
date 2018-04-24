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
import retrofit2.http.HTTP;

/**
 * @author yqy
 * @date on 2018/4/3
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultDetailModel extends BaseModel {
    public void creatEvent(final ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_CREAT_EVENT);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void postUrineData(String eventId, String detectionType, String value, String valueLevel, long dateTime, final ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_POST_CHECK_DATA);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        params.put("eventId", eventId);
        params.put("detectionType", detectionType);
        params.put("valueLevel", valueLevel);
        params.put("dateTime", String.valueOf(dateTime));
        params.put("value", value);
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }


    public void postUriTag(String eventId, String tag, final ObserverListener listener) {
        Map<String, String> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_POST_TAG);
        params.put("_se", SPCacheUtils.get("sessionId", "").toString());
        params.put("eventId", eventId);
        params.put("tag", tag);
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
