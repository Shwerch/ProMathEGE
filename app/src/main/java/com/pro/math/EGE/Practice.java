package com.pro.math.EGE;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

public class Practice {
    private static final byte TASKS = 19;
    private static boolean Setup = false;
    static Resources resources;
    public static ArrayList<Integer>[] TasksYear = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TasksWave = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TasksTexts = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksAnswers = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksSolutions = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TasksRewards = new ArrayList[TASKS];
    private static void AddTask(int Number,int Year,int Wave,int Text,String Answer,String Solution,int Reward) {
        TasksYear[Number].add(Year);
        TasksWave[Number].add(Wave);
        TasksTexts[Number].add(Text);
        TasksAnswers[Number].add(Answer);
        TasksSolutions[Number].add(Solution);
        TasksRewards[Number].add(Reward);
    }
    static {
        for (int i = 0;i < TASKS;i++) {
            TasksYear[i] = new ArrayList<>();
            TasksWave[i] = new ArrayList<>();
            TasksTexts[i] = new ArrayList<>();
            TasksAnswers[i] = new ArrayList<>();
            TasksSolutions[i] = new ArrayList<>();
            TasksRewards[i] = new ArrayList<>();
        }
    }
    public static void Setup(Context context) {
        if (Setup)
            return;
        Setup = true;
        resources = context.getResources();
        AddTask(1,2022,R.string.EarlyWave,R.string.Task_1_1,"9","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=848",40);
        AddTask(1,2022,R.string.MainWave,R.string.Task_1_2,"134","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=516",40);
        AddTask(1,2022,R.string.MainWave,R.string.Task_1_3,"29","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=692",40);
        resources = null;
    }
}
