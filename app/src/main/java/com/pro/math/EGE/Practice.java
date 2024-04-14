package com.pro.math.EGE;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

public class Practice {
    private static final byte TASKS = 19;
    private static boolean Setup = false;
    public static ArrayList<String>[] TasksAnnotations = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksTexts = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksAnswers = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksSolutions = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TasksRewards = new ArrayList[TASKS];
    private static void AddTask(int TaskNumber,String Annotation,String Test,String Answer,String Solution,int Reward) {
        TasksAnnotations[TaskNumber].add(Annotation);
        TasksTexts[TaskNumber].add(Test);
        TasksAnswers[TaskNumber].add(Answer);
        TasksSolutions[TaskNumber].add(Solution);
        TasksRewards[TaskNumber].add(Reward);
    }
    public static void Setup(Context context) {
        if (Setup)
            return;
        Setup = true;
        Resources resources = context.getResources();
        AddTask(1,resources.getString(R.string.EGE)+" 2022. "+resources.getString(R.string.EarlyWave),resources.getString(R.string.Task_1_1),"9","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=848",40);
        AddTask(1,resources.getString(R.string.EGE)+" 2022. "+resources.getString(R.string.MainWave),resources.getString(R.string.Task_1_2),"134","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=516",40);
        AddTask(1,resources.getString(R.string.EGE)+" 2022. "+resources.getString(R.string.MainWave),resources.getString(R.string.Task_1_3),"29","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=692",40);
    }
}
