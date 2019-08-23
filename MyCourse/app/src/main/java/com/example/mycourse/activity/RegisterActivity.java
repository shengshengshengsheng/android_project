package com.example.mycourse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourse.R;
import com.example.mycourse.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    private TextView tv_back;//返回按钮
    private TextView tv_main_title;//标题
    private RelativeLayout rl_title_bar;//标题布局

    private Button btn_register;//注册按钮
    private EditText et_user_name, et_password, et_password_again;//用户名、密码、再次输入密码的控件
    private String userName, user_password, pswAgain;//用户名、密码、再次输入密码的获取值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {//返回按钮点击事件处理
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();//结束当前注册页面
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {//注册按钮的点击事件处理
            @Override
            public void onClick(View view) {
                getEditString();//获取输入在控件当中的字符串
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(user_password)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pswAgain.equals(user_password)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一样，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else if (isExitUserName(userName))//读取用户名，并判断当前用户名是否存在
                {
                    Toast.makeText(RegisterActivity.this, "此用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //将用户名和密码存储到SharedPreference当中
                    saveRegisterInfo(userName, user_password);
                    //注册成功之后将用户名传递到LoginActivity.java当中
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);//把注册成功的用户名传递到登录界面
                    RegisterActivity.this.finish();
                }
            }
        });
    }

    private void initView() {

        //从main_title_bar.xml当中获取控件
        tv_back = findViewById(R.id.tv_back);
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);//标题栏设置背景颜色

        //从activity_register.xml当中获取控件
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);
        et_password_again = findViewById(R.id.et_password_again);
    }

    /**
     * 将注册信息存储到SharedPreferences当中
     */
    private void saveRegisterInfo(String userName, String user_password) {
        String md5Psw = MD5Utils.md5(user_password);//将密码用MD5加密算法加密
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);//LoginInfo表示存储的文件名
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName, md5Psw);//用户名为key，md5Psw为密码保存到SharedPreferences当中
        editor.commit();//提交修改
    }

    /**
     * 读取用户名，判断当前用户名是否存在
     */
    private boolean isExitUserName(String userName) {
        boolean has_userName = false;
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String spPassword = sharedPreferences.getString(userName, "");
        if (!TextUtils.isEmpty(spPassword)) {
            has_userName = true;
        }
        return has_userName;
    }

    /**
     * 获取输入在控件当中的字符串
     */
    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        user_password = et_password.getText().toString().trim();
        pswAgain = et_password_again.getText().toString().trim();
    }
}
