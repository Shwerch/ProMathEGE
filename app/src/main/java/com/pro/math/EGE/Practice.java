package com.pro.math.EGE;

import java.util.ArrayList;
import java.util.TreeMap;

public class Practice {
    private static final byte TASKS = 19;
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
    static {

    }
}
