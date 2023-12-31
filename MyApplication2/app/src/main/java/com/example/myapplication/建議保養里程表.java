package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class 建議保養里程表 extends AppCompatActivity {

    //宣告全域變數
    ListView lisPrefer;
    String[] Fix_Item = new String[] {"機油", "齒輪油", "煞車皮", "煞車線", "煞車油", "空氣濾網", "火星塞", "傳動皮帶", "普利珠", "普利盤", "離合器片", "前輪胎", "後輪胎", "電瓶"};
    String[] Fix_Km = new String[] {"1,000 公里", "2,000 公里", "10,000 公里", "10,000 公里", "20,000 公里", "5,000 公里", "10,000 公里", "15,000 公里", "20,000 公里", "20,000 公里", "20,000 公里",
            "15,000 公里", "10,000 公里", "3~5 (年)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_fix);

        //設定顯示頂部導航列的返回鍵
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //設定變數及對應的元件id
        lisPrefer = (ListView)findViewById(R.id.lisPrefer);

        //設定ListView的adapter
        MyAdapter adapter = new MyAdapter(this);
        lisPrefer.setAdapter(adapter);
    }

    //自訂的adapter類別
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public MyAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return Fix_Item.length;
        }

        @Override
        public Object getItem(int position) {
            return Fix_Item[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //設定ListView使用自訂的Layout版面配置，並設定變數及對應的元件id，及從陣列裡取出資料放進TextView裡
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.recommend_fix_show,null);

            TextView txtName = ((TextView)convertView.findViewById(R.id.txtName));
            TextView txtKm = ((TextView)convertView.findViewById(R.id.txtKm));

            txtName.setText(Fix_Item[position]);

            txtKm.setText((Fix_Km[position]));
            return convertView;
        }
    }

    //頂部導航列返回鍵的觸發事件
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent=new Intent();
            intent.setClass(建議保養里程表.this, 保養.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}