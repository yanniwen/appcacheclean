package com.webeye.cleantools.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yanni on 15/4/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cleaner.db";
    private static final int FIRST_DATABASE_VERSION = 1000;
    private static final int DATABASE_VERSION = 1000;

    private static DBHelper instance = null;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("wenyan", "DBHelper oncreate");
        db.execSQL(SQL.CREATE_TABLE_APPCACHE);

        // 若不是第一个版本安装，直接执行数据库升级
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 使用for实现跨版本升级数据库
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {

                default:
                    break;
            }
        }
    }
}