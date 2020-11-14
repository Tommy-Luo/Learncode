package com.example.diarycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelperDb extends SQLiteOpenHelper {
    private final String BASE_NAME = "create table wordinformation(_id integer primary key autoincrement" +
            ", word varchar(255) , meaning VARCHAR(20),example VARCHAR(20))" ;

    public OpenHelperDb(Context context , String baseName , int version)
    {
        super(context , baseName , null , version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库表
        db.execSQL(BASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
