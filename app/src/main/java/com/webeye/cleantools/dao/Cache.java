package com.webeye.cleantools.dao;

import android.util.Log;

/**
 * Created by yanni on 15/4/16.
 */
public class Cache {

    private String mItemName = "";
    private String mPackageName = "";
    private String mDir = "";
    private String mSubDir = "";
    private boolean mShouldRemoveDir = false;
    private boolean mIsRegular = true;

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String name) {
        mItemName = name;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public String getDir() {
        return mDir;
    }

    public void setDir(String dir) {
        this.mDir = dir;
    }

    public String getSubDir() {
        return mSubDir;
    }

    public void setSubDir(String subDir) {
        this.mSubDir = subDir;
    }

    public void flagRemoveDir() {
        this.mShouldRemoveDir = true;
    }

    public boolean isRemoveDir() {
        return mShouldRemoveDir;
    }

    public boolean isRegular() {
        return mIsRegular;
    }

    public void flagNoRegular() {
        this.mIsRegular = false;
    }

    public void dumpInfo() {
        Log.d("Cache", "pkg=" + mPackageName + ", dir=" + mDir + ", subdir=" + mSubDir);
    }
}
