package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);

        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on main page
        TextView mainTxtView = (TextView) findViewById(R.id.mainVersion);
        mainTxtView.setText(mainTxtView.getText() + " " + versionNumber);
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

    public void loadConverter(View view){
        Intent intent = new Intent(this, DisplayConverter.class);
        startActivity(intent);
    }

    public void loadSqlMainPage(View view){
        Intent intent = new Intent(this, SqlMainPage.class);
        startActivity(intent);
    }
}
