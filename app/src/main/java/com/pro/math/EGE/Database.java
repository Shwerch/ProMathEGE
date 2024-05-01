package com.pro.math.EGE;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pro.math.EGE.Products.AbstractProduct;
import com.pro.math.EGE.Products.Product;
import com.pro.math.EGE.Products.SubTopic;

import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    private static SQLiteDatabase database = null;
    static class CurrencyNames {
        private static final String Table = CurrencyValues.class.getSimpleName();
        static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (currencyId INTEGER PRIMARY KEY AUTOINCREMENT, defaultValue LONG), name TEXT NOT NULL UNIQUE");
            database.execSQL("INSERT OR IGNORE INTO "+Table+" (defaultValue, name) VALUES (0, 'Points')");
        }
        static int Get(String name) {
            final Cursor cursor = database.rawQuery("SELECT currencyId FROM "+Table+" WHERE name = '"+name+"'",null);
            cursor.moveToFirst();
            final int value = cursor.getInt(0);
            cursor.close();
            return value;
        }
        static String Get(int currencyId) {
            final Cursor cursor = database.rawQuery("SELECT name FROM "+Table+" WHERE currencyId = "+currencyId,null);
            cursor.moveToFirst();
            final String name = cursor.getString(0);
            cursor.close();
            return name;
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class CurrencyValues {
        private static final String Table = CurrencyValues.class.getSimpleName();
        static void Create() {
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (currencyId INTEGER PRIMARY KEY, value LONG)");
            Cursor cursor = CurrencyNames.Select();
            cursor.moveToFirst();
            do {
                database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+cursor.getInt(0)+", "+cursor.getInt(1)+")");
            } while (cursor.moveToNext());
            cursor.close();
        }
        static void Update(int currencyId,long value) {
            database.execSQL("UPDATE "+Table+" SET value = "+value+" WHERE currencyId = "+currencyId);
        }
        static long Get(int currencyId) {
            final Cursor cursor = database.rawQuery("SELECT value FROM "+Table+" WHERE currencyId = "+currencyId,null);
            cursor.moveToFirst();
            final long value = cursor.getLong(0);
            cursor.close();
            return value;
        }
    }
    static class TheoryTopics {
        private static final String Table = TheoryTopics.class.getSimpleName();
        static void Create(Context context) {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER PRIMARY KEY AUTOINCREMENT, topic TEXT NOT NULL UNIQUE, topicText TEXT NOT NULL UNIQUE)");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                database.execSQL("INSERT OR IGNORE INTO "+Table+" (topic, topicText) VALUES ('"+TopicsAttributes[i]+"', '"+TopicsAttributes[i+1]+"')");
            }
        }
        static int Get(String topic) {
            final Cursor cursor = database.rawQuery("SELECT topicId FROM "+Table+" WHERE topic = '"+topic+"'",null);
            cursor.moveToLast();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        static String[] Get(int topicId) {
            final Cursor cursor = database.rawQuery("SELECT topic, topicText FROM "+Table+" WHERE topicId = "+topicId,null);
            cursor.moveToLast();
            final String[] topicInfo = new String[] {cursor.getString(0),cursor.getString(1)};
            cursor.close();
            return topicInfo;
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class TheorySubTopics {
        private static final String Table = TheorySubTopics.class.getSimpleName();
        static void Create(Context context) {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (subTopicId INTEGER PRIMARY KEY AUTOINCREMENT, topicId INTEGER, subTopic TEXT NOT NULL UNIQUE)");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, TopicsAttributes[i]);
                for (String subTopic : SubTopicsAttributes) {
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" (topicId, subTopic) VALUES ("+TheoryTopics.Get(TopicsAttributes[i])+", '"+subTopic+"')");
                }
            }
        }
        static int Get(int topicId, String subTopic) {
            final Cursor cursor = database.rawQuery("SELECT subTopicId FROM "+Table+" WHERE topicId = "+topicId+" AND subTopic = '"+subTopic+"'",null);
            cursor.moveToLast();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        static String Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT topic FROM "+Table+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId,null);
            cursor.moveToLast();
            final String topic = cursor.getString(0);
            cursor.close();
            return topic;
        }
        static Cursor Select(int topicId) {
            return database.rawQuery("SELECT * FROM "+Table+" WHERE topicId = "+topicId,null);
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class TheoryAttributes {
        private static final String Table = TheoryAttributes.class.getSimpleName();
        static void Create(Context context) {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER, subTopicId INTEGER, availability BIT, cost INTEGER, tasksCount INTEGER, PRIMARY KEY (topicId, subTopicId))");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (String topic : TopicsAttributes) {
                final int topicId = TheoryTopics.Get(topic);
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, topic);
                for (String subTopic : SubTopicsAttributes) {
                    final int tasksCount = Sources.GetStringArray(resources,topic+" "+subTopic).length;
                    final int subTopicId = TheorySubTopics.Get(topicId,subTopic);
                    final int cost = Sources.GetInteger(resources, topic+" "+subTopic+" cost");
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+topicId+", "+subTopicId+", "+(cost == 0 ? 1 : 0)+", "+cost+", "+tasksCount+")");
                }
            }
        }
        static void Update(int topicId, int subTopicId, boolean availability) {
            database.execSQL("UPDATE "+Table+" SET availability = "+(availability ? 1 : 0)+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId);
        }
        static SubTopic Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT availability, cost, tasksCount FROM "+Table+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId,null);
            cursor.moveToLast();
            SubTopic subTopic = new SubTopic(topicId,subTopicId,cursor.getInt(0) == 1,cursor.getInt(1),cursor.getInt(2));
            cursor.close();
            return subTopic;
        }
        static Cursor Select(int topicId) {
            return database.rawQuery("SELECT * FROM "+Table+" WHERE topicId = "+topicId,null);
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class TheoryTasks {
        private static final String Table = TheoryAttributes.class.getSimpleName();
        static void Create(Context context) {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER, subTopicId INTEGER, task TEXT NOT NULL UNIQUE, PRIMARY KEY (topicId, subTopicId, task))");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (String topic : TopicsAttributes) {
                final int topicId = TheoryTopics.Get(topic);
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, topic);
                for (String subTopic : SubTopicsAttributes) {
                    final int subTopicId = TheorySubTopics.Get(topicId,subTopic);
                    String[] SubTopicTasks = Sources.GetStringArray(resources, topic+" "+subTopic);
                    for (String task : SubTopicTasks) {
                        if (task.split(" = ").length < 2)
                            throw new RuntimeException("Incorrect task text! There should be equal statements separated by ' = '. Incorrect task: '"+task+"', '"+ Arrays.toString(task.split(" = ")) +"'");
                        database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+topicId+", "+subTopicId+", '"+task+"')");
                    }
                }
            }
        }
        static String[][] Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT task FROM "+Table+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId,null);
            cursor.moveToFirst();
            String[][] tasks = new String[cursor.getCount()][];
            for (int i = 0;i < cursor.getCount();i++) {
                tasks[i] = cursor.getString(0).split(" = ");
                cursor.moveToNext();
            }
            cursor.close();
            return tasks;
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class PracticeTasks {
        private static final String Table = TheoryAttributes.class.getSimpleName();
        static void Create(Context context) {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (tasksGroupNumber INTEGER, taskIndex INTEGER, answer TEXT, solution TEXT, image TEXT , PRIMARY KEY (tasksGroupNumber,taskIndex))");
            for (int i = 1;i <= resources.getInteger(R.integer.Tasks);i++) {
                String[] tasks = Sources.GetStringArray(context,"Task "+i);
                final int step;
                if (tasks[3].startsWith("task_"))
                    step = 4;
                else
                    step = 3;
                PracticeTasksAttributes.Insert(i,tasks.length,Sources.GetInteger(context,"Task "+i+" reward"));
                for (int k = 0;k < tasks.length;k += step) {
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+i+", "+(k/step)+", "+tasks[k+1]+", '"+tasks[k+2]+"', "+(step == 4 ? "'"+tasks[k+3]+"'" : "NULL")+")");
                }
            }
        }
        static String[] Get(int tasksGroupNumber,int taskIndex) {
            Cursor cursor = database.rawQuery("SELECT answer, solution, image FROM "+Table+" WHERE tasksGroupNumber = "+tasksGroupNumber+" AND taskIndex = "+taskIndex,null);
            cursor.moveToFirst();
            String[] taskInfo = new String[] {cursor.getString(0),cursor.getString(1),cursor.isNull(2) ? null : cursor.getString(2)};
            cursor.close();
            return taskInfo;
        }
        static boolean Image(int tasksGroupNumber) {
            Cursor cursor = database.rawQuery("SELECT image FROM "+Table+" WHERE tasksGroupNumber = "+tasksGroupNumber,null);
            cursor.moveToFirst();
            boolean image = !cursor.getString(0).isEmpty();
            cursor.close();
            return image;
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static class PracticeTasksAttributes {
        private static final String Table = TheoryAttributes.class.getSimpleName();
        static void Create() {
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (tasksGroupNumber INTEGER PRIMARY KEY, tasksCount INTEGER, reward INTEGER)");
        }
        static void Insert(int tasksGroupNumber,int tasksCount,int reward) {
            database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+tasksGroupNumber+", "+tasksCount+", "+reward+")");
        }
        static int[] Get(int tasksGroupNumber) {
            Cursor cursor = database.rawQuery("SELECT tasksCount, reward  FROM "+Table+" WHERE tasksGroupNumber = "+tasksGroupNumber,null);
            cursor.moveToFirst();
            int[] task = new int[] {cursor.getInt(0),cursor.getInt(1)};
            cursor.close();
            return task;
        }
        static Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    private static final String DATABASE = "Storage";
    static void DefineDataBase(Context context) {
        database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);

        ResetShop();

        CurrencyNames.Create();
        CurrencyValues.Create();

        TheoryTopics.Create(context);
        TheorySubTopics.Create(context);
        TheoryAttributes.Create(context);
        TheoryTasks.Create(context);

        PracticeTasksAttributes.Create();
        PracticeTasks.Create(context);

        Cursor cursor = TheoryAttributes.Select();
        cursor.moveToFirst();
        do {
            if (cursor.getInt(2) == 0)
                AddToShop(new Product(cursor.getInt(0),cursor.getInt(1)));
        } while (cursor.moveToNext());
        cursor.close();
    }
    static ArrayList<AbstractProduct> ShopAttributes = new ArrayList<>();
    static void ResetShop() {
        ShopAttributes.clear();
    }
    static void AddToShop(AbstractProduct product) {
        if (!ContainsShop(product))
            ShopAttributes.add(product);
    }
    static void RemoveFromShop(AbstractProduct product) {
        ShopAttributes.remove(product);
    }
    static boolean ContainsShop(AbstractProduct product) {
        return ShopAttributes.contains(product);
    }
    static ArrayList<String> GetShop(Context context) {
        ArrayList<String> arrayList = new ArrayList<>(ShopAttributes.size());
        for (int i = 0;i < ShopAttributes.size();i++) {
            AbstractProduct product = ShopAttributes.get(i);
            arrayList.set(i,Sources.GetStringArray(context.getResources(),TheoryTopics.Get(product.topicId)[0])[product.subTopicId]
                    +" - "+Sources.GetRightPointsEnd(context,TheoryAttributes.Get(product.topicId, product.subTopicId).cost)+
                    " ("+context.getResources().getStringArray(R.array.TopicsAttributes)[product.topicId]+")");
        }
        return arrayList;
    }
    static boolean BuySubTopic(AbstractProduct product) {
        SubTopic subTopic = TheoryAttributes.Get(product.topicId, product.subTopicId);
        if (subTopic.cost >= GetPoints() && !subTopic.availability) {
            ChangePoints(subTopic.cost);
            TheoryAttributes.Update(subTopic.topicId,subTopic.subTopicId,true);
            RemoveFromShop(subTopic);
            return true;
        }
        return false;
    }
    static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        database = null;
        DefineDataBase(context);
    }
    static ArrayList<Integer> GetAvailableSubTopics(int topicId) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Cursor cursor = TheoryAttributes.Select(topicId);
        cursor.moveToFirst();
        do {
            if (cursor.getInt(2) == 1) {
                arrayList.add(cursor.getInt(1));
            }
        } while (cursor.move(1));
        cursor.close();
        return arrayList;
    }
    static void ChangePoints(long difference) {
        CurrencyValues.Update(1, CurrencyValues.Get(1) + difference);
    }
    static long GetPoints() {
        return CurrencyValues.Get(1);
    }
}



    /*static void OpenOrCreateDatabase(Context context) {
        boolean create = false;
        if (database == null)
            create = true;
        else if (!database.isOpen()) {
            create = true;
        } if (create)
            database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);
    }
    static void CloseDatabase() {
        boolean close = true;
        if (database == null)
            close = false;
        else if (!database.isOpen()) {
            close = false;
        } if (close)
            database.close();
    }*/

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

//database.execSQL("CREATE TABLE IF NOT EXISTS PracticeSolutions (number INTEGER, taskId INTEGER, solutions INTEGER, PRIMARY KEY (number, taskId))");
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