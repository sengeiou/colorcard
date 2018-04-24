package com.color.card.entity;

import java.util.Date;

/**
 * @author yqy
 * @date on 2018/4/5
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class CheckData {
    private String value;

    private Date time;

    private String valueLevel;

    private String takeTime;


    private String showTime;


    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getValueLevel() {
        return valueLevel;
    }

    public void setValueLevel(String valueLevel) {
        this.valueLevel = valueLevel;
    }

    public String getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(String takeTime) {
        this.takeTime = takeTime;
    }
}
