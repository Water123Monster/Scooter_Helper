package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class 查詢愛車資料 extends AppCompatActivity {

    //宣告全域變數
    ListView My_Scooter_Show;
    String my_scooter_Name, my_scooter_License, my_scooter_Model, my_scooter_Manufacture_Date, my_scooter_age, Scooter_Data_Engine_Displacement, Scooter_Data_Fuel_Size, Scooter_Data_Equipment_Weight,
           Scooter_Data_Front_Brake_System, Scooter_Data_Rear_Brake_System, Scooter_Data_Front_Suspension_System, Scooter_Data_Rear_Suspension_System, Scooter_Data_Sitting_Height, Scooter_Data_Environmental_Regulations;
    String[] Scooter_Data_Column = new String[]{"名稱","車牌","型號","出廠日期","車齡","實際排氣量(c.c.)","油箱容量(L)","裝備重量(Kg)","前煞車系統","後煞車系統","前避震系統","後避震系統","座高(mm)","環保法規"};
    String[] Scooter_Data;
    Button btn_My_Scooter_Delete, btn_My_Scooter_Update;

    String id, Name, License, Model, Manufacture_Date;

    private int Scooter_age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_my_scooter_show);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        btn_My_Scooter_Delete = findViewById(R.id.btn_My_Scooter_Delete);
        btn_My_Scooter_Update = findViewById(R.id.btn_My_Scooter_Update);
        btn_My_Scooter_Delete.setOnClickListener(btnDeleteListener);
        btn_My_Scooter_Update.setOnClickListener(btnUpdateListener);

        //呼叫方法countAge計算車齡
        try {
            countAge();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //呼叫方法Select_Data
        Select_Data();

        //設定ListView的adapter
        My_Scooter_Show = findViewById(R.id.My_Scooter_Show);
        MyAdapter adapter = new MyAdapter(this);
        My_Scooter_Show.setAdapter(adapter);

    }

    //自訂的adapter類別
    class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public MyAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return Scooter_Data_Column.length;
        }

        @Override
        public Object getItem(int position) {
            return Scooter_Data_Column[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //設定ListView使用自訂的Layout版面配置，並設定變數及對應的元件id，及從陣列裡取出資料放進TextView裡
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.my_scooter_show_layout,null);

            TextView txt_Column = (convertView.findViewById(R.id.txt_Column));
            TextView txt_Column_Data = (convertView.findViewById(R.id.txt_Column_Data));

            txt_Column.setText(Scooter_Data_Column[position]);
            txt_Column_Data.setText(Scooter_Data[position]);

            return convertView;
        }
    }

    //先呼叫get_Intent取得從【車庫管理】intent傳過來的資料(id)，再呼叫資料庫方法，查詢某愛車的所有資料，並存入字串變數，最後再將字串變數放進字串陣列中
    public void Select_Data() {
        get_Intent();
        DatabaseManage myDB = new DatabaseManage(查詢愛車資料.this);
        Cursor cursor1 = myDB.Select_My_Scooter_Data(id);
        while (cursor1.moveToNext()){
            my_scooter_Name = cursor1.getString(1);
            my_scooter_License = cursor1.getString(2);
            my_scooter_Model = cursor1.getString(3);
            my_scooter_Manufacture_Date = cursor1.getString(4);
            my_scooter_age = cursor1.getString(5);
        }

        Cursor cursor2 = myDB.Select_partOf_Scooter_Data(Model);
        while (cursor2.moveToNext()){
            Scooter_Data_Engine_Displacement = cursor2.getString(0);
            Scooter_Data_Fuel_Size = cursor2.getString(1);
            Scooter_Data_Equipment_Weight = cursor2.getString(2);
            Scooter_Data_Front_Brake_System = cursor2.getString(3);
            Scooter_Data_Rear_Brake_System = cursor2.getString(4);
            Scooter_Data_Front_Suspension_System = cursor2.getString(5);
            Scooter_Data_Rear_Suspension_System = cursor2.getString(6);
            Scooter_Data_Sitting_Height = cursor2.getString(7);
            Scooter_Data_Environmental_Regulations = cursor2.getString(8);
        }


        Scooter_Data = new String[] {my_scooter_Name, my_scooter_License, my_scooter_Model, my_scooter_Manufacture_Date, my_scooter_age, Scooter_Data_Engine_Displacement, Scooter_Data_Fuel_Size,
                Scooter_Data_Equipment_Weight, Scooter_Data_Front_Brake_System, Scooter_Data_Rear_Brake_System, Scooter_Data_Front_Suspension_System, Scooter_Data_Rear_Suspension_System,
                Scooter_Data_Sitting_Height, Scooter_Data_Environmental_Regulations};

    }

    //先呼叫get_Intent取得從【車庫管理】intent傳過來的資料(id)，再計算車齡，計算完後呼叫資料庫方法，更改愛車車齡
    public void countAge() throws ParseException {
        get_Intent();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start_day = sdf.parse(Manufacture_Date);
        Date end_day = new Date();

        long day=(end_day.getTime() - start_day.getTime()) / (24 * 60 * 60 * 1000);
        int year = (int)day / 365;
        Scooter_age = year;

        DatabaseManage myDB = new DatabaseManage(查詢愛車資料.this);
        myDB.Update_My_Scooter_Age(id,Scooter_age);
    }

    //刪除愛車Button的觸發事件，先呼叫get_Intent取得從【車庫管理】intent傳過來的資料(id)，再呼叫warning_dialog
    private Button.OnClickListener btnDeleteListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            get_Intent();
            warning_dialog();
        }
    };

    //更改資料Button的觸發事件，先呼叫get_and_go_Intent取得從【車庫管理】intent傳過來的資料(id)並intent傳值到【更改愛車資料】
    private Button.OnClickListener btnUpdateListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            get_and_go_Intent();
        }
    };

    //呼叫資料庫方法，刪除某愛車的資料
    public void warning_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("刪除愛車  " + Name);
        builder.setMessage("您確定要刪除愛車  " + Name + "  嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseManage myDB = new DatabaseManage(查詢愛車資料.this);
                myDB.Delete_My_Scooter(id);
                myDB.Delete_All_Oil_Data(id);
                myDB.Delete_All_Fix_Data(id);
                Intent intent = new Intent();
                intent.setClass(查詢愛車資料.this, 車庫管理.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.create().show();
    }

    //接收從【車庫管理】intent傳過來的資料
    public void get_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);
        id = getIntent().getStringExtra("id");
        Name = getIntent().getStringExtra("Name");
        Model = getIntent().getStringExtra("Model");
        Manufacture_Date = getIntent().getStringExtra("Manufacture_Date");
    }

    //接收從【車庫管理】intent傳過來的資料，並再inetent傳值到【更改愛車資料】
    public void get_and_go_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);

        id = getIntent().getStringExtra("id");
        Name = getIntent().getStringExtra("Name");
        License = getIntent().getStringExtra("License");
        Manufacture_Date = getIntent().getStringExtra("Manufacture_Date");

        Intent go_intent = new Intent(查詢愛車資料.this,更改愛車資料.class);

        go_intent.putExtra("id", id);
        go_intent.putExtra("Name", Name);
        go_intent.putExtra("License", License);
        go_intent.putExtra("Manufacture_Date", Manufacture_Date);
        startActivity(go_intent);
    }

    //頂部導航列返回鍵的觸發事件
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(查詢愛車資料.this,車庫管理.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //覆蓋過去intent傳入的資料
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}


