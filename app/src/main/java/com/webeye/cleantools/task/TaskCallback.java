package com.webeye.cleantools.task;

import java.util.HashMap;

/**
 * Created by yanni on 15/4/21.
 */
public interface TaskCallback {

    public void onScanResult(HashMap<String, Long> mPackageSize);
    public void onCleanResult();
}
