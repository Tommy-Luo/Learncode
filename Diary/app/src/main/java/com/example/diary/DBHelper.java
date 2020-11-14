package com.example.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "wordinformation";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public DBHelper(Context context , String baseName , int version)
    {
        super(context , baseName , null , version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表名
        db.execSQL("CREATE TABLE IF NOT EXISTS wordinformation (_id INTEGER PRIMARY KEY AUTOINCREMENT,word VARCHAR(20),meaning VARCHAR(20),example VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "wordinformation");
        onCreate(db);

    }
}