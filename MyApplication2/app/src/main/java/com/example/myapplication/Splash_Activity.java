package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

public class Splash_Activity extends AppCompatActivity {

    //宣告全域變數
    TextView textView5;
    CharSequence charSequence;
    int index;
    long delay = 150;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //設定隱藏標題列
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //設定變數對應的元件id
        textView5 = findViewById(R.id.textView5);

        //設定全螢幕顯示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //呼叫方法animeText，並傳入字元序列 "多功能機車油耗計算器"
        animeText("多功能機車油耗計算器");

        //設定TextView動畫結束後，開始intent到【MainActivity】頁面之間所間隔的時間，3000毫秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(Splash_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    //設定讓字元序列從index = 0開始顯示一個字元直到字元序列.length()結束，字元及字元間隔150毫秒顯示
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView5.setText(charSequence.subSequence(0, index++));

            if(index <= charSequence.length()) {
                handler.postDelayed(runnable,delay);
            }
        }
    };

    //方法animeText，使TextView預設為空，並一個個顯示字元
    public void animeText(CharSequence cs) {

        charSequence = cs;
        index = 0;
        textView5.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);

    }
}