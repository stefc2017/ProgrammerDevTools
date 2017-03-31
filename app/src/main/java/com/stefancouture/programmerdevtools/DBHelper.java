package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase.db";
    public static int versionNumber = 2;
    public static final String STUDENTS_TABLE_NAME = "Students";
    public static final String STUDENTS_COLUMN_STUDNUM = "student_number";
    public static final String STUDENTS_COLUMN_FIRSTNAME = "first_name";
    public static final String STUDENTS_COLUMN_LASTNAME = "last_name";
    public static final String STUDENTS_COLUMN_GPA = "gpa";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, versionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Students " +
                        "(student_number integer primary key, first_name text, last_name text," +
                        " gpa double)"
        );
    }//end onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("DROP TABLE IF EXISTS Students");
        onCreate(db);
    }

    public int getCount(){
        int count;

        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + STUDENTS_TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public ListItems [] getAllSortedBy(String column, String order){
        ListItems [] items;
        String colName;
        String orderBy;
        int numberOfRows;

        colName = getSQLColumnName(column);
        orderBy = getSQLOrderName(order);

        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + STUDENTS_TABLE_NAME + " " +
                        "ORDER BY " + colName + " " + orderBy;

        Cursor cursor = db.rawQuery(query, null);

        numberOfRows = cursor.getCount();

        items = new ListItems[numberOfRows];
        items = initialize(items, numberOfRows);

        cursor.moveToFirst();

        for(int i = 0; i < numberOfRows; i++){
            items[i].setStudId(cursor.getString(cursor.getColumnIndex("student_number")));
            items[i].setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
            items[i].setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
            items[i].setGpa(cursor.getString(cursor.getColumnIndex("gpa")));

            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    public String getSQLColumnName(String column){
        String result = " ";

        if(column.equals("Student #"))
            result = "student_number";
        else if(column.equals("First name"))
            result = "first_name";
        else if(column.equals("Last name"))
            result = "last_name";
        else if(column.equals("Gpa"))
            result = "gpa";

        return result;
    }

    public String getSQLOrderName(String order){
        String result = " ";

        if(order.equals("Ascending"))
            result = "";
        else if(order.equals("Descending"))
            result = "DESC";

        return result;
    }

    public ListItems [] initialize(ListItems [] list, int count){
        ListItems [] listToReturn = list;

        for(int i = 0; i < count; i++){
            listToReturn[i] = new ListItems();
        }

        return listToReturn;
    }

    public boolean insert(String query){
        boolean isValid = true;
        SQLiteDatabase db = this.getWritableDatabase();

        String insertQuery = query;

        try{
        db.execSQL(insertQuery);
        }catch (Exception e){
            isValid = false;
        }

        return isValid;
    }
}
