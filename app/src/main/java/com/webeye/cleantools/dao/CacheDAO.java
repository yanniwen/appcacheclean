package com.webeye.cleantools.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanni on 15/4/16.
 */
public class CacheDAO {

    private Context mContext;
    private SQLiteDatabase mDatabase = null;

    public CacheDAO(Context context) {
        mContext = context;
        mDatabase = DBHelper.getInstance(mContext).getWritableDatabase();
        generateMockData();
    }

    public List<Cache> queryCacheByPkgName(String... pkgName) {
        if (pkgName == null) {
            throw new IllegalArgumentException("package name should not be null");
        }
        List<Cache> cacheList = new ArrayList<Cache>();

        String sql = "SELECT * FROM " + SQL.TABLE_APP_CACHE + " WHERE " + SQL.CACHE_PACKAGE_NAME + "=?";
        if (pkgName.length > 1) {
            for (int i = 0; i < pkgName.length - 1; i++) {
                sql += " or " + SQL.CACHE_PACKAGE_NAME + "=?";
            }
        }
        Cursor cursor = mDatabase.rawQuery(sql, pkgName);

        while (cursor.moveToNext()) {
            Cache cache = new Cache();
            cache.setPackageName(cursor.getString(2));
            cache.setDir(cursor.getString(3));
            cache.setSubDir(cursor.getString(4));
            if (cursor.getInt(5) == 1) {
                cache.flagRemoveDir();
            }
            if (cursor.getInt(6) == 0) {
                cache.flagNoRegular();
            }
            cacheList.add(cache);
        }
        cursor.close();
        return cacheList;
    }

    public long insert(Cache cache) {
        String sql = "INSERT INTO APP_CACHE";
        ContentValues cv = new ContentValues();
        cv.put(SQL.CACHE_ITEM_NAME, cache.getItemName());
        cv.put(SQL.CACHE_PACKAGE_NAME, cache.getPackageName());
        cv.put(SQL.CACHE_DIR, cache.getDir());
        cv.put(SQL.CACHE_SUB_DIR, cache.getSubDir());
        cv.put(SQL.CACHE_REMOVE_DIR, cache.isRemoveDir());
        cv.put(SQL.CACHE_REGULAR, cache.isRegular());

        long rowId = mDatabase.insert(SQL.TABLE_APP_CACHE, null, cv);
        return rowId;
    }

    private void generateMockData() {
        String sql = "SELECT COUNT(1) FROM " + SQL.TABLE_APP_CACHE;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        if (count == 0) {
            // 微信数据
            Cache cache = new Cache();
            cache.setItemName("MicroMsg_UserAvatar");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/[0-9a-zA-Z]{32}/avatar");
            cache.flagRemoveDir();
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_Brandicon");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/[0-9a-zA-Z]{32}/brandicon");
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_SNS");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/[0-9a-zA-Z]{32}/sns");
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_Voice");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/[0-9a-zA-Z]{32}/voice2");
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_ImageCache");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/[0-9a-zA-Z]{32}/image");
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_DiskCache");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/diskcache");
            cache.flagNoRegular();
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_Crash");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/crash");
            cache.flagNoRegular();
            insert(cache);

            cache = new Cache();
            cache.setItemName("MicroMsg_XLog");
            cache.setPackageName("com.tencent.mm");
            cache.setDir("/tencent/MicroMsg");
            cache.setSubDir("/xlog");
            cache.flagNoRegular();
            insert(cache);
        }
    }
}