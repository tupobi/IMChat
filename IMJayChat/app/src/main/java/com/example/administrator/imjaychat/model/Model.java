package com.example.administrator.imjaychat.model;

import android.content.Context;

import com.example.administrator.imjaychat.model.dao.UserAccountDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/4.
 */

//数据模型层全局类
public class Model {
    private Context mContext;
    private ExecutorService executorService = Executors.newCachedThreadPool();

//    创建对象
    private static Model model = new Model();
    private UserAccountDao userAccountDao;

    //    私有化构造
    private Model(){

    }

//    获取单例对象
    public static Model getInstance(){
        return model;
    }

    public void init(Context context){
        mContext = context;
        userAccountDao = new UserAccountDao(context);
    }

    public ExecutorService getGlobalThreadPool(){
        return executorService;
    }

    public void loginSuccess() {

    }

    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }
}
