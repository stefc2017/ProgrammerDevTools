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
