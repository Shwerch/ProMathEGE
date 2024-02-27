package com.pro.math.EGE;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MyAppCompatActivity extends AppCompatActivity {
    protected void BackToMainMenu(Button button) {
        button.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));/*startActivity(new Intent(this,MainActivity.class))*/
    }
    protected void DefineDataBases() {
        TheoryStorage.FormulasIDs = TheoryStorage.FormulasAvailability.clone();
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase("PointStorage", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, points LONG)");
            query = db.rawQuery("SELECT * FROM data;",null);
            db.execSQL("INSERT OR IGNORE INTO data VALUES (1,0);");
            query.close();
            int id = 1;
            for (int topic = 0;topic < TheoryStorage.FormulasAvailability.length;topic++) {
                for (int chapter = 0;chapter < TheoryStorage.FormulasAvailability[topic].length;chapter++) {
                    id += 1;
                    db.execSQL("INSERT OR IGNORE INTO data VALUES ("+id+","+TheoryStorage.FormulasAvailability[topic][chapter]+");");
                    TheoryStorage.FormulasIDs[topic][chapter] = id;
                }
            }

            query = db.rawQuery("SELECT * FROM data;",null);
            query.moveToFirst();
            for (int topic = 0;topic < TheoryStorage.FormulasAvailability.length;topic++) {
                for (int chapter = 0; chapter < TheoryStorage.FormulasAvailability[topic].length; chapter++) {
                    if (query.moveToNext()) {
                        TheoryStorage.FormulasAvailability[topic][chapter] = query.getInt(1);
                    }
                }
            }
            Log.d("MYLOG", Arrays.deepToString(TheoryStorage.FormulasAvailability));
            query.close();
            db.close();
        } catch (Exception e) {
            try { assert query != null; query.close(); } catch (Exception ignored) {} try { db.close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase DefineDataBase: "+e);
        }
    }

    protected void ChangePoints(long difference) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase("PointStorage", MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM data;",null);
            if (query.moveToFirst()){
                long points = query.getLong(1);
                db.execSQL("UPDATE data SET points = "+(points+difference)+" WHERE id = 0");
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { assert query != null; query.close(); } catch (Exception ignored) {} try { db.close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase ChangePoints: "+e);
        }
    }

    protected long GetPoints() {
        long points;
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase("PointStorage", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, points LONG)");
            query = db.rawQuery("SELECT * FROM data;",null);
            if (query.moveToFirst()){
                points = query.getLong(1);
                query.close();
                //db.close();
            } else {
                query.close();
                //db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { assert query != null; query.close(); } catch (Exception ignored) {} try { db.close(); } catch (Exception ignored) {}
            points = -1;
            Log.d("MYLOG","DataBase GetPoints: "+e);
        }
        return points;
    }

    protected void SetPoints(long points) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase("PointStorage", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY AUTOINCREMENT, points LONG)");
            query = db.rawQuery("SELECT * FROM data;",null);
            if (query.moveToFirst()){
                db.execSQL("UPDATE data SET points = "+points+" WHERE id = 1");
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { assert query != null; query.close(); } catch (Exception ignored) {} try { db.close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase SetPoints: "+e);
        }
    }

    protected String GetRightPointsEnd(long points) {
        long remainder  = points % 10;
        String text;
        if ((points > 9 && points < 20) || remainder == 0 || remainder >= 5) {
            text = points + " " + getResources().getString(R.string.points1);
        } else if (remainder == 1) {
            text = points + " " + getResources().getString(R.string.points2);
        } else {
            text = points + " " + getResources().getString(R.string.points3);
        }
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.Background));
        getWindow().setStatusBarColor(getResources().getColor(R.color.Background));
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,R.anim.fadein,R.anim.fadeout);
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.fadeout,R.anim.fadein);
        }
    }
    protected void SetSizes(Button[] Objects, TextView Title) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        double multiplier = (Math.min(size.x,size.y)/1250d-1)/1.3d+1;
        double textSize = multiplier-0.1d;

        if (Title != null) {
            Title.setTextSize(0,(float)(Title.getTextSize()*multiplier));
        }

        for (Button object : Objects) {
            ViewGroup.LayoutParams params = object.getLayoutParams();
            params.width = (int)(params.width * multiplier);
            params.height = (int)(params.height * multiplier);
            object.setLayoutParams(params);
            object.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(object.getTextSize() * textSize));
        }
    }
}
