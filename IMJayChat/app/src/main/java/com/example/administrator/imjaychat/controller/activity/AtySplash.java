package com.example.administrator.imjaychat.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.imjaychat.R;
import com.example.administrator.imjaychat.model.Model;
import com.example.administrator.imjaychat.model.bean.UserInfo;
import com.example.administrator.imjaychat.utils.LogUtil;
import com.hyphenate.chat.EMClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/3.
 */

public class AtySplash extends Activity {
    private Handler handler;
    private TextView tvCountDown;
    private Timer timer;
    private TimerTask timerTask;
    private int countDown = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        launchAty();
    }

    private void launchAty() {
        handler = new Handler();

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //这里为每个时间间隔所做的事情。
                        countDown--;
                        tvCountDown.setText(countDown + "秒 跳过");
                        if (countDown < 0) {
                            timer.cancel();
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, 1000, 1000);  //timerTask不能为空，已经new出来才行。
        //第二个参数为延时，第三个为时间间隔

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainOrLogin();
            }
        }, 6000);
    }

    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()){
                    LogUtil.e("登陆过：" + EMClient.getInstance().getCurrentUser());
                    UserInfo userInfo = Model.getInstance().getUserAccountDao().getAccountByHxid(EMClient.getInstance().getCurrentUser());
                    if (userInfo == null){
                        LogUtil.e("userInfo为null");
                        startAtyLogin();
                    }else {
                        LogUtil.e("userInfo不为null, hxid=" + userInfo.getHxid());
                        startAtyMain();
                    }
                }else {
                    startAtyLogin();
                }
            }
        });
    }

    private void startAtyLogin() {
        Intent intent = new Intent(this, AtyLogin.class);
        startActivity(intent);
        finish();
    }

    private void startAtyMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        setContentView(R.layout.aty_splash);
        tvCountDown = (TextView) findViewById(R.id.tv_countDown);
        //finish()后，移除handler，否则handler继续执行启动主Aty，也可以判断解除
        tvCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainOrLogin();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
