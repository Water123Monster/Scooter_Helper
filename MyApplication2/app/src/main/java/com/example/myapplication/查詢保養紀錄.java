package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class 查詢保養紀錄 extends AppCompatActivity {

    //宣告全域變數
    ListView Scooter_Fix_Data_Show;
    ArrayList<String> Scooter_Fix_Data_id, Scooter_Fix_Mileage, Scooter_Fix_Date, Kilometer, Scooter_Fix_Item, Money, Scooter_Fix_Cost, Dollar;
    FloatingActionButton add_button;

    ImageView empty_imageview;
    TextView no_data;

    String id, Name, License, Model, Manufacture_Date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_fix_show);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        Scooter_Fix_Data_Show = findViewById(R.id.Scooter_Fix_Data_Show);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        //建立ArrayList
        Scooter_Fix_Data_id = new ArrayList<>();
        Scooter_Fix_Mileage = new ArrayList<>();
        Scooter_Fix_Date = new ArrayList<>();
        Kilometer = new ArrayList<>();
        Scooter_Fix_Item = new ArrayList<>();
        Money = new ArrayList<>();
        Scooter_Fix_Cost = new ArrayList<>();
        Dollar = new ArrayList<>();

        //呼叫get_Intent
        get_Intent();
        //呼叫Show_All_Fixl_Data
        Show_All_Fix_Data();

        //設定浮動按鈕的監聽
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(AddDataListener);

        //設定ListView的adapter
        MyAdapter adapter = new MyAdapter(this);
        Scooter_Fix_Data_Show.setAdapter(adapter);
        //設定ListView的長按觸發事件，呼叫資料庫方法，刪除某愛車的某一筆保養紀錄
        Scooter_Fix_Data_Show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(查詢保養紀錄.this);
                builder.setTitle("刪除保養紀錄  " + Scooter_Fix_Date.get(position));
                builder.setMessage("您確定要刪除油耗紀錄  " + Scooter_Fix_Date.get(position) + "  嗎?");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManage myDB = new DatabaseManage(查詢保養紀錄.this);
                        myDB.Delete_Fix_Data(Scooter_Fix_Data_id.get(position));
                        Intent intent = new Intent(查詢保養紀錄.this, 保養.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                builder.create().show();
                return false;
            }
        });
    }

    //自訂的adapter類別
    class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public MyAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return Scooter_Fix_Data_id.size();
        }

        @Override
        public Object getItem(int position) {
            return Scooter_Fix_Data_id.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //設定ListView使用自訂的Layout版面配置，並設定變數及對應的元件id，及從ArrayList裡取出資料放進TextView裡
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.fix_data_show_layout,null);

            TextView txt_Fix_Date = (convertView.findViewById(R.id.txt_Fix_Date));
            TextView txt_Fix_Mileage = (convertView.findViewById(R.id.txt_Fix_Mileage));
            TextView txt_Fix_Kilometer = (convertView.findViewById(R.id.txt_Fix_Kilometer));
            TextView txt_Fix_Item = (convertView.findViewById(R.id.txt_Fix_Item));
            TextView txt_Money = (convertView.findViewById(R.id.txt_Money));
            TextView txt_Fix_Cost = (convertView.findViewById(R.id.txt_Fix_Cost));
            TextView txt_Dollar = (convertView.findViewById(R.id.txt_Dollar));

            txt_Fix_Date.setText(Scooter_Fix_Date.get(position));
            txt_Fix_Mileage.setText(Scooter_Fix_Mileage.get(position));
            txt_Fix_Kilometer.setText(Kilometer.get(position));
            txt_Fix_Item.setText(Scooter_Fix_Item.get(position));
            txt_Money.setText(Money.get(position));
            txt_Fix_Cost.setText(Scooter_Fix_Cost.get(position));
            txt_Dollar.setText(Dollar.get(position));

            return convertView;
        }
    }

    //呼叫資料庫方法，查詢保養紀錄
    public void Show_All_Fix_Data() {
        DatabaseManage myDB = new DatabaseManage(查詢保養紀錄.this);
        Cursor cursor = myDB.Select_All_Fix_Data(id);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"該愛車沒有保養紀錄", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            //如果cursor回傳值有下一筆資料，就add進ArrayList的第一個位置，讓日期越新的資料顯示在越上面的位置
            while (cursor.moveToNext()) {
                Scooter_Fix_Data_id.add(0, cursor.getString(0));
                Scooter_Fix_Item.add(0, cursor.getString(2));
                Scooter_Fix_Date.add(0, cursor.getString(3));
                Scooter_Fix_Cost.add(0, cursor.getString(4));
                Scooter_Fix_Mileage.add(0, cursor.getString(5));
            }
            for(int i = 1; i <= Scooter_Fix_Data_id.size(); i++) {
                Kilometer.add("公里");
                Money.add("$");
                Dollar.add("元");

            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    //接收從【保養】intent傳過來的資料
    public void get_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);
        id = getIntent().getStringExtra("id");
        Name = getIntent().getStringExtra("Name");
        License = getIntent().getStringExtra("License");
        Model = getIntent().getStringExtra("Model");
        Manufacture_Date = getIntent().getStringExtra("Manufacture_Date");
    }

    //浮動按鈕的觸發事件，把(id)傳值到【新增保養紀錄】，並轉換頁面到【新增保養紀錄】
    FloatingActionButton.OnClickListener AddDataListener = new FloatingActionButton.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.putExtra("id",id);
            intent.setClass(查詢保養紀錄.this, 新增保養紀錄.class);
            startActivity(intent);
        }
    };

    //設定頂部顯示導航列右邊的選單按鈕
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fix_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //頂部導航列選單按鈕的觸發事件
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回鍵的觸發事件
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(查詢保養紀錄.this,保養.class);
            startActivity(intent);
        }

        //選單按鈕的觸發事件，呼叫方法【warning_dialog】
        if(item.getItemId() == R.id.delete_all_fix){
            warning_dialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //呼叫資料庫方法，刪除某愛車所有的保養紀錄
    public void warning_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("刪除  " + Name + "  所有的保養紀錄");
        builder.setMessage("您確定要刪除  " + Name + "  所有的保養紀錄嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseManage myDB = new DatabaseManage(查詢保養紀錄.this);
                myDB.Delete_All_Fix_Data(id);

                Intent intent = new Intent(查詢保養紀錄.this, 保養.class);
                startActivity(intent);
                finish();
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

}
