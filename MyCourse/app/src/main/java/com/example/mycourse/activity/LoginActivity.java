package com.example.mycourse.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourse.R;
import com.example.mycourse.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
private TextView tv_main_title;
private TextView tv_back,tv_register,tv_find_psw;
private EditText et_user_name,et_password;
private Button btn_login;
private String userName,passWord,spPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        initListener();
    }

    private void initListener() {
        //返回按钮的点击事件处理
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        //立即注册按钮的点击事件处理
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);//跳转到注册页面，从注册页面回传数据到登录页面，intent是数据载体，1是请求码
            }
        });
        //找回密码按钮点击事件处理
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到找回密码界面
            }
        });
        //登录按钮的点击事件处理
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取用户输入的账号和密码
                userName=et_user_name.getText().toString().trim();
                passWord=et_password.getText().toString().trim();
                String md5Psw= MD5Utils.md5(passWord);
                spPassword=readPsw(userName);
                if(TextUtils.isEmpty(userName))
                {
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passWord))
                {
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else if(md5Psw.equals(spPassword))
                {
                    //登录成功的处理方法
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //保存登录状态和登录的用户名
                    saveLoginStatus(true,userName);
                    //把登录成功的状态传递到MainActivity当中
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }
                else if((!TextUtils.isEmpty(spPassword)&&!md5Psw.equals(spPassword)))
                {
                    Toast.makeText(LoginActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    Toast.makeText(LoginActivity.this,"此用户名不存在，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
/**
 * 保存登录状态和登录的用户名
 * */
    private void saveLoginStatus(boolean status, String userName) {
        //loginInfo表示文件名
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isLogin",status);//存储登录状态
        editor.putString("loginUserName",userName);//存储登录时的用户名
        editor.commit();//提交修改

    }

    /**
 * 从sharePreference当中读取用户名对应的密码
 * */
    private String readPsw(String userName) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sharedPreferences.getString(userName,"");
    }

    private void initView() {
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=findViewById(R.id.tv_back);
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        et_user_name=findViewById(R.id.et_user_name);
        et_password=findViewById(R.id.et_password);
        btn_login=findViewById(R.id.btn_login);
    }

    @Override//获取注册页面传回来的用户名，并将其设置到et_user_name控件上，并调用setSelection方法设置控件位置
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null)
        {
            //从注册界面传递过来的用户名
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName))
            {
                et_user_name.setText(userName);
                //设置光标的位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}
