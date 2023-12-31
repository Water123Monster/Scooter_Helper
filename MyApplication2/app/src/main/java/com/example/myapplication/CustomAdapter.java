package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//設定RecyclerView的自訂Adapter類別
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //宣告全域變數
    Context context;
    ArrayList Scooter_id, Scooter_Name, Scooter_License, Scooter_Model, Scooter_Manufacture_Date;
    OnScooterListener MyOnScooterListener;
    Animation translate_animation;

    //建構方法
    CustomAdapter(Context context, ArrayList Scooter_id, ArrayList Scooter_Name, ArrayList Scooter_License,
                  ArrayList Scooter_Model, ArrayList Scooter_Manufacture_Date, OnScooterListener MyOnScooterListener) {
        this.context = context;
        this.Scooter_id = Scooter_id;
        this.Scooter_Name = Scooter_Name;
        this.Scooter_License = Scooter_License;
        this.Scooter_Model = Scooter_Model;
        this.Scooter_Manufacture_Date = Scooter_Manufacture_Date;
        this.MyOnScooterListener = MyOnScooterListener;
    }

    //設定自訂的RecyclerView的版面配置
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_my_scooter, parent, false);
        return new MyViewHolder(view, MyOnScooterListener);
    }

    //從ArrayList裡提出資料，設定RecyclerView的TextView要顯示的資料
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.Scooter_Name_txt.setText(String.valueOf(Scooter_Name.get(position)));
        holder.Scooter_License_txt.setText(String.valueOf(Scooter_License.get(position)));
        holder.Scooter_Model_txt.setText(String.valueOf(Scooter_Model.get(position)));
    }

    @Override
    public int getItemCount() {
        return Scooter_id.size();
    }

    //設定RecyclerView的變數及對應的元件id
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView Scooter_Name_txt,Scooter_License_txt,Scooter_Model_txt;
        LinearLayout my_row_layout;
        OnScooterListener onScooterListener;

        public MyViewHolder(@NonNull View itemView, OnScooterListener onScooterListener) {
            super(itemView);
            Scooter_Name_txt = itemView.findViewById(R.id.Scooter_Name_txt);
            Scooter_License_txt = itemView.findViewById(R.id.Scooter_License_txt);
            Scooter_Model_txt = itemView.findViewById(R.id.Scooter_Model_txt);
            my_row_layout = itemView.findViewById(R.id.my_row_layout);
            translate_animation = AnimationUtils.loadAnimation(context, R.anim.translate_animation);
            my_row_layout.setAnimation(translate_animation);

            this.onScooterListener = onScooterListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onScooterListener.OnScooterClick(getAdapterPosition());
        }
    }

    //自訂介面OnScooterListener，讓車庫管理/油耗/保養可以實作不同的方法內容
    public interface OnScooterListener {
        void OnScooterClick(int position);
    }
}
