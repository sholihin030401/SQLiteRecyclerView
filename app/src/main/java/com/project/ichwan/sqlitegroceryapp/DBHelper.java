package com.project.ichwan.sqlitegroceryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "shoplist.db";
    public static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SHOPLIST_TABLE = "CREATE TABLE " +
                Contract.ShopEntry.TABLE_NAME + " (" +
                Contract.ShopEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.ShopEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                Contract.ShopEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                Contract.ShopEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        sqLiteDatabase.execSQL(SHOPLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.ShopEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
