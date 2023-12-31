package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseManage extends SQLiteOpenHelper {

    private Context context;
    //資料庫_機不可失
    private static final String Database_Name = "機不可失.db";
    private static final int Database_Version = 1;

    //資料表_車庫管理
    private static final String Table_1_Name = "車庫管理";
    private static final String Column_Scooter_id = "_id";
    private static final String Column_Scooter_Name = "名稱";
    private static final String Column_Scooter_License = "車牌";
    private static final String Column_Scooter_Model = "型號";
    private static final String Column_Scooter_Manufacture_Date = "出廠日期";
    private static final String Column_Scooter_age = "車齡";

    //油耗_資料表
    private static final String Table_2_Name = "油耗";
    private static final String Column_Oil_Data_id = "_id";
    private static final String Column_Oil_Scooter_id = "機車_id";
    private static final String Column_Oil_Add_Oil_Quantity = "加油量";
    private static final String Column_Oil_Add_Oil_Date = "加油日期";
    private static final String Column_Oil_Mileage = "總里程";
    private static final String Column_Oil_Fuel_Consumption = "區間油耗";
    private static final String Column_Oil_Fuel_Kind = "加油種類";

    //保養_資料表
    private static final String Table_3_Name = "保養";
    private static final String Column_Fix_Data_id = "_id";
    private static final String Column_Fix_Scooter_id = "機車_id";
    private static final String Column_Fix_Maintainence_Check_Item = "保養項目";
    private static final String Column_Fix_Maintainence_Check_Date = "保養日期";
    private static final String Column_Fix_Maintainence_Check_Cost = "保養費用";
    private static final String Column_Fix_Maintainence_Check_Mileage = "總里程";

    //車種資料_資料表
    private static final String Table_4_Name = "車種資料";
    private static final String Column_Scooter_Data_Factory = "廠牌";
    private static final String Column_Scooter_Data_Model = "型號";
    private static final String Column_Scooter_Data_Engine_Displacement = "[實際排氣量(c.c.)]";
    private static final String Column_Scooter_Data_Fuel_Size = "[油箱容量(L)]";
    private static final String Column_Scooter_Data_Power_Form = "動力形式";
    private static final String Column_Scooter_Data_Maximum_Horsepower = "最大馬力";
    private static final String Column_Scooter_Data_Maximum_Torque = "最大扭力";
    private static final String Column_Scooter_Data_Equipment_Weight = "[裝備重量(Kg)]";
    private static final String Column_Scooter_Data_Front_Brake_System = "前煞車系統";
    private static final String Column_Scooter_Data_Rear_Brake_System = "後煞車系統";
    private static final String Column_Scooter_Data_Front_Suspension_System = "前避震系統";
    private static final String Column_Scooter_Data_Rear_Suspension_System = "後避震系統";
    private static final String Column_Scooter_Data_Length_width_height = "[長*寬*高(mm)]";
    private static final String Column_Scooter_Data_Wheelbase = "[軸距(mm)]";
    private static final String Column_Scooter_Data_Sitting_Height = "[座高(mm)]";
    private static final String Column_Scooter_Data_Front_Wheel_Size = "前輪尺寸";
    private static final String Column_Scooter_Data_Rear_Wheel_Size = "後輪尺寸";
    private static final String Column_Scooter_Data_Headlight = "前大燈";
    private static final String Column_Scooter_Data_Taillight = "尾燈";
    private static final String Column_Scooter_Data_Front_Turn_Signal = "前方向燈";
    private static final String Column_Scooter_Data_Rear_Turn_Signal = "後方向燈";
    private static final String Column_Scooter_Data_Energy_Efficiency_Rating = "能源效率等級";
    private static final String Column_Scooter_Data_Fuel_Consumption_Test_Value = "[油耗測試值(Km/L)]";
    private static final String Column_Scooter_Data_Environmental_Regulations = "環保法規";

    public DatabaseManage(@Nullable Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立資料表_車庫管理
        String query_My_Scooter = "CREATE TABLE " + Table_1_Name +
                " (" + Column_Scooter_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Scooter_Name + " TEXT, " +
                Column_Scooter_License + " TEXT, " +
                Column_Scooter_Model + " TEXT, " +
                Column_Scooter_Manufacture_Date + " TEXT, " +
                Column_Scooter_age + " INTEGER);";
        db.execSQL(query_My_Scooter);

        //建立資料表_油耗
        String query_Oil = "CREATE TABLE " + Table_2_Name +
                " (" + Column_Oil_Data_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Oil_Scooter_id + " INTEGER, " +
                Column_Oil_Add_Oil_Quantity + " REAL, " +
                Column_Oil_Add_Oil_Date + " TEXT, " +
                Column_Oil_Mileage + " INTEGER, " +
                Column_Oil_Fuel_Consumption + " TEXT, " +
                Column_Oil_Fuel_Kind + " TEXT);";
        db.execSQL(query_Oil);

        //建立資料表_保養
        String query_Fix = "CREATE TABLE " + Table_3_Name +
                " (" + Column_Fix_Data_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Column_Fix_Scooter_id + " INTEGER, " +
                Column_Fix_Maintainence_Check_Item + " TEXT, " +
                Column_Fix_Maintainence_Check_Date + " TEXT, " +
                Column_Fix_Maintainence_Check_Cost + " INTEGER, " +
                Column_Fix_Maintainence_Check_Mileage + " INTEGER);";
        db.execSQL(query_Fix);

        //建立資料表_車種資料
        String query_Scooter_Data = "CREATE TABLE " + Table_4_Name +
                " (" +  Column_Scooter_Data_Factory + " TEXT, " +
                Column_Scooter_Data_Model + " TEXT PRIMARY KEY, " +
                Column_Scooter_Data_Engine_Displacement + " REAL, " +
                Column_Scooter_Data_Fuel_Size + " REAL, " +
                Column_Scooter_Data_Power_Form + " TEXT, " +
                Column_Scooter_Data_Maximum_Horsepower + " TEXT, " +
                Column_Scooter_Data_Maximum_Torque + " TEXT, " +
                Column_Scooter_Data_Equipment_Weight + " INTEGER, " +
                Column_Scooter_Data_Front_Brake_System + " TEXT, " +
                Column_Scooter_Data_Rear_Brake_System + " TEXT, " +
                Column_Scooter_Data_Front_Suspension_System + " TEXT, " +
                Column_Scooter_Data_Rear_Suspension_System + " TEXT, " +
                Column_Scooter_Data_Length_width_height + " TEXT, " +
                Column_Scooter_Data_Wheelbase + " INTEGER, " +
                Column_Scooter_Data_Sitting_Height + " INTEGER, " +
                Column_Scooter_Data_Front_Wheel_Size + " TEXT, " +
                Column_Scooter_Data_Rear_Wheel_Size + " TEXT, " +
                Column_Scooter_Data_Headlight + " TEXT, " +
                Column_Scooter_Data_Taillight + " TEXT, " +
                Column_Scooter_Data_Front_Turn_Signal + " TEXT, " +
                Column_Scooter_Data_Rear_Turn_Signal + " TEXT, " +
                Column_Scooter_Data_Energy_Efficiency_Rating + " TEXT, " +
                Column_Scooter_Data_Fuel_Consumption_Test_Value + " REAL, " +
                Column_Scooter_Data_Environmental_Regulations + " TEXT);";
        db.execSQL(query_Scooter_Data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_1_Name);
        onCreate(db);
    }

    //------------------------------------------------以下為【資料表_車庫管理】所使用的方法------------------------------------------------

    //新增愛車資料的方法(INSERT)，被呼叫於【新增愛車.class】
    public void Insert_My_Scooter(String Scooter_Name, String Scooter_License, String Scooter_Model, String Scooter_Manufacture_Date, int Scooter_age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Scooter_Name, Scooter_Name);
        cv.put(Column_Scooter_License, Scooter_License);
        cv.put(Column_Scooter_Model, Scooter_Model);
        cv.put(Column_Scooter_Manufacture_Date, Scooter_Manufacture_Date);
        cv.put(Column_Scooter_age, Scooter_age);

        long result = db.insert(Table_1_Name,null, cv);

        if(result == -1) {
            Toast.makeText(context, "新增愛車資料失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "新增愛車資料成功", Toast.LENGTH_SHORT).show();
        }
    }

    //查詢所有愛車的方法(SELECT)，被呼叫於【車庫管理.class】，用來顯示所有愛車
    Cursor Select_All_My_Scooter() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_1_Name;

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //查詢某愛車的資料的方法(SELECT)，被呼叫於【查詢愛車資料.class】，用來顯示id=?的愛車之資料
    Cursor Select_My_Scooter_Data(String _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Table_1_Name + " WHERE _id = " + _id;

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //更改愛車資料的方法(UPDATE)，被呼叫於【更改愛車資料.class】，用來更改id=?的愛車資料
    void Update_My_Scooter(String row_id, String Scooter_Name, String Scooter_License, String Scooter_Model, String Scooter_Manufacture_Date, int Scooter_age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Scooter_Name, Scooter_Name);
        cv.put(Column_Scooter_License, Scooter_License);
        cv.put(Column_Scooter_Model, Scooter_Model);
        cv.put(Column_Scooter_Manufacture_Date, Scooter_Manufacture_Date);
        cv.put(Column_Scooter_age, Scooter_age);

        long result = db.update(Table_1_Name, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "更改愛車資料失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "更改愛車資料成功", Toast.LENGTH_SHORT).show();
        }
    }

    //更改愛車車齡的方法(UPDATE)，被呼叫於【查詢愛車資料.class】，用來更改id=?的愛車車齡
    void Update_My_Scooter_Age(String row_id, int Scooter_age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Scooter_age, Scooter_age);

        long result = db.update(Table_1_Name, cv, "_id=?", new String[]{row_id});
    }

    //刪除愛車資料的方法(DELETE)，被呼叫於【查詢愛車資料.class】，用來刪除id=?的愛車資料
    void Delete_My_Scooter(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Table_1_Name,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "刪除愛車失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "刪除愛車成功", Toast.LENGTH_SHORT).show();
        }
    }
    
    //刪除所有愛車資料的方法(DELETE)，被呼叫於【車庫管理.class】，用來刪除所有的愛車資料
    void Delete_All_My_Scooter() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_1_Name);
    }

    //------------------------------------------------以上為【資料表_車庫管理】所使用的方法------------------------------------------------

    //------------------------------------------------以下為【資料表_油耗】所使用的方法----------------------------------------------------

    //新增油耗紀錄的方法(INSERT)，被呼叫於【新增油耗紀錄.class】
    public void Insert_Oil_Data(int Oil_Scooter_id, Double Oil_Add_Oil_Quantity, String Oil_Add_Oil_Date, int Oil_Mileage, String Oil_Fuel_Consumption, String Oil_Fuel_Kind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Oil_Scooter_id, Oil_Scooter_id);
        cv.put(Column_Oil_Add_Oil_Quantity, Oil_Add_Oil_Quantity);
        cv.put(Column_Oil_Add_Oil_Date, Oil_Add_Oil_Date);
        cv.put(Column_Oil_Mileage, Oil_Mileage);
        cv.put(Column_Oil_Fuel_Consumption, Oil_Fuel_Consumption);
        cv.put(Column_Oil_Fuel_Kind, Oil_Fuel_Kind);

        long result = db.insert(Table_2_Name,null, cv);

        if(result == -1) {
            Toast.makeText(context, "新增油耗資料失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "新增油耗資料成功", Toast.LENGTH_SHORT).show();
        }
    }

    //查詢某愛車的前一筆總里程資料的方法(SELECT)，才能計算當期油耗，被呼叫於【新增油耗紀錄.class】
    Cursor Select_Oil_Mileage(String _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 總里程 FROM " + Table_2_Name + " WHERE 機車_id = " + _id + " ORDER BY 總里程 DESC LIMIT 0,1";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //查詢某愛車的油耗紀錄的方法(SELECT)，被呼叫於【查詢油耗紀錄.class】
    Cursor Select_All_Oil_Data(String _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_2_Name + " WHERE 機車_id = " + _id + " ORDER BY 加油日期";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //刪除某愛車的某一筆油耗紀錄的方法(DELETE)，被呼叫於【查詢油耗紀錄.class】，用來刪除id=?的油耗紀錄
    public void Delete_Oil_Data(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Table_2_Name,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "刪除油耗紀錄失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "刪除油耗紀錄成功", Toast.LENGTH_SHORT).show();
        }
    }

    //刪除某愛車全部油耗紀錄的方法(DELETE)，被呼叫於【查詢油耗紀錄.class】，用來刪除機車id=?的愛車油耗紀錄
    public void Delete_All_Oil_Data(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Table_2_Name,"機車_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "刪除油耗紀錄失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "刪除油耗紀錄成功", Toast.LENGTH_SHORT).show();
        }
    }

    //刪除所有愛車全部油耗紀錄的方法(DELETE)，被呼叫於【車庫管理.class】，當所有愛車資料被刪除，而將所有愛車的油耗紀錄也同步刪除，節省資料庫空間
    public void Delete_All_Scooter_Oil_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_2_Name);
    }

    //------------------------------------------------以上為【資料表_油耗】所使用的方法----------------------------------------------------

    //------------------------------------------------以下為【資料表_保養】所使用的方法----------------------------------------------------

    //新增保養紀錄的方法(INSERT)，被呼叫於【新增保養紀錄.class】
    public void Insert_Fix_Data(int Fix_Scooter_id, String Fix_Item, String Fix_Date, int Fix_Cost, int Fix_Mileage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Column_Fix_Scooter_id, Fix_Scooter_id);
        cv.put(Column_Fix_Maintainence_Check_Item, Fix_Item);
        cv.put(Column_Fix_Maintainence_Check_Date, Fix_Date);
        cv.put(Column_Fix_Maintainence_Check_Cost, Fix_Cost);
        cv.put(Column_Fix_Maintainence_Check_Mileage, Fix_Mileage);

        long result = db.insert(Table_3_Name,null, cv);

        if(result == -1) {
            Toast.makeText(context, "新增保養資料失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "新增保養資料成功", Toast.LENGTH_SHORT).show();
        }
    }

    //查詢某愛車的保養紀錄的方法(SELECT)，被呼叫於【查詢保養紀錄.class】
    Cursor Select_All_Fix_Data(String _id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_3_Name + " WHERE 機車_id = " + _id + " ORDER BY 保養日期";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //刪除某愛車的某一筆保養紀錄的方法(DELETE)，被呼叫於【查詢保養紀錄.class】，用來刪除id=?的保養紀錄
    public void Delete_Fix_Data(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Table_3_Name,"_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "刪除保養紀錄失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "刪除保養紀錄成功", Toast.LENGTH_SHORT).show();
        }
    }

    //刪除某愛車全部保養紀錄的方法(DELETE)，被呼叫於【查詢保養紀錄.class】，用來刪除機車id=?的愛車保養紀錄
    public void Delete_All_Fix_Data(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Table_3_Name,"機車_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "刪除保養紀錄失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "刪除保養紀錄成功", Toast.LENGTH_SHORT).show();
        }
    }

    //刪除所有愛車全部保養紀錄的方法(DELETE)，被呼叫於【車庫管理.class】，當所有愛車資料被刪除，而將所有愛車的保養紀錄也同步刪除，節省資料庫空間
    public void Delete_All_Scooter_Fix_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Table_3_Name);
    }

    //------------------------------------------------以上為【資料表_保養】所使用的方法----------------------------------------------------

    //------------------------------------------------以下為【資料表_車種資料】所使用的方法-------------------------------------------------

    //新增機車資料的方法(SELECT)，被呼叫於【MainActivity.class】，目前共有4廠牌共12台機車的資料
    public void Insert_Scooter_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Z1 attila資料
        cv.put(Column_Scooter_Data_Factory, "SYM");
        cv.put(Column_Scooter_Data_Model, "Z1 attila");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.6);
        cv.put(Column_Scooter_Data_Fuel_Size, 5.5);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程2V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "7.92ps@8,100rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "7.8Nm@6,250rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 109);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ205/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ180/對向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1810 x 705 x 1095");
        cv.put(Column_Scooter_Data_Wheelbase, 1255);
        cv.put(Column_Scooter_Data_Sitting_Height, 750);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "3.50-10");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "3.50-10");
        cv.put(Column_Scooter_Data_Headlight, "鹵素燈泡/55W");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "2級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 49.1);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_Z1_attila = db.insert(Table_4_Name,null, cv);

        //JET SR資料
        cv.put(Column_Scooter_Data_Factory, "SYM");
        cv.put(Column_Scooter_Data_Model, "JET SR");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.6);
        cv.put(Column_Scooter_Data_Fuel_Size, 6.8);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "11.2ps@8,000rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "10.6Nm@6,000rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 124);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ226/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ190/單向單活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1810 x 705 x 1100");
        cv.put(Column_Scooter_Data_Wheelbase, 1290);
        cv.put(Column_Scooter_Data_Sitting_Height, 780);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "110/70-12");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "120/70-12");
        cv.put(Column_Scooter_Data_Headlight, "LED");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "3級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 45.8);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_JET_SR = db.insert(Table_4_Name,null, cv);

        //DRG BT資料
        cv.put(Column_Scooter_Data_Factory, "SYM");
        cv.put(Column_Scooter_Data_Model, "DRG BT");
        cv.put(Column_Scooter_Data_Engine_Displacement, 158.0);
        cv.put(Column_Scooter_Data_Fuel_Size, 7.4);
        cv.put(Column_Scooter_Data_Power_Form, "水冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "15.5ps@8,000rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "15.1Nm@5,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 135);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ260/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ230/對向雙活塞內置卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "中置單槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1990 x 735 x 1130");
        cv.put(Column_Scooter_Data_Wheelbase, 1380);
        cv.put(Column_Scooter_Data_Sitting_Height, 803);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "120/70-13");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "130/70-13");
        cv.put(Column_Scooter_Data_Headlight, "LED");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "1級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 42.4);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_DRG_BT = db.insert(Table_4_Name,null, cv);

        //RS NEO資料
        cv.put(Column_Scooter_Data_Factory, "YAMAHA");
        cv.put(Column_Scooter_Data_Model, "RS NEO");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.0);
        cv.put(Column_Scooter_Data_Fuel_Size, 4.0);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程2V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "6.8ps@6,700rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "8.0Nm@4,700rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 93);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ180/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "鼓煞");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "側置單後避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1740 x 670 x 1070");
        cv.put(Column_Scooter_Data_Wheelbase, 1205);
        cv.put(Column_Scooter_Data_Sitting_Height, 755);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "90/90-10");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "90/90-10");
        cv.put(Column_Scooter_Data_Headlight, "鹵素燈泡 / 35W");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "1級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 57.9);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_RS_NEO = db.insert(Table_4_Name,null, cv);

        //CYGNUS GRYPHUS資料
        cv.put(Column_Scooter_Data_Factory, "YAMAHA");
        cv.put(Column_Scooter_Data_Model, "CYGNUS GRYPHUS");
        cv.put(Column_Scooter_Data_Engine_Displacement, 125.0);
        cv.put(Column_Scooter_Data_Fuel_Size, 6.1);
        cv.put(Column_Scooter_Data_Power_Form, "水冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "12.24ps@8,000rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "11.1Nm@6,000rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 124);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ245/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ230/單向單活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1935 x 690 x 1160");
        cv.put(Column_Scooter_Data_Wheelbase, 1340);
        cv.put(Column_Scooter_Data_Sitting_Height, 785);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "120/70-12");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "130/70-12");
        cv.put(Column_Scooter_Data_Headlight, "LED");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "2級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 48.9);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_CYGNUS_GRYPHUS = db.insert(Table_4_Name,null, cv);

        //FORCE資料
        cv.put(Column_Scooter_Data_Factory, "YAMAHA");
        cv.put(Column_Scooter_Data_Model, "FORCE");
        cv.put(Column_Scooter_Data_Engine_Displacement, 155.0);
        cv.put(Column_Scooter_Data_Fuel_Size, 7.4);
        cv.put(Column_Scooter_Data_Power_Form, "水冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "10.5ps@7,300rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "11.7Nm@5,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 141);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ267/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ245/單向單活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "中置單槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1990 x 715 x 1115");
        cv.put(Column_Scooter_Data_Wheelbase, 1405);
        cv.put(Column_Scooter_Data_Sitting_Height, 805);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "120/70-13");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "130/70-13");
        cv.put(Column_Scooter_Data_Headlight, "鹵素燈泡/55W");
        cv.put(Column_Scooter_Data_Taillight, "鹵素燈泡/5W");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "2級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 36.8);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "六期");

        long result_FORCE = db.insert(Table_4_Name,null, cv);

        //GP 125資料
        cv.put(Column_Scooter_Data_Factory, "KYMCO");
        cv.put(Column_Scooter_Data_Model, "GP 125");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.6);
        cv.put(Column_Scooter_Data_Fuel_Size, 6.0);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程2V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "10.1ps@7,500rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "9.9Nm@6,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 113);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "鼓煞");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "中置單槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1800 x 700 x 1080");
        cv.put(Column_Scooter_Data_Wheelbase, 1220);
        cv.put(Column_Scooter_Data_Sitting_Height, 775);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "100/90-10");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "100/90-10");
        cv.put(Column_Scooter_Data_Headlight, "鹵素燈泡/55W");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "2級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 45.8);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_GP_125 = db.insert(Table_4_Name,null, cv);

        //VJR 125資料
        cv.put(Column_Scooter_Data_Factory, "KYMCO");
        cv.put(Column_Scooter_Data_Model, "VJR 125");
        cv.put(Column_Scooter_Data_Engine_Displacement, 121.4);
        cv.put(Column_Scooter_Data_Fuel_Size, 5.5);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "10.2ps@7,000rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "9.4Nm@6,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 103);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "復筒式強化雙後避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1770 x 650 x 1060");
        cv.put(Column_Scooter_Data_Wheelbase, 1210);
        cv.put(Column_Scooter_Data_Sitting_Height, 755);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "90/90-10");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "100/80-10");
        cv.put(Column_Scooter_Data_Headlight, "鹵素燈泡/55W");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "4級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 43.1);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_VJR_125 = db.insert(Table_4_Name,null, cv);

        //Racing S 150資料
        cv.put(Column_Scooter_Data_Factory, "KYMCO");
        cv.put(Column_Scooter_Data_Model, "Racing S 150");
        cv.put(Column_Scooter_Data_Engine_Displacement, 149.0);
        cv.put(Column_Scooter_Data_Fuel_Size, 5.7);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "13.7ps@7,700rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "12.5Nm@5,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 128);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ240/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ200/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1855 x 750 x 1100");
        cv.put(Column_Scooter_Data_Wheelbase, 1270);
        cv.put(Column_Scooter_Data_Sitting_Height, 790);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "110/70-12");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "120/70-12");
        cv.put(Column_Scooter_Data_Headlight, "H17/35W");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "4級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 41.9);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_Racing_S_150 = db.insert(Table_4_Name,null, cv);

        //BON 125資料
        cv.put(Column_Scooter_Data_Factory, "PGO");
        cv.put(Column_Scooter_Data_Model, "BON 125");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.6);
        cv.put(Column_Scooter_Data_Fuel_Size, 5.8);
        cv.put(Column_Scooter_Data_Power_Form, "空冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "10.1ps@8,000rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "9.6Nm@6,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 120);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ200/單向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "鼓煞");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1780 x 735 x 1080");
        cv.put(Column_Scooter_Data_Wheelbase, 1270);
        cv.put(Column_Scooter_Data_Sitting_Height, 760);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "3.50-10");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "3.50-10");
        cv.put(Column_Scooter_Data_Headlight, "H4/60W");
        cv.put(Column_Scooter_Data_Taillight, "鹵素燈泡55W");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "鹵素燈泡/10W");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "3級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 44.4);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_BON_125 = db.insert(Table_4_Name,null, cv);

        //ALPHA MAX資料
        cv.put(Column_Scooter_Data_Factory, "PGO");
        cv.put(Column_Scooter_Data_Model, "ALPHA MAX");
        cv.put(Column_Scooter_Data_Engine_Displacement, 124.8);
        cv.put(Column_Scooter_Data_Fuel_Size, 7.5);
        cv.put(Column_Scooter_Data_Power_Form, "油冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "11.1ps@8,500rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "10.2Nm@6,750rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 123);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ245/單向三活塞卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ200/對向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "雙槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "1870 x 715 x 1100");
        cv.put(Column_Scooter_Data_Wheelbase, 1300);
        cv.put(Column_Scooter_Data_Sitting_Height, 790);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "110/70-12");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "120/70-12");
        cv.put(Column_Scooter_Data_Headlight, "LED");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "3級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 44.4);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "七期");

        long result_ALPHA_MAX = db.insert(Table_4_Name,null, cv);

        //TIGRA 200資料
        cv.put(Column_Scooter_Data_Factory, "PGO");
        cv.put(Column_Scooter_Data_Model, "TIGRA 200");
        cv.put(Column_Scooter_Data_Engine_Displacement, 198.8);
        cv.put(Column_Scooter_Data_Fuel_Size, 8.0);
        cv.put(Column_Scooter_Data_Power_Form, "水冷四行程4V單汽缸");
        cv.put(Column_Scooter_Data_Maximum_Horsepower, "19.7ps@8,500rpm");
        cv.put(Column_Scooter_Data_Maximum_Torque, "17.5Nm@7,500rpm");
        cv.put(Column_Scooter_Data_Equipment_Weight, 156);
        cv.put(Column_Scooter_Data_Front_Brake_System, "碟煞/Φ267/單向四活塞輻射卡鉗");
        cv.put(Column_Scooter_Data_Rear_Brake_System, "碟煞/Φ245/對向雙活塞卡鉗");
        cv.put(Column_Scooter_Data_Front_Suspension_System, "正立式前叉");
        cv.put(Column_Scooter_Data_Rear_Suspension_System, "水平式中置單槍避震");
        cv.put(Column_Scooter_Data_Length_width_height, "2000 x 745 x 1100");
        cv.put(Column_Scooter_Data_Wheelbase, 1450);
        cv.put(Column_Scooter_Data_Sitting_Height, 785);
        cv.put(Column_Scooter_Data_Front_Wheel_Size, "120/70-13");
        cv.put(Column_Scooter_Data_Rear_Wheel_Size, "140/70-13");
        cv.put(Column_Scooter_Data_Headlight, "LED");
        cv.put(Column_Scooter_Data_Taillight, "LED");
        cv.put(Column_Scooter_Data_Front_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Rear_Turn_Signal, "LED");
        cv.put(Column_Scooter_Data_Energy_Efficiency_Rating, "4級");
        cv.put(Column_Scooter_Data_Fuel_Consumption_Test_Value, 30.5);
        cv.put(Column_Scooter_Data_Environmental_Regulations, "六期");

        long result_TIGRA_200 = db.insert(Table_4_Name,null, cv);
    }

    //查詢機車資料的方法(SELECT)，被呼叫於【查詢車種資料.class】，用來顯示model=?的機車之資料
    Cursor Select_Scooter_Data (String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + Table_4_Name + " WHERE 型號 = '" + model + "'";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //查詢愛車資料的方法(SELECT)，被呼叫於【查詢愛車資料.class】，顯示愛車的重點規格 : [實際排氣量(c.c.)],[油箱容量(L)],[裝備重量(Kg)],前煞車系統,後煞車系統,前避震系統,後避震系統,[座高(mm)],環保法規
    Cursor Select_partOf_Scooter_Data (String model) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT [實際排氣量(c.c.)],[油箱容量(L)],[裝備重量(Kg)],前煞車系統,後煞車系統,前避震系統,後避震系統,[座高(mm)],環保法規 FROM " + Table_4_Name + " WHERE 型號 = '" + model + "'";

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    //------------------------------------------------以上為【資料表_車種資料】所使用的方法-------------------------------------------------

}
