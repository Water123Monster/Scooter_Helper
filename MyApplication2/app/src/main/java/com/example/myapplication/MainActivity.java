package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設定底部導行列
        bottomNavigationView = findViewById(R.id.bottomNav);
        //取消底部導行列自帶動畫
        bottomNavigationView.getMenu().setGroupCheckable(0, false, false);
        //設定底部導行列的資料來源
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        //呼叫資料庫新增機車資料的方法(SELECT)
        DatabaseManage myDB = new DatabaseManage(MainActivity.this);
        myDB.Insert_Scooter_Data();
    }

    //設定底部導行列的觸發事件
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId())
                    {
                        case R.id.車庫管理:
                            //使用intent切換activity(車庫管理)
                            Intent intent_moto=new Intent();
                            intent_moto.setClass(MainActivity.this, 車庫管理.class);
                            startActivity(intent_moto);
                            break;
                        case R.id.油耗:
                            //使用intent切換activity(油耗)
                            Intent intent_oil=new Intent();
                            intent_oil.setClass(MainActivity.this, 油耗.class);
                            startActivity(intent_oil);
                            break;
                        case R.id.保養:
                            //使用intent切換activity(保養)
                            Intent intent_fix=new Intent();
                            intent_fix.setClass(MainActivity.this, 保養.class);
                            startActivity(intent_fix);
                            break;
                        case R.id.機車資料:
                            //使用intent切換activity(車種資料)
                            Intent intent_data=new Intent();
                            intent_data.setClass(MainActivity.this, 車種資料.class);
                            startActivity(intent_data);
                    }
                    return false;
                }
            };
}