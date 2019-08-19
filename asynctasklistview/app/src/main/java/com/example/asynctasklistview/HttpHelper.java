package com.example.asynctasklistview;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import static com.example.asynctasklistview.Request.URL_TYPE_GET;
import static com.example.asynctasklistview.Request.URL_TYPE_POST_BODY;
import static com.example.asynctasklistview.Request.URL_TYPE_POST_FORM_DATA;

public class HttpHelper {
    public static ExCall http_request(final Request user_request) {
        okhttp3.Request okHttpRequest=null;
        switch (user_request.getMethod()){
            case URL_TYPE_GET:
                okHttpRequest=new okhttp3.Request.Builder().url(user_request.getUrl()).get().build();
                break;
            case URL_TYPE_POST_BODY:
                RequestBody body_post = RequestBody.create(null,user_request.getParams().toString());
                okHttpRequest=new okhttp3.Request.Builder().url(user_request.getUrl()).post(body_post).build();
                break;
            case URL_TYPE_POST_FORM_DATA:
                Map<String, String> map=(Map)user_request.getParams();
                 okhttp3.FormBody.Builder builder=new FormBody.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.add(entry.getKey(),entry.getValue());
                }
                RequestBody post_form_data = builder.build();
                okHttpRequest=new okhttp3.Request.Builder()
                        .url(user_request.getUrl())
                        .post(post_form_data)
                        .build();
                break;
        }
        return new ExCall(new OkHttpClient().newCall(okHttpRequest));
    }
}
