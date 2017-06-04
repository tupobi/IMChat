package com.example.administrator.imjaychat.model.dao;

/**
 * Created by Administrator on 2017/6/4.
 */

public class UserAccountTable {
    public static final String TAB_NAME = "tab_account";
    public static final String COL_NAME = "name";
    public static final String COL_HXID = "hxid";
    public static final String COL_NICK = "nick";
    public static final String COL_PHOTO = "photo";

    //注意sql语句的空格！
    public static final String CREATE_TAB = "create table "
           + TAB_NAME + " ("
           + COL_HXID + " text primary key,"
           + COL_NAME + " text,"
           + COL_NICK + " text,"
           + COL_PHOTO + " text);";
}
