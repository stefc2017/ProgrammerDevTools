package com.stefancouture.programmerdevtools;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowTableContents extends AppCompatActivity {

    private DBHelper mydb;
    private TableInformation tableInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_table_contents);
        setTitle("Table: Students"); //will use in the future when >1 table //sets the action bar title

        String versionNumber;
        String column;
        String order;
        int rows;


        mydb = new DBHelper(this);
        tableInfo = new TableInformation();

        column = tableInfo.getcolumnSortBy();
        order = tableInfo.getOrder();
        rows = tableInfo.getDisplayNumber();

        ListItems [] items = mydb.getAllSortedBy(column, order);

        tableInfo.setLengthOfData(items.length); //set the length of how many entries in table

        TableLayout layout = (TableLayout) findViewById(R.id.table_view);
        layout.setStretchAllColumns(true);

        int textSize = 20; //size for the text

        //header
        TableRow tr = new TableRow(this); //make a row

        TextView c1 = new TextView(this); //column 1
        c1.setText(R.string.StudNum);
        c1.setBackgroundResource(R.color.black);
        c1.setTextColor(Color.WHITE);
        c1.setTextSize(textSize);

        TextView c2 = new TextView(this); //column 2
        c2.setText(R.string.Fname);
        c2.setBackgroundResource(R.color.black);
        c2.setTextColor(Color.WHITE);
        c2.setTextSize(textSize);

        TextView c3 = new TextView(this); //column 3
        c3.setText(R.string.Lname);
        c3.setBackgroundResource(R.color.black);
        c3.setTextColor(Color.WHITE);
        c3.setTextSize(textSize);

        TextView c4 = new TextView(this); //column 4
        c4.setText(R.string.gpa);
        c4.setBackgroundResource(R.color.black);
        c4.setTextColor(Color.WHITE);
        c4.setTextSize(textSize);

        tr.addView(c1);
        tr.addView(c2);
        tr.addView(c3);
        tr.addView(c4);

        layout.addView(tr);

        int counter = 0; //to determine how many rows we have created
        int i = tableInfo.getStart();
        int numOfElements = items.length;
        int length = rows;

        while( i < numOfElements && counter < length){ //TODO change length
            tr = new TableRow(this); //make a row

            c1 = new TextView(this); //column 1
            c2 = new TextView(this); //column 2
            c3 = new TextView(this); //column 3
            c4 = new TextView(this); //column 4

            if(counter % 2 == 0){
                c1.setText(items[i].getStudId());
                c1.setBackgroundResource(R.color.beige);
                c1.setTextSize(textSize);

                c2.setText(items[i].getFirstName());
                c2.setBackgroundResource(R.color.beige);
                c2.setTextSize(textSize);

                c3.setText(items[i].getLastName());
                c3.setBackgroundResource(R.color.beige);
                c3.setTextSize(textSize);

                c4.setText(items[i].getGpa());
                c4.setBackgroundResource(R.color.beige);
                c4.setTextSize(textSize);
            }
            else {
                c1.setText(items[i].getStudId());
                c1.setBackgroundResource(R.color.lightgray);
                c1.setTextSize(textSize);

                c2.setText(items[i].getFirstName());
                c2.setBackgroundResource(R.color.lightgray);
                c2.setTextSize(textSize);

                c3.setText(items[i].getLastName());
                c3.setBackgroundResource(R.color.lightgray);
                c3.setTextSize(textSize);

                c4.setText(items[i].getGpa());
                c4.setBackgroundResource(R.color.lightgray);
                c4.setTextSize(textSize);
            }
            //add columns to row
            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tr.addView(c4);

            //add row to table
            layout.addView(tr);


            i++;
            counter++;
        }

        tableInfo.setIndexOfLastData(i);
        enableDisableButtons(tableInfo.getStart(), tableInfo.getIndexOfLastData(), items.length); //enables/disables buttons if needed

        //get versionNumber
        versionNumber = getPackageVersionNum();

        //display on ShowTableContents page
        TextView textView = (TextView) findViewById(R.id.table_contents_version);
        textView.setText(textView.getText() + " " + versionNumber);
    }

    /*
    start = is first position of next data to display
    length = is total number of data in table
    */
    public void enableDisableButtons(int start, int indexLastData,int length){
        //buttons
        Button left = (Button) findViewById(R.id.button_left_tableContents);
        Button right = (Button) findViewById(R.id.button_right_tableContents);

        if(start == 0){
            left.setEnabled(false);
        }
        else if(indexLastData >= length){
            right.setEnabled(false);
        }
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

    public void next(View view){
        Intent intent = new Intent(ShowTableContents.this, ShowTableContents.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        int rows = tableInfo.getDisplayNumber(); //get the number of rows user wants to see
        tableInfo.incrementStart(rows); //increment to position of first item on next page

        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

    public void previous(View view){
        Intent intent = new Intent(ShowTableContents.this, ShowTableContents.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        int rows = tableInfo.getDisplayNumber(); //get the number of rows user wants to see
        tableInfo.decrementStart(rows); //decrement to position of first item on previous page

        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
