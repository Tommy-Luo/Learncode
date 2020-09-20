package com.example.a0919android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a0919android.model.student;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        int age=intent.getIntExtra("age",20);
        student student=(student)intent.getSerializableExtra("student");
        TextView textView=findViewById(R.id.textviewReceivedData);
        textView.setText("Name:"+name+"Age:"+age+"｜"+"Student:"+student.getName()+"/"+student.getAge());

        Button buttonsecond=findViewById(R.id.buttonReturn);
        buttonsecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String name=intent.getStringExtra("name");
                Integer age=intent.getIntExtra("age",20);
                intent.putExtra("Result","Name:"+name+"Age:"+age);
                setResult(0,intent);
                finish();
            }
        });
    }
}