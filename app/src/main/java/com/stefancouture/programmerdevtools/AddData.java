package com.stefancouture.programmerdevtools;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddData extends AppCompatActivity {

    private DBHelper mydb ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);

        mydb = new DBHelper(this);
Log.d("asasaasasasa " + mydb.getCount() + " ", " yay");
        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on main page
        TextView mainTxtView = (TextView) findViewById(R.id.insert_data_version);
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

    public void save(View view){
        EditText editText= (EditText) findViewById(R.id.sqlUserStatement);
        final String query = editText.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm

                mydb.insert(query);
                //add to database here

                Intent intent = new Intent(AddData.this, SqlMainPage.class);
                startActivity(intent);
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_insert_data)
                .setTitle(R.string.insert_data)
                .setIcon(R.drawable.question);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm
                Intent intent = new Intent(AddData.this, SqlMainPage.class);
                startActivity(intent);
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_cancel_insert)
                .setTitle(R.string.cancel_insert)
                .setIcon(R.drawable.alert);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
