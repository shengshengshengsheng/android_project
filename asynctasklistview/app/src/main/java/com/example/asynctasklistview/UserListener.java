package com.example.asynctasklistview;

import java.util.ArrayList;

public interface UserListener {
    void onSuccess(ArrayList<String> arrayList);
    void onFailure(String code,String reason);
}
