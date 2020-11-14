package com.example.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity  {
    private static String DB_NAME = "mydb";
    private EditText et_word;
    private EditText et_meaning;
    private EditText et_example;
    private ArrayList<Map<String, Object>> data;
    ArrayList wordname = new ArrayList();
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private SimpleAdapter listAdapter;
    private View view;
    private ListView listview,listview2;
    private Button selBtn, addBtn, updBtn, delBtn,content;
    private Map<String, Object> item;
    private ContentValues selCV;
    private String selword,string;

    private ContentResolver resolver ;
    private static final String WORD = "word" ;
    private static final String MEANING = "meaning" ;
    private static final String EXAMPLE = "example" ;
    private static final String AUTHORITY = "com.example.diarycontentprovider.provider" ;
    private static final Uri ALL_URI = Uri.parse("content://" + AUTHORITY + "/wordinformation") ;
    private static final Uri SINGLE_URI = Uri.parse("content://" + AUTHORITY + "/wordinformation") ;
    private static final String _ID = "_id" ;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_word = (EditText) findViewById(R.id.words_name);
        et_meaning = (EditText) findViewById(R.id.words_meaning);
        et_example = (EditText) findViewById(R.id.words_example);
        listview = (ListView) findViewById(R.id.list_view);
        listview2=(ListView)findViewById(R.id.list_view2);
        addBtn = (Button) findViewById(R.id.submit);
        delBtn=(Button)findViewById(R.id.delete);
        updBtn=(Button)findViewById(R.id.update);
        selBtn=(Button)findViewById(R.id.search);
        content=(Button)findViewById(R.id.CONTENT);

        addBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dbAdd();
                dbFindAll();
            }
        });

        delBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                dbDel();
                dbFindAll();

            }
        });
        updBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dbUpdate();
                dbFindAll();
            }
        });

        selBtn.setOnClickListener(new Button.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                String word = et_word.getText().toString();
                ArrayList<Map<String, String>> list = find(dbHelper.getReadableDatabase(), word);
                if( list != null && list.size() != 0){
                    wordname.clear();
                    for ( int i=0; i<list.size(); i++ ){
                        wordname.add(list.get(i).getOrDefault("word","未查到"));
                    }
                    searchshow();
                }else{
                    Toast.makeText(MainActivity.this, "未找到该单词", Toast.LENGTH_SHORT).show();
                }
            }
        });

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(MainActivity.this,view);
                getMenuInflater().inflate(R.menu.option,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.INSERT_item:

                                ContentValues values = new ContentValues() ;
                                values.put(WORD , "turtle");
                                values.put(MEANING , "乌龟" );
                                values.put(EXAMPLE , "turle is good" );

                                resolver.insert(ALL_URI , values ) ;
                                initthecontent();
                                return true;
                            case R.id.DELETE_item:

                                resolver.delete(ALL_URI , null , null) ;
                                initthecontent();
                                return true;
                            case R.id.UPDATE_item:

                                ContentValues values_new = new ContentValues() ;
                                values_new.put(WORD , "big turtle");
                                values_new.put(MEANING , "大乌龟！" );
                                values_new.put(EXAMPLE , "big turtle is good！" );

                                resolver.update(ALL_URI , values_new , new String("word = ?") , new String[]{"turtle"} ) ;
                                initthecontent();

                                return true;


                            default:
                                return false;

                        }
                    }
                });

                popupMenu.show();


            }
        });

        dbHelper = new DBHelper(this, DB_NAME, null, 1);
        db = dbHelper.getWritableDatabase();// 打开数据库
        data = new ArrayList<Map<String, Object>>();
        dbFindAll();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // TODO Auto-generated method stub
                Map<String, Object> listItem = (Map<String, Object>) listview.getItemAtPosition(position);
                et_word.setText((String) listItem.get("word"));
                et_meaning.setText((String) listItem.get("meaning"));
                et_example.setText((String) listItem.get("example"));
                selword=et_word.getText().toString().trim();
                Log.i("mydbDemo", "word=" + et_word);

            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                string = (String) parent.getItemAtPosition(position);
               theword();
              }
        });

        resolver = getContentResolver() ;
        initthecontent() ;
    }

    private void initthecontent() {
        Cursor cursor = resolver.query(ALL_URI , null , null ,null ,null) ;
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this , R.layout.dict_adapt_listview,
                cursor , new String[]{WORD , MEANING,EXAMPLE} ,new int[] {R.id.WordTetView , R.id.MeaningTetView,R.id.ExampleTetView}) ;
        listview.setAdapter(adapter);
        System.out.println("initthecontent") ;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void theword() {


            ArrayList<Map<String, String>> dancilist = find(dbHelper.getReadableDatabase(), string);
            et_word.setText(dancilist.get(0).getOrDefault("word","test"));
            et_meaning.setText(dancilist.get(0).getOrDefault("meaning","test"));
            et_example.setText(dancilist.get(0).getOrDefault("example","test"));

    }

    public void searchshow(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,wordname);
        ListView lv = findViewById(R.id.list_view2);
        lv.setAdapter(adapter);
    }

    private void showList() {
        // TODO Auto-generated method stub
        listAdapter = new SimpleAdapter(this, data,
                R.layout.word, new String[]{"word", "meaning", "example"},
                new int[]{R.id.txt_words_name, R.id.txt_words_meaning, R.id.txt_words_example});
        listview.setAdapter(listAdapter);
    }



    //插入数据
    protected void dbAdd() {
        // TODO Auto-generated method stub
        ContentValues values = new ContentValues();
        values.put("word", et_word.getText().toString().trim());
        values.put("meaning", et_meaning.getText().toString().trim());
        values.put("example", et_example.getText().toString().trim());
        long rowid = db.insert(dbHelper.TB_NAME, null, values);
        if (rowid == -1)
            Log.i("myDbDemo", "数据插入失败！");
        else
            Log.i("myDbDemo", "数据插入成功!" + rowid);
    }
    private ArrayList<Map<String, String>> find(SQLiteDatabase sqLiteDatabase, String word){
//        Cursor cursor = sqLiteDatabase.query("tb_danci", null,
//                "danci=?", new String[]{danci}, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from wordinformation where word like ?", new String[]{"%"+word+"%"});
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()){
            Map<String, String> map = new HashMap<String, String>();
            map.put("word",cursor.getString(1));
            map.put("meaning",cursor.getString(2));
            map.put("example",cursor.getString(3));
            resultList.add(map);
        }
        return resultList;
    }
    //查询数据
    protected void dbFindAll() {
        // TODO Auto-generated method stub
        data.clear();
        cursor = db.rawQuery("select * from wordinformation", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String word = cursor.getString(1);
            String meaning = cursor.getString(2);
            String example = cursor.getString(3);
            item = new HashMap<String, Object>();
            item.put("word", word);
            item.put("meaning", meaning);
            item.put("example", example);
            data.add(item);
            cursor.moveToNext();

        }
        showList();
    }

    //数据删除
    protected void dbDel() {
        // TODO Auto-generated method stub
        db.execSQL("DELETE FROM wordinformation WHERE word = '"+selword+"' ;");
    }

    //更新列表中的数据
    protected void dbUpdate() {
        ContentValues values = new ContentValues();
        values.put("meaning", et_meaning.getText().toString().trim());
        values.put("example", et_example.getText().toString().trim());
        String[]args={selword};
        int i = db.update(dbHelper.TB_NAME, values, "word=?", args);
        if (i > 0)
            Log.i("myDbDemo", "数据更新成功！");
        else
            Log.i("myDbDemo", "数据未更新");
}




}