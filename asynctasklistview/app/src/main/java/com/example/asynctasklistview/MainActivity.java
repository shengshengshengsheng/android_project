package com.example.asynctasklistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.example.asynctasklistview.ConstantClassField.URL_TYPE_GET;
import static com.example.asynctasklistview.ConstantClassField.URL_TYPE_POST_BODY;
import static com.example.asynctasklistview.ConstantClassField.URL_TYPE_POST_FORM_DATA;
import static com.example.asynctasklistview.ConstantClassField.url_get;
import static com.example.asynctasklistview.ConstantClassField.url_post_body;
import static com.example.asynctasklistview.ConstantClassField.url_post_formData;

public class MainActivity extends Activity {
    public ListView lv_get;
    public ListView lv_post_formData;
    public ListView lv_post_body;
//    private Button btn_clear;
//    private String responseData_get;
//    private String responseData_post_formData;
//    private String responseData_post_body;
//    public ArrayList<String>sorted_list_get=new ArrayList<String>();
//    public ArrayList<String>sorted_list_post_body=new ArrayList<String>();
//    public ArrayList<String>sorted_list_post_formData=new ArrayList<String>();
    private MyAdapter list_item_get;
    private MyAdapter list_item_post_body;
    private MyAdapter list_item_post_formData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //优化前的方法
        //initDataWithOldWay();
        //优化后的方法
        initDataWithOptimizedWay();
    }

    private void initDataWithOptimizedWay() {

//        /**
//         *清除按钮点击事件处理（未实现）
//         */
//
//        btn_clear=findViewById(R.id.bt_clear);
//        //清除按钮点击事件处理
//        btn_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp1= getSharedPreferences("SP_Data_List_Post_FormData",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = sp1.edit();
//                editor1.clear();
//                editor1.commit();
//                SharedPreferences sp2= getSharedPreferences("SP_Data_List_Post_Body",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor2 = sp2.edit();
//                editor2.clear();
//                editor2.commit();
//                SharedPreferences sp3= getSharedPreferences("SP_Data_List_Get",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor3 = sp3.edit();
//                editor3.clear();
//                editor3.commit();
//                Toast.makeText(MainActivity.this,"清除缓存",Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "btnClear: "+ "清除sharedPreferences缓存");
//            }
//        });
        lv_post_formData=findViewById(R.id.lv_post_formData);
        lv_post_body=findViewById(R.id.lv_post_body);
        lv_get =findViewById(R.id.lv_get);

        /**
         * 第二次修改开始
         * */
        RequestProvider.UserInfo.getAgeList(url_get, "2019-08-01", "Liu", new HttpListener() {
            @Override
            public void onSuccess(AgeObject ageObject) {
                final ArrayList<String> final_list=DealWithData(ageObject);
                list_item_get = new MyAdapter(final_list,MainActivity.this);
                        lv_get.setAdapter(list_item_get);
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_get = new MyAdapter(final_list,MainActivity.this);
//                        lv_get.setAdapter(list_item_get);
//                    }
//                });
            }
            @Override
            public void onFailure(String code, String reason) {

            }
        });
        RequestProvider.UserInfo.postBodyAgeList(url_post_body, "", new HttpListener() {
            @Override
            public void onSuccess(AgeObject ageObject) {
                final ArrayList<String> final_list=DealWithData(ageObject);
                        list_item_post_body = new MyAdapter(final_list,MainActivity.this);
                        lv_post_body.setAdapter(list_item_post_body);
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_post_body = new MyAdapter(final_list,MainActivity.this);
//                        lv_post_body.setAdapter(list_item_post_body);
//                    }
//                });

            }

            @Override
            public void onFailure(String code, String reason) {
                //失败

            }
        });
        RequestProvider.UserInfo.postFormDataAgeList(url_post_formData, "2019-08-01", "Liu", new HttpListener() {
            @Override
            public void onSuccess(AgeObject ageObject) {
                final ArrayList<String> final_list=DealWithData(ageObject);
                list_item_post_formData = new MyAdapter(final_list,MainActivity.this);
                lv_post_formData.setAdapter(list_item_post_formData);
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_post_formData = new MyAdapter(final_list,MainActivity.this);
//                        lv_post_formData.setAdapter(list_item_post_formData);
//                    }
//                });

            }

            @Override
            public void onFailure(String code, String reason) {

            }
        });
        /**
         * 第二次修改结束
         * */

        /**
         * 第一次修改开始
         * */
//        HashMap<String,String> map=new HashMap<>();
//        map.put("date","");
//        map.put("name","");
//        Request request_get=new Request.GetBuilder().url(url_get).params(map).build();
//        HttpHelper.http_request(request_get).enqueue(new HttpListener() {
//            @Override
//            public void onSuccess(AgeObject ageObject) {
//                final ArrayList<String> final_list=DealWithData(ageObject);
////                //存储数据
////                SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Get", Activity.MODE_PRIVATE).edit();//创建sp对象
////                editor.putString("KEY_Data_List_DATA_Get", String.valueOf(final_list));
////                editor.commit();//提交
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_get = new MyAdapter(final_list,MainActivity.this);
//                        lv_get.setAdapter(list_item_get);
//                    }
//                });
//            }
//            @Override
//            public void onFailure(String code, String reason) {
//                Log.d("MainActivity.this","http request failed"+reason);
//
//            }
//        });
//
//        Request request_post_form_data=new Request.PostFormDataBuilder().url(url_post_formData).params(map).build();
//        HttpHelper.http_request(request_post_form_data).enqueue(new HttpListener() {
//            @Override
//            public void onSuccess(AgeObject ageObject) {
//                final ArrayList<String> final_list=DealWithData(ageObject);
////                //存储数据
////                SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_FormData", Activity.MODE_PRIVATE).edit();//创建sp对象
////                editor.putString("KEY_Data_List_DATA_POST_FormData", String.valueOf(final_list));
////                editor.commit();//提交
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_post_formData = new MyAdapter(final_list,MainActivity.this);
//                        lv_post_formData.setAdapter(list_item_post_formData);
//                    }
//                });
//            }
//            @Override
//            public void onFailure(String code, String reason) {
//                Log.d("MainActivity.this","http request failed"+reason);
//
//            }
//        });
//
//        String body="";
//        Request request_post_body=new Request.PostBodyBuilder().url(url_post_body).params(body).build();
//        HttpHelper.http_request(request_post_body).enqueue(new HttpListener() {
//            @Override
//            public void onSuccess(AgeObject ageObject) {
//                final ArrayList<String> final_list=DealWithData(ageObject);
////                //存储数据
////                SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_Body", Activity.MODE_PRIVATE).edit();//创建sp对象
////                editor.putString("KEY_Data_List_DATA_POST_Body", String.valueOf(final_list));
////                editor.commit();//提交
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        list_item_post_body = new MyAdapter(final_list,MainActivity.this);
//                        lv_post_body.setAdapter(list_item_post_body);
//                    }
//                });
//            }
//            @Override
//            public void onFailure(String code, String reason) {
//                Log.d("MainActivity.this","http request failed"+reason);
//
//            }
//        });

        /**
         * 第一次修改结束
         * */


    }
//对list进行排序和合并
    private ArrayList<String> DealWithData(AgeObject ageObject) {
        List<AgeObject.DataBean.UserListBean> userListBeans=ageObject.getData().getUserList();
        Collections.sort(userListBeans, new Comparator<AgeObject.DataBean.UserListBean>() {
            @Override
            public int compare(AgeObject.DataBean.UserListBean userListBean1, AgeObject.DataBean.UserListBean userListBean2) {
                int age1=userListBean1.getAge();
                int age2=userListBean2.getAge();
                return age1-age2;
            }
        });
        //合并list到sorted_list
        ArrayList<String>sorted_list=new ArrayList<String>();
        int now_age=0;
        for (int i = 0; i < userListBeans.size(); i++) {
            if(userListBeans.get(i).getAge()==now_age)
            {
                sorted_list.add(userListBeans.get(i).getName());
            }
            else
            {
                now_age=userListBeans.get(i).getAge();
                sorted_list.add(String.valueOf(userListBeans.get(i).getAge()));
                sorted_list.add(userListBeans.get(i).getName());
            }
        }
        return sorted_list;
    }

    /**
     * 第零次修改开始
     * */

//    private void initDataWithOldWay() {
//
//        lv_get =findViewById(R.id.lv_get);
//        getDataFromURLorSP(url_get,URL_TYPE_GET);
//
//        lv_post_body=findViewById(R.id.lv_post_body);
//        getDataFromURLorSP(url_post_body,URL_TYPE_POST_BODY);
//
//        lv_post_formData=findViewById(R.id.lv_post_formData);
//        getDataFromURLorSP(url_post_formData,URL_TYPE_POST_FORM_DATA);
//
//        btn_clear=findViewById(R.id.bt_clear);
//        //清除按钮点击事件处理
//        btn_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp1= getSharedPreferences("SP_Data_List_Post_FormData",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = sp1.edit();
//                editor1.clear();
//                editor1.commit();
//                SharedPreferences sp2= getSharedPreferences("SP_Data_List_Post_Body",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor2 = sp2.edit();
//                editor2.clear();
//                editor2.commit();
//                SharedPreferences sp3= getSharedPreferences("SP_Data_List_Get",Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor3 = sp3.edit();
//                editor3.clear();
//                editor3.commit();
//                Toast.makeText(MainActivity.this,"清除缓存",Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "btnClear: "+ "清除sharedPreferences缓存");
//            }
//        });
//    }
//
//    private void getDataFromURLorSP(final String url, final int type) {
//                switch (type)
//                {
//                    case URL_TYPE_GET:
//                        get_function(url);
//                        break;
//                    case URL_TYPE_POST_BODY:
//                        post_body_function(url);
//                        break;
//                    case URL_TYPE_POST_FORM_DATA:
//                        post_form_function(url);
//                        break;
//                }
//    }
//
//    //post 表单获取数据
//    private void post_form_function(final String url) {
//        SharedPreferences sharedPreferences_post_form_data = getSharedPreferences("SP_Data_List_Post_FormData", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
//        responseData_post_formData = sharedPreferences_post_form_data.getString("KEY_Data_List_DATA_POST_Form", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
//        Log.i(TAG, "从SharedPreferences取出的post_form_json数据: " + responseData_post_formData);//responseData便是取出的数据了
//        //如果SharedPreferences当中的没有数据，则从网络获取
//        if (responseData_post_formData == null || "".equals(responseData_post_formData)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        OkHttpClient client = new OkHttpClient();
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//                        Date date = new Date(System.currentTimeMillis());
//                        RequestBody requestBody_post_formData = new FormBody.Builder()
//                                .add("date", simpleDateFormat.format(date))
//                                .build();
//                        okhttp3.Request request = new okhttp3.Request.Builder()
//                                .url(url)
//                                .post(requestBody_post_formData)
//                                .build();
//
//                        Response response_post_formData = client.newCall(request).execute();
//                        responseData_post_formData = response_post_formData.body().string();
//                        SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_FormData", Activity.MODE_PRIVATE).edit();//创建sp对象
//                        editor.putString("KEY_Data_List_DATA_POST_Form", String.valueOf(responseData_post_formData)); //存入json串
//                        editor.commit();//提交
//                        Message message = Message.obtain();
//                        message.what=URL_TYPE_POST_FORM_DATA;
//                        message.obj=responseData_post_formData;
//                        handler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
//        else
//        {
//            sorted_list_post_formData=parseJSONWithJSONObject(responseData_post_formData);
//            list_item_post_formData = new MyAdapter(sorted_list_post_formData,MainActivity.this);
//            lv_post_formData.setAdapter(list_item_post_formData);
//        }
//    }
//    //post body获取数据
//    private void post_body_function(final String url) {
//        SharedPreferences sharedPreferences_post_body = getSharedPreferences("SP_Data_List_Post_Body", Activity.MODE_PRIVATE);
//        responseData_post_body = sharedPreferences_post_body.getString("KEY_Data_List_DATA_POST_BODY", "");
//        Log.i(TAG, "从SharedPreferences取出的post_body_json数据: " + responseData_post_body);
//        if (responseData_post_body == null || "".equals(responseData_post_body)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        OkHttpClient client = new OkHttpClient();
//                        okhttp3.Request request_post_Body = new okhttp3.Request.Builder()
//                                .url(url)
//                                .post(RequestBody.create(null, "")).build();
////                        Response response_post_body = client.newCall(request_post_Body).execute();
//                        Response response_post_body = client.newCall(request_post_Body).execute();
//                        responseData_post_body = response_post_body.body().string();
////                Log.d("MainActivity", "post_body取出的数据:" + data);
//                        SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_Body", Activity.MODE_PRIVATE).edit();//创建sp对象
//                        editor.putString("KEY_Data_List_DATA_POST_BODY", String.valueOf(responseData_post_body)); //存入json串
//                        editor.commit();//提交
//                        Message message = Message.obtain();
//                        message.what=URL_TYPE_POST_BODY;
//                        message.obj=responseData_post_body;
//                        handler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
//        else
//        {
//            sorted_list_post_body=parseJSONWithJSONObject(responseData_post_body);
//            list_item_post_body = new MyAdapter(sorted_list_post_body,MainActivity.this);
//            lv_post_body.setAdapter(list_item_post_body);
//        }
//    }
//    //get获取数据
//    private void get_function(final String url) {
//        SharedPreferences sharedPreferences_get = getSharedPreferences("SP_Data_List_Get", Activity.MODE_PRIVATE);
//        responseData_get = sharedPreferences_get.getString("KEY_Data_List_DATA_GET", "");
//        Log.i(TAG, "从SharedPreferences取出的get_json数据: " + responseData_get);
//        if (responseData_get == null || "".equals(responseData_get)) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        OkHttpClient client = new OkHttpClient();
//                        final okhttp3.Request request_get = new okhttp3.Request.Builder().url(url).build();
//                        final Call call = client.newCall(request_get);
//                        Response response_get = call.execute();
//                        responseData_get = response_get.body().string();
////                        Log.d("MainActivity", "get取出的数据:" + data);
//                        SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Get", Activity.MODE_PRIVATE).edit();
//                        editor.putString("KEY_Data_List_DATA_GET", String.valueOf(responseData_get)); //存入json串
//                        editor.commit();
//                        Message message = Message.obtain();
//                        message.what=URL_TYPE_GET;
//                        message.obj=responseData_get;
//                        handler.sendMessage(message);
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
//        else
//        {
//            sorted_list_get=parseJSONWithJSONObject(responseData_get);
//            list_item_get = new MyAdapter(sorted_list_get,MainActivity.this);
//            lv_get.setAdapter(list_item_get);
//        }
//    }
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case URL_TYPE_POST_FORM_DATA:
//                    responseData_post_formData=(String)msg.obj;
//                    sorted_list_post_formData=parseJSONWithJSONObject(responseData_post_formData);
//                    list_item_post_formData = new MyAdapter(sorted_list_post_formData,MainActivity.this);
//                    lv_post_formData.setAdapter(list_item_post_formData);
//                    break;
//                case URL_TYPE_POST_BODY:
//                    responseData_post_body=(String)msg.obj;
//                    sorted_list_post_body=parseJSONWithJSONObject(responseData_post_body);
//                    list_item_post_body = new MyAdapter(sorted_list_post_body,MainActivity.this);
//                    lv_post_body.setAdapter(list_item_post_body);
//                    break;
//                case URL_TYPE_GET:
//                    responseData_get=(String)msg.obj;
//                    sorted_list_get=parseJSONWithJSONObject(responseData_get);
//                    list_item_get = new MyAdapter(sorted_list_get,MainActivity.this);
//                    lv_get.setAdapter(list_item_get);
//                    break;
//            }
//        }
//    };
//
////将Json数据转化为list
//    private ArrayList<String> parseJSONWithJSONObject(String jsonData) {
//        /**
//         * Gson方式获取json数据，取出UserListBean
//         * */
////        Gson gson = new Gson();
////        ResObject.DataBean.UserListBean list_before_merge = gson.fromJson(jsonData, ResObject.DataBean.UserListBean.class);
//        /**
//         * Gson方式结束
//         * */
//        ArrayList<Map<String, Object>> list_before_merge = new ArrayList<Map<String, Object>>();
//        ArrayList<String> final_list=new ArrayList<>();
//                if (jsonData != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(jsonData);
//                //获取数据中的code值，如果是0则正确
//                String resultCode = jsonObject.getString("code");
//                if (resultCode.equals("0000")) {
//                    JSONArray resultJsonArray=null;
//                        resultJsonArray= jsonObject.getJSONObject("data").getJSONArray("userList");
//                        for (int i = 0; i < resultJsonArray.length(); i++) {
//                            //循环遍历，获取json数据中userList数组里的内容
//                            JSONObject Object = resultJsonArray.getJSONObject(i);
//                            Map<String, Object> map = new HashMap<String, Object>();
//                            try {
//                                String name = Object.getString("name");
//                                String age = Object.getString("age");
//                                map.put("name", name);
//                                map.put("age", age);
//                                list_before_merge.add(map);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    final_list=sort_and_merge_list(list_before_merge);
//                    }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//         return final_list;
//    }
//
//    //对list进行排序和合并
//    private ArrayList<String> sort_and_merge_list(ArrayList<Map<String, Object>> list_before_merge) {
//        ArrayList<String>sorted_list=new ArrayList<String>();
//        //排序
//        Collections.sort(list_before_merge, new Comparator<Map<String, Object>>() {
//            @Override
//            public int compare(Map<String, Object> stringObjectMap1, Map<String, Object> stringObjectMap2) {
//                String age1=stringObjectMap1.get("age").toString();
//                String age2=stringObjectMap2.get("age").toString();
//                return age1.compareTo(age2);
//            }
//        });
//        //合并list到sorted_list
//        String now_age="0";
//        for (int i = 0; i < list_before_merge.size(); i++) {
//            if(list_before_merge.get(i).get("age").toString().equals(now_age))
//            {
//                sorted_list.add(list_before_merge.get(i).get("name").toString());
//            }
//            else
//            {
//                now_age=list_before_merge.get(i).get("age").toString();
//                sorted_list.add(list_before_merge.get(i).get("age").toString());
//                sorted_list.add(list_before_merge.get(i).get("name").toString());
//            }
//        }
//        return sorted_list;
//    }

    /**
     * 第零次修改结束
     * */
}