package com.example.mycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 中间栏内容
     */
    private FrameLayout mBodyLayout;
    /**
     * 底部按钮栏
     */
    private LinearLayout mBottomLayout;
    /**
     * 底部按钮
     */
    private View mCourseBtn;
    private View mExerciseBtn;
    private View mMyInfoBtn;
    private TextView tv_course;
    private TextView tv_exercise;
    private TextView tv_myInfo;
    private ImageView iv_course;
    private ImageView iv_exercise;
    private ImageView iv_myInfo;
    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置此界面为竖屏
        initView();//初始化视图
        setListener();//设置底部三个按钮的点击事件监听
        setInitStatus();//设置界面view的初始化状态,默认选中课程按钮
    }

    /**
     * 初始化视图
     */
    private void initView() {
        initTitleBar();
        initBodyLayout();
        initBottomBar();
    }

    /**
     * 设置底部三个按钮的点击事件监听
     */
    private void setListener() {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    /**
     * 设置界面view的初始化状态
     */
    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);//设置底部按钮的选中状态
        createView(0);//显示课程页面的视图
    }

    /**
     * 控件的点击事件处理
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //课程的点击事件处理
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();//清除底部按钮的选中状态
                selectDisplayView(0);//显示对应的页面
                break;
            //练习的点击事件处理
            case R.id.bottom_bar_exercise_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //个人中心的点击事件处理
            case R.id.bottom_bar_me_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
            default:
                break;
        }

    }

    /**
     * 选择视图
     */
    private void createView(int viewIndex) {
        switch (viewIndex) {
            case 0:
                //课程界面
                break;
            case 1:
                //习题界面
                break;
            case 2:
                //我的界面
                break;
        }
    }

    /**
     * 记录两次点击返回按钮的时间差，如果时间差小于2s则直接退出应用，否则提示用户再次点击即可退出
     */
    protected long exitTime;//记录第一次点击的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                MainActivity.this.finish();
                if (readLoginStatus()) {
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 清除sharedPreference当中的登录状态
     */
    private void clearLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean("isLogin", false);//清除登录状态
        editor.putString("loginUserName", "");//清除登录时的用户名
        editor.commit();//提交修改
    }

    /**
     * 获取sharePreference当中的登录状态
     */
    private boolean readLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        return isLogin;
    }

    /**
     * 获取顶部控件
     */
    private void initTitleBar() {
        tv_back = findViewById(R.id.tv_back);
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("课程");
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_back.setVisibility(View.GONE);
    }

    /**
     * 获取中间layout
     */
    private void initBodyLayout() {
        mBodyLayout = findViewById(R.id.main_body);
    }

    /**
     * 获取底部导航栏上的控件
     */
    private void initBottomBar() {
        mBottomLayout = findViewById(R.id.main_bottom_bar);
        mCourseBtn = findViewById(R.id.bottom_bar_course_btn);
        mExerciseBtn = findViewById(R.id.bottom_bar_exercise_btn);
        mMyInfoBtn = findViewById(R.id.bottom_bar_me_btn);
        tv_course = findViewById(R.id.bottom_bar_text_course);
        tv_exercise = findViewById(R.id.bottom_bar_text_exercise);
        tv_myInfo = findViewById(R.id.bottom_bar_text_me);
        iv_course = findViewById(R.id.bottom_bar_image_course);
        iv_exercise = findViewById(R.id.bottom_bar_image_exercise);
        iv_myInfo = findViewById(R.id.bottom_bar_image_me);
    }

    /**
     * 显示对应的页面
     */
    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }

    /**
     * 清除底部按钮的选中状态
     */
    private void clearBottomImageState() {
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercise.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.mipmap.main_course_icon);
        iv_exercise.setImageResource(R.mipmap.main_exercise_icon);
        iv_myInfo.setImageResource(R.mipmap.main_me_icon);
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }

    /**
     * 设置底部按钮的选中状态
     */
    private void setSelectedStatus(int index) {
        switch (index) {
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.mipmap.main_course_selected_icon);
                tv_course.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("课程");
                break;
            case 1:
                mExerciseBtn.setSelected(true);
                iv_exercise.setImageResource(R.mipmap.main_exercise_selected_icon);
                tv_exercise.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("练习");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.mipmap.main_me_selected_icon);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 移除不需要的视图
     */
    private void removeAllView() {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

}
