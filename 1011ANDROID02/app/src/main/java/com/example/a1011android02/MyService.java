package com.example.a1011android02;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }


    public String calculate(String s){
        String s1,s2;
        int i1;
        if(s.indexOf("*") != -1){
            s1 = s.substring(0, s.indexOf("*"));
            s2 = s.substring(s.indexOf("*")+1);
            i1 = Integer.parseInt(s1) * Integer.parseInt(s2);
            s = i1 + "";
        }else if(s.indexOf("/") != -1){
            s1 = s.substring(0, s.indexOf("/"));
            s2 = s.substring(s.indexOf("/")+1);
            i1 = Integer.parseInt(s1) / Integer.parseInt(s2);
            s = i1 + "";

        }else if (s.indexOf("＋") != -1) {
            s1 = s.substring(0, s.indexOf("＋"));
            s2 = s.substring(s.indexOf("＋") + 1);
            i1 = Integer.parseInt(s1) + Integer.parseInt(s2);
            s = i1 + "";
        }else if(s.indexOf("-") != -1){
            s1 = s.substring(0, s.indexOf("-"));
            s2 = s.substring(s.indexOf("-")+1);
            i1 = Integer.parseInt(s1) - Integer.parseInt(s2);
            s = i1 + "";

        }

        return s;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
