package com.example.administrator.imjaychat;

import android.app.Application;

import com.example.administrator.imjaychat.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

/**
 * Created by Administrator on 2017/6/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
        options.setAutoAcceptGroupInvitation(false);    //设置是否自动接受群组邀请
        options.setAcceptInvitationAlways(false);       //设置是否自动接受好友邀请
        EaseUI.getInstance().init(this, options);       //单例模式，环信已经设置过

        Model.getInstance().init(this);                    //初始化单例线程池类。
    }
}
