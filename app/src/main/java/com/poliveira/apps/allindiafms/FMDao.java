package com.poliveira.apps.allindiafms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by i80429 on 2/25/2015.
 */
public class FMDao {

    private SQLiteDatabase mDatabase;
    private FMSQLLiteHelper mFMSQLLiteHelper;


    private String[] mAllCols = {FMSQLLiteHelper.COLUMN_ID, FMSQLLiteHelper.COLUMN_NAME, FMSQLLiteHelper.COLUMN_URL, FMSQLLiteHelper.COLUMN_IMAGEURL};

    public FMDao(Context context) {
        mFMSQLLiteHelper = new FMSQLLiteHelper(context);
    }

    public void open() {
        mDatabase = mFMSQLLiteHelper.getWritableDatabase();
    }

    public void close() {
        mFMSQLLiteHelper.close();
    }

    public void addFM(FM fm) {
        ContentValues values = new ContentValues();
        values.put(FMSQLLiteHelper.COLUMN_FMID, fm.id);
        values.put(FMSQLLiteHelper.COLUMN_NAME, fm.name);
        values.put(FMSQLLiteHelper.COLUMN_URL, fm.url);
        values.put(FMSQLLiteHelper.COLUMN_IMAGEURL, fm.imageurl);
        mDatabase.insert(FMSQLLiteHelper.TABLE_HINDIFMS, null, values);
//        long insertId = mDatabase.insert(FMSQLLiteHelper.TABLE_HINDIFMS, null, values);
//        Cursor cursor = mDatabase.query(FMSQLLiteHelper.TABLE_HINDIFMS, mAllCols, FMSQLLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//        cursor.moveToFirst();
//        Comment newComment = cursorToComment(cursor);
//        cursor.close();
//        return newComment;

    }

    public void deleteFM(FM fm) {
        int id = fm.getId();
        deleteFM(id);

    }

    public void deleteFM(int id) {
        mDatabase.delete(mFMSQLLiteHelper.TABLE_HINDIFMS, mFMSQLLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + FMSQLLiteHelper.TABLE_HINDIFMS);
    }

    private FM cursorToFM(Cursor cursor) {
        FM fm = new FM();
        fm.id = cursor.getInt(0);
        fm.name = cursor.getString(1);
        fm.url = cursor.getString(2);
        fm.imageurl = cursor.getString(3);
        return fm;
    }

    public ArrayList<FM> getAllInfo() {
        ArrayList<FM> fms = new ArrayList<>();
        String[] tableColumns = new String[]{FMSQLLiteHelper.COLUMN_FMID, FMSQLLiteHelper.COLUMN_NAME, FMSQLLiteHelper.COLUMN_URL, FMSQLLiteHelper.COLUMN_IMAGEURL};
        Cursor cursor = mDatabase.query(FMSQLLiteHelper.TABLE_HINDIFMS, tableColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fms.add(cursorToFM(cursor));
            cursor.moveToNext();
        }
        return fms;
    }
}

