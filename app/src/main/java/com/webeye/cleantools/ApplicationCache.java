package com.webeye.cleantools;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.webeye.cleantools.task.CleanTask;
import com.webeye.cleantools.task.DefaultCacheCleanTask;
import com.webeye.cleantools.task.TaskCallback;

import java.text.DecimalFormat;
import java.util.HashMap;


public class ApplicationCache extends ActionBarActivity implements TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_cache);

        DefaultCacheCleanTask agent = new DefaultCacheCleanTask(this, this);
        agent.execute(CleanTask.TASK_SCAN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_application_cache, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScanResult(HashMap<String, Long> packageSize) {
        TextView report = (TextView) findViewById(R.id.txt_status);
        String total = "";
        for (String key : packageSize.keySet()) {
            total += key + ": " + formatFileSize(packageSize.get(key)) + "\n";
        }
        report.setText("result:\n " + total);
    }

    @Override
    public void onCleanResult() {

    }

    public String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
