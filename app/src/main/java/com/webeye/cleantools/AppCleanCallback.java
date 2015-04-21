package com.webeye.cleantools;

import java.util.HashMap;

/**
 * Created by yanni on 15/4/21.
 */
public interface AppCleanCallback {

    public void onScanResult(HashMap<String, Long> mPackageSize);
    public void onCleanResult();
}
