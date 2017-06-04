package com.example.administrator.imjaychat.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.imjaychat.model.bean.UserInfo;
import com.example.administrator.imjaychat.model.db.UserAccountDB;
import com.example.administrator.imjaychat.utils.LogUtil;

/**
 * Created by Administrator on 2017/6/4.
 */

public class UserAccountDao {
    private UserAccountDB mHelper;


    public UserAccountDao(Context context) {
        this.mHelper = new UserAccountDB(context);
    }

    public void addAcount(UserInfo userInfo){
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID, userInfo.getHxid());
        values.put(UserAccountTable.COL_NAME, userInfo.getName());
        values.put(UserAccountTable.COL_NICK, userInfo.getNick());
        values.put(UserAccountTable.COL_PHOTO, userInfo.getPhoto());

        db.replace(UserAccountTable.TAB_NAME, null, values);
    }

    public UserInfo getAccountByHxid(String hxid){
        SQLiteDatabase db = mHelper.getWritableDatabase();

        String sql = "SELECT * FROM " + UserAccountTable.TAB_NAME + " WHERE " + UserAccountTable.COL_HXID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxid});

        UserInfo userInfo = null;
        if (cursor.moveToNext()){
            userInfo = new UserInfo();
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));

            LogUtil.e("----------------->hxid=" + userInfo.getHxid());
        }
        cursor.close();

        return userInfo;
    }
}
