package com.example.a0920android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String TAG = "--MainActivity--";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //输出日志
        Log.d(TAG, "-----onCreate-----");
        //

    }
    //以下是重写AppCompatActivity中的六个方法，
    @Override
    public void onStart() {
        super.onStart();
        //
        Log.d(TAG, "------onStart-------");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //
        Log.d(TAG, "------ onRestart-------");
    }

    @Override
    public void onResume() {
        super.onResume();
        //
        Log.d(TAG, "------onResume-------");
    }

    @Override
    public void onPause() {
        super.onPause();
        //
        Log.d(TAG, "------onPause-------");
    }

    @Override
    public void onStop() {
        super.onStop();
        //
        Log.d(TAG, "------onStop-------");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //
        Log.d(TAG, "------onDestroy-------");
    }
}