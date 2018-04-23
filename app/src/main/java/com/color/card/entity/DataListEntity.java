package com.color.card.entity;

/**
 * @author yqy
 * @date on 2018/4/1
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class DataListEntity {
    private static final DataListEntity ourInstance = new DataListEntity();

    public static DataListEntity getInstance() {
        return ourInstance;
    }

    private DataListEntity() {
    }
}
