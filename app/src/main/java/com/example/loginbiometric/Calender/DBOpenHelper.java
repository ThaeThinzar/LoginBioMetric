package com.example.loginbiometric.Calender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_EVENT_TABLE = "create table " + DBStructure.EVENT_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBStructure.EVENT + " TEXT, " + DBStructure.TIME + " TEXT, " + DBStructure.DATE + " TEXT, " + DBStructure.MONTH + "TEXT, "
            + DBStructure.YEAR + " TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXIST " + DBStructure.EVENT_TABLE_NAME;

    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void SaveEvent(String event, String time, String date, String month, String year, SQLiteDatabase database) {
        ContentValues cv = new ContentValues();
        cv.put(DBStructure.EVENT, event);
        cv.put(DBStructure.TIME, time);
        cv.put(DBStructure.DATE, date);
        cv.put(DBStructure.MONTH, month);
        cv.put(DBStructure.YEAR, year);
        database.insert(DBStructure.EVENT_TABLE_NAME, null, cv);
    }

    public Cursor readEvent(String date, SQLiteDatabase database) {
        String[] projections = {DBStructure.EVENT, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String selection = DBStructure.DATE + "=?";
        String[] selectionArgs = {date};
        return database.query(DBStructure.EVENT_TABLE_NAME, projections, selection, selectionArgs, null, null, null);
    }
    public Cursor readEventPerMonth(String month, String year, SQLiteDatabase database) {
        String[] projections = {DBStructure.EVENT, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String selection = DBStructure.MONTH + "=? and " +DBStructure.YEAR + "=?";
        String[] selectionArgs = {month,year};
        return database.query(DBStructure.EVENT_TABLE_NAME, projections, selection, selectionArgs, null, null, null);
    }
}
