package com.example.mycourse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycourse.R;
import com.example.mycourse.bean.ExerciseBean;
import com.example.mycourse.utils.AnalysisUtils;

import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExerciseBean> eb1;
    private OnSelectListener onSelectListener;

    public ExercisesDetailAdapter(Context context, OnSelectListener onSelectListener) {
        this.mContext = context;
        this.onSelectListener = onSelectListener;
    }

    public void setData(List<ExerciseBean> eb1) {
        this.eb1 = eb1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eb1 == null ? 0 : eb1.size();
    }

    @Override
    public ExerciseBean getItem(int position) {
        return eb1 == null ? null : eb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //记录点击的位置
    private ArrayList<String> selectedPosition = new ArrayList<>();

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercises_detail_list_item, null);
            viewHolder.tv_subject = convertView.findViewById(R.id.tv_subject);
            viewHolder.tv_a = convertView.findViewById(R.id.tv_a);
            viewHolder.tv_b = convertView.findViewById(R.id.tv_b);
            viewHolder.tv_c = convertView.findViewById(R.id.tv_c);
            viewHolder.iv_a = convertView.findViewById(R.id.iv_a);
            viewHolder.iv_b = convertView.findViewById(R.id.iv_b);
            viewHolder.iv_c = convertView.findViewById(R.id.iv_c);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ExerciseBean bean = getItem(position);
        if (bean != null) {
            viewHolder.tv_subject.setText(bean.subject);
            viewHolder.tv_a.setText(bean.a);
            viewHolder.tv_b.setText(bean.b);
            viewHolder.tv_c.setText(bean.c);
        }
        if (!selectedPosition.contains("" + position)) {
            viewHolder.iv_a.setImageResource(R.mipmap.exercise_a);
            viewHolder.iv_b.setImageResource(R.mipmap.exercise_b);
            viewHolder.iv_c.setImageResource(R.mipmap.exercise_c);
            AnalysisUtils.setABCEnable(true, viewHolder.iv_a, viewHolder.iv_b, viewHolder.iv_c);
        } else {
            AnalysisUtils.setABCEnable(false, viewHolder.iv_a, viewHolder.iv_b, viewHolder.iv_c);
            switch (bean.select) {
                case 0://用户选项是正确的
                    if (bean.answer == 1) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_b);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_c);
                    } else if (bean.answer == 2) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_a);
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_right_icon);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_c);
                    } else if (bean.answer == 3) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_a);
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_b);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_right_icon);
                    }
                    break;
                case 1:
                    viewHolder.iv_a.setImageResource(R.mipmap.exercise_wrong_icon);
                    if (bean.answer == 2) {
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_right_icon);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_c);
                    } else if (bean.answer == 3) {
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_b);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_right_icon);
                    }
                    break;
                case 2:
                    viewHolder.iv_b.setImageResource(R.mipmap.exercise_wrong_icon);
                    if (bean.answer == 1) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_c);
                    } else if (bean.answer == 3) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_a);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_right_icon);
                    }
                    break;
                case 3:
                    viewHolder.iv_c.setImageResource(R.mipmap.exercise_wrong_icon);
                    if (bean.answer == 1) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_right_icon);
                        viewHolder.iv_b.setImageResource(R.mipmap.exercise_b);
                    } else if (bean.answer == 2) {
                        viewHolder.iv_a.setImageResource(R.mipmap.exercise_a);
                        viewHolder.iv_c.setImageResource(R.mipmap.exercise_right_icon);
                    }
                    break;
                default:
                    break;
            }
        }
        //用户点击A选项的点击事件
        viewHolder.iv_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断selection当中是否包含此时点击的position
                if (selectedPosition.contains("" + position)) {
                    selectedPosition.remove("" + position);
                } else {
                    selectedPosition.add(position + "");
                }
                onSelectListener.onSelectA(position, viewHolder.iv_a, viewHolder.iv_b, viewHolder.iv_c);
            }
        });
        //用户点击B选项的点击事件
        viewHolder.iv_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断selection当中是否包含此时点击的position
                if (selectedPosition.contains("" + position)) {
                    selectedPosition.remove("" + position);
                } else {
                    selectedPosition.add(position + "");
                }
                onSelectListener.onSelectB(position, viewHolder.iv_a, viewHolder.iv_b, viewHolder.iv_c);
            }
        });
        //用户点击C选项的点击事件
        viewHolder.iv_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断selection当中是否包含此时点击的position
                if (selectedPosition.contains("" + position)) {
                    selectedPosition.remove("" + position);
                } else {
                    selectedPosition.add(position + "");
                }
                onSelectListener.onSelectC(position, viewHolder.iv_a, viewHolder.iv_b, viewHolder.iv_c);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView tv_subject, tv_a, tv_b, tv_c;
        public ImageView iv_a, iv_b, iv_c;
    }

    public interface OnSelectListener {
        void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c);

        void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c);

        void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c);
    }
}
