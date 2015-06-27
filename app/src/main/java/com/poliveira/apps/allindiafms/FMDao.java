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
        values.put(FMSQLLiteHelper.COLUMN_FMID, fm.fmid);
        values.put(FMSQLLiteHelper.COLUMN_NAME, fm.name);
        values.put(FMSQLLiteHelper.COLUMN_URL, fm.url);
        values.put(FMSQLLiteHelper.COLUMN_IMAGEURL, fm.imageurl);
        values.put(FMSQLLiteHelper.COLUMN_FAVOURITE, fm.favourite);
        mDatabase.insert(FMSQLLiteHelper.TABLE_HINDIFMS, null, values);
//        long insertId = mDatabase.insert(FMSQLLiteHelper.TABLE_HINDIFMS, null, values);
//        Cursor cursor = mDatabase.query(FMSQLLiteHelper.TABLE_HINDIFMS, mAllCols, FMSQLLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
//        cursor.moveToFirst();
//        Comment newComment = cursorToComment(cursor);
//        cursor.close();
//        return newComment;

    }

    public void deleteFM(FM fm) {
        deleteFM(fm.fmid);

    }

    public void deleteFM(String fmid) {
        mDatabase.delete(mFMSQLLiteHelper.TABLE_HINDIFMS, mFMSQLLiteHelper.COLUMN_FMID + " = '" + fmid + "'", null);
    }

    public void deleteAll() {
        mDatabase.execSQL("delete from " + FMSQLLiteHelper.TABLE_HINDIFMS);
    }

    private FM cursorToFM(Cursor cursor) {
        FM fm = new FM();
        fm.fmid = cursor.getString(0);
        fm.name = cursor.getString(1);
        fm.url = cursor.getString(2);
        fm.imageurl = cursor.getString(3);
        fm.favourite = cursor.getString(4);
        return fm;
    }

    public ArrayList<FM> getInfo(String infoType) {
        String filter = "";
        if (infoType.equalsIgnoreCase(AppConst.ARG_FAVOURITE_FM)) {
            filter = " " + FMSQLLiteHelper.COLUMN_FAVOURITE + " = 'Y'";
        }
        ArrayList<FM> fms = new ArrayList<>();
        String[] tableColumns = new String[]{FMSQLLiteHelper.COLUMN_FMID, FMSQLLiteHelper.COLUMN_NAME, FMSQLLiteHelper.COLUMN_URL, FMSQLLiteHelper.COLUMN_IMAGEURL, FMSQLLiteHelper.COLUMN_FAVOURITE};
        Cursor cursor = mDatabase.query(FMSQLLiteHelper.TABLE_HINDIFMS, tableColumns, filter, null, null, null, FMSQLLiteHelper.COLUMN_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            fms.add(cursorToFM(cursor));
            cursor.moveToNext();
        }
        return fms;
    }

    public void setFavourite(String fmid, String flag) {
        ContentValues values = new ContentValues();
        values.put(FMSQLLiteHelper.COLUMN_FAVOURITE, flag);
        mDatabase.update(FMSQLLiteHelper.TABLE_HINDIFMS, values, FMSQLLiteHelper.COLUMN_FMID + " = '" + fmid + "'", null);
    }
}

