package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTable extends AppCompatActivity {

    private TableInformation tableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String versionNumber;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_table_page);

//        mydb = new DBHelper(this);

        //get versionNumber
        versionNumber = getPackageVersionNum();

//      //display on view page
        TextView textView = (TextView) findViewById(R.id.viewTable_version);
        textView.setText(textView.getText() + " " + versionNumber);

        //dropdown for number of rows to display
        Spinner spinner_rows = (Spinner) findViewById(R.id.rowsToDisplay_selector);
        ArrayAdapter<CharSequence> adapter_rows = ArrayAdapter.createFromResource(this, R.array.numOfRows,
                android.R.layout.simple_spinner_item);
        spinner_rows.setAdapter(adapter_rows);

        //dropdown for sort by columns
        Spinner spinner_cols = (Spinner) findViewById(R.id.sortByColumns_selector);
        ArrayAdapter<CharSequence> adapter_cols = ArrayAdapter.createFromResource(this, R.array.students_columns,
                android.R.layout.simple_spinner_item);
        spinner_cols.setAdapter(adapter_cols);

        //dropdown for sort by asc/desc order
        Spinner spinner_order = (Spinner) findViewById(R.id.ascDesc_selector);
        ArrayAdapter<CharSequence> adapter_order = ArrayAdapter.createFromResource(this, R.array.ascDesc,
                android.R.layout.simple_spinner_item);
        spinner_order.setAdapter(adapter_order);


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

    public void showTableContents(View view){
        final String [] values = new String[3];
        tableInfo = new TableInformation();

        //get selected column to sort by
        values[0] = getSelectedColumn();

        //get selected order (asc/desc) to sort by
        values[1] = getSelectedOrder();

        //get selected number of rows to display
        values[2] = getSelectedRowCount();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm
                Intent intent = new Intent(ViewTable.this, ShowTableContents.class);

                tableInfo.setStart(0);
                tableInfo.setcolumnSortBy(values[0]);
                tableInfo.setOrder(values[1]);
                tableInfo.setDisplayNumber(Integer.parseInt(values[2]));
                startActivity(intent);
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_confirm_viewTable)
                .setTitle(R.string.confirm_viewTable)
                .setIcon(R.drawable.help);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String getSelectedColumn(){
        String result = " ";

        //get selected column to sort by
        Spinner spinner_cols = (Spinner) findViewById(R.id.sortByColumns_selector);
        result = spinner_cols.getSelectedItem().toString();

        return result;
    }

    public String getSelectedOrder(){
        String result = " ";

        //get selected order (asc/desc) to sort by
        Spinner spinner_order = (Spinner) findViewById(R.id.ascDesc_selector);
        result = spinner_order.getSelectedItem().toString();

        return result;
    }

    public String getSelectedRowCount(){
        String result = " ";

        //get selected number of rows to display
        Spinner spinner_rows = (Spinner) findViewById(R.id.rowsToDisplay_selector);
        result = spinner_rows.getSelectedItem().toString();

        return result;
    }

    public void cancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Add the buttons
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked confirm
                Intent intent = new Intent(ViewTable.this, SqlMainPage.class);
                startActivity(intent);
            }//end onClick
        });//end confirmButton

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                //User clicked cancel
            }//end onClick
        });//end confirmButton

        builder.setMessage(R.string.question_cancel_viewTable)
                .setTitle(R.string.cancel_viewTable)
                .setIcon(R.drawable.alert);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
