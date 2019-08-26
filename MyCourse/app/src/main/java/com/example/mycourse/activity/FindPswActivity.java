package com.example.mycourse.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mycourse.utils.AnalysisUtils;
import com.example.mycourse.utils.MD5Utils;

public class FindPswActivity extends AppCompatActivity {
    private EditText et_validate_name,et_user_name;
    private Button btn_validate;
    private TextView tv_main_title;
    private TextView tv_back;
    //from为security时从设置密保界面跳转过来的，否则就是从登录界面跳转过来的
    private String from;
    private TextView tv_reset_psw,tv_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //获取从登录界面传递过来的数据
        from=getIntent().getStringExtra("from");
        initView();
        initListener();

    }

    private void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindPswActivity.this.finish();
            }
        });
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validateName=et_validate_name.getText().toString().trim();
                if("security".equals(from)){//设置密保
                    if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入要验证的姓名",Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(FindPswActivity.this,"密保设置成功",Toast.LENGTH_SHORT).show();
                        //保存密保到SharedPreferences当中
                        saveSecurity(validateName);
                        FindPswActivity.this.finish();
                    }
                }else{//找回密码
                    String userName=et_user_name.getText().toString().trim();
                    String sp_security=readSecurity(userName);
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPswActivity.this,"请输入你的用户名",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!isExitUserName(userName)){
                        Toast.makeText(FindPswActivity.this,"您输入的用户名不存在",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPswActivity.this,"请输入你要验证的用户名",Toast.LENGTH_SHORT).show();
                        return;
                    }if(!validateName.equals(sp_security)){
                        Toast.makeText(FindPswActivity.this,"输入的密保不正确",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        //输入的密保正确，重新给用户设置一个密码
                        tv_reset_psw.setVisibility(View.VISIBLE);
                        tv_reset_psw.setText("初始密码：123456");
                        savePsw(userName);
                    }
                }
            }
        });
    }
/**
 * 从SharedPreferences中根据用户输入的用户名来判断是否有此用户
 * */
    private boolean isExitUserName(String userName) {
        boolean hasUserName=false;
        SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
        String spPsw=sharedPreferences.getString(userName,"");
        if(!TextUtils.isEmpty(spPsw)){
            hasUserName=true;
        }
        return hasUserName;
    }

    /**
 * 从SharedPreferences当中读取密保
 * */
    private String readSecurity(String userName) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String security=sharedPreferences.getString(userName+"_security",null);
        return security;
    }

    /**
 * 保存密保到SharedPreferences当中
 * */
    private void saveSecurity(String validateName) {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);//存入对应的密保
        editor.commit();

    }

    /**
 * 保存初始化密码
 * */
    private void savePsw(String userName) {
        String md5Psw= MD5Utils.md5("123456");//把密码用md5加密算法加密
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }

    private void initView() {
        et_validate_name=findViewById(R.id.et_validate_name);
        et_user_name=findViewById(R.id.et_user_name);
        btn_validate=findViewById(R.id.btn_validate);
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_back=findViewById(R.id.tv_back);
        tv_reset_psw=findViewById(R.id.tv_reset_psw);
        tv_user_name=findViewById(R.id.tv_user_name);
        if("security".equals(from)){
            tv_main_title.setText("设置密保");
        }
        else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
    }
}
