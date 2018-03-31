package com.color.card.mvp.model;

import com.color.card.listener.ObserverListener;
import com.color.card.mvp.base.BaseModel;
import com.color.card.util.TokenUtil;

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
public class UserCreateModel extends BaseModel {


    public void createUser(String name, String mobile, String pwd, ObserverListener listener) {
        Map<String, Object> params = HttpUrlUtils.getCommonMap();
        params.put("_api", HttpUrlUtils.URL_USER_CREATE);
        params.put("name", name);
        params.put("mobile_ex", mobile);
        params.put("pwd", pwd);
        String at = TokenUtil.token(TokenUtil.getParameterMap(params), HttpUrlUtils.URL_KEY_SECRET); // 计算加密  params.put("_at", at);
        params.put("_at", at);
        apiSubscribe(ApiManager.getApiService().getApiRequset(params), listener);
    }
}
