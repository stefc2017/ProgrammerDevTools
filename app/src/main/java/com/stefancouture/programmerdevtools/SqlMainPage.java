package com.stefancouture.programmerdevtools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class SqlMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String buildNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_homepage);

        //get versionNumber
        VersionNumber versionNumber = new VersionNumber();
        buildNumber = versionNumber.getVersionNumber();

        //display on sqlconverter page
        TextView txtView = (TextView) findViewById(R.id.sqlconverterText);
        txtView.setText(txtView.getText() + " " + buildNumber);
    }

    public String getPackageVersionNum() {
        String version = null;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public void createTable(View view){
        Intent intent = new Intent(this, CreateTable.class);
        startActivity(intent);
    }
}
