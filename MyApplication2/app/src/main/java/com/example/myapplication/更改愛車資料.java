package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Calendar;


public class 更改愛車資料 extends AppCompatActivity {

    //宣告全域變數
    private EditText Scooter_Name_Input_Update, Scooter_License_Input_Update, Scooter_Manufacture_Date_Input_Update;
    private Button btn_Enter, btn_Cancel;
    private Spinner Spinner_Scooter_Factory_Update, Spinner_Scooter_Model_Update;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String[] Scooter_Factory = new String[] {"三陽 SYM","光陽 KMYCO","山葉 YAMAHA","摩特動力 PGO"};
    String[] SYM = {"Z1 attila","JET SR","DRG BT"};
    String[] KMYCO = {"GP 125","VJR 125","Racing S 150"};
    String[] YAMAHA = {"RS NEO","CYGNUS GRYPHUS","FORCE"};
    String[] PGO = {"BON 125","ALPHA MAX","TIGRA 200"};
    String Scooter_Factory_Select, Scooter_Model_Select;

    String id, Name, License, Manufacture_Date;

    Handler handler = new Handler();
    LottieAnimationView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_my_scooter_update);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        Scooter_Name_Input_Update = findViewById(R.id.Scooter_Name_Input_Update);
        Scooter_License_Input_Update = findViewById(R.id.Scooter_License_Input_Update);
        Scooter_Manufacture_Date_Input_Update = findViewById(R.id.Scooter_Manufacture_Date_Input_Update);
        Spinner_Scooter_Factory_Update = findViewById(R.id.Spinner_Scooter_Factory_Update);
        Spinner_Scooter_Model_Update = findViewById(R.id.Spinner_Scooter_Model_Update);

        check = findViewById(R.id.check);

        //呼叫get_And_Set_IntentData
        get_And_Set_IntentData();

        //設定選擇廠牌的下拉式選單的Adapter
        ArrayAdapter<String> adapter_Scooter_Factory = new ArrayAdapter<String>(更改愛車資料.this,android.R.layout.simple_spinner_dropdown_item,Scooter_Factory);
        adapter_Scooter_Factory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Scooter_Factory_Update.setAdapter(adapter_Scooter_Factory);
        Spinner_Scooter_Factory_Update.setOnItemSelectedListener(Spinner_Scooter_Factory_Listener);

        //呼叫方法select_Scooter_Manufacture_Date
        select_Scooter_Manufacture_Date();

        //設定Button的監聽事件
        btn_Enter = findViewById(R.id.btn_My_Scooter_Enter_Update);
        btn_Enter.setOnClickListener(btnEnterListener);
        btn_Cancel = findViewById(R.id.btn_My_Scooter_Cancel_Update);
        btn_Cancel.setOnClickListener(btnCancelListener);
    }

    //設定選擇年/月/日期的小工具
    public void select_Scooter_Manufacture_Date() {

        Scooter_Manufacture_Date_Input_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(更改愛車資料.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Scooter_Manufacture_Date_Input_Update.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    //接收從【查詢愛車資料】intent傳過來的資料，再設定給EditText顯示更改前的資料
    public void get_And_Set_IntentData() {

        if(getIntent().hasExtra("id") && getIntent().hasExtra("Name") && getIntent().hasExtra("License") && getIntent().hasExtra("Manufacture_Date")){
            id = getIntent().getStringExtra("id");
            Name = getIntent().getStringExtra("Name");
            License = getIntent().getStringExtra("License");
            Manufacture_Date = getIntent().getStringExtra("Manufacture_Date");

            Scooter_Name_Input_Update.setText(Name);
            Scooter_License_Input_Update.setText(License);
            Scooter_Manufacture_Date_Input_Update.setText(Manufacture_Date);

        }else{
            Toast.makeText(this, "沒有資料", Toast.LENGTH_SHORT).show();
        }
    }

    //下拉式選單的觸發事件
    private Spinner.OnItemSelectedListener Spinner_Scooter_Factory_Listener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Scooter_Factory_Select = Scooter_Factory[position];
            //如果點擊廠牌SYM，則設定選擇型號的下拉式選單的Adapter
            if(position == 0) {
                ArrayAdapter<String> adapter_SYM = new ArrayAdapter<String>(更改愛車資料.this,android.R.layout.simple_spinner_dropdown_item,SYM);
                adapter_SYM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Model_Update.setAdapter(adapter_SYM);

                Spinner_Scooter_Model_Update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Scooter_Model_Select = SYM[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });
            }
            //如果點擊廠牌KMYCO，則設定選擇型號的下拉式選單的Adapter
            if(position == 1) {
                ArrayAdapter<String> adapter_KMYCO = new ArrayAdapter<String>(更改愛車資料.this,android.R.layout.simple_spinner_dropdown_item,KMYCO);
                adapter_KMYCO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Model_Update.setAdapter(adapter_KMYCO);

                Spinner_Scooter_Model_Update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Scooter_Model_Select = KMYCO[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });
            }
            //如果點擊廠牌YAMAHA，則設定選擇型號的下拉式選單的Adapter
            if(position == 2) {
                ArrayAdapter<String> adapter_YAMAHA = new ArrayAdapter<String>(更改愛車資料.this,android.R.layout.simple_spinner_dropdown_item,YAMAHA);
                adapter_YAMAHA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Model_Update.setAdapter(adapter_YAMAHA);

                Spinner_Scooter_Model_Update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Scooter_Model_Select = YAMAHA[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });
            }
            //如果點擊廠牌PGO，則設定選擇型號的下拉式選單的Adapter
            if(position == 3) {
                ArrayAdapter<String> adapter_PGO = new ArrayAdapter<String>(更改愛車資料.this,android.R.layout.simple_spinner_dropdown_item,PGO);
                adapter_PGO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Model_Update.setAdapter(adapter_PGO);

                Spinner_Scooter_Model_Update.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Scooter_Model_Select = PGO[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //
                    }
                });
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    //確定輸入Button的觸發事件，呼叫資料庫方法更改愛車資料
    public Button.OnClickListener btnEnterListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(Scooter_Name_Input_Update.getText().toString().equals("") && Scooter_License_Input_Update.getText().toString().equals("")) {
                Toast.makeText(更改愛車資料.this, "愛車名稱及車牌不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_License_Input_Update.getText().toString().equals("")) {
                Toast.makeText(更改愛車資料.this, "愛車車牌不得為空白", Toast.LENGTH_SHORT).show();
            } else if(Scooter_Name_Input_Update.getText().toString().equals("")) {
                Toast.makeText(更改愛車資料.this, "愛車名稱不得為空白", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseManage myDB = new DatabaseManage(更改愛車資料.this);
                myDB.Update_My_Scooter(id,
                        Scooter_Name_Input_Update.getText().toString().trim(),
                        Scooter_License_Input_Update.getText().toString().trim(),
                        Scooter_Model_Select,
                        Scooter_Manufacture_Date_Input_Update.getText().toString().trim(),
                        0);

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
                intent.setClass(更改愛車資料.this, 車庫管理.class);
                startActivity(intent);
            }
        },1320);
    }

    //取消Button的觸發事件，intent回【車庫管理】頁面
    public Button.OnClickListener btnCancelListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            onNewIntent(intent);
            intent.setClass(更改愛車資料.this, 車庫管理.class);
            startActivity(intent);
        }
    };

    //頂部導航列的返回鍵的觸發事件，intent回【車庫管理】頁面
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            onNewIntent(intent);
            intent.setClass(更改愛車資料.this, 車庫管理.class);
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