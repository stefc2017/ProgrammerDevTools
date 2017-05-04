package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchData extends AppCompatActivity {

    private DBHelper mydb;
    private TableInformation tableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_data);

        mydb = new DBHelper(this);
        tableInfo = new TableInformation();

        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on main page
        TextView mainTxtView = (TextView) findViewById(R.id.search_data_version);
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

    public void search(View view){
        EditText editText= (EditText) findViewById(R.id.sqlUserStatement_searchData);
        final String query = editText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm
                ListItems [] items;

                //add to database here
                items = mydb.search(query);

                Intent intent = new Intent(SearchData.this, DisplayData.class);
                tableInfo.setListItems(items);
                startActivity(intent);

                showNotification(items[0].getIsValid());
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_search_data)
                .setTitle(R.string.search_data)
                .setIcon(R.drawable.help);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void showNotification(boolean isValid){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        String text = "";

        if(isValid)
            text = "Successfully searched in database";
        else
            text = "Unsuccessfully searched in database";

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void cancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm
                Intent intent = new Intent(SearchData.this, SqlMainPage.class);
                startActivity(intent);
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_cancel_search)
                .setTitle(R.string.cancel_search)
                .setIcon(R.drawable.alert);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
