package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Database {
    private static final String DATABASE = "Storage";
    private static boolean dataBaseDefined = false;
    public static ArrayList<String[]> ShopAttributes = new ArrayList<>();
    public static void AddToShop(String topic, String subTopic) {
        for (int i = 0;i < ShopAttributes.size();i++) {
            if (Objects.equals(ShopAttributes.get(i)[0],topic) && Objects.equals(ShopAttributes.get(i)[1],subTopic)) {
                return;
            }
        }
        ShopAttributes.add(new String[]{topic,subTopic});
    }
    public static void RemoveFromShop(String topic,String subTopic) {
        for (int i = 0;i < ShopAttributes.size();i++) {
            if (Objects.equals(ShopAttributes.get(i)[0],topic) && Objects.equals(ShopAttributes.get(i)[1],subTopic)) {
                ShopAttributes.remove(i);
                return;
            }
        }
    }
    public static void ResetShop() {
        ShopAttributes.clear();
    }
    public static ArrayList<String> GetShop(Context context) {
        ArrayList<String> arrayList = new ArrayList<>(ShopAttributes.size());
        for (String[] attributes : ShopAttributes) {
            arrayList.add(Sources.SubTopicsNames(context)[Sources.GetTopic(attributes[0])][Sources.GetSubTopic(attributes[1])[1]]+
                    " - "+Sources.GetRightPointsEnd(context,100)+" ("+Sources.TopicsNames(context)[Sources.GetTopic(attributes[0])]+")");
        }
        return arrayList;
    }
    public static void DefineDataBase(Context context) {
        if (dataBaseDefined) return;
        dataBaseDefined = true;
        ResetShop();
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);

        Cursor query;

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
                boolean queryCorrect = false;
                if (query.moveToFirst()) {
                    if (query.isFirst() && query.isLast()) {
                        queryCorrect = true;
                        if (query.getInt(0) == 1)
                            AddToShop(topic, subTopic);
                    }
                } if (!queryCorrect)
                    Console.L("Error with topicId = "+i+" and topicId = '"+subTopic+"' in TheoryAvailability");
                query.close();
            }
        }

        for (int i = 0;i < Practice.TaskId.length;i++) {
            for (int k : Practice.TaskId[i]) {
                database.execSQL("INSERT OR IGNORE INTO PracticeSolutions VALUES ("+i+","+k+",0);");
            }
        }
        database.close();
    }
    public static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        DefineDataBase(context);
    }
    public static ArrayList<String> GetAvailableSubTopics(Context context, String topic) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);

    }
    public static boolean BuySubTopic(Context context, String topic, String subTopic) {
        boolean success = false;
        long points = GetPoints(context);
        if (points < 100)
            return false;
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);

        Cursor query = database.rawQuery("SELECT topicId FROM TheoryTopics WHERE topic = '"+topic+"';",null);
        int topicId = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isFirst()) {
                topicId = query.getInt(0);
            }
        }
        query.close();
        if (topicId != -1) {
            query = database.rawQuery("SELECT availability FROM TheoryAvailability WHERE topicId = "+topicId+" AND subTopic = '"+subTopic+"';",null);
            boolean availabilityCorrect = false;
            boolean availability = false;
            if (query.moveToFirst()) {
                if (query.isFirst() && query.isFirst()) {
                    availabilityCorrect = true;
                    availability = query.getInt(0) == 1;
                }
            } if (availabilityCorrect) {
                if (!availability) {
                    success = true;
                    ChangePoints(context,-100);
                    database.execSQL("UPDATE TheoryAvailability SET availability = 1 WHERE topicId = "+topicId+" AND subTopic = '"+subTopic+"';");
                    RemoveFromShop(topic,subTopic);
                }
            } else
                Console.L("Error with topicId = "+topicId+" AND subTopic = '"+subTopic+"' in TheoryAvailability");
            query.close();
        } else
            Console.L("Error with topic = '"+topic+"' in TheoryTopics");
        database.close();
        return success;
    }
    public static int GetPracticeTask(Context context,int number,int Id) {
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
        Cursor query = database.rawQuery("SELECT solutions FROM PracticeSolutions WHERE number = "+number+" AND taskId = "+Id+";", null);
        int solutions = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isLast()) {
                solutions = query.getInt(0);
            }
        } if (solutions == -1) {
            solutions = 0;
            Console.L("Error with number = "+number+" and taskId = "+Id+" in PracticeSolutions");
        }
        query.close();
        database.close();
        return solutions;
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
            database.execSQL("UPDATE PracticeSolutions SET solutions = "+(solutions+difference)+" WHERE number = "+number+" AND taskId = "+Id+";");
        else
            Console.L("Error with number = "+number+" and taskId = "+Id+" in PracticeSolutions");
        query.close();
        database.close();
    }
    public static void ChangePoints(Context context, long difference) {
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
        Cursor query = database.rawQuery("SELECT value FROM Currencies WHERE id = 0",null);
        long points = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isLast()) {
                points = query.getLong(0);
            }
        } if (points != -1)
            database.execSQL("UPDATE Currencies SET value = "+(points+difference)+" WHERE id = 0",null);
        else
            Console.L("Error with points in Currencies");
        query.close();
        database.close();
    }
    public static long GetPoints(Context context) {
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
        Cursor query = database.rawQuery("SELECT value FROM Currencies WHERE id = 0",null);
        long points = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isLast()) {
                points = query.getLong(0);
            }
        } if (points == -1)
            Console.L("Error with points in Currencies");
        query.close();
        database.close();
        return points;
    }
}


        /*database.execSQL("CREATE TABLE IF NOT EXISTS System (id TEXT PRIMARY KEY, value INT)");
        database.execSQL("INSERT OR IGNORE INTO Currencies VALUES ('DataBaseDefined',0);");
        query = database.rawQuery("SELECT value FROM System WHERE id = 'DataBaseDefined';", null);
        if (query.moveToFirst()) {
            if (query.isLast() && query.isFirst()) {
                if (query.getInt(0) == 1)
                else
                    database.execSQL("UPDATE System SET value = 1 WHERE id = 'DataBaseDefined'");
            }
        }
        query.close();
        if (dataBaseDefined) return;*/