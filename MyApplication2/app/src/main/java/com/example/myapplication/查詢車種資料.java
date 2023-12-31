package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class 查詢車種資料 extends AppCompatActivity {

    //宣告全域變數
    ListView Scooter_Data_Show;
    String Scooter_Data_Factory, Scooter_Data_Model, Scooter_Data_Engine_Displacement, Scooter_Data_Fuel_Size, Scooter_Data_Power_Form, Scooter_Data_Maximum_Horsepower, Scooter_Data_Maximum_Torque,
            Scooter_Data_Equipment_Weight, Scooter_Data_Front_Brake_System, Scooter_Data_Rear_Brake_System, Scooter_Data_Front_Suspension_System, Scooter_Data_Rear_Suspension_System,
            Scooter_Data_Length_width_height, Scooter_Data_Wheelbase, Scooter_Data_Sitting_Height, Scooter_Data_Front_Wheel_Size, Scooter_Data_Rear_Wheel_Size, Scooter_Data_Headlight,
            Scooter_Data_Taillight, Scooter_Data_Front_Turn_Signal, Scooter_Data_Rear_Turn_Signal, Scooter_Data_Energy_Efficiency_Rating, Scooter_Data_Fuel_Consumption_Test_Value,Scooter_Data_Environmental_Regulations;
    String[] Scooter_Data_Column = new String[]{"廠牌","型號","實際排氣量(c.c.)","油箱容量(L)","動力形式","最大馬力","最大扭力","裝備重量(Kg)","前煞車系統","後煞車系統","前避震系統","後避震系統","長*寬*高(mm)","軸距(mm)",
                                                "座高(mm)","前輪尺寸","後輪尺寸","前大燈","尾燈","前方向燈","後方向燈","能源效率等級","油耗測試值(Km/L)","環保法規"};
    String[] Scooter_Data_Data;
    String Scooter_Model_Select;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scooter_data_show);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //呼叫方法Select_Data
        Select_Data();

        //設定ListView的adapter
        Scooter_Data_Show = findViewById(R.id.Scooter_Data_Show);
        MyAdapter adapter = new MyAdapter(this);
        Scooter_Data_Show.setAdapter(adapter);

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
            txt_Column_Data.setText(Scooter_Data_Data[position]);

            return convertView;
        }
    }

    //先呼叫get_Intent取得從【車種資料】intent傳過來的資料(型號)，再呼叫資料庫方法，查詢某愛車的所有資料，並存入字串變數，最後再將字串變數放進字串陣列中
    public void Select_Data() {
        get_Intent();
        DatabaseManage myDB = new DatabaseManage(查詢車種資料.this);
        Cursor cursor = myDB.Select_Scooter_Data(Scooter_Model_Select);

        while (cursor.moveToNext()){
            Scooter_Data_Factory = cursor.getString(0);
            Scooter_Data_Model = cursor.getString(1);
            Scooter_Data_Engine_Displacement = cursor.getString(2);
            Scooter_Data_Fuel_Size = cursor.getString(3);
            Scooter_Data_Power_Form = cursor.getString(4);
            Scooter_Data_Maximum_Horsepower = cursor.getString(5);
            Scooter_Data_Maximum_Torque = cursor.getString(6);
            Scooter_Data_Equipment_Weight = cursor.getString(7);
            Scooter_Data_Front_Brake_System = cursor.getString(8);
            Scooter_Data_Rear_Brake_System = cursor.getString(9);
            Scooter_Data_Front_Suspension_System = cursor.getString(10);
            Scooter_Data_Rear_Suspension_System = cursor.getString(11);
            Scooter_Data_Length_width_height = cursor.getString(12);
            Scooter_Data_Wheelbase = cursor.getString(13);
            Scooter_Data_Sitting_Height = cursor.getString(14);
            Scooter_Data_Front_Wheel_Size = cursor.getString(15);
            Scooter_Data_Rear_Wheel_Size = cursor.getString(16);
            Scooter_Data_Headlight = cursor.getString(17);
            Scooter_Data_Taillight = cursor.getString(18);
            Scooter_Data_Front_Turn_Signal = cursor.getString(19);
            Scooter_Data_Rear_Turn_Signal = cursor.getString(20);
            Scooter_Data_Energy_Efficiency_Rating = cursor.getString(21);
            Scooter_Data_Fuel_Consumption_Test_Value = cursor.getString(22);
            Scooter_Data_Environmental_Regulations = cursor.getString(23);
        }

        Scooter_Data_Data = new String[] {Scooter_Data_Factory, Scooter_Data_Model, Scooter_Data_Engine_Displacement, Scooter_Data_Fuel_Size, Scooter_Data_Power_Form, Scooter_Data_Maximum_Horsepower,
                Scooter_Data_Maximum_Torque, Scooter_Data_Equipment_Weight, Scooter_Data_Front_Brake_System, Scooter_Data_Rear_Brake_System, Scooter_Data_Front_Suspension_System, Scooter_Data_Rear_Suspension_System,
                Scooter_Data_Length_width_height, Scooter_Data_Wheelbase, Scooter_Data_Sitting_Height, Scooter_Data_Front_Wheel_Size, Scooter_Data_Rear_Wheel_Size, Scooter_Data_Headlight, Scooter_Data_Taillight,
                Scooter_Data_Front_Turn_Signal, Scooter_Data_Rear_Turn_Signal, Scooter_Data_Energy_Efficiency_Rating, Scooter_Data_Fuel_Consumption_Test_Value, Scooter_Data_Environmental_Regulations};
    }

    //接收從【車種資料】intent傳過來的資料(型號)
    public void get_Intent() {
        Intent get_intent = this.getIntent();
        onNewIntent(get_intent);
        Scooter_Model_Select = getIntent().getStringExtra("Scooter_Model_Select");
    }

    //頂部導航列返回鍵的觸發事件
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(查詢車種資料.this,車種資料.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
