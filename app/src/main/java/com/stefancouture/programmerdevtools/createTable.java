package com.stefancouture.programmerdevtools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CreateTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String buildNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_table);

        //get versionNumber
        VersionNumber versionNumber = new VersionNumber();
        buildNumber = versionNumber.getVersionNumber();

        //display on main page
        TextView mainTxtView = (TextView) findViewById(R.id.create_table_version);
        mainTxtView.setText(mainTxtView.getText() + " " + buildNumber);
    }
}
