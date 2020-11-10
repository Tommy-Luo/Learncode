package com.example.management;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    StudentDBHelper mDbHelper;
    private List<Student> student_information_List=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建SQLiteOpenHelper对象，注意第一次运行时，此时数据库并没有被创建
        mDbHelper = new StudentDBHelper(this);
        init();



    }



    protected void onDestroy()                 //关闭数据库
    {
        super.onDestroy();
        mDbHelper.close();   }


    private void init()                       //初始构造words
    {
        student_information_List.clear();                       //清空list中所有元素
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.query("studnet_information", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            //int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String date=cursor.getString(cursor.getColumnIndex("date"));
            String temperature=cursor.getString(cursor.getColumnIndex("temperature"));
            String address=cursor.getString(cursor.getColumnIndex("address"));
            String others=cursor.getString(cursor.getColumnIndex("others"));
            Student temp=new Student(date,temperature,address,others);
            //temp.setId(id);
            student_information_List.add(temp);
        }
        StudentAdapter adapter=new StudentAdapter(MainActivity.this,R.layout.unit,student_information_List);
        final ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);




    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView textId=null;
        TextView textDate=null;
        TextView textTemperature=null;
        TextView textAddress=null;
        TextView textOthers=null;
        AdapterView.AdapterContextMenuInfo info=null;
        View itemView=null;
        switch (id) {
            case R.id.action_insert:

                InsertDialog();
                return true;
            case R.id.action_search:

                SearchDialog();
                return true;
            case R.id.action_delete:

                DeleteDialog();
                return true;
            case R.id.action_change:

                final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.change_information, null);
                new AlertDialog.Builder(this)
                        .setTitle("SEARCH")//标题
                        .setView(tableLayout)//设置视图
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String txtSearchDate = ((EditText) tableLayout.findViewById(R.id.key_search)).getText().toString();
                                EditDialog(txtSearchDate);
                            }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) { }
                }).create().show();
                return true;

            case R.id.action_return:
                setContentView(R.layout.activity_main);
                init();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }





    private void SearchDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.search_date, null);
        new AlertDialog.Builder(this)
                .setTitle("SEARCH")//标题
                .setView(tableLayout)//设置视图
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchDate = ((EditText) tableLayout.findViewById(R.id.key)).getText().toString();
                        Search(txtSearchDate);
                    }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) { }
        }).create().show();
    }
    public void Search(String key)
    {
        if(key.equals("")==true)
        {
            init();
        }
        else if(key.equals("")==false)
        {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String sql="select * from studnet_information where date like ? order by date desc";
            Cursor c=db.rawQuery(sql,new String[]{"%"+key+"%"});
            student_information_List.clear();
            while (c.moveToNext())
            {
                String date=c.getString(c.getColumnIndex("date"));
                String temperature=c.getString(c.getColumnIndex("temperature"));
                String address=c.getString(c.getColumnIndex("address"));
                String others=c.getString(c.getColumnIndex("others"));
                Student temp=new Student(date,temperature,address,others);
                student_information_List.add(temp);
            }
            StudentAdapter adapter=new StudentAdapter(MainActivity.this,R.layout.unit,student_information_List);
            ListView listView=(ListView)findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            listView.setOnCreateContextMenuListener(this);                //注册contextmenu
        }
    }
    private void EditDialog(final String strDate) //编辑操作
    {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.add_information, null);
        new AlertDialog.Builder(this)
                .setTitle("CHANGE")
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strNewDate = ((EditText) tableLayout.findViewById(R.id.key)).getText().toString();
                        String strNewTemperature = ((EditText) tableLayout.findViewById(R.id.txttemperature)).getText().toString();
                        String strNewAddress = ((EditText) tableLayout.findViewById(R.id.txtaddress)).getText().toString();
                        String strNewOthers = ((EditText) tableLayout.findViewById(R.id.txtothers)).getText().toString();
                        Edit(strNewDate,strNewTemperature,strNewAddress,strNewOthers,strDate);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {}

        }).create().show();
    }

    public void Edit(String strDate, String strTemperature, String strAddress, String strothers,String theDate)         //数据库修改操作
    {
        String sql="update studnet_information set date=?,temperature=?,address=?,others=? where date=?";//数据库修改语句
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(sql,new String[]{strDate,strTemperature,strAddress,strothers,theDate});
        init();
    }

    private void DeleteDialog()  //删除操作
    {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.delete_date, null);
        new AlertDialog.Builder(this)
                .setTitle("DELETE")//标题
                .setView(tableLayout)//设置视图
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchDate = ((EditText) tableLayout.findViewById(R.id.key_delete)).getText().toString();
                        Delete(txtSearchDate);
                    }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) { }
        }).create().show();


    }
    public void Delete(String date)                  //数据库删除操作
    {
        String sql="delete from studnet_information where date='"+date+"'";//数据库删除语句
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.execSQL(sql);
        init();
    }
    private void InsertDialog()  //增加操作
    {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.add_information, null);
        new AlertDialog.Builder(this)
                .setTitle("INSERT")             //标题
                .setView(tableLayout)           //设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strDate=((EditText)tableLayout.findViewById(R.id.key)).getText().toString();
                        String strTemperature=((EditText)tableLayout.findViewById(R.id.txttemperature)).getText().toString();
                        String strAddress=((EditText)tableLayout.findViewById(R.id.txtaddress)).getText().toString();
                        String strothers=((EditText)tableLayout.findViewById(R.id.txtothers)).getText().toString();
                        Insert(strDate, strTemperature, strAddress,strothers);
                    }})
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .create()
                .show();
    }

    public void Insert(String strDate, String strTemperature, String strAddress, String strothers)    //数据库添加函数
    {
        String sql="insert into  studnet_information(date,temperature,address,others) values(?,?,?,?)";
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.execSQL(sql,new String[]{strDate,strTemperature,strAddress,strothers});
        student_information_List.add(new Student(strDate,strTemperature,strAddress,strothers));
    }











}