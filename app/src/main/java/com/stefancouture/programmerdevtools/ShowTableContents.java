package com.stefancouture.programmerdevtools;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ShowTableContents extends AppCompatActivity {

    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table_contents);

        String versionNumber;
        String [] values;

        values = getIntent().getStringArrayExtra("object");

        mydb = new DBHelper(this);

        ListItems [] items = mydb.getAllSortedBy(values[0], values[1]);

        Log.d("Chosen value:", values[2]);
        for(int i = 0; i < items.length; i++) {
            Log.d("HEHEHEHEHEHHEHEHE" + i, items[i].getStudId() + " " + items[i].getFirstName() + " " +
                    items[i].getLastName() + " " + items[i].getGpa() + " ");
        }
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
