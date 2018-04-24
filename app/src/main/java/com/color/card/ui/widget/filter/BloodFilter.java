package com.color.card.ui.widget.filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * @author yqy
 * @date on 2018/4/5
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class BloodFilter implements InputFilter {

    /**
     * source    新输入的字符串
     * start    新输入的字符串起始下标，一般为0
     * end    新输入的字符串终点下标，一般为source长度-1
     * dest    输入之前文本框内容
     * dstart    原内容起始坐标，一般为0
     * dend    原内容终点坐标，一般为dest长度-1
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String dValue = dest.toString();
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }
        //修复.导致的bug
        if (".".equals(dValue)) {
            return source;
        }
        //第一位输入.自动过变为9.0
        if (dest.length() == 1 && "20".equals(source.toString())) {
            return "20.0";
        }
        //第一位输入.自动过变为0.
        if (dest.length() == 0 && ".".equals(source)) {
            return "0.";
        }
        //第一位不能输入0
        if (dest.length() == 0 && "0".equals(source)) {
            return "";
        }
        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            String dotValue = splitArray[1];
            if (dotValue.length() == 1) {
                if (end == 1) {
                    if (TextUtils.isEmpty(splitArray[0])) {
                        return source;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }
        } else {
            if (!TextUtils.isEmpty(dValue)) {
                double value = Double.parseDouble(dValue);
                if (dstart == 1) {
                    return "";
                } else {
                    return value >= 0.0 && value < 20.0 ? source : "";
                }
            } else {
                return source + ".0";
            }
        }
        return null;
    }
}
