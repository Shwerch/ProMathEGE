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

import java.util.Objects;

public class MyAppCompatActivity extends AppCompatActivity {
    private final String storageName = "Storage";
    private final String pointsTableName = "Points";
    private final String subTopicsName = "SubTopics";
    protected void BackToMainMenu(Button button) {
        button.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));/*startActivity(new Intent(this,MainActivity.class))*/
    }
    protected void DefineDataBases() {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+pointsTableName+" (id INTEGER PRIMARY KEY AUTOINCREMENT, points LONG)");
            db.execSQL("INSERT OR IGNORE INTO "+pointsTableName+" VALUES (1,0);");

            db.execSQL("CREATE TABLE IF NOT EXISTS "+subTopicsName+" (id INTEGER PRIMARY KEY AUTOINCREMENT, availability INTEGER)");
            int id = 0;
            for (int topic = 0;topic < Theory.FormulasAvailability.length;topic++) {
                for (int chapter = 0;chapter < Theory.FormulasAvailability[topic].length;chapter++) {
                    id += 1;
                    db.execSQL("INSERT OR IGNORE INTO "+subTopicsName+" VALUES ("+id+","+Theory.FormulasAvailability[topic][chapter]+");");
                }
            }

            id = 0;
            Shop.ResetShop();
            query = db.rawQuery("SELECT * FROM "+subTopicsName+";",null);
            query.moveToFirst();
            for (int topic = 0;topic < Theory.FormulasAvailability.length;topic++) {
                for (int chapter = 0; chapter < Theory.FormulasAvailability[topic].length; chapter++) {
                    id += 1;
                    int availability = query.getInt(1);
                    Theory.FormulasAvailability[topic][chapter] = availability;
                    if (availability == 0) {
                        Shop.AddToShop(Theory.SubTopics[topic][chapter],id);
                    }
                    query.moveToNext();
                }
            }
            query.close();
            db.close();
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase DefineDataBase: "+e);
        }
    }
    protected void ResetDataBases() {
        SQLiteDatabase db = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);
            db.execSQL("DROP TABLE IF EXISTS "+pointsTableName);
            db.execSQL("DROP TABLE IF EXISTS "+subTopicsName);
            db.close();
            Theory.FormulasAvailability = Theory.FormulasAvailabilityDefault.clone();
            DefineDataBases();
        } catch (Exception e) {
            try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase ResetDataBases: "+e);
        }
    }

    protected boolean BuySubTopic(long subTopicID) {
        boolean Successful = false;

        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);

            query = db.rawQuery("SELECT * FROM "+pointsTableName+";",null);
            query.moveToFirst();
            long points = query.getLong(1);
            if (points >= 100) {
                db.execSQL("UPDATE "+pointsTableName+" SET points = "+(points-100L)+" WHERE id = 1");
                query.close();
                Successful = true;
            } else {
                db.close();
                query.close();
                throw new Exception("Need 100+ points");
            }

            query = db.rawQuery("SELECT * FROM "+subTopicsName+";",null);
            query.move((int)subTopicID);
            if (query.getInt(1) == 0) {
                db.execSQL("UPDATE "+subTopicsName+" SET availability = 1 WHERE id = "+subTopicID);
                query.close();
                db.close();
                Shop.RemoveFromShop(subTopicID);
                DefineDataBases();
            } else {
                query.close();
                db.close();
                throw new Exception("Already have this");
            }

            query = db.rawQuery("SELECT * FROM "+subTopicsName+";",null);
            query.moveToFirst();
            for (int topic = 0;topic < Theory.FormulasAvailability.length;topic++) {
                for (int chapter = 0; chapter < Theory.FormulasAvailability[topic].length; chapter++) {
                    int availability = query.getInt(1);
                    Theory.FormulasAvailability[topic][chapter] = availability;
                    query.moveToNext();
                }
            }
            query.close();
            db.close();
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase BuySubTopic: "+e);
        }
        return Successful;
    }
    protected void ChangePoints(long difference) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+pointsTableName+";",null);
            if (query.moveToFirst()){
                long points = query.getLong(1);
                db.execSQL("UPDATE "+pointsTableName+" SET points = "+(points+difference)+" WHERE id = 1");
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase ChangePoints: "+e);
        }
    }

    protected long GetPoints() {
        long points;
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+pointsTableName+";",null);
            if (query.moveToFirst()){
                points = query.getLong(1);
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            points = -1;
            Log.d("MYLOG","DataBase GetPoints: "+e);
        }
        return points;
    }

    /*protected void SetPoints(long points) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = getBaseContext().openOrCreateDatabase(storageName, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+pointsTableName+";",null);
            if (query.moveToFirst()){
                db.execSQL("UPDATE "+pointsTableName+" SET points = "+(points)+" WHERE id = 1");
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Log.d("MYLOG","DataBase ChangePoints: "+e);
        }
    }*/

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,R.anim.fadein,R.anim.fadeout);
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.fadeout,R.anim.fadein);
        }
    }
    protected void SetSizes(Button[] Objects, TextView Title) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        double multiplier = (Math.min(size.x,size.y)/1500d-1)/2.5d+1;
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
