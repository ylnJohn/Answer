package com.yln.question;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by linnan.yao on 2017/10/17.
 */

public class AboutActivity extends BaseActivity{

    private TextView mVersionTV,mContactTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mVersionTV= (TextView) findViewById(R.id.version_text);
        mContactTV= (TextView) findViewById(R.id.contact_text);
        String versionName="";
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;
//        int versioncode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String str=String.format(getResources().getString(R.string.version_text),versionName);
        mVersionTV.setText(str);
        mContactTV.setText(SystemConfig.CONTACT);
        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
