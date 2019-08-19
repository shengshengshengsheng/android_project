package com.example.asynctasklistview;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ExCall {

    Call call;
    public ExCall( Call call){
        this.call=call;
    }

    public static AgeObject decodeFromJson(String responseContext) {
        AgeObject baseInfo;
        if(TextUtils.isEmpty(responseContext)){
            return new AgeObject();
        }
        Gson gson = new Gson();
        baseInfo = gson.fromJson(responseContext, AgeObject.class);
        return baseInfo;
    }

    public String execute() throws IOException {
        return call.execute().body().string();
    }
    public void enqueue(final HttpListener httpListener){

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(httpListener==null)
                {
                    String code="-100";
                    String errMsg=e.getMessage();
                    httpListener.onFailure(code,errMsg);
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(httpListener!=null)
                {
                    String responseContext=response.body().string();
                    //BaseResponseObject baseResponseObject=decodeFromJson(responseContext);
                    final AgeObject ageObject=decodeFromJson(responseContext);
                    String code=ageObject.getCode();
                    if(!"0000".equals(code))
                    {
                        httpListener.onFailure(code,ageObject.getDesc());
                    }
                    if(Looper.getMainLooper() ==Looper.myLooper())
                   {
                       httpListener.onSuccess(ageObject);
                   }
                    else
                    {
                        Handler mainHandler = new Handler(Looper.getMainLooper());
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                httpListener.onSuccess(ageObject);
                            }
                        });
                    }
                }
            }
        });
    }
    public void cancel(){
    }
}

