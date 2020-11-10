package com.example.management;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "student_information_db";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本

    private final static String SQL_CREATE_DATABASE = "CREATE TABLE "
            +Student.Student_information.TABLE_NAME + " (" +
            Student.Student_information._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Student.Student_information.COLUMN_NAME_DATE + " TEXT" + "," +
            Student.Student_information.COLUMN_NAME_TEMPERATURE + " TEXT" + ","+
            Student.Student_information.COLUMN_NAME_OTHERS+ " TEXT" + ","
            +  Student.Student_information.COLUMN_NAME_ADDRESS + " TEXT" + " )";

    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Student.Student_information.TABLE_NAME;

    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }


}
