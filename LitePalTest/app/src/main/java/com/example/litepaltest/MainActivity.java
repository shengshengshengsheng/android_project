package com.example.litepaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;
import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建数据库
        Button createDataBase=findViewById(R.id.bt_create_dataBase);
        createDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connector.getDatabase();
            }
        });
        //添加数据
        Button addData=findViewById(R.id.bt_add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book=new Book();
                book.setName("xx");
                book.setAuthor("author");
                book.setPages(454);
                book.setPrice(30);
                book.save();

            }
        });
        //更新数据
        Button updateData=findViewById(R.id.bt_update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //方法一只能对已经存储的对象修改数据
//                Book book=new Book();
//                book.setName("xx");
//                book.setAuthor("author");
//                book.setPages(454);
//                book.setPrice(30);
//                book.save();
//                book.setPrice(40);
//                book.save();
                Book book=new Book();
                book.setPrice(100);
                book.setPages(40);
                book.updateAll("name=? and author=?","xxx","author");
            }
        });
        //删除数据
        Button deleteData=findViewById(R.id.bt_delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
