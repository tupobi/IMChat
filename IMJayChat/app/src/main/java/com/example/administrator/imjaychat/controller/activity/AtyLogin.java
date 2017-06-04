package com.example.administrator.imjaychat.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.imjaychat.R;
import com.example.administrator.imjaychat.model.Model;
import com.example.administrator.imjaychat.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Administrator on 2017/6/4.
 */

public class AtyLogin extends Activity {
    private TextView tvHeader;
    private ImageView ivLoginLogo;
    private EditText etLoginName;
    private EditText etInputPwd;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForgetPwd, tvLoadingPrompt;
    private ProgressBar pbLoadingPrompt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.aty_login);

        tvHeader = (TextView)findViewById( R.id.tv_header );
        ivLoginLogo = (ImageView)findViewById( R.id.iv_loginLogo );
        etLoginName = (EditText)findViewById( R.id.et_loginName );
        etInputPwd = (EditText)findViewById( R.id.et_inputPwd );
        btnLogin = (Button)findViewById( R.id.btn_login );
        tvRegister = (TextView)findViewById( R.id.tv_register );
        tvForgetPwd = (TextView)findViewById( R.id.tv_forgetPwd );
        pbLoadingPrompt = (ProgressBar) findViewById(R.id.pb_loadingPrompt);
        tvLoadingPrompt = (TextView) findViewById(R.id.tv_lodingPrompt);

        setListener();
    }

    private void setListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regist();
            }
        });
    }

    private void regist() {
        final String registName = etLoginName.getText().toString();
        final String registPwd = etInputPwd.getText().toString();

        if (registName.isEmpty() || registPwd.isEmpty()){
            Toast.makeText(this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(registName, registPwd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AtyLogin.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AtyLogin.this, "注册失败！" + e.getDescription(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void login() {
        final String loginName = etLoginName.getText().toString();
        final String loginPwd = etInputPwd.getText().toString();

        if (loginName.isEmpty() || loginPwd.isEmpty()){
            Toast.makeText(this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Model.getInstance().loginSuccess();
                        Model.getInstance().getUserAccountDao().addAcount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Model.getInstance().getUserAccountDao().addAcount(new UserInfo(loginName));
                                tvLoadingPrompt.setVisibility(View.GONE);
                                pbLoadingPrompt.setVisibility(View.GONE);
                                Toast.makeText(AtyLogin.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AtyLogin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvLoadingPrompt.setVisibility(View.GONE);
                                pbLoadingPrompt.setVisibility(View.GONE);
                                Toast.makeText(AtyLogin.this, "登陆失败！" + s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        tvLoadingPrompt.setVisibility(View.VISIBLE);
                        pbLoadingPrompt.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

}
