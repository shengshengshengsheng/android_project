package com.example.mycourse.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Xml;
import android.widget.ImageView;

import com.example.mycourse.bean.ExerciseBean;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于获取用户名的工具类
 * */
public class AnalysisUtils {
    /**
     * 从SharedPreference当中获取登录用户名
     * */
    public static String readLoginUserName(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName=sharedPreferences.getString("loginUserName","");
        return userName;
    }
    /**
     * 解析每章的习题
     * */
    public static List<ExerciseBean>getExercisesInfos(InputStream is)throws Exception{
        XmlPullParser parser= Xml.newPullParser();
        parser.setInput(is,"utf-8");
        List<ExerciseBean> exercisesInfos=null;
        ExerciseBean exercisesInfo=null;
        int type=parser.getEventType();
        while(type!=XmlPullParser.END_DOCUMENT){
            switch (type){
                case XmlPullParser.START_TAG:
                    if("infos".equals(parser.getName())){
                        exercisesInfos=new ArrayList<ExerciseBean>();
                    }else if("exercises".equals(parser.getName())){
                        exercisesInfo=new ExerciseBean();
                        String ids=parser.getAttributeValue(0);
                        exercisesInfo.subjectId=Integer.parseInt(ids);
                    }else if("subject".equals(parser.getName())){
                        String subject=parser.nextText();
                        exercisesInfo.subject=subject;
                    }else if("a".equals(parser.getName())){
                        String a=parser.nextText();
                        exercisesInfo.a=a;
                    }
                    else if("b".equals(parser.getName())){
                        String b=parser.nextText();
                        exercisesInfo.b=b;
                    }else if("c".equals(parser.getName())){
                        String c=parser.nextText();
                        exercisesInfo.c=c;
                    }else if("answer".equals(parser.getName())){
                        String answer=parser.nextText();
                        exercisesInfo.answer=Integer.parseInt(answer);
                    }
                    break;
                    case XmlPullParser.END_TAG:
                        if("exercises".equals(parser.getName())){
                            exercisesInfos.add(exercisesInfo);
                            exercisesInfo=null;
                        }
                        break;
            }
            type=parser.next();
        }
        return exercisesInfos;
    }
    /**
     * 设置ABC选项是否可以被点击
     * */
    public static  void setABCEnable(boolean value, ImageView iv_a, ImageView iv_b, ImageView iv_c){
        iv_a.setEnabled(value);
        iv_b.setEnabled(value);
        iv_c.setEnabled(value);
    }

}
