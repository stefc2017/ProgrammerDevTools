package com.stefancouture.programmerdevtools;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class SqlMainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_homepage);

        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on sqlconverter page
        TextView txtView = (TextView) findViewById(R.id.sqlconverterText);
        txtView.setText(txtView.getText() + " " + versionNumber);
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

    public void addData(View view){
        Intent intent = new Intent(this, AddData.class);
        startActivity(intent);
    }

    public void viewTable(View view){
        Intent intent = new Intent(this, ViewTable.class);
        startActivity(intent);
    }

    public void insertInformation(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked the button
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.insert_data_body)
                .setTitle(R.string.insert_data_header)
                .setIcon(R.drawable.info);

        AlertDialog dialog = builder.create();
        dialog.show();
    }//end insertInformation

    public void viewDataInformation(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked the button
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.view_data_body)
                .setTitle(R.string.view_data_header)
                .setIcon(R.drawable.info);

        AlertDialog dialog = builder.create();
        dialog.show();
    }//end viewDataInformation
}
