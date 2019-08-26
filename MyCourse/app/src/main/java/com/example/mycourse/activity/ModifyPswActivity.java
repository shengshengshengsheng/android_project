package com.example.mycourse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import com.example.mycourse.utils.AnalysisUtils;
import com.example.mycourse.utils.MD5Utils;

public class ModifyPswActivity extends AppCompatActivity {
private RelativeLayout rl_title_bar;
private TextView tv_back;
private TextView tv_main_title;
private EditText et_original_psw,et_new_psw,et_new_psw_again;
private Button btn_save;
private String originalPsw,newPsw,newPswAgain;
private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        initListener();
        userName= AnalysisUtils.readLoginUserName(this);
    }

    private void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyPswActivity.this.finish();
            }
        });
        //保存按钮的点击事件处理
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
                if(TextUtils.isEmpty(originalPsw)){
                    Toast.makeText(ModifyPswActivity.this,"请输入原始密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!MD5Utils.md5(originalPsw).equals(readPsw())){
                    Toast.makeText(ModifyPswActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else if(MD5Utils.md5(newPsw).equals(readPsw())){
                    Toast.makeText(ModifyPswActivity.this,"新密码不能与原始密码相同",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newPsw)){
                    Toast.makeText(ModifyPswActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(newPswAgain)){
                    Toast.makeText(ModifyPswActivity.this,"请再次输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!newPsw.equals(newPswAgain)){
                    Toast.makeText(ModifyPswActivity.this,"两次输入的新密码不同，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(ModifyPswActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                    //修改保存在SharedPreferences当中的密码
                    modifyPsw(newPsw);
                    //跳转到登录页面进行重新登录
                    Intent intent=new Intent(ModifyPswActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SettingActivity.instance.finish();//关闭设置页面
                    ModifyPswActivity.this.finish();//关闭修改密码页面
                }

            }
        });
    }
/**
 * 修改保存在SharedPreferences当中的密码
 * */
    private void modifyPsw(String newPsw) {
        String md5Psw=MD5Utils.md5(newPsw);//把密码用md5加密算法进行加密
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();//获取编辑器
        editor.putString(userName,md5Psw);//保存新密码
        editor.commit();//提交修改
    }

    private String readPsw() {
        SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String psw=sharedPreferences.getString(userName,"");
        return psw;

    }

    //获取控件上的字符串
    private void getEditString() {
        originalPsw=et_original_psw.getText().toString().trim();
        newPsw=et_new_psw.getText().toString().trim();
        newPswAgain=et_new_psw_again.getText().toString().trim();

    }

    private void initView() {
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改密码");
        tv_back=findViewById(R.id.tv_back);
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        et_original_psw=findViewById(R.id.et_original_psw);
        et_new_psw=findViewById(R.id.et_new_psw);
        et_new_psw_again=findViewById(R.id.et_new_psw_again);
        btn_save=findViewById(R.id.btn_save);
    }
}
