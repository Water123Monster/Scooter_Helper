package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class 車種資料 extends AppCompatActivity {

    //宣告全域變數
    private Button btn_Enter,btn_Cancel;
    private Spinner Spinner_Scooter_Data_Factory, Spinner_Scooter_Data_Model;

    String[] Scooter_Factory = new String[] {"三陽 SYM","光陽 KMYCO","山葉 YAMAHA","摩特動力 PGO"};
    String[] SYM = {"Z1 attila","JET SR","DRG BT"};
    String[] KMYCO = {"GP 125","VJR 125","Racing S 150"};
    String[] YAMAHA = {"RS NEO","CYGNUS GRYPHUS","FORCE"};
    String[] PGO = {"BON 125","ALPHA MAX","TIGRA 200"};
    String Scooter_Factory_Select, Scooter_Model_Select;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_data);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        btn_Enter = findViewById(R.id.btn_Scooter_Data_Enter);
        btn_Cancel = findViewById(R.id.btn_Scooter_Data_Cancel);
        Spinner_Scooter_Data_Factory = findViewById(R.id.Spinner_Scooter_Data_Factory);
        Spinner_Scooter_Data_Model = findViewById(R.id.Spinner_Scooter_Data_Model);

        //設定選擇廠牌的下拉式選單的Adapter
        ArrayAdapter<String> adapter_Scooter_Factory = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Scooter_Factory);
        adapter_Scooter_Factory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_Scooter_Data_Factory.setAdapter(adapter_Scooter_Factory);
        Spinner_Scooter_Data_Factory.setOnItemSelectedListener(Spinner_Scooter_Factory_Listener);

        //設定Button的監聽
        btn_Enter.setOnClickListener(btnEnterListener);
        btn_Cancel.setOnClickListener(btnbackListener);
    }

    //下拉式選單的觸發事件
    private Spinner.OnItemSelectedListener Spinner_Scooter_Factory_Listener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Scooter_Factory_Select = Scooter_Factory[position];
            //如果點擊廠牌SYM，則設定選擇型號的下拉式選單的Adapter
            if(position == 0) {
                ArrayAdapter<String> adapter_SYM = new ArrayAdapter<String>(車種資料.this,android.R.layout.simple_spinner_dropdown_item,SYM);
                adapter_SYM.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Data_Model.setAdapter(adapter_SYM);

                Spinner_Scooter_Data_Model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                ArrayAdapter<String> adapter_KMYCO = new ArrayAdapter<String>(車種資料.this,android.R.layout.simple_spinner_dropdown_item,KMYCO);
                adapter_KMYCO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Data_Model.setAdapter(adapter_KMYCO);

                Spinner_Scooter_Data_Model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                ArrayAdapter<String> adapter_YAMAHA = new ArrayAdapter<String>(車種資料.this,android.R.layout.simple_spinner_dropdown_item,YAMAHA);
                adapter_YAMAHA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Data_Model.setAdapter(adapter_YAMAHA);

                Spinner_Scooter_Data_Model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                ArrayAdapter<String> adapter_PGO = new ArrayAdapter<String>(車種資料.this,android.R.layout.simple_spinner_dropdown_item,PGO);
                adapter_PGO.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spinner_Scooter_Data_Model.setAdapter(adapter_PGO);

                Spinner_Scooter_Data_Model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    //確定輸入Button的觸發事件，傳值(型號)並intent到【查詢車種資料】頁面
    private Button.OnClickListener btnEnterListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(車種資料.this, 查詢車種資料.class);
            intent.putExtra("Scooter_Model_Select", Scooter_Model_Select);
            startActivity(intent);
        }
    };

    //取消Button的觸發事件，intent回【MainActivity】頁面
    private Button.OnClickListener btnbackListener=new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(車種資料.this, MainActivity.class);
            startActivity(intent);
        }
    };

    //頂部導航列的返回鍵的觸發事件，intent回【MainActivity】頁面
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(車種資料.this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
