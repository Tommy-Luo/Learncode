package com.example.a1011android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=findViewById(R.id.btnStart1);
        Button button2=findViewById(R.id.btnStart2);


          editText=(EditText) findViewById(R.id.edt);

          textView1=(TextView)findViewById(R.id.txtContent1);
         final TextView textView2=(TextView)findViewById(R.id.txtContent2);




        final Handler handler1 = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int s = Integer.parseInt(editText.getText().toString());
                boolean pd = true;
                if(s >= 2){
                    for (int i=2; i<s; i++){
                        if(s % i == 0){
                            pd = false;
                            break;
                        }
                    }
                }
                if(pd)
                    textView1.setText("是素数");
                else
                    textView1.setText("不是素数");
            }
        };

        final Runnable Worker = new Runnable() {
            @Override
            public void run() {
                int s = Integer.parseInt(editText.getText().toString());
                Message msg = new Message();
                msg.arg1 = s;
                handler1.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread workThread = new Thread(null, Worker, "workThread");
                workThread.start();
            }
        });

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView2.setText(msg.arg1+"Got");
            }
        };

        final Runnable Worker1 = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<100) {
                    i++;
                    Message msg = new Message();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread workThread = new Thread(null, Worker1, "workThread1");
                workThread.start();
            }
        });
    }


    }
