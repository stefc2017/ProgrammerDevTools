package com.stefancouture.programmerdevtools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myDatabase.db";
    private static int versionNumber = 3;
    private static final String STUDENTS_TABLE_NAME = "Students";
    public static final String STUDENTS_COLUMN_STUDNUM = "studNum";
    public static final String STUDENTS_COLUMN_FIRSTNAME = "fName";
    public static final String STUDENTS_COLUMN_LASTNAME = "lName";
    public static final String STUDENTS_COLUMN_GPA = "gpa";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, versionNumber);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS Students " +
                        "(studNum integer primary key, fName text, lName text," +
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
            items[i].setStudId(cursor.getString(cursor.getColumnIndex("studNum")));
            items[i].setFirstName(cursor.getString(cursor.getColumnIndex("fName")));
            items[i].setLastName(cursor.getString(cursor.getColumnIndex("lName")));
            items[i].setGpa(cursor.getString(cursor.getColumnIndex("gpa")));

            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    public String getSQLColumnName(String column){
        String result = " ";

        if(column.equals("Student #"))
            result = "studNum";
        else if(column.equals("First name"))
            result = "fName";
        else if(column.equals("Last name"))
            result = "lName";
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

        if(insertQuery.indexOf("DELETE") == -1) {
            try {
                db.execSQL(insertQuery);
            } catch (Exception e) {
                isValid = false;
            }
        }
        else{
            isValid = false;
        }

        return isValid;
    }

    public boolean remove(String query){
        boolean isValid = true;
        SQLiteDatabase db = this.getWritableDatabase();

        String removeQuery = query;

        if(removeQuery.indexOf("INSERT") == -1) {
            try {
                db.execSQL(removeQuery);
            } catch (Exception e) {
                isValid = false;
            }
        }
        else{
            isValid = false;
        }

        return isValid;
    }

    public ListItems[] search(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows = 0;
        ListItems [] items = new ListItems[1];
        items = initialize(items, 1);

        String searchQuery = query;

        if(searchQuery.indexOf("INSERT") == -1 && searchQuery.indexOf("DELETE") == -1) {
            try {
                Cursor cursor = db.rawQuery(searchQuery,null);

                numberOfRows = cursor.getCount();

                items = new ListItems[numberOfRows];
                items = initialize(items, numberOfRows);

                cursor.moveToFirst();

                for(int i = 0; i < numberOfRows; i++){
                    items[i].setStudId(cursor.getString(cursor.getColumnIndex("studNum")));
                    items[i].setFirstName(cursor.getString(cursor.getColumnIndex("fName")));
                    items[i].setLastName(cursor.getString(cursor.getColumnIndex("lName")));
                    items[i].setGpa(cursor.getString(cursor.getColumnIndex("gpa")));
                    cursor.moveToNext();
                }
                cursor.close();
                items[0].setIsValid(true);
            } catch (Exception e) {
                items[0].setIsValid(false);
            }
        }
        else{
            items[0].setIsValid(false);
        }

        return items;
    }
}
