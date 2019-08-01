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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    public ListView lv_get;
    public ListView lv_post_formData;
    public ListView lv_post_body;

    private Button btn_clear;
    private String responseData_get;
    private String responseData_post_formData;
    private String responseData_post_body;

    String data;
    public ArrayList<String>sorted_list_get=new ArrayList<String>();
    public ArrayList<String>sorted_list_post_body=new ArrayList<String>();
    public ArrayList<String>sorted_list_post_formData=new ArrayList<String>();

    private MyAdapter list_item_get;
    private MyAdapter list_item_post_body;
    private MyAdapter list_item_post_formData;

    final int URL_TYPE_GET=0;
    final int URL_TYPE_POST_BODY=1;
    final int URL_TYPE_POST_FORM_DATA=2;
    String url_get="https://www.easy-mock.com/mock/5d3e4dd006f17a411ae42f0a/test/get";
    String url_post_body="https://www.easy-mock.com/mock/5d3e4dd006f17a411ae42f0a/test/body";
    String url_post_formData="https://www.easy-mock.com/mock/5d3e4dd006f17a411ae42f0a/test/formdata";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }


    private void initData() {
        btn_clear=findViewById(R.id.bt_clear);


        lv_get =findViewById(R.id.lv_get);
        sorted_list_get=getDataFromURLorSP(url_get,URL_TYPE_GET);
        list_item_get = new MyAdapter(sorted_list_get,this);
        lv_get.setAdapter(list_item_get);

        lv_post_body=findViewById(R.id.lv_post_body);
        sorted_list_post_body=getDataFromURLorSP(url_post_body,URL_TYPE_POST_BODY);
        list_item_post_body = new MyAdapter(sorted_list_post_body,this);
        lv_post_body.setAdapter(list_item_post_body);

        lv_post_formData=findViewById(R.id.lv_post_formData);
        sorted_list_post_formData=getDataFromURLorSP(url_post_formData,URL_TYPE_POST_FORM_DATA);
        list_item_post_formData = new MyAdapter(sorted_list_post_formData,this);
        lv_post_formData.setAdapter(list_item_post_formData);

        //清除按钮点击事件处理
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SP_Data_List",Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this,"清除缓存",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btnClear: "+ "清除sharedPreferences缓存");
            }
        });
    }
    //
    private ArrayList<String> getDataFromURLorSP(final String url, final int type) {
        ArrayList<String> final_sorted_list=new ArrayList<String>();
                switch (type)
                {
                    case URL_TYPE_GET:
                        responseData_get=get_function(url);
                        final_sorted_list=parseJSONWithJSONObject(responseData_get);
                        break;
                    case URL_TYPE_POST_BODY:
                        responseData_post_body=post_body_function(url);
                        final_sorted_list=parseJSONWithJSONObject(responseData_post_body);
                        break;
                    case URL_TYPE_POST_FORM_DATA:
                        responseData_post_formData=post_form_function(url);
                        final_sorted_list=parseJSONWithJSONObject(responseData_post_formData);
                        break;
                }
        return final_sorted_list;
    }
//将Json数据转化为list，对list进行排序和合并
    private ArrayList<String> parseJSONWithJSONObject(String jsonData) {
        ArrayList<Map<String, Object>> list_before_merge = new ArrayList<Map<String, Object>>();
        ArrayList<String> final_list=new ArrayList<>();
                if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                //获取数据中的code值，如果是0则正确
                String resultCode = jsonObject.getString("code");
                if (resultCode.equals("0000")) {
                    JSONArray resultJsonArray=null;
                        resultJsonArray= jsonObject.getJSONObject("data").getJSONArray("userList");
                        for (int i = 0; i < resultJsonArray.length(); i++) {
                            //循环遍历，获取json数据中userList数组里的内容
                            JSONObject Object = resultJsonArray.getJSONObject(i);
                            Map<String, Object> map = new HashMap<String, Object>();
                            try {
                                String name = Object.getString("name");
                                String age = Object.getString("age");
                                map.put("name", name);
                                map.put("age", age);
                                list_before_merge.add(map);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    final_list=sort_and_merge_list(list_before_merge);
                    }
                    handler.sendEmptyMessageDelayed(1, 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         return final_list;
    }
//对list进行排序和合并
    private ArrayList<String> sort_and_merge_list(ArrayList<Map<String, Object>> list_before_merge) {
         ArrayList<String>sorted_list=new ArrayList<String>();
        //排序
        Collections.sort(list_before_merge, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> stringObjectMap1, Map<String, Object> stringObjectMap2) {
                String age1=stringObjectMap1.get("age").toString();
                String age2=stringObjectMap2.get("age").toString();
                return age1.compareTo(age2);
            }
        });
        //合并list到sorted_list
        String now_age="0";
        for (int i = 0; i < list_before_merge.size(); i++) {
            if(list_before_merge.get(i).get("age").toString().equals(now_age))
            {
                sorted_list.add(list_before_merge.get(i).get("name").toString());
            }
            else
            {
                now_age=list_before_merge.get(i).get("age").toString();
                sorted_list.add(list_before_merge.get(i).get("age").toString());
                sorted_list.add(list_before_merge.get(i).get("name").toString());
            }
        }
        return sorted_list;
    }
//post 表单获取数据
    private String post_form_function(final String url) {
//                String the_url=url
                //从sharedPreference取数据
                SharedPreferences sharedPreferences_post_form_data = getSharedPreferences("SP_Data_List_Post_FormData", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
                data = sharedPreferences_post_form_data.getString("KEY_Data_List_DATA_POST_Form", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
                Log.i(TAG, "从SharedPreferences取出的post_form_json数据: " + data);//responseData便是取出的数据了
                //如果SharedPreferences当中的没有数据，则从网络获取
                if (data == null || "".equals(data)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                    try {
                        /**
                         * 2.Post方式提交表单数据
                         */
                        OkHttpClient client = new OkHttpClient();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        RequestBody requestBody_post_formData = new FormBody.Builder()
                                .add("date", simpleDateFormat.format(date))
                                .build();
                        Request request = new Request.Builder()
                                .url(url)
                                .post(requestBody_post_formData)
                                .build();
                        Response response_post_formData = client.newCall(request).execute();
                        data = response_post_formData.body().string();
                        Log.d("MainActivity", "post_formData取出的数据:" + data);
                        SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_FormData", Activity.MODE_PRIVATE).edit();//创建sp对象
                        editor.putString("KEY_Data_List_DATA_POST_Form", String.valueOf(data)); //存入json串
                        editor.commit();//提交
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                        }
                    }).start();
                }
        return data;
    }
//post body获取数据
    private String post_body_function(final String url) {
        //从sharedPreference取数据
        SharedPreferences sharedPreferences_post_body = getSharedPreferences("SP_Data_List_Post_Body", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        data = sharedPreferences_post_body.getString("KEY_Data_List_DATA_POST_BODY", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        Log.i(TAG, "从SharedPreferences取出的post_body_json数据: " + data);//responseData便是取出的数据了
        //如果SharedPreferences当中的没有数据，则从网络获取
        if (data == null || "".equals(data)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request_post_Body = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(null, "")).build();
                Response response_post_body = client.newCall(request_post_Body).execute();
                data = response_post_body.body().string();
                Log.d("MainActivity", "post_body取出的数据:" + data);
                SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Post_Body", Activity.MODE_PRIVATE).edit();//创建sp对象
                editor.putString("KEY_Data_List_DATA_POST_BODY", String.valueOf(data)); //存入json串
                editor.commit();//提交
            } catch (Exception e) {
                e.printStackTrace();
            }
                }
            }).start();
        }
        return data;
    }
//get获取数据
    private String get_function(final String url) {
        //从sharedPreference取数据
        SharedPreferences sharedPreferences_get = getSharedPreferences("SP_Data_List_Get", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        data = sharedPreferences_get.getString("KEY_Data_List_DATA_GET", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        Log.i(TAG, "从SharedPreferences取出的get_json数据: " + data);//responseData便是取出的数据了
        //如果SharedPreferences当中的没有数据，则从网络获取
        if (data == null || "".equals(data)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        final Request request_get = new Request.Builder().url(url).build();
                        final Call call = client.newCall(request_get);
                        Response response_get = call.execute();
                        data = response_get.body().string();
                        Log.d("MainActivity", "get取出的数据:" + data);
                        SharedPreferences.Editor editor = getSharedPreferences("SP_Data_List_Get", Activity.MODE_PRIVATE).edit();//创建sp对象
                        editor.putString("KEY_Data_List_DATA_GET", String.valueOf(data)); //存入json串
                        editor.commit();//提交
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return data;
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    list_item_get.notifyDataSetChanged();
                    list_item_post_body.notifyDataSetChanged();
                    list_item_post_formData.notifyDataSetChanged();
                    break;
            }
        }
    };
}