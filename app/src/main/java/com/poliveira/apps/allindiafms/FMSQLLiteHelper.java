package com.poliveira.apps.allindiafms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by i80429 on 2/25/2015.
 */

public class FMSQLLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_HINDIFMS = "hindifms";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FMID = "fmid";
    public static final String COLUMN_NAME = AppConst.JSON_FM_NAME;
    public static final String COLUMN_URL = AppConst.JSON_FM_URL;
    public static final String COLUMN_IMAGEURL = AppConst.JSON_FM_IMAGE_URL;
    public static final String COLUMN_WEBURL = AppConst.JSON_FM_WBURL;
    public static final String COLUMN_SLOGAN = AppConst.JSON_FM_SLOGAN;
    public static final String COLUMN_GENRE = AppConst.JSON_FM_GENRE;
    public static final String COLUMN_ADDRESS = AppConst.JSON_FM_ADDRESS;
    public static final String COLUMN_FAVOURITE = "favourite";

    private static final String DATABASE_NAME = "fms.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement 
    private static final String DATABASE_CREATE = "create table " + TABLE_HINDIFMS +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_FMID + " integer not null," +
            COLUMN_NAME + " text not null," +
            COLUMN_IMAGEURL + " text" +
            COLUMN_ADDRESS + " text not null,"+
            COLUMN_WEBURL + " text," +
            COLUMN_URL + " text not null," +
            COLUMN_SLOGAN +" text,"+
            COLUMN_GENRE + " text,"+
            COLUMN_FAVOURITE + " text"+
            ");";

    public FMSQLLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FMSQLLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HINDIFMS);
        onCreate(db);
    }

}  
