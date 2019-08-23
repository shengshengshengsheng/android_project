package com.example.mycourse.utils;

import android.content.Context;
import android.content.SharedPreferences;

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

}
