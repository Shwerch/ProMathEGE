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
    private static final String DATABASE = "Storage";
    private static SQLiteDatabase database;
    public static CurrencyNames currencyNames;
    public static class CurrencyNames {
        private final String Table = this.getClass().getSimpleName();
        private CurrencyNames() {
            if (currencyNames != this)
                currencyNames = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (currencyId INTEGER PRIMARY KEY AUTOINCREMENT, defaultValue LONG, name TEXT NOT NULL UNIQUE)");
            database.execSQL("INSERT OR IGNORE INTO "+Table+" (defaultValue, name) VALUES (0, 'Points')");
        }
        public int Get(String name) {
            final Cursor cursor = database.rawQuery("SELECT currencyId FROM "+Table+" WHERE name = '"+name+"'",null);
            cursor.moveToFirst();
            final int value = cursor.getInt(0);
            cursor.close();
            return value;
        }
        public String Get(int currencyId) {
            final Cursor cursor = database.rawQuery("SELECT name FROM "+Table+" WHERE currencyId = "+currencyId,null);
            cursor.moveToFirst();
            final String name = cursor.getString(0);
            cursor.close();
            return name;
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static CurrencyValues currencyValues;
    public static class CurrencyValues {
        private final String Table = this.getClass().getSimpleName();
        private CurrencyValues() {
            if (currencyValues != this)
                currencyValues = this;
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (currencyId INTEGER PRIMARY KEY, value LONG)");
            Cursor cursor = currencyNames.Select();
            cursor.moveToFirst();
            do {
                database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+cursor.getInt(0)+", "+cursor.getInt(1)+")");
            } while (cursor.moveToNext());
            cursor.close();
        }
        public void Update(String currencyName,long value) {
            database.execSQL("UPDATE "+Table+" SET value = "+value+" WHERE currencyId = "+ currencyNames.Get(currencyName));
        }
        public long Get(String currencyName) {
            final Cursor cursor = database.rawQuery("SELECT value FROM "+Table+" WHERE currencyId = "+ currencyNames.Get(currencyName),null);
            cursor.moveToFirst();
            final long value = cursor.getLong(0);
            cursor.close();
            return value;
        }
    }
    public static TheoryTopics theoryTopics;
    public static class TheoryTopics {
        private final String Table = this.getClass().getSimpleName();
        private TheoryTopics(Context context) {
            if (theoryTopics != this)
                theoryTopics = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER PRIMARY KEY AUTOINCREMENT, topic TEXT NOT NULL UNIQUE, topicText TEXT NOT NULL UNIQUE, testAvailable BIT)");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                final boolean testAvailable = Sources.GetStringArray(resources,TopicsAttributes[i]).length != 0;
                database.execSQL("INSERT OR IGNORE INTO "+Table+" (topic, topicText, testAvailable) VALUES ('"+TopicsAttributes[i]+"', '"+TopicsAttributes[i+1]+"',"+(testAvailable ? 1 : 0)+")");
            }
        }
        public int Get(String topic) {
            final Cursor cursor = database.rawQuery("SELECT topicId FROM "+Table+" WHERE topic = '"+topic+"'",null);
            cursor.moveToFirst();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        public String[] Get(int topicId) {
            final Cursor cursor = database.rawQuery("SELECT topic, topicText FROM "+Table+" WHERE topicId = "+topicId,null);
            cursor.moveToFirst();
            final String[] topicInfo = new String[] {cursor.getString(0),cursor.getString(1)};
            cursor.close();
            return topicInfo;
        }
        public boolean TestAvailable(int topicId) {
            final Cursor cursor = database.rawQuery("SELECT testAvailable FROM "+Table+" WHERE topicId = "+topicId,null);
            cursor.moveToFirst();
            final boolean testAvailable = cursor.getInt(0) == 1;
            cursor.close();
            return testAvailable;
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static TheorySubTopics theorySubTopics;
    public static class TheorySubTopics {
        private final String Table = this.getClass().getSimpleName();
        private TheorySubTopics(Context context) {
            if (theorySubTopics != this)
                theorySubTopics = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (subTopicId INTEGER PRIMARY KEY AUTOINCREMENT, topicId INTEGER, subTopic TEXT NOT NULL UNIQUE)");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, TopicsAttributes[i]);
                for (String subTopic : SubTopicsAttributes) {
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" (topicId, subTopic) VALUES ("+ theoryTopics.Get(TopicsAttributes[i])+", '"+subTopic+"')");
                }
            }
        }
        public int Get(int topicId, String subTopic) {
            final Cursor cursor = database.rawQuery("SELECT subTopicId FROM "+Table+" WHERE topicId = "+topicId+" AND subTopic = '"+subTopic+"'",null);
            cursor.moveToFirst();
            final int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        public String Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT topic FROM "+Table+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId,null);
            cursor.moveToFirst();
            final String topic = cursor.getString(0);
            cursor.close();
            return topic;
        }
        public Cursor Select(int topicId) {
            return database.rawQuery("SELECT * FROM "+Table+" WHERE topicId = "+topicId,null);
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static TheoryAttributes theoryAttributes;
    public static class TheoryAttributes {
        private final String Table = this.getClass().getSimpleName();
        private TheoryAttributes(Context context) {
            if (theoryAttributes != this)
                theoryAttributes = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER, subTopicId INTEGER, availability BIT, cost INTEGER, tasksCount INTEGER, reward INTEGER, PRIMARY KEY (topicId, subTopicId))");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                String topic = TopicsAttributes[i];
                final int topicId = theoryTopics.Get(topic);
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, topic);
                for (String subTopic : SubTopicsAttributes) {
                    final int tasksCount = Sources.GetStringArray(resources,topic+" "+subTopic).length;
                    final int subTopicId = theorySubTopics.Get(topicId,subTopic);
                    final int cost = Sources.GetInteger(resources, topic+" "+subTopic+" cost");
                    final int reward = Sources.GetInteger(resources, topic+" reward");
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+topicId+", "+subTopicId+", "+(cost == 0 ? 1 : 0)+", "+cost+", "+tasksCount+", "+reward+")");
                }
            }
        }
        public void Update(int topicId, int subTopicId, boolean availability) {
            database.execSQL("UPDATE "+Table+" SET availability = "+(availability ? 1 : 0)+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId);
        }
        public SubTopic Get(int topicId, int subTopicId) {
            final Cursor cursor = database.rawQuery("SELECT availability, cost, tasksCount, reward FROM "+Table+" WHERE topicId = "+topicId+" AND subTopicId = "+subTopicId,null);
            cursor.moveToFirst();
            SubTopic subTopic = new SubTopic(topicId,subTopicId,cursor.getInt(0) == 1,cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
            cursor.close();
            return subTopic;
        }
        public Cursor Select(int topicId) {
            return database.rawQuery("SELECT * FROM "+Table+" WHERE topicId = "+topicId,null);
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static TheoryTasks theoryTasks;
    public static class TheoryTasks {
        private final String Table = this.getClass().getSimpleName();
        private TheoryTasks(Context context) {
            if (theoryTasks != this)
                theoryTasks = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (topicId INTEGER, subTopicId INTEGER, task TEXT NOT NULL UNIQUE, PRIMARY KEY (topicId, subTopicId, task))");
            String[] TopicsAttributes = resources.getStringArray(R.array.TopicsAttributes);
            for (int i = 0;i < TopicsAttributes.length;i += 2) {
                String topic = TopicsAttributes[i];
                final int topicId = theoryTopics.Get(topic);
                String[] SubTopicsAttributes = Sources.GetStringArray(resources, topic);
                for (String subTopic : SubTopicsAttributes) {
                    final int subTopicId = theorySubTopics.Get(topicId,subTopic);
                    String[] SubTopicTasks = Sources.GetStringArray(resources, topic+" "+subTopic);
                    for (String task : SubTopicTasks) {
                        if (task.split(" = ").length < 2)
                            throw new RuntimeException("Incorrect task text! There should be equal statements separated by ' = '. Incorrect task: '"+task+"', '"+ Arrays.toString(task.split(" = ")) +"'");
                        database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+topicId+", "+subTopicId+", '"+task+"')");
                    }
                }
            }
        }
        public String[][] Get(int topicId, int subTopicId) {
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
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static PracticeTasks practiceTasks;
    public static class PracticeTasks {
        private final String Table = this.getClass().getSimpleName();
        private PracticeTasks(Context context) {
            if (practiceTasks != this)
                practiceTasks = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            Resources resources = Sources.GetLocaleResources(context);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (tasksGroupNumber INTEGER, taskIndex INTEGER, answer TEXT, solution TEXT, image TEXT, PRIMARY KEY (tasksGroupNumber,taskIndex))");
            for (int i = 1;i <= resources.getInteger(R.integer.Tasks);i++) {
                String[] tasks = Sources.GetStringArray(context,"Task "+i);
                final int step;
                if (tasks[3].startsWith("task_"))
                    step = 4;
                else
                    step = 3;
                practiceTasksAttributes.Insert(i,tasks.length / step,Sources.GetInteger(context,"Task "+i+" reward"));
                for (int k = 0;k < tasks.length;k += step) {
                    database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+i+", "+(k/step)+", '"+tasks[k+1]+"', '"+tasks[k+2]+"', "+(step == 4 ? "'"+tasks[k+3]+"'" : "NULL")+")");
                }
            }
        }
        public String[] Get(int tasksGroupNumber,int taskIndex) {
            Cursor cursor = database.rawQuery("SELECT answer, solution, image FROM "+Table+" WHERE tasksGroupNumber = "+tasksGroupNumber+" AND taskIndex = "+taskIndex,null);
            cursor.moveToFirst();
            String[] taskInfo = new String[] {cursor.getString(0),cursor.getString(1),cursor.isNull(2) ? null : cursor.getString(2)};
            cursor.close();
            return taskInfo;
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    public static PracticeTasksAttributes practiceTasksAttributes;
    public static class PracticeTasksAttributes {
        private final String Table = this.getClass().getSimpleName();
        private PracticeTasksAttributes() {
            if (practiceTasksAttributes != this)
                practiceTasksAttributes = this;
            database.execSQL("DROP TABLE IF EXISTS "+Table);
            database.execSQL("CREATE TABLE IF NOT EXISTS "+Table+" (tasksGroupNumber INTEGER PRIMARY KEY, tasksCount INTEGER, reward INTEGER)");
        }
        public void Insert(int tasksGroupNumber,int tasksCount,int reward) {
            database.execSQL("INSERT OR IGNORE INTO "+Table+" VALUES ("+tasksGroupNumber+", "+tasksCount+", "+reward+")");
        }
        public int[] Get(int tasksGroupNumber) {
            Cursor cursor = database.rawQuery("SELECT tasksCount, reward  FROM "+Table+" WHERE tasksGroupNumber = "+tasksGroupNumber,null);
            cursor.moveToFirst();
            int[] task = new int[] {cursor.getInt(0),cursor.getInt(1)};
            cursor.close();
            return task;
        }
        public Cursor Select() {
            return database.rawQuery("SELECT * FROM "+Table,null);
        }
    }
    static void DefineDataBase(Context context) {
        database = context.openOrCreateDatabase(DATABASE,MODE_PRIVATE,null);

        ResetShop();

        currencyNames = new CurrencyNames();
        currencyValues = new CurrencyValues();

        theoryTopics = new TheoryTopics(context);
        theorySubTopics = new TheorySubTopics(context);
        theoryAttributes = new TheoryAttributes(context);
        theoryTasks = new TheoryTasks(context);

        practiceTasksAttributes = new PracticeTasksAttributes();
        practiceTasks = new PracticeTasks(context);

        Cursor cursor = theoryAttributes.Select();
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
            arrayList.set(i,Sources.GetStringArray(context.getResources(), theoryTopics.Get(product.topicId)[0])[product.subTopicId]
                    +" - "+Sources.GetRightPointsEnd(context, theoryAttributes.Get(product.topicId, product.subTopicId).cost)+
                    " ("+context.getResources().getStringArray(R.array.TopicsAttributes)[product.topicId]+")");
        }
        return arrayList;
    }
    static boolean BuySubTopic(AbstractProduct product) {
        SubTopic subTopic = theoryAttributes.Get(product.topicId, product.subTopicId);
        if (subTopic.cost >= GetPoints() && !subTopic.availability) {
            ChangePoints(subTopic.cost);
            theoryAttributes.Update(subTopic.topicId,subTopic.subTopicId,true);
            RemoveFromShop(subTopic);
            return true;
        }
        return false;
    }
    static void ResetDataBases(Context context) {
        context.deleteDatabase(DATABASE);
        DefineDataBase(context);
    }
    static ArrayList<Integer> GetAvailableSubTopics(int topicId) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Cursor cursor = theoryAttributes.Select(topicId);
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
        currencyValues.Update("Points", currencyValues.Get("Points") + difference);
    }
    static long GetPoints() {
        return currencyValues.Get("Points");
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