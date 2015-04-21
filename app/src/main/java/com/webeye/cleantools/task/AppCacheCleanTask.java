package com.webeye.cleantools.task;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AndroidRuntimeException;
import android.util.Log;

import com.webeye.cleantools.AppCleanCallback;
import com.webeye.cleantools.dao.Cache;
import com.webeye.cleantools.dao.CacheDAO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yanni on 15/4/15.
 */
public class AppCacheCleanTask extends AsyncTask<Integer, String, Integer> {

    public static final int APP_CACHE_SCAN = 0;
    public static final int APP_CACHE_CLEAN = 1;

    private static final String TAG = AppCacheCleanTask.class.getSimpleName();
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    private Context mContext;
    private CacheDAO mCacheDAO;
    private AppCleanCallback mCallback;

    private List<Cache> mAppCaches = new ArrayList<Cache>();

    private HashMap<String, Long> mCounter = new HashMap<String, Long>();

    public AppCacheCleanTask(Context context, AppCleanCallback callback) {
        super();
        mContext = context;
        mCallback = callback;
        mCacheDAO = new CacheDAO(mContext);
    }

    @Override
    protected Integer doInBackground(Integer... params) {

        if (params == null) {
            throw new AndroidRuntimeException("params must not be null");
        }

        if (params[0] == APP_CACHE_SCAN) {
            List<PackageInfo> installedApp = mContext.getPackageManager().getInstalledPackages(0);
            List<String> pkgNames = new ArrayList<String>();
            for (PackageInfo packageInfo : installedApp) {
                pkgNames.add(packageInfo.packageName);
            }
            mAppCaches.addAll(mCacheDAO.queryCacheByPkgName(
                    pkgNames.toArray(new String[installedApp.size()])));
            /*for (Cache cache : mAppCaches) {
                cache.dumpInfo();
            }*/

            scanCache();
        } else if (params[0] == APP_CACHE_CLEAN) {
            for (Cache cache : mAppCaches) {
                cleanCache(cache);
            }
        } else {
            throw new AndroidRuntimeException("Unknown task step, please check the definition");
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == APP_CACHE_SCAN) {
            mCallback.onScanResult(mCounter);
        } else if (result == APP_CACHE_CLEAN) {
            mCallback.onCleanResult();
        } else {
            throw new AndroidRuntimeException("Unknown task step, please check the definition");
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values != null) {

        }
    }

    public String getRegular(String subDir) {
        String regular = subDir.substring(1);
        regular = regular.substring(0, regular.indexOf("/"));
        return regular;
    }

    public void scanCache() {
        for (Cache cache : mAppCaches) {
            String path = SDCARD_PATH + cache.getDir();
            File rootDir = new File(path);
            if (rootDir.exists() && rootDir.isDirectory()) {
                String[] subDirs = rootDir.list();
                for (String dir : subDirs) {

                    if (cache.isRegular()) {
                        String regular = getRegular(cache.getSubDir());
                        Pattern pattern = Pattern.compile(regular);
                        Matcher matcher = pattern.matcher(dir);
                        if (matcher.find()) {
                            cache.setSubDir(cache.getSubDir().replace(regular, dir));
                            break;
                        }
                    }
                }
                cacheSizeCounter(cache);
            }
        }
    }

    public void cleanCache(Cache cache) {
        String path = SDCARD_PATH + cache.getDir() + cache.getSubDir();
        // 计算目标目录大小
        File cleanDirectory = new File(path);
        // 按策略删除数据
        if (cache.isRemoveDir()) {
            //cleanDirectory.delete();
        } else {
            File[] files = cleanDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    //file.delete();
                }
            }
        }
    }

    public void cacheSizeCounter(Cache cache) {
        String path = SDCARD_PATH + cache.getDir() + cache.getSubDir();
        // 计算目标目录大小
        File cleanDirectory = new File(path);
        long total = 0;
        long current = 0;
        if (mCounter.containsKey(cache.getPackageName())) {
            total = mCounter.get(cache.getPackageName());
        }
        try {
            current = getFileSize(cleanDirectory);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        total += current;
        Log.d(TAG, "cacheSizeCounter: path=" + path + ", current=" +
                current + ", total=" + total);
        mCounter.put(cache.getPackageName(), total);
    }

    private long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }
}
