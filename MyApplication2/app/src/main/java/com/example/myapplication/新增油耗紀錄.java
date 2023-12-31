package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Calendar;

public class 新增油耗紀錄 extends AppCompatActivity {

    //宣告全域變數
    DatabaseManage myDB;
    EditText Scooter_Oil_Liter, Scooter_Oil_Total_Mileage, Scooter_Add_Oil_Date;
    private Button btn_Enter,btn_Cancel;
    Spinner Spinner_Oil;
    String[] Oil_Fuel_Kind = new String[] {"92無鉛汽油","95無鉛汽油","98無鉛汽油"};
    String id, Oil_Fuel_Kind_Select, Fuel_Consumption;
    int Mileage_now, Mileage_before = 0;
    Double Oil_Liter, Fuel_Consumption_temp;
    String judgment;
    Handler handler = new Handler();
    LottieAnimationView check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_oil_insert);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        Scooter_Oil_Liter = findViewById(R.id.Scooter_Oil_Liter);
        Scooter_Oil_Total_Mileage = findViewById(R.id.Scooter_Oil_Total_Mileage);
        Scooter_Add_Oil_Date = findViewById(R.id.Scooter_Add_Oil_Date);
        btn_Enter = findViewById(R.id.btn_Oil_Enter);
        btn_Cancel = findViewById(R.id.btn_Oil_Cancel);

        Spinner_Oil = findViewById(R.id.Spinner_Oil);

        check = findViewById(R.id.check);

        //設定選擇油的種類的下拉式選單的Adapter
        ArrayAdapter<String> adapter_Oil_Fuel_Kind = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Oil_Fuel_Kind);
        adapter_Oil_Fuel_Kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Oil.setAdapter(adapter_Oil_Fuel_Kind);
        Spinner_Oil.setOnItemSelectedListener(Spinner_Oil_Listener);

        //呼叫方法select_Add_Oil_Date
        select_Add_Oil_Date();

        //設定Button的監聽事件
        btn_Enter.setOnClickListener(btnEnterListener);
        btn_Cancel.setOnClickListener(btnbackListener);

        //呼叫方法get_Intent
        get_Intent();
    }

    //下拉式選單的觸發事件
    private Spinner.OnItemSelectedListener Spinner_Oil_Listener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Oil_Fuel_Kind_Select = Oil_Fuel_Kind[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    //設定選擇年/月/日期的小工具
    public void select_Add_Oil_Date() {

        Scooter_Add_Oil_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(新增油耗紀錄.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Scooter_Add_Oil_Date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    //接收從【查詢油耗紀錄】intent傳過來的資料
    public void get_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);
        id = getIntent().getStringExtra("id");

    }

    //計算油耗的方法，先呼叫資料庫方法，查詢某愛車的前一筆總里程，再進行油耗計算
    public String calculate_Fuel_Consumption() {

        if(Scooter_Oil_Liter.getText().toString().equals("") && Scooter_Oil_Total_Mileage.getText().toString().equals("") && Scooter_Add_Oil_Date.getText().toString().equals("")) {
            return judgment = "加油量及總里程及加油日期為空白";
        } else if(Scooter_Oil_Liter.getText().toString().equals("") && Scooter_Oil_Total_Mileage.getText().toString().equals("")) {
            return judgment = "加油量及總里程為空白";
        } else if(Scooter_Oil_Liter.getText().toString().equals("") && Scooter_Add_Oil_Date.getText().toString().equals("")) {
            return judgment = "加油量及加油日期為空白";
        } else if(Scooter_Oil_Total_Mileage.getText().toString().equals("") && Scooter_Add_Oil_Date.getText().toString().equals("")) {
            return judgment = "總里程及加油日期為空白";
        } else if(Scooter_Oil_Liter.getText().toString().equals("")) {
            return judgment = "加油量為空白";
        } else if(Scooter_Oil_Total_Mileage.getText().toString().equals("")) {
            return judgment = "總里程為空白";
        } else if(Scooter_Add_Oil_Date.getText().toString().equals("")) {
            return judgment = "加油日期為空白";
        } else {
            Oil_Liter = Double.parseDouble(Scooter_Oil_Liter.getText().toString());
            Mileage_now = Integer.parseInt(Scooter_Oil_Total_Mileage.getText().toString());
        }

        myDB = new DatabaseManage(新增油耗紀錄.this);
        Cursor cursor = myDB.Select_Oil_Mileage(id);

        //如果cursor沒有傳回值沒有總里程(即該愛車尚無油耗紀錄)，則用初始值 = 0計算
        if(cursor.getCount() == 0) {
            Fuel_Consumption_temp = (Mileage_now - Mileage_before) / Oil_Liter;
            //設定計算結果四捨五入到小數點後第2位
            Fuel_Consumption = new java.text.DecimalFormat("#.00").format(Fuel_Consumption_temp);
        } else {
            if(cursor.moveToNext()) {
                Mileage_before = cursor.getInt(0);
                Fuel_Consumption_temp = (Mileage_now - Mileage_before) / Oil_Liter;
                //設定計算結果四捨五入到小數點後第2位
                Fuel_Consumption = new java.text.DecimalFormat("#.00").format(Fuel_Consumption_temp);
            }
        }
        return judgment = "資料輸入正確";
    }

    //確定輸入Button的觸發事件，呼叫資料庫方法，新增油耗資料
    private Button.OnClickListener btnEnterListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            calculate_Fuel_Consumption();

            if(judgment.equals("加油量及總里程及加油日期為空白")) {
                Toast.makeText(新增油耗紀錄.this, "加油量及總里程及加油日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("加油量及總里程為空白")) {
                Toast.makeText(新增油耗紀錄.this, "加油量及總里程不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("加油量及加油日期為空白")) {
                Toast.makeText(新增油耗紀錄.this, "加油量及加油日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("總里程及加油日期為空白")) {
                Toast.makeText(新增油耗紀錄.this, "總里程及加油日期不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("加油量為空白")) {
                Toast.makeText(新增油耗紀錄.this, "加油量不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("總里程為空白")) {
                Toast.makeText(新增油耗紀錄.this, "總里程不得為空白", Toast.LENGTH_SHORT).show();
            } else if(judgment.equals("加油日期為空白")) {
                Toast.makeText(新增油耗紀錄.this, "加油日期不得為空白", Toast.LENGTH_SHORT).show();
            } else {
                myDB = new DatabaseManage(新增油耗紀錄.this);
                myDB.Insert_Oil_Data(Integer.parseInt(id),
                        Oil_Liter,
                        Scooter_Add_Oil_Date.getText().toString().trim(),
                        Mileage_now,
                        Fuel_Consumption,
                        Oil_Fuel_Kind_Select);
                checkMark_animation();

                /*Intent intent = new Intent();
                intent.setClass(新增油耗紀錄.this, 油耗.class);
                startActivity(intent);*/
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
                intent.setClass(新增油耗紀錄.this, 油耗.class);
                startActivity(intent);
            }
        },1320);
    }

    //取消Button的觸發事件，intent回【油耗】頁面
    private Button.OnClickListener btnbackListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(新增油耗紀錄.this, 油耗.class);
            startActivity(intent);
        }
    };

    //頂部導航列的返回鍵的觸發事件，intent回【油耗】頁面
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(新增油耗紀錄.this,油耗.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
