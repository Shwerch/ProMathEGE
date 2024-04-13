package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Objects;
import java.util.TreeMap;

public class Database {
    private static final String DATABASE = "Storage";
    private static final String CURRENCIES = "Currencies";
    private static TreeMap<String,TreeMap<String,Boolean>> FormulasAvailability;
    public static TreeMap<String,TreeMap<String,Boolean>> GetFormulasAvailability() {
        return FormulasAvailability;
    }
    private static void SetFormulasAvailability() {
        FormulasAvailability = new TreeMap<>();
        for (int i = 0;i < Resources.Topics.length;i++) {
            String topic = Resources.Topics[i];
            FormulasAvailability.put(topic,new TreeMap<>());
            for (String subTopic : Resources.SubTopics[i]) {
                FormulasAvailability.get(topic).put(subTopic,Theory.FormulasAvailability.get(topic).get(subTopic));
            }
        }
    }
    public static void DefineDataBases(Context context) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            SetFormulasAvailability();
            ShopDataBase.ResetShop();
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+CURRENCIES+" (id INT PRIMARY KEY, value LONG)");
            db.execSQL("INSERT OR IGNORE INTO "+CURRENCIES+" VALUES (1,0);");

            for (int i = 0;i < Resources.Topics.length;i++) {
                String topic = Resources.Topics[i];
                db.execSQL("CREATE TABLE IF NOT EXISTS "+topic+" (subTopic TEXT PRIMARY KEY, availability BIT)");
                for (String subTopic : Resources.SubTopics[i]) {
                    byte availability = 1;
                    if (Boolean.FALSE.equals(Theory.FormulasAvailability.get(topic).get(subTopic))) {
                        availability = 0;
                    }
                    db.execSQL("INSERT OR IGNORE INTO "+topic+" VALUES ('"+subTopic+"',"+availability+");");
                }
            }

            for (int i = 0;i < Resources.Topics.length;i++) {
                String topic = Resources.Topics[i];
                for (String subTopic : Resources.SubTopics[i]) {
                    query = db.rawQuery("SELECT * FROM "+topic+" WHERE subTopic = '"+subTopic+"';", null);
                    if (query.moveToFirst()) {
                        int availability = query.getInt(1);
                        FormulasAvailability.get(topic).put(subTopic,availability == 1);
                        if (availability == 0) {
                            ShopDataBase.AddToShop(topic,subTopic);
                        }
                        //Logcat.Log(topic+" "+subTopic+" "+(availability == 1),context.getClass().getName(),"Database");
                    } else
                        throw new Exception("Key '"+subTopic+"' not found in table "+topic);
                }
            }
        } catch (Exception e) {
            Logcat.Log("DefineDataBase: "+e,context.getClass().getName(),"Database");
        }
        try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
    }
    public static void ResetDataBases(Context context) {
        SQLiteDatabase db = null;
        try {
            context.deleteDatabase(DATABASE);
            /*db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            SetPoints(context,0);
            for (int i = 0;i < Resources.Topics.length;i++) {
                String topic = Resources.Topics[i];
                for (String subTopic : Resources.SubTopics[i]) {
                    byte availability = 1;
                    if (Boolean.FALSE.equals(Theory.FormulasAvailability.get(topic).get(subTopic))) {
                        availability = 0;
                    }
                    db.execSQL("UPDATE "+topic+" SET availability="+availability+" WHERE subTopic='"+subTopic+"';");
                }
            }*/
        } catch (Exception e) {
            Logcat.Log("ResetDataBases: "+e,context.getClass().getName(),"Database");
        }
        //try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
    }
    public static boolean BuySubTopic(Context context, String topic, String subTopic) {
        boolean success = false;
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            long points = GetPoints(context);
            if (points < 100)
                return false;
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+topic+" WHERE subTopic = '"+subTopic+"';",null);
            if (query.moveToFirst()) {
                if (query.getInt(1) == 0) {
                    ChangePoints(context,-100);
                    db.execSQL("UPDATE "+topic+" SET availability = 1 WHERE subTopic = '"+subTopic+"';");
                    ShopDataBase.RemoveFromShop(topic,subTopic);
                    DefineDataBases(context);
                    success = true;
                } else
                    throw new Exception("You already have '"+subTopic+"' in "+topic);
            } else
                throw new Exception("Key '"+subTopic+"' not found in table "+topic);
        } catch (Exception e) {
            Logcat.Log("BuySubTopic: "+e,context.getClass().getName(),"Database");
        }
        try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
        return success;
    }
    protected static void ChangePoints(Context context, long difference) {
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+CURRENCIES+";",null);
            if (query.moveToFirst()){
                long points = query.getLong(1);
                db.execSQL("UPDATE "+CURRENCIES+" SET value = "+(points+difference)+" WHERE id = 1");
                query.close();
                db.close();
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Logcat.Log("ChangePoints: "+e,context.getClass().getName(),"Database");
        }
    }
    protected static long GetPoints(Context context) {
        long points;
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            query = db.rawQuery("SELECT * FROM "+CURRENCIES+";",null);
            if (query.moveToFirst()){
                points = query.getLong(1);
                query.close();
                db.close();
                return points;
            } else {
                query.close();
                db.close();
                throw new Exception();
            }
        } catch (Exception e) {
            try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Logcat.Log("GetPoints: "+e,context.getClass().getName(),"Database");
            return -1;
        }
    }
    protected static void SetPoints(Context context, long points) {
        SQLiteDatabase db = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("UPDATE "+CURRENCIES+" SET value = "+(points)+" WHERE id = 1");
            db.close();
        } catch (Exception e) {
            try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
            Logcat.Log("SetPoints: "+e,context.getClass().getName(),"Database");
        }
    }
}
