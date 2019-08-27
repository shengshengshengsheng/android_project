package com.example.mycourse.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mycourse.R;
import com.example.mycourse.adapter.ExercisesDetailAdapter;
import com.example.mycourse.bean.ExerciseBean;
import com.example.mycourse.utils.AnalysisUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailActivity extends Activity {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private int id;
    private String title;
    private List<ExerciseBean> ebl;
    private ExercisesDetailAdapter adapter;
    private ListView lv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);
        //获取从习题界面传递过来的章节id
        id = getIntent().getIntExtra("id", 0);
        //获取从习题界面传递过来的章节标题
        title = getIntent().getStringExtra("title");
        ebl = new ArrayList<ExerciseBean>();
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExercisesDetailActivity.this.finish();
            }
        });
        adapter = new ExercisesDetailAdapter(ExercisesDetailActivity.this, new ExercisesDetailAdapter.OnSelectListener() {
            @Override
            public void onSelectA(int position, ImageView iv_a, ImageView iv_b,
                                  ImageView iv_c) {
                if (ebl.get(position).answer != 1) {
                    ebl.get(position).select = 1;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.mipmap.exercise_right_icon);
                        iv_a.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                    case 3:
                        iv_c.setImageResource(R.mipmap.exercise_right_icon);
                        iv_a.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                }
                AnalysisUtils.setABCEnable(false, iv_a, iv_b, iv_c);
            }

            @Override
            public void onSelectB(int position, ImageView iv_a, ImageView iv_b,
                                  ImageView iv_c) {
                if (ebl.get(position).answer != 2) {
                    ebl.get(position).select = 2;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        iv_b.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.mipmap.exercise_right_icon);

                        break;
                    case 3:
                        iv_c.setImageResource(R.mipmap.exercise_right_icon);
                        iv_b.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                }
                AnalysisUtils.setABCEnable(false, iv_a, iv_b, iv_c);
            }

            @Override
            public void onSelectC(int position, ImageView iv_a, ImageView iv_b,
                                  ImageView iv_c) {
                if (ebl.get(position).answer != 3) {
                    ebl.get(position).select = 3;
                } else {
                    ebl.get(position).select = 0;
                }
                switch (ebl.get(position).answer) {
                    case 1:
                        iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        iv_c.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.mipmap.exercise_right_icon);
                        iv_c.setImageResource(R.mipmap.exercise_wrong_icon);
                        break;
                    case 3:
                        iv_c.setImageResource(R.mipmap.exercise_right_icon);

                        break;
                }
                AnalysisUtils.setABCEnable(false, iv_a, iv_b, iv_c);
            }
        });
        adapter.setData(ebl);
        lv_list.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            //从xml文件中获取习题数据
            InputStream is = getResources().getAssets().open("chapter" + id + ".xml");
            ebl = AnalysisUtils.getExercisesInfos(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        lv_list = findViewById(R.id.lv_list);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        tv_main_title.setText(title);
        TextView tv = new TextView(this);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(16.0f);
        tv.setText("一、选择题");
        tv.setPadding(10, 15, 0, 0);
        lv_list.addHeaderView(tv);
    }



}
