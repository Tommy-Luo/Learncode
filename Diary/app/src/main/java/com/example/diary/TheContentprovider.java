package com.example.diary;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TheContentprovider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS = 1 ;
    private static final String AUTHORITY = "com.example.diarycontentprovider.provider" ;
    private static final int WORD = 2 ;
    private static final String _ID = "_id" ;

    private DBHelper helperDb ;
    static {
        matcher.addURI(AUTHORITY , "wordinformation" ,WORDS);
        matcher.addURI(AUTHORITY , "wordinformation/#" , WORD) ;
    }


    @Override
    public boolean onCreate() {

        helperDb = new DBHelper(getContext() , "mydb" , 1) ;
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = helperDb.getReadableDatabase() ;
        switch ( matcher.match(uri))
        {
            case WORDS :
                return db.query("wordinformation",null,null,null,null,null,null) ;
            case WORD :
                return db.query("wordinformation",null,"_id = ?" ,selectionArgs ,null , null , null) ;
            default:
                throw new IllegalArgumentException("Unrecognized Uri !");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = helperDb.getReadableDatabase() ;
        switch (matcher.match(uri)){
            case WORDS :
                long rowId = db.insert("wordinformation" ,null, values) ;
                if (rowId > 0)
                {
                    Uri wordUri = ContentUris.withAppendedId(uri , rowId) ;
                    //通知数据改变
                    getContext().getContentResolver().notifyChange(wordUri , null);
                    return wordUri ;
                }
                break ;
            default:
                throw new IllegalArgumentException("Unrecognized Uri !");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = helperDb.getReadableDatabase() ;
        //记录所删除的记录数目
        int num = 0 ;
        switch (matcher.match(uri))
        {
            case WORDS :
                num = db.delete("wordinformation" , null , null) ;
                break ;
            case WORD :
                //解析处所要删除的记录Id
                long id = ContentUris.parseId(uri) ;
                String whereClause = _ID + "=" + id ;
                num = db.delete("wordinformation" , whereClause ,selectionArgs) ;
                break ;

            default:
                throw new IllegalArgumentException("Unrecognized Uri !");
        }
        //通知数据改变
        getContext().getContentResolver().notifyChange(uri , null);
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helperDb.getReadableDatabase() ;
        //记录需要修改的记录数目
        int num = 0 ;
        switch (matcher.match(uri))
        {
            case WORDS :
                //db.update()
                num = db.update("wordinformation" , values ,selection ,selectionArgs) ;
                break;
            default:
                throw new IllegalArgumentException("Unrecognized Uri !");
        }
        //通知数据改变
        getContext().getContentResolver().notifyChange(uri , null);
        return num;
    }
}