package com.example.network;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.util.Date;

public abstract class BaseActivity extends Activity implements NetBroadcastReceiver.NetChangeListener {
    //双击退出
    private long mLastBackTime = 0;
    private long TIME_DIFF = 2 * 1000;

    public static NetBroadcastReceiver.NetChangeListener listener;
    private MyAlertDialog alertDialog = null;

    private int netType;//网络类型

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全部禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        listener = this;
        checkNet();
        initView();
        initData();
    }

    /**
     * 初始化时判断有没有网络
     */
    public boolean checkNet() {
        this.netType = NetUtil.getNetWorkState(BaseActivity.this);
        if (!isNetConnect()) {
            //网络异常，请检查网络
            showNetDialog();
            Toast.makeText(this,"网络异常，请检查网络",Toast.LENGTH_SHORT).show();
        }
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onChangeListener(int netMobile) {
        this.netType = netMobile;
        Log.i("netType", "netType:" + netMobile);
        if (!isNetConnect()) {
            showNetDialog();
            Toast.makeText(this,"网络异常，请检查网络",Toast.LENGTH_SHORT).show();
        } else {//打开网络之后的操作
            hideNetDialog();
            Toast.makeText(this,"网络恢复",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 隐藏设置网络框
     */
    private void hideNetDialog() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        alertDialog = null;
    }

    /**
     * 弹出设置网络框
     */
    private void showNetDialog() {
        if (alertDialog == null) {
            alertDialog = new MyAlertDialog(this).builder().setTitle("网络异常")
                    .setNegativeButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("设置", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).setCancelable(false);
        }
        alertDialog.show();
        showMsg("网络异常，请检查网络");
    }

    /**
     * 判断有无网络
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netType == 1) {
            return true;
        } else if (netType == 0) {
            return true;
        } else if (netType == -1) {
            return false;
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void finishActivity() {
        finish();
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isTaskRoot()) {
                long now = new Date().getTime();
                if (now - mLastBackTime < TIME_DIFF) {
                    return super.onKeyDown(keyCode, event);
                } else {
                    mLastBackTime = now;
                    showMsg("再按一次返回键退出");
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
