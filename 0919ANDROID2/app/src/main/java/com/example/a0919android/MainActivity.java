package com.example.a0919android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a0919android.model.student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonfirst = findViewById(R.id.buttonStartAnotherActivity);
        buttonfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name", "Tommy Luo");
                intent.putExtra("age", "20");
                student student = new student();
                student.setName("Arthur Wang");
                student.setAge(20);
                intent.putExtra("student", student);

                startActivityForResult(intent, 0);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            String str = data.getStringExtra("Result");
            if (resultCode == 0) {
                String getData = data.getStringExtra("Result");
                Toast.makeText(MainActivity.this,getData,Toast.LENGTH_LONG).show();
            }
        }

    }
}