package com.example.a0927androidtest;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private final static String SharedPreferencesFileName="config";
    private final static String Key_UserName="UserName";
    private final static String Key_LoginDate="LoginDate";
    private final static String Key_UserType="UserType";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences(SharedPreferencesFileName,
                MODE_PRIVATE);
        editor=preferences.edit();
        Button btnWrite=(Button)findViewById(R.id.button1);
        Button btnRead=(Button)findViewById(R.id.button2);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strLoginDate = simpleDateFormat.format(new Date());
                editor.putString(Key_UserName, "lhq");
                editor.putString(Key_LoginDate, strLoginDate);
                editor.putInt(Key_UserType, 2018011129);
                editor.commit();
            }
        });
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUserName = preferences.getString(Key_UserName, null);
                String strLoginDate = preferences.getString(Key_LoginDate, null);
                int nUserType=preferences.getInt(Key_UserType,0);
                if (strUserName != null && strLoginDate != null)
                    Toast.makeText(MainActivity.this, "name:" + strUserName + "time:" + strLoginDate+"id:"+nUserType, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "无数据", Toast.LENGTH_LONG).show();
            }
        });
    }




}