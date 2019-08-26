package com.example.mycourse.utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycourse.bean.UserBean;
import com.example.mycourse.sqlite.SQLiteHelper;

public class DBUtils {
    private static DBUtils instance=null;
    private static SQLiteHelper helper;
    public static SQLiteDatabase db;

    public DBUtils(Context context) {
        helper=new SQLiteHelper(context);
        db=helper.getWritableDatabase();
    }

    public static DBUtils getInstance(Context context) {
        if(instance==null)
        {
            instance=new DBUtils(context);
        }
        return instance;
    }
    /**
     * 保存个人资料
     * */
    public void saveUserInfo(UserBean userBean){
        ContentValues contentValues=new ContentValues();
        contentValues.put("userName",userBean.userName);
        contentValues.put("nickName",userBean.nickName);
        contentValues.put("sex",userBean.sex);
        contentValues.put("signature",userBean.signature);
        db.insert(SQLiteHelper.U_USERINFO,null,contentValues);
    }
    /**
     * 获取个人资料
     * */
    public UserBean getUserInfo(String userName){
        String sql="SELECT * FROM "+SQLiteHelper.U_USERINFO+" WHERE userName=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        UserBean bean=null;
        while(cursor.moveToNext())
        {
            bean=new UserBean();
            bean.userName=cursor.getString(cursor.getColumnIndex("userName"));
            bean.nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            bean.sex=cursor.getString(cursor.getColumnIndex("sex"));
            bean.signature=cursor.getString(cursor.getColumnIndex("signature"));
        }
        cursor.close();
        return bean;
    }
    /**
     * 修改个人资料
     * */
    public void updateUserInfo(String key,String value,String userName){
        ContentValues contentValues=new ContentValues();
        contentValues.put(key,value);
        db.update(SQLiteHelper.U_USERINFO,contentValues,"userName=?",new String[]{userName});
    }
}
