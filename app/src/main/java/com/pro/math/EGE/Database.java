package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Database {
    private static final String DATABASE = "Storage";
    private static boolean dataBaseDefined = false;
    public static ArrayList<int[]> ShopAttributes = new ArrayList<>();
    public static void DefineDataBase(Context context) {
        if (dataBaseDefined) return;
        dataBaseDefined = true;
        SQLiteDatabase database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
        Cursor query;

        ResetShop();

        database.execSQL("CREATE TABLE IF NOT EXISTS Currencies (id INT PRIMARY KEY, value LONG)");
        database.execSQL("INSERT OR IGNORE INTO Currencies VALUES (0,0);");

        database.execSQL("CREATE TABLE IF NOT EXISTS TheoryTopics (topicId INT PRIMARY KEY, topic TEXT)");
        database.execSQL("CREATE TABLE IF NOT EXISTS TheorySubTopics (topicId INT, subTopicId INT, subTopic TEXT, PRIMARY KEY (topicId, subTopicId))");
        database.execSQL("CREATE TABLE IF NOT EXISTS TheoryAvailability (topicId INT, subTopicId INT, availability BIT, PRIMARY KEY (topicId, subTopicId))");

        Resources LocaleResources = Sources.GetLocaleResources(context,new Locale(Locale.ENGLISH.getLanguage()));
        String[] TopicsAttributes = LocaleResources.getStringArray(R.array.TopicsAttributes);
        for (int i = 0;i < TopicsAttributes.length;i += 2) {
            database.execSQL("INSERT OR IGNORE INTO TheoryTopics VALUES ("+i+",'"+TopicsAttributes[i].replace(" ","_")+"');");
            String[] SubTopicsAttributes = Sources.GetStringArray(LocaleResources,TopicsAttributes[i].replace(" ","_"));
            for (int k = 0;k < SubTopicsAttributes.length;k += 2) {
                database.execSQL("INSERT OR IGNORE INTO TheorySubTopics VALUES ("+i+","+k+",'"+SubTopicsAttributes[k].replace(" ","_")+"');");
                database.execSQL("INSERT OR IGNORE INTO TheoryAvailability VALUES ("+i+",'"+k+"',"+((Objects.equals(SubTopicsAttributes[k + 1], "0")) ? 1 : 0)+");");
                query = database.rawQuery("SELECT availability FROM TheoryAvailability WHERE topicId = "+i+" AND subTopicId = "+k+";", null);
                if (query.moveToFirst()) {
                    if (query.isFirst() && query.isLast() && query.getInt(0) == 0) {
                        AddToShop(i, k);
                    }
                }
                query.close();
            }
        }
        database.close();
    }
    public static void ResetShop() {
        ShopAttributes.clear();
    }
    public static void AddToShop(int topicIndex, int subTopicIndex) {
        final int[] newAttribute = new int[] {topicIndex,subTopicIndex};
        for (int[] attributes : ShopAttributes) {
            if (attributes == newAttribute) {
                return;
            }
        }
        ShopAttributes.add(newAttribute);
    }
    public static void RemoveFromShop(int topicIndex, int subTopicIndex) {
        final int[] newAttribute = new int[] {topicIndex,subTopicIndex};
        for (int i = 0;i < ShopAttributes.size();i++) {
            if (ShopAttributes.get(i) == newAttribute) {
                ShopAttributes.remove(i);
                return;
            }
        }
    }
    @NonNull
    public static ArrayList<String> GetShop(Context context) {
        ArrayList<String> arrayList = new ArrayList<>(ShopAttributes.size());
        Resources LocaleResources = Sources.GetLocaleResources(context,new Locale(Locale.ENGLISH.getLanguage()));
        String[] TopicsAttributes = LocaleResources.getStringArray(R.array.TopicsAttributes);
        for (int[] attributes : ShopAttributes) {
            String[] SubTopicsAttributes = Sources.GetStringArray(LocaleResources,TopicsAttributes[attributes[0]].replace(" ","_"));
            arrayList.add(
                    SubTopicsAttributes[attributes[1]]+" - "
                            +Sources.GetRightPointsEnd(context, Long.parseLong(SubTopicsAttributes[attributes[1] + 1]))+
                            " ("+TopicsAttributes[attributes[0]]+")"
            );
        }
        return arrayList;
    }
    // working
    public static boolean BuySubTopic(Context context, int topicIndex, int subTopicIndex) {
        boolean success = false;
        Resources LocaleResources = Sources.GetLocaleResources(context,new Locale(Locale.ENGLISH.getLanguage()));
        long price = Long.parseLong(Sources.GetStringArray(LocaleResources,
                LocaleResources.getStringArray(R.array.TopicsAttributes)[topicIndex].replace(" ","_"))[subTopicIndex + 1]);
        long points = GetPoints(context);
        if (points < price)
            return false;
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
        Cursor query = database.rawQuery("SELECT availability FROM TheoryAvailability WHERE topicId = "+topicIndex+" AND subTopicId = "+subTopicIndex+";",null);
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
                ChangePoints(context,-price);
                database.execSQL("UPDATE TheoryAvailability SET availability = 1 WHERE topicId = "+topicIndex+" AND subTopicId = '"+subTopicIndex+"';");
                RemoveFromShop(topicIndex,subTopicIndex);
            }
        } else
            Console.L("Error with topicId = "+topicIndex+" AND subTopicId = "+subTopicIndex+" in TheoryAvailability");
        query.close();
        database.close();
        return success;
    }
    public static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        dataBaseDefined = false;
        DefineDataBase(context);
    }
    public static ArrayList<String> GetAvailableSubTopics(Context context, String topic) {
        ArrayList<String> arrayList = new ArrayList<>();
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
            query = database.rawQuery("SELECT * FROM TheoryAvailability WHERE topicId = "+topicId+" AND availability = 1;",null);
            if (query.moveToFirst()) {
                do {
                    arrayList.add(query.getString(1));
                } while (query.isLast());
            } else
                Console.L("Error with topicId = "+topicId+" and availability = 1 in TheoryAvailability");
            query.close();
        } else
            Console.L("Error with topic = '"+topic+"' in TheoryTopics");
        return arrayList;
    }
    /*public static int GetPracticeTask(Context context,int number,int Id) {
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
    }*/
    public static void ChangePoints(Context context, long difference) {
        SQLiteDatabase database = context.getApplicationContext().openOrCreateDatabase(DATABASE, MODE_PRIVATE, null);
        Cursor query = database.rawQuery("SELECT value FROM Currencies WHERE id = 0",null);
        long points = -1;
        if (query.moveToFirst()) {
            if (query.isFirst() && query.isLast()) {
                points = query.getLong(0);
            }
        } if (points != -1)
            database.execSQL("UPDATE Currencies SET value = "+(points+difference)+" WHERE id = 0;");
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

//database.execSQL("CREATE TABLE IF NOT EXISTS PracticeSolutions (number INT, taskId INT, solutions INT, PRIMARY KEY (number, taskId))");
        /*for (int j = 0;j < Practice.TaskId.length;j++) {
            for (int k : Practice.TaskId[j]) {
                database.execSQL("INSERT OR IGNORE INTO PracticeSolutions VALUES ("+j+","+k+",0);");
            }
        }*/

        /*database.execSQL("CREATE TABLE IF NOT EXISTS System (id TEXT PRIMARY KEY, value TEXT)");
        database.execSQL("INSERT OR IGNORE INTO System VALUES ('AppVersion','-');");
        query = database.rawQuery("SELECT value FROM System WHERE id = 'AppVersion';", null);
        if (query.moveToFirst()) {
            if (query.isLast() && query.isFirst()) {
                if (Objects.equals(query.getString(0), AppVersion))
                    dataBaseDefined = true;
                else
                    database.execSQL("UPDATE System SET AppVersion = '"+AppVersion+"' WHERE id = 'AppVersion'");
            }
        }
        query.close();
        if (dataBaseDefined) return;*/