package com.example.diarycontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ContentResolver resolver;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*matcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authorities = "com.example.words.provider";
        matcher.addURI(authorities,"words",0);
        matcher.addURI(authorities,"words/#",1);*/
        resolver = this.getContentResolver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button)findViewById(R.id.btn_add);
        Button btnDelete = (Button)findViewById(R.id.btn_delete);
        Button btnSearch = (Button)findViewById(R.id.btn_search);
        Button btnUpdate = (Button)findViewById(R.id.btn_update);

        uri = Uri.parse("content://com.example.diary/wordinformation");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse("content://com.example.words.provider/words");
                System.out.println(uri.toString());*/
                ContentValues values = new ContentValues();
                values.put("word", "turtle");
                System.out.println(uri);
                Uri newUri = resolver.insert(uri, values);
                if (newUri != null) {
                    Toast.makeText(MainActivity.this, "add successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                        String example = cursor.getString(cursor.getColumnIndex("example"));

                        Log.d("TAG", "onClick: word: " + word +  " meaning: " + meaning+  " example: " + example);
                    }
                    cursor.close();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("word", "turtle");
                values.put("meaning", "乌龟");
                values.put("example", "turtle is good");
                if (getContentResolver().update(uri, values, null, null) > 0) {
                    Toast.makeText(MainActivity.this, "update successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";//简单起见，这里指定ID，用户可在程序中设置id的实际值
                int result = resolver.delete(uri, null, null);
            }
        });

    }
}
