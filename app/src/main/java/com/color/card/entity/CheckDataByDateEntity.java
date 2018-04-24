package com.color.card.entity;

import android.util.Log;

import com.color.card.util.DateUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yqy
 * @date on 2018/4/16
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class CheckDataByDateEntity {
    private String showDate;

    private List<CheckData> checkData;


    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public List<CheckData> getCheckData() {
        return checkData;
    }

    public void setCheckData(List<CheckData> checkData) {
        this.checkData = checkData;
    }


    /**
     * 按每天的日期归类对接信息公共方法
     *
     * @author shen.guoliang
     * @version 1.0, 2017年3月30日 参数说明
     * @since [产品/模块版本] 表示从那个版本开始就有这个方法
     */
    public static List<CheckDataByDateEntity> sortOutByDate(List<CheckData> connlist) {
        //以日期为key  list为value装分完类的信息
        Map<String, List<CheckData>> map = new LinkedHashMap<String, List<CheckData>>();
        String conntime = "";
        if (connlist != null && connlist.size() > 0) {

            //循环对接信息列表，按日期归类
            for (int pos = 0; pos < connlist.size(); pos++) {
                CheckData con = connlist.get(pos);
                //默认第一张图片为该对接信息的展示图片
                conntime = DateUtil.getStringDate(con.getTime());
                //格式化后的时间
                String formattime = DateUtil.getStringDate10(con.getTime());
                //24小时制格式时间
                String time = conntime.substring(11, 16);
                if (pos == 0) {
                    List<CheckData> list = new ArrayList<CheckData>();
                    con.setShowTime(time);
                    list.add(con);
                    map.put(formattime, list);
                } else {
                    boolean flag = map.containsKey(formattime);
                    //如果之前有当天的信息则把当前信息也放进这天的key的list里面
                    if (flag) {
                        List<CheckData> list = map.get(formattime);
                        con.setShowTime(time);
                        list.add(con);
                        map.put(formattime, list);
                    } else {
                        List<CheckData> list = new ArrayList<CheckData>();
                        con.setShowTime(time);
                        list.add(con);
                        map.put(formattime, list);
                    }
                }
            }

        }


        List<CheckDataByDateEntity> infolist = new ArrayList();
        //循环map集合
        for (String key : map.keySet()) {
            CheckDataByDateEntity tool = new CheckDataByDateEntity();
            tool.setShowDate(key);
            tool.setCheckData(map.get(key));
            infolist.add(tool);
        }
        return infolist;
    }
}
