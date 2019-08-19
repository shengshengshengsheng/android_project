package com.example.asynctasklistview;
import java.util.HashMap;

public class RequestProvider {
    public static class UserInfo {

        public static void getAgeList(String url, String date, String name,HttpListener httpListener)
        {
            HashMap<String,String> map=new HashMap<>();
            map.put("date",date);
            map.put("name",name);
            Request request_get=new Request.GetBuilder().url(url).params(map).build();
            HttpHelper.http_request(request_get).enqueue(httpListener);
        }
        public static void postBodyAgeList(String url, String body, HttpListener httpListener)
        {
            Request request_post_body=new Request.PostBodyBuilder().url(url).params(body).build();
            HttpHelper.http_request(request_post_body).enqueue(httpListener);
        }
        public static void postFormDataAgeList(String url, String date, String name, HttpListener httpListener)
        {
            HashMap<String,String> map=new HashMap<>();
            map.put("date",date);
            map.put("name",name);
            Request request_post_form_data=new Request.PostFormDataBuilder().url(url).params(map).build();
            HttpHelper.http_request(request_post_form_data).enqueue(httpListener);
        }
    }
}
