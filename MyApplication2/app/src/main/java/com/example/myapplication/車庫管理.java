package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//繼承【CustomAdapter】的介面OnScooterListener
public class 車庫管理 extends AppCompatActivity implements CustomAdapter.OnScooterListener {

    //宣告全域變數
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    DatabaseManage myDB;
    ArrayList<String> Scooter_id, Scooter_Name, Scooter_License, Scooter_Model, Scooter_Manufacture_Date;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_my_scooter);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        recyclerView = findViewById(R.id.recyclerView);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        //設定浮動按鈕的觸發事件
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(車庫管理.this, 新增愛車.class);
                startActivity(intent);
            }
        });

        //建立ArrayList及資料庫物件
        myDB = new DatabaseManage(車庫管理.this);
        Scooter_id = new ArrayList<>();
        Scooter_Name = new ArrayList<>();
        Scooter_License = new ArrayList<>();
        Scooter_Model = new ArrayList<>();
        Scooter_Manufacture_Date = new ArrayList<>();

        //呼叫方法Show_All_My_Scooter
        Show_All_My_Scooter();

        //建立recyclerView的自訂Adapter類別的物件，並將ArrayList傳入建構子
        customAdapter = new CustomAdapter(車庫管理.this, Scooter_id, Scooter_Name, Scooter_License, Scooter_Model, Scooter_Manufacture_Date, this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(車庫管理.this));
    }

    //呼叫資料庫方法，查詢所有愛車資料
    public void Show_All_My_Scooter() {
        Cursor cursor = myDB.Select_All_My_Scooter();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"車庫裡沒有任何一台愛車喔", Toast.LENGTH_SHORT).show();
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

    //設定頂部顯示導航列右邊的選單按鈕
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //頂部導航列選單按鈕的觸發事件
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回鍵的觸發事件
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(車庫管理.this,MainActivity.class);
            startActivity(intent);
        }

        //選單按鈕的觸發事件，呼叫方法【warning_dialog】
        if(item.getItemId() == R.id.delete_all_scooter){
            warning_dialog();
        }

        return super.onOptionsItemSelected(item);
    }

    //呼叫資料庫方法，刪除所有愛車的資料
    public void warning_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("刪除所有愛車");
        builder.setMessage("您確定要刪除所有愛車嗎?");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseManage myDB = new DatabaseManage(車庫管理.this);
                myDB.Delete_All_My_Scooter();
                myDB.Delete_All_Scooter_Oil_Data();
                myDB.Delete_All_Scooter_Fix_Data();

                Intent intent = new Intent(車庫管理.this, 車庫管理.class);
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

    //實作介面的方法【OnScooterClick】，把觸發的愛車資料intent傳值到【查詢愛車資料】
    @Override
    public void OnScooterClick(int position) {
        Intent intent = new Intent(this, 查詢愛車資料.class);
        intent.putExtra("id", Scooter_id.get(position));
        intent.putExtra("Name", Scooter_Name.get(position));
        intent.putExtra("License", Scooter_License.get(position));
        intent.putExtra("Model", Scooter_Model.get(position));
        intent.putExtra("Manufacture_Date", Scooter_Manufacture_Date.get(position));
        startActivity(intent);
    }
}
