package com.pro.math.EGE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*public class OpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PointStorage";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "data";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "points";

    OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " LONG PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " LONG); ";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}*/