package com.stefancouture.programmerdevtools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

public class VersionNumber extends AppCompatActivity{
    private String versionNumber;

    public VersionNumber(){
        versionNumber = getPackageVersionNum();
    }

    private String getPackageVersionNum() {
        String version = null;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public String getVersionNumber(){
        return versionNumber;
    }
}
