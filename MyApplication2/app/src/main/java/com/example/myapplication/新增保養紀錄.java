package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Calendar;

public class 新增保養紀錄 extends AppCompatActivity {

    //宣告全域變數
    DatabaseManage myDB;
    EditText Scooter_Fix_Cost, Scooter_Fix_Date, Scooter_Fix_Mileage;
    private Button btn_Enter,btn_Cancel;
    Spinner Spinner_Fix;
    String[] Fix_Item = new String[] {"機油","齒輪油","煞車皮","煞車線","煞車油","空氣濾網","火星塞","傳動皮帶","普利珠","普利盤","離合器片","前輪胎","後輪胎","電瓶"};
    String id, Fix_Item_Select;
    Handler handler = new Handler();
    LottieAnimationView check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_fix_insert);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        Scooter_Fix_Cost = findViewById(R.id.Scooter_Fix_Cost);
        Scooter_Fix_Date = findViewById(R.id.Scooter_Fix_Date);
        Scooter_Fix_Mileage = findViewById(R.id.Scooter_Fix_Mileage);
        btn_Enter = findViewById(R.id.btn_Fix_Enter);
        btn_Cancel = findViewById(R.id.btn_Fix_Cancel);

        Spinner_Fix = findViewById(R.id.Spinner_Fix);

        check = findViewById(R.id.check);

        //設定選擇保養項目的下拉式選單的Adapter
        ArrayAdapter<String> adapter_Fix_Item = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Fix_Item);
        adapter_Fix_Item.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Fix.setAdapter(adapter_Fix_Item);
        Spinner_Fix.setOnItemSelectedListener(Spinner_Fix_Listener);

        //呼叫方法select_Fix_Date
        select_Fix_Date();

        //設定Button的監聽事件
        btn_Enter.setOnClickListener(btnEnterListener);
        btn_Cancel.setOnClickListener(btnbackListener);

        //呼叫方法get_Intent
        get_Intent();
    }

    //下拉式選單的觸發事件
    private Spinner.OnItemSelectedListener Spinner_Fix_Listener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Fix_Item_Select = Fix_Item[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    //設定選擇年/月/日期的小工具
    public void select_Fix_Date() {

        Scooter_Fix_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(新增保養紀錄.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Scooter_Fix_Date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    //接收從【新增保養紀錄】intent傳過來的資料
    public void get_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);
        id = getIntent().getStringExtra("id");
    }

    //確定輸入Button的觸發事件，呼叫資料庫方法，新增保養資料
    private Button.OnClickListener btnEnterListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(Scooter_Fix_Date.getText().toString().equals("") && Scooter_Fix_Cost.getText().toString().equals("") && Scooter_Fix_Mileage.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "保養費用及總里程及保養日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Date.getText().toString().equals("") && Scooter_Fix_Cost.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "保養費用及保養日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Mileage.getText().toString().equals("") && Scooter_Fix_Cost.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "保養費用及總里程不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Mileage.getText().toString().equals("") && Scooter_Fix_Date.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "總里程及保養日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Cost.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "保養費用不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Mileage.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "總里程不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Fix_Date.getText().toString().equals("")) {
                Toast.makeText(新增保養紀錄.this, "保養日期不得為空白", Toast.LENGTH_SHORT).show();
            } else {
                myDB = new DatabaseManage(新增保養紀錄.this);
                myDB.Insert_Fix_Data(Integer.parseInt(id),
                        Fix_Item_Select,
                        Scooter_Fix_Date.getText().toString().trim(),
                        Integer.parseInt(Scooter_Fix_Cost.getText().toString()),
                        Integer.parseInt(Scooter_Fix_Mileage.getText().toString()));

                checkMark_animation();
            }
        }
    };

    //顯示預設為隱藏的勾選動畫，並間隔1320毫秒再隱藏動畫並切換畫面
    public void checkMark_animation() {
        check.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check.setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setClass(新增保養紀錄.this, 保養.class);
                startActivity(intent);
            }
        },1320);
    }

    //取消Button的觸發事件，intent回【保養】頁面
    private Button.OnClickListener btnbackListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(新增保養紀錄.this,保養.class);
            startActivity(intent);
        }
    };

    //頂部導航列的返回鍵的觸發事件，intent回【保養】頁面
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(新增保養紀錄.this,保養.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
