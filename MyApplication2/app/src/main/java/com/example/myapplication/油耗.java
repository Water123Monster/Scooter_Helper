package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//繼承【CustomAdapter】的介面OnScooterListener
public class 油耗 extends AppCompatActivity implements CustomAdapter.OnScooterListener {

    //宣告全域變數

    RecyclerView recyclerView;

    ImageView empty_imageview;
    TextView no_data;

    DatabaseManage myDB;
    ArrayList<String> Scooter_id, Scooter_Name, Scooter_License, Scooter_Model, Scooter_Manufacture_Date;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_oil);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        //建立ArrayList及資料庫物件
        myDB = new DatabaseManage(油耗.this);
        Scooter_id = new ArrayList<>();
        Scooter_Name = new ArrayList<>();
        Scooter_License = new ArrayList<>();
        Scooter_Model = new ArrayList<>();
        Scooter_Manufacture_Date = new ArrayList<>();

        //呼叫方法Show_All_My_Scooter
        Show_All_My_Scooter();

        //建立recyclerView的自訂Adapter類別的物件，並將ArrayList傳入建構子
        customAdapter = new CustomAdapter(油耗.this, Scooter_id, Scooter_Name, Scooter_License, Scooter_Model, Scooter_Manufacture_Date, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(油耗.this));
    }

    //呼叫資料庫方法，查詢所有愛車資料
    public void Show_All_My_Scooter() {
        Cursor cursor = myDB.Select_All_My_Scooter();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"請先在車庫新增愛車再使用【油耗】功能", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            //如果cursor回傳值有下一筆資料，就add進ArrayList
            while (cursor.moveToNext()){
                Scooter_id.add(cursor.getString(0));
                Scooter_Name.add(cursor.getString(1));
                Scooter_License.add(cursor.getString(2));
                Scooter_Model.add(cursor.getString(3));
                Scooter_Manufacture_Date.add(cursor.getString(4));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    //實作介面的方法【OnScooterClick】，把觸發的愛車資料intent傳值到【查詢油耗紀錄】
    @Override
    public void OnScooterClick(int position) {
        Intent intent = new Intent(this, 查詢油耗紀錄.class);
        intent.putExtra("id", Scooter_id.get(position));
        intent.putExtra("Name", Scooter_Name.get(position));
        intent.putExtra("License", Scooter_License.get(position));
        intent.putExtra("Model", Scooter_Model.get(position));
        intent.putExtra("Manufacture_Date", Scooter_Manufacture_Date.get(position));
        startActivity(intent);
    }

    //設定頂部顯示導航列右邊的選單按鈕
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(油耗.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
