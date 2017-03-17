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

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String buildNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get versionNumber
        VersionNumber versionNumber = new VersionNumber();
        buildNumber = versionNumber.getVersionNumber();
        //display on main page
        TextView mainTxtView = (TextView) findViewById(R.id.mainVersion);
        mainTxtView.setText(mainTxtView.getText() + " " + buildNumber);
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
