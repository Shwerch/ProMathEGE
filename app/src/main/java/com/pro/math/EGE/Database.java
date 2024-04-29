package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Database {
    private static SQLiteDatabase database = null;
    private static class Currencies {
        public static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS Currencies (currencyId INT PRIMARY KEY, value LONG)");
        }
        public static void Insert(int currencyId, long value) {
            database.execSQL("INSERT OR IGNORE INTO Currencies VALUES ("+currencyId+", "+value+");");
        }
        public static void Update(int currencyId,long value) {
            database.execSQL("UPDATE Currencies SET value = "+value+" WHERE currencyId = "+currencyId+";");
        }
        public static long Get(int currencyId) {
            final Cursor cursor = database.rawQuery("SELECT value FROM Currencies WHERE currencyId = "+currencyId+";",null);
            cursor.moveToFirst();
            final long value = cursor.getLong(0);
            cursor.close();
            return value;
        }
    }
    private static class TheoryTopics {
        public static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS TheoryTopics (topicId INT PRIMARY KEY AUTOINCREMENT, topic TEXT)");
        }
        public static int Insert(String topic) {
            database.execSQL("INSERT OR IGNORE INTO TheoryTopics VALUES ('"+topic+"');");
            return TheoryTopics.Get(topic);
        }
        public static int Get(String topic) {
            final Cursor cursor = database.rawQuery("SELECT topicId FROM TheoryTopics WHERE topic = '"+topic+"';",null);
            cursor.moveToLast();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        public static String Get(int topicId) {
            final Cursor cursor = database.rawQuery("SELECT topic FROM TheoryTopics WHERE topicId = "+topicId+";",null);
            cursor.moveToLast();
            final String topic = cursor.getString(0);
            cursor.close();
            return topic;
        }
    }
    private static class TheorySubTopics {
        public static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS TheorySubTopics (subTopicId INT PRIMARY KEY AUTOINCREMENT, topicId INT, subTopic TEXT)");
        }
        public static int Insert(int topicId, String topic) {
            database.execSQL("INSERT OR IGNORE INTO TheorySubTopics VALUES ("+topicId+", '"+topic+"');");
            return TheorySubTopics.Get(topicId,topic);
        }
        public static int Get(int topicId, String topic) {
            final Cursor cursor = database.rawQuery("SELECT subTopicId FROM TheorySubTopics WHERE topicId = "+topicId+" AND topic = '"+topic+"';",null);
            cursor.moveToLast();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        public static String Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT topic FROM TheorySubTopics WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId+";",null);
            cursor.moveToLast();
            final String topic = cursor.getString(0);
            cursor.close();
            return topic;
        }
    }
    private static class TheoryAvailability {
        public static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS TheoryAvailability (topicId INT, subTopicId INT, availability BIT, PRIMARY KEY (topicId, subTopicId))");
        }
        public static boolean Insert(int topicId, int subTopicId, boolean availability) {
            database.execSQL("INSERT OR IGNORE INTO TheoryAvailability VALUES ("+topicId+", "+subTopicId+","+(availability ? 1 : 0)+");");
            return TheoryAvailability.Get(topicId,subTopicId);
        }
        public static void Update(int topicId, int subTopicId, boolean availability) {
            database.execSQL("UPDATE TheoryAvailability SET availability = "+(availability ? 1 : 0)+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId+";");
        }
        public static boolean Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT availability FROM TheoryAvailability WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId+";",null);
            cursor.moveToLast();
            final boolean availability = cursor.getInt(0) == 1;
            cursor.close();
            return availability;
        }
        public static Cursor Select(Integer topicId) {
            return database.rawQuery("SELECT * FROM TheoryAvailability WHERE topicId = "+topicId+";",null);
        }
    }
    private static void OpenOrCreateDatabase(Context context) {
        boolean create = false;
        if (database == null)
            create = true;
        else if (!database.isOpen()) {
            create = true;
        } if (create)
            database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
    }
    private static void Close(Context context) {
        boolean close = true;
        if (database == null)
            close = false;
        else if (!database.isOpen()) {
            close = false;
        } if (close)
            database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
    }
    private static final String DATABASE = "Storage";
    public static ArrayList<int[]> ShopAttributes = new ArrayList<>();
    public static void DefineDataBase(Context context) {
        OpenOrCreateDatabase(context);

        ResetShop();

        Currencies.Create();
        Currencies.Insert(1,0);

        TheoryTopics.Create();
        TheorySubTopics.Create();
        TheoryAvailability.Create();

        Resources EngRes = Sources.GetLocaleResources(context);
        String[] TopicsAttributes = EngRes.getStringArray(R.array.TopicsAttributes);
        for (int i = 0;i < TopicsAttributes.length;i += 2) {
            final int topicId = TheoryTopics.Insert(TopicsAttributes[i]);
            String[] SubTopicsAttributes = Sources.GetStringArray(EngRes,TopicsAttributes[i]);
            for (String subTopicsAttribute : SubTopicsAttributes) {
                final int subTopicId = TheorySubTopics.Insert(topicId, subTopicsAttribute);
                final boolean availability = TheoryAvailability.Insert(topicId, subTopicId, Sources.GetInteger(EngRes, TopicsAttributes[i] + " " + subTopicsAttribute + " cost") == 0);
                if (!availability)
                    AddToShop(topicId, subTopicId);
            }
        }
        Close(context);
    }
    public static void ResetShop() {
        ShopAttributes.clear();
    }
    public static void AddToShop(int topicId, int subTopicId) {
        final int[] newAttribute = new int[] {topicId,subTopicId};
        if (!ShopAttributes.contains(newAttribute))
            ShopAttributes.add(newAttribute);
    }
    public static void RemoveFromShop(int topicId, int subTopicId) {
        ShopAttributes.remove(new int[] {topicId,subTopicId});
    }
    @NonNull
    public static ArrayList<String> GetShop(Context context) {
        ArrayList<String> arrayList = new ArrayList<>(ShopAttributes.size());
        Resources EngRes = Sources.GetLocaleResources(context);
        String[] TopicsAttributes = EngRes.getStringArray(R.array.TopicsAttributes);
        for (int[] attributes : ShopAttributes) {
            String[] SubTopicsAttributes = Sources.GetStringArray(context.getResources(),TopicsAttributes[attributes[0]]);
            arrayList.add(SubTopicsAttributes[attributes[1]]+" - "
                    +Sources.GetRightPointsEnd(context, Sources.GetInteger(EngRes,TopicsAttributes[attributes[0]]+" cost"))+
                    " ("+context.getResources().getStringArray(R.array.TopicsAttributes)[attributes[0]]+")"
            );
        }
        return arrayList;
    }
    public static boolean BuySubTopic(Context context, int topicId, int subTopicId) {
        boolean success = false;
        Resources EngRes = Sources.GetLocaleResources(context);
        String topic = TheoryTopics.Get(topicId);
        String subTopic = TheorySubTopics.Get(topicId,subTopicId);
        long price = Sources.GetInteger(EngRes, topic+" "+subTopic+" cost");
        long points = GetPoints(context);
        if (points < price)
            return false;
        OpenOrCreateDatabase(context);
        boolean availability = TheoryAvailability.Get(topicId,subTopicId);
        if (!availability) {
            success = true;
            ChangePoints(context,-price);
            TheoryAvailability.Update(topicId,subTopicId,true);
            RemoveFromShop(topicId,subTopicId);
        }
        Close(context);
        return success;
    }
    public static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        database = null;
        DefineDataBase(context);
    }
    public static ArrayList<Integer> GetAvailableSubTopics(Context context, int topicId) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        OpenOrCreateDatabase(context);
        Cursor cursor = TheoryAvailability.Select(topicId);
        cursor.moveToFirst();
        do {
            if (cursor.getInt(2) == 1) {
                arrayList.add(cursor.getInt(1));
            }
        } while (cursor.move(1));
        Close(context);
        return arrayList;
    }
    public static void ChangePoints(@NonNull Context context, long difference) {
        OpenOrCreateDatabase(context);
        Currencies.Update(1,Currencies.Get(1) + difference);
        Close(context);
    }
    public static long GetPoints(@NonNull Context context) {
        OpenOrCreateDatabase(context);
        return Currencies.Get(1);
    }
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