package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Objects;

public class Database {
    private static final String DATABASE = "Storage";
    private static boolean defined = false;
    public static void DefineDataBase(Context context) {
        if (defined) return;
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);

        Cursor query;
        database.execSQL("CREATE TABLE IF NOT EXISTS System (id TEXT PRIMARY KEY, value INT)");
        database.execSQL("INSERT OR IGNORE INTO Currencies VALUES ('DataBaseDefined',0);");
        query = database.rawQuery("SELECT value FROM System WHERE id = 'DataBaseDefined';", null);
        if (query.moveToFirst()) {
            if (query.isLast() && query.isFirst()) {
                if (query.getInt(0) == 1)
                    defined = true;
                else
                    database.execSQL("UPDATE System SET value = 1 WHERE id = 'DataBaseDefined'");
            }
        }
        query.close();
        if (defined) return;

        database.execSQL("CREATE TABLE IF NOT EXISTS Currencies (id INT PRIMARY KEY, value LONG)");
        database.execSQL("INSERT OR IGNORE INTO Currencies VALUES (0,0);");

        database.execSQL("CREATE TABLE IF NOT EXISTS TheoryTopics (topicId INT PRIMARY KEY, topic TEXT)");
        database.execSQL("CREATE TABLE IF NOT EXISTS TheoryAvailability (topicId INT PRIMARY KEY, subTopic TEXT PRIMARY KEY, availability BIT)");

        database.execSQL("CREATE TABLE IF NOT EXISTS PracticeSolutions (number INT PRIMARY KEY, taskId INT PRIMARY KEY, solutions INT)");

        for (int i = 0;i < Sources.Topics.length;i++) {
            String topic = Sources.Topics[i];
            database.execSQL("INSERT OR IGNORE INTO TheoryTopics VALUES ("+i+","+topic+");");
            for (String subTopic : Sources.SubTopics[i]) {
                int availability = (Boolean.TRUE.equals(Objects.requireNonNull(Theory.FormulasAvailability.get(topic)).get(subTopic)) ? 1 : 0);
                database.execSQL("INSERT OR IGNORE INTO TheoryAvailability VALUES ("+i+","+subTopic+","+availability+");");
                query = database.rawQuery("SELECT availability FROM TheoryAvailability WHERE topicId = "+i+" AND subTopic = '"+subTopic+"';", null);
                boolean queryRight = false;
                if (query.moveToFirst()) {
                    if (query.isFirst() && query.isLast()) {
                        queryRight = true;
                        if (query.getInt(0) == 1)
                            ShopDataBase.AddToShop(context, topic, subTopic);
                    }
                } if (!queryRight)
                    Console.L("Error with topicId = "+i+" and topicId = '"+subTopic+"' in TheoryAvailability");
                query.close();
            }
        }

        for (int i = 0;i < Practice.TaskId.length;i++) {
            for (int k : Practice.TaskId[i]) {
                database.execSQL("INSERT OR IGNORE INTO PracticeSolutions VALUES ("+i+","+k+",0);");
            }
        }
    }
    public static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        DefineDataBase(context);
    }
    public static void ChangePracticeTask(Context context,int number,int Id,int difference) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
        Cursor query = database.rawQuery("SELECT solutions FROM PracticeSolutions WHERE number = "+number+" AND taskId = "+Id+";", null);
        int solutions = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isLast()) {
                solutions = query.getInt(0);
            }
        } if (solutions != -1)
            database.execSQL("UPDATE PracticeSolutions SET solutions = "+(solutions + difference)+" WHERE number = "+number+" AND taskId = "+Id+";");
        else
            Console.L("Error with number = "+number+" and taskId = "+Id+" in PracticeSolutions");
        query.close();

        /*SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+PRACTICETASKS+"_"+Number+" (id INT PRIMARY KEY, count INT)");
            query = db.rawQuery("SELECT * FROM "+PRACTICETASKS+"_"+Number+" WHERE id = "+Id+";", null);
            if (query.moveToFirst()) {
                int count = query.getInt(1);
                db.execSQL("UPDATE "+PRACTICETASKS+"_"+Number+" SET count = "+(count+difference)+" WHERE id = "+Id+";");
            }
        } catch (Exception e) {
            Console.L("SetupPracticeTask: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(query).close(); Objects.requireNonNull(db).close(); } catch (Exception ignored) {}*/
    }
    /*
    public static void SetupPracticeTask(Context context,int Number,int Id) {
        if (!defined)
            DefineDataBases(context);
        SQLiteDatabase db = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+PRACTICETASKS+"_"+Number+" (id INT PRIMARY KEY, count INT)");
            db.execSQL("INSERT OR IGNORE INTO "+PRACTICETASKS+"_"+Number+" VALUES ("+Id+",0);");
        } catch (Exception e) {
            Console.L("SetupPracticeTask: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
    }

    public static void DefineDataBases(Context context) {
        FormulasAvailability = new TreeMap<>();
        for (int i = 0;i < Resources.Topics.length;i++) {
            String topic = Resources.Topics[i];
            FormulasAvailability.put(topic,new TreeMap<>());
            for (String subTopic : Resources.SubTopics[i]) {
                FormulasAvailability.get(topic).put(subTopic,Theory.FormulasAvailability.get(topic).get(subTopic));
            }
        }
        ShopDataBase.ResetShop();
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
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
                        if (availability == 0)
                            ShopDataBase.AddToShop(context,topic,subTopic);
                    } else
                        throw new Exception("Key '"+subTopic+"' not found in table "+topic);
                    query.close();
                }
            }
            defined = true;
        } catch (Exception e) {
            Console.L("DefineDataBase: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(query).close(); Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
    }
    public static void ResetDataBases(Context context) {
        try {
            context.deleteDatabase(DATABASE);
        } catch (Exception e) {
            Console.L("ResetDataBases: "+e,context.getClass().getName(),Class);
        }
    }

    public static void ChangePracticeTask(Context context,int Number,int Id,int difference) {
        if (!defined)
            DefineDataBases(context);
        SQLiteDatabase db = null;
        Cursor query = null;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+PRACTICETASKS+"_"+Number+" (id INT PRIMARY KEY, count INT)");
            query = db.rawQuery("SELECT * FROM "+PRACTICETASKS+"_"+Number+" WHERE id = "+Id+";", null);
            if (query.moveToFirst()) {
                int count = query.getInt(1);
                db.execSQL("UPDATE "+PRACTICETASKS+"_"+Number+" SET count = "+(count+difference)+" WHERE id = "+Id+";");
            }
        } catch (Exception e) {
            Console.L("SetupPracticeTask: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(query).close(); Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
    }
    public static int GetPracticeTask(Context context,int Number,int Id) {
        if (!defined)
            DefineDataBases(context);
        SQLiteDatabase db = null;
        Cursor query = null;
        int count = 0;
        try {
            db = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+PRACTICETASKS+"_"+Number+" (id INT PRIMARY KEY, count INT)");
            query = db.rawQuery("SELECT * FROM "+PRACTICETASKS+"_"+Number+" WHERE id = "+Id+";", null);
            if (query.moveToFirst())
                count = query.getInt(1);
        } catch (Exception e) {
            Console.L("SetupPracticeTask: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
        return count;
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
            Console.L("BuySubTopic: "+e,context.getClass().getName(),Class);
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
            if (query.moveToFirst()) {
                long points = query.getLong(1);
                db.execSQL("UPDATE "+CURRENCIES+" SET value = "+(points+difference)+" WHERE id = 1");
            }
        } catch (Exception e) {
            Console.L("ChangePoints: "+e,context.getClass().getName(),Class);
        }
        try { Objects.requireNonNull(query).close(); } catch (Exception ignored) {} try { Objects.requireNonNull(db).close(); } catch (Exception ignored) {}
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
            Console.L("GetPoints: "+e,context.getClass().getName(),Class);
            return -1;
        }
    }*/
}
