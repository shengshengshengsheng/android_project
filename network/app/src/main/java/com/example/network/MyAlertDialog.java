package com.example.network;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
public class MyAlertDialog {
    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private View dialog_marBottom;
    private Button btn_neg;
    private Button btn_pos;
    private View img_line;
    private Display display;

    public MyAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public MyAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.toast_view_alertdialog, null);
        txt_title=view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        // 获取自定义Dialog布局中的控件
        dialog_marBottom = (View) view.findViewById(R.id.view_middleLine);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (View) view.findViewById(R.id.view_bottomMiddleLine);
        img_line.setVisibility(View.GONE);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        return this;
    }


    public MyAlertDialog setTitle(String title) {
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }
    public MyAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }
    public MyAlertDialog setPositiveButton(String text, final OnClickListener listener) {
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public MyAlertDialog setNegativeButton(String text, final OnClickListener listener) {
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }
    private void setLayout() {
        txt_title.setVisibility(View.VISIBLE);
        btn_pos.setVisibility(View.VISIBLE);
        btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
        btn_neg.setVisibility(View.VISIBLE);
        btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
        img_line.setVisibility(View.VISIBLE);
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }
}
