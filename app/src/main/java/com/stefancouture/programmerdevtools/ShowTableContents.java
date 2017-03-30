package com.stefancouture.programmerdevtools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ShowTableContents extends AppCompatActivity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table_contents);

        mydb = new DBHelper(this);

        Object [] items = mydb.getAllSortedBy("student_number", "ASC");

        Log.d("HEHEHEHEHEHHEHEHE", items[0] + " ");
        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on ShowTableContents page
        TextView textView = (TextView) findViewById(R.id.table_contents_version);
        textView.setText(textView.getText() + " " + versionNumber);

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
}
