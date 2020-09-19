package com.example.a0919android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonlogin;
    LinearLayout login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonlogin=(Button)findViewById(R.id.buttonlogin);
        buttonlogin.setOnClickListener(new myClick());
    }

    class myClick implements View.OnClickListener{
        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);


        public void onClick(View arg)
        {   if (arg == buttonlogin) {
            login = (LinearLayout) getLayoutInflater().inflate(R.layout.login, null);
            dialog.setTitle("Login in").setMessage("Please set your ID and Password").setView(login);
            dialog.setPositiveButton("Login", new loginClick());
            dialog.setNegativeButton("Quit", new exitClick());
            //dialog.setIcon(R.drawable.icon2);
            dialog.create();
            dialog.show();
        }

        }

        class loginClick implements DialogInterface.OnClickListener {
            EditText txt;

            @Override

            public void onClick(DialogInterface dialog, int which) {
                txt = (EditText) login.findViewById(R.id.ed2);
                if ((txt.getText().toString()).equals("admin")) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }

        class exitClick implements DialogInterface.OnClickListener {
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        }



    }
}