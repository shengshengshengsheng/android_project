package com.example.mycourse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.mycourse.MainActivity;
import com.example.mycourse.R;

public class SplashActivity extends AppCompatActivity {
    private Context mContext;
    private TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置当前界面为竖屏模式
        init();
    }

    private void init() {
        tvVersion = findViewById(R.id.tvVersion);
        /**
         * 页面获取并显示当前版本号
         * */
        try {
            //获取程序版本号信息
            int versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            tvVersion.setText("V" + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tvVersion.setText("V");
        }
/**
 * 设置当前task延迟三秒后执行
 * */
        //让当前界面延迟3s再跳转到主界面
        new Handler(new Handler.Callback() {
            //处理接收到的消息的方法
            @Override
            public boolean handleMessage(Message arg0) {
                //实现页面跳转
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                SplashActivity.this.finish();//进入主页面后销毁当前SplashActivity（不销毁：在MainActivity页面点击返回会回退到SplashActivity页面
                return false;
            }
        }).sendEmptyMessageDelayed(0, 3000); //表示延时三秒进行任务的执行
///**
// * 设置当前task延迟三秒后执行
// * */
//        Timer timer=new Timer();
//        //TimerTask类表示一个在指定时间内执行的task
//        TimerTask task=new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(intent);
//                SplashActivity.this.finish();
//            }
//        };
//        timer.schedule(task,3000);//设置当前task在延迟3s之后立即执行

    }
}
