package com.firstapp.tutorial05_datahandling.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + UserMaster.Users.TABLE_NAME + " (" +
                        UserMaster.Users._ID + " INTEGER PRIMARY KEY," +
                        UserMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                        UserMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    //INSERT VALUES//

    public void addInfo(String userName, String password){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_USERNAME, userName);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserMaster.Users.TABLE_NAME, null, values);
    }


    //READING DATA FROM THE TABLE//

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                UserMaster.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();
        while(cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));
        }
        cursor.close();
        return userNames;
    }

    //DELETE INFO FROM THE TABLE//
    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        // Define 'where' part of query.
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { userName };
        // Issue SQL statement.
        int deletedRows = db.delete(UserMaster.Users.TABLE_NAME, selection, selectionArgs);

    }

    //UPDATE INFO FROM THE TABLE//
    public void updateInfo(String userName, String password){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        // Which row to update, based on the title
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME+ " LIKE ?";
        String[] selectionArgs = { userName };

        int count = db.update(
                UserMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }
}

