package com.example.asynctasklistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//listView适配器
public class MyAdapter extends BaseAdapter {
    public ArrayList<String> sorted_list;
    private Context mContext;
    public MyAdapter(ArrayList<String> sorted_list, Context mContext) {
        this.sorted_list = sorted_list;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return sorted_list.size();
    }
    @Override
    public Object getItem(int position) {
        return sorted_list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private static final int TYPE_AGE = 0;
    private static final int TYPE_NAME = 1;
    public static final int TOTAL_TYPE_COUNT=2;
    @Override
    public int getItemViewType(int position) {

        if(Character.isDigit(sorted_list.get(position).codePointAt(0)))
            return TYPE_AGE;
        else
            return TYPE_NAME;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AgeViewHolder ageViewHolder=null;
        NameViewHolder nameViewHolder=null;
        switch (getItemViewType(position))
        {
            case TYPE_AGE:
                if(convertView==null)
                {
                    ageViewHolder=new AgeViewHolder();
                    convertView = View.inflate(mContext,R.layout.listview_item_age,null);
                    ageViewHolder.age = (TextView) convertView.findViewById(R.id.tvAge);
                    convertView.setTag(ageViewHolder);
                }
                else
                {
                    ageViewHolder=(AgeViewHolder)convertView.getTag();
                }
                ageViewHolder.age.setText("age："+sorted_list.get(position));
                break;
            case TYPE_NAME:
                if(convertView==null)
                {
                    nameViewHolder=new NameViewHolder();
                    convertView = View.inflate(mContext,R.layout.listview_item_name,null);
                    nameViewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
                    convertView.setTag(nameViewHolder);
                }
                else
                {
                    nameViewHolder=(NameViewHolder) convertView.getTag();
                }
                nameViewHolder.name.setText("name："+sorted_list.get(position));
                break;
        }
        return convertView;
    }
}
