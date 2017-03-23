package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myDatabase.db";
    public static final String STUDENTS_TABLE_NAME = "Students";
    public static final String STUDENTS_COLUMN_STUDNUM = "student_number";
    public static final String STUDENTS_COLUMN_FIRSTNAME = "first_name";
    public static final String STUDENTS_COLUMN_LASTNAME = "last_name";
    public static final String STUDENTS_COLUMN_AGE = "age";
    public static final String STUDENTS_COLUMN_GPA = "gpa";
    public static final String STUDENTS_COLUMN_DEGREE = "degree";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Students " +
                        "(student_number integer primary key, first_name text, last_name text," +
                        "age integer, gpa double, degree text)"
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

    public void insert(String query){

        SQLiteDatabase db = this.getWritableDatabase();

        String insertQuery = query;

        db.execSQL(insertQuery);
    }
}
