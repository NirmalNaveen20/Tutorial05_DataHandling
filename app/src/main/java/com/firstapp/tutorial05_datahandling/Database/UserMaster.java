package com.firstapp.tutorial05_datahandling.Database;
import android.provider.BaseColumns;

public final class UserMaster {

    private UserMaster(){}

    /*Inner class that defines the table contents*/
    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}


