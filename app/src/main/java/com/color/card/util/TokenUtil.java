package com.color.card.util;

import android.text.TextUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yqy
 * @date on 2018/3/28
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class TokenUtil {
    public static String token(TreeMap<String, String> params, String security) {
        if (TextUtils.isEmpty(security) || (params == null)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (TextUtils.equals(entry.getKey(), "_at")) {
                continue;
            }
            if (TextUtils.equals(entry.getKey(), "_sign")) {
                continue;
            }
            if (TextUtils.equals(entry.getKey(), "_")) {
                continue;
            }
            if (TextUtils.equals(entry.getKey(), "callback")) {
                continue;
            }
            sb.append(entry.getKey());
            sb.append(entry.getValue());
        }
        String md5 = MD5Util.getMD5String(sb.toString());
        return MD5Util.getMD5String(md5 + security);
    }

    public static TreeMap<String, String> getParameterMap(Map<String, String> params) {
        TreeMap<String, String> returnMap = new TreeMap<String, String>();
        Iterator<?> entries = params.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {

                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


    public static TreeMap<String, String> getParameterMap2(Map<String, String[]> params) {
        TreeMap<String, String> returnMap = new TreeMap<String, String>();
        Iterator<?> entries = params.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {

                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

}
