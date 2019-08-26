package com.example.mycourse.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycourse.R;
import com.example.mycourse.bean.UserBean;
import com.example.mycourse.utils.AnalysisUtils;
import com.example.mycourse.utils.DBUtils;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_back;
    private TextView tv_main_title;
    private TextView tv_user_name, tv_nickName, tv_sex, tv_signature;
    private RelativeLayout rl_account, rl_nickName, rl_sex, rl_signature, rl_title_bar;
    private String spUserName;
    private String new_info;//最新数据
    private static final int CHANGE_NICKNAME=1;//修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE=2;//修改签名的自定义常量

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHANGE_NICKNAME://个人资料修改界面传递回来的昵称数据
                if(data!=null){
                    new_info=data.getStringExtra("nickName");
                    if(TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_nickName.setText(new_info);
                    //更新数据库中的昵称字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName",new_info,spUserName);
                }
                break;
            case CHANGE_SIGNATURE://个人资料修改界面传递回来的签名数据
                if(data!=null){
                    new_info=data.getStringExtra("signature");
                    if(TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中的签名字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature",new_info,spUserName);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        //设置页面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //从SharedPreferences当中获取用户名
        spUserName = AnalysisUtils.readLoginUserName(this);
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_nickName = findViewById(R.id.tv_nickName);
        tv_sex = findViewById(R.id.tv_sex);
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#000000"));
        tv_signature = findViewById(R.id.tv_signature);
        rl_account = findViewById(R.id.rl_account);
        rl_nickName = findViewById(R.id.rl_nickName);
        rl_sex = findViewById(R.id.rl_sex);
        rl_signature = findViewById(R.id.rl_signature);
    }

    /**
     * 获取数据
     */
    private void initData() {
        tv_main_title.setText("个人资料");
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据库是否有效
        if (bean == null) {
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "nick name";
            bean.sex = "男";
            bean.signature = "signature";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValues(bean);
    }

    /**
     * 为界面控件设置值
     */
    private void setValues(UserBean bean) {
        tv_user_name.setText(bean.userName);
        tv_nickName.setText(bean.nickName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }

    /**
     * 设置控件的点击事件监听
     */
    private void initListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }

    /**
     * 控件的点击事件
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back://返回按钮的点击事件监听
                this.finish();
                break;
            case R.id.rl_nickName://昵称的点击事件监听
                String name=tv_nickName.getText().toString();//获取昵称控件上的数据
                Bundle bdName=new Bundle();
                bdName.putString("content",name);//传递界面上的昵称数据
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);//flag为1时表示修改昵称
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);//跳转到个人资料修改页面
                break;
            case R.id.rl_sex://性别的点击事件监听
                String sex=tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signature://签名的点击事件监听
                String signature=tv_signature.getText().toString();
                Bundle bdSignature=new Bundle();
                bdSignature.putString("content",signature);
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdSignature);
                break;
            default:
                break;

        }
    }
    /**
     *获取回传数据时需要使用的跳转方法，第一个参数to表示需要跳转到的页面
     * 第二个参数requestCode表示一个请求码，第三个参数b表示跳转时传递的数据
     * */
    public void enterActivityForResult(Class<?>to,int requestCode,Bundle b)
    {
        Intent intent=new Intent(this,to);
        intent.putExtras(b);
        startActivityForResult(intent,requestCode);
    }


    /**
     * 设置性别的弹出框
     * */
    private void sexDialog(String sex){
        int sexFlag=0;
        if("男".equals(sex)){
            sexFlag=0;
        }else if("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }
/**
 * 更新界面上的数据显示
 * */
    private void setSex(String item) {
        tv_sex.setText(item);
        //更新数据库中的字段
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",item,spUserName);
    }
}
