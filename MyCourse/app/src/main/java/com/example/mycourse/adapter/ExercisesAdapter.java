package com.example.mycourse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mycourse.R;
import com.example.mycourse.activity.ExercisesDetailActivity;
import com.example.mycourse.bean.ExerciseBean;

import java.util.List;

public class ExercisesAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExerciseBean> eb1;
    public ExercisesAdapter(Context context)
    {
        this.mContext=context;
    }
    /**
     * 设置数据更新界面
     * */
    public void setData(List<ExerciseBean> eb1){
        this.eb1=eb1;
        notifyDataSetChanged();
    }
    /**
     * 获取item的总数
     * */
    @Override
    public int getCount() {
        return eb1==null?0:eb1.size();
    }
/**
 * 根据position得到对应的item对象
 * */
    @Override
    public ExerciseBean getItem(int i) {
        return eb1==null?null:eb1.get(i);
    }
/**
 * 根据position得到对应的id
 * */
    @Override
    public long getItemId(int i) {
        return i;
    }
/**
 * 得到相应position对应的item视图，position是当前item的位置，convertView参数就是滑出屏幕的Item的view
 * */
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        //复用convertView
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
            viewHolder.title=convertView.findViewById(R.id.tv_title);
            viewHolder.content=convertView.findViewById(R.id.tv_content);
            viewHolder.order=convertView.findViewById(R.id.tv_order);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //获取position对应的Item数据对象
        final ExerciseBean bean=getItem(position);
        if(bean!=null){
            viewHolder.order.setText(position+1+"");
            viewHolder.title.setText(bean.title);
            viewHolder.content.setText(bean.content);
            viewHolder.order.setBackgroundResource(bean.background);
        }
        //为每个item设置点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean==null)
                    return;
                //跳转到习题详情页面
                Intent intent=new Intent(mContext, ExercisesDetailActivity.class);
                //把章节id传递到习题详情页面
                intent.putExtra("id",bean.id);
                //把标题传递到习题详情页面
                intent.putExtra("title",bean.title);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView title,content,order;
    }
}
