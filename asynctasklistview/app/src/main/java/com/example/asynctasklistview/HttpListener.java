package com.example.asynctasklistview;

public interface HttpListener {
    void onSuccess(AgeObject ageObject);
    void onFailure(String code,String reason);
}
