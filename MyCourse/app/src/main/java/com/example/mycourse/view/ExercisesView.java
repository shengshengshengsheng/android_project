package com.example.mycourse.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.mycourse.R;
import com.example.mycourse.adapter.ExercisesAdapter;
import com.example.mycourse.bean.ExerciseBean;

import java.util.ArrayList;
import java.util.List;

public class ExercisesView {
    private ListView lv_list;
    private ExercisesAdapter adapter;
    private List<ExerciseBean> eb1;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;

    public ExercisesView(Activity mContext) {
        this.mContext = mContext;
        //为之后将Layout转化为view时使用
        mInflater = LayoutInflater.from(mContext);
    }

    public void createView() {
        initView();
    }

    /**
     * 获取当前在导航栏上方显示对应的view
     */
    public View getView() {
        if (mCurrentView == null) {
            createView();
        }
        return mCurrentView;
    }

    /**
     * 显示导航栏上方对应的view界面
     */
    public void showView() {
        if (mCurrentView == null) {
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mCurrentView = mInflater.inflate(R.layout.main_view_exercises, null);
        lv_list = mCurrentView.findViewById(R.id.lv_list);
        adapter = new ExercisesAdapter(mContext);
        initData();
        adapter.setData(eb1);
        lv_list.setAdapter(adapter);
    }

    /**
     * 设置数据
     */
    private void initData() {
        eb1 = new ArrayList<ExerciseBean>();
        for (int i = 0; i < 20; i++) {
            ExerciseBean bean = new ExerciseBean();
            bean.id = (1 + i);
            switch (i) {
                case 0:
                    bean.title = "第一章 android基础入门";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg1;
                    break;
                case 1:
                    bean.title = "第二章 android UI开发";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg2;
                    break;
                case 2:
                    bean.title = "第三章 activity";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg3;
                    break;
                case 3:
                    bean.title = "第四章 数据存储";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg4;
                    break;
                case 4:
                    bean.title = "第五章 SQLite数据库";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg1;
                    break;
                case 5:
                    bean.title = "第六章 广播接收者";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg2;
                    break;
                case 6:
                    bean.title = "第七章 服务";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg3;
                    break;
                case 7:
                    bean.title = "第八章 内容提供者";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg4;
                    break;
                case 8:
                    bean.title = "第九章 网络编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg1;
                    break;
                case 9:
                    bean.title = "第十章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg2;
                    break;
                case 10:
                    bean.title = "第十一章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg3;
                    break;
                case 11:
                    bean.title = "第十二章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg4;
                    break;
                case 12:
                    bean.title = "第十三章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg1;
                    break;
                case 13:
                    bean.title = "第十四章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg2;
                    break;
                case 14:
                    bean.title = "第十五章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg3;
                    break;
                case 15:
                    bean.title = "第十六章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg4;
                    break;
                case 16:
                    bean.title = "第十七章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg1;
                    break;
                case 17:
                    bean.title = "第十八章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg2;
                    break;
                case 18:
                    bean.title = "第十九章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg3;
                    break;
                case 19:
                    bean.title = "第二十章 高级编程";
                    bean.content = "共计5个题";
                    bean.background = R.mipmap.exercise_bg4;
                    break;
                default:
                    break;
            }
            eb1.add(bean);
        }
    }

}
