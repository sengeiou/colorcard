package com.color.card.util;

import android.util.Log;

/**
 * @author yqy
 * @date on 2018/4/3
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class BloodRuleUtil {
    public static final int LOW = 0;
    public static final int NORMAL = 1;
    public static final int CRITICAL = 2;
    public static final int HIGH = 3;


    public static int getBloodLevel(double value) {
        if (new Double("0.0").compareTo(new Double(value)) < 0 && new Double(4.0).compareTo(new Double(value)) >= 0) {
            return LOW;
        } else if (new Double(4.0).compareTo(new Double(value)) < 0 && new Double(6.0).compareTo(new Double(value)) >= 0) {
            return NORMAL;
        } else if (new Double(6.0).compareTo(new Double(value)) < 0 && new Double(9.0).compareTo(new Double(value)) >= 0) {
            return CRITICAL;
        } else {
            return HIGH;
        }
    }



    public static String getBloodLevelText(double value) {
        if (new Double("0.0").compareTo(new Double(value)) < 0 && new Double(4.0).compareTo(new Double(value)) >= 0) {
            return "偏低";
        } else if (new Double(4.0).compareTo(new Double(value)) < 0 && new Double(7.0).compareTo(new Double(value)) >= 0) {
            return "正常";
        } else if (new Double(7.0).compareTo(new Double(value)) < 0 && new Double(9.0).compareTo(new Double(value)) >= 0) {
            return "临界值";
        } else {
            return "偏高";
        }
    }
}
