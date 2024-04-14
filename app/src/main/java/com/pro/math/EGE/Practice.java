package com.pro.math.EGE;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

public class Practice {
    private static final byte TASKS = 19;
    private static boolean Setup = false;
    static Resources resources;
    public static ArrayList<Integer>[] TasksTexts = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksAnswers = new ArrayList[TASKS];
    public static ArrayList<String>[] TasksSolutions = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TasksRewards = new ArrayList[TASKS];
    private static void AddTask(int Number,int Text,String Answer,String Solution,int Reward) {
        TasksTexts[Number].add(Text);
        TasksAnswers[Number].add(Answer);
        TasksSolutions[Number].add(Solution);
        TasksRewards[Number].add(Reward);
    }
    static {
        for (int i = 0;i < TASKS;i++) {
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
        AddTask(1,R.string.Task_1_1,"9","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=848",40);
        AddTask(1,R.string.Task_1_2,"134","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=516",40);
        AddTask(1,R.string.Task_1_3,"29","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=692",40);
        AddTask(1,R.string.Task_1_4,"16","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1023",40);
        AddTask(1,R.string.Task_1_5,"65","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1241",40);
        AddTask(1,R.string.Task_1_6,"8","https://www.youtube.com/watch?v=95SVJcvZbUQ",40);
        AddTask(1,R.string.Task_1_7,"2","https://www.youtube.com/watch?v=4Ob4-F3pIeU",40);

        AddTask(2,R.string.Task_2_1,"10","https://www.youtube.com/watch?v=wKDv6LWJGIk&t=894",40);
        AddTask(2,R.string.Task_2_2,"10","https://www.youtube.com/watch?v=fQaW-WtuNEs&t=1571",40);
        AddTask(2,R.string.Task_2_3,"0,96","https://www.youtube.com/watch?v=fQaW-WtuNEs&t=2013",40);

        AddTask(1,R.string.Task_3_1,"21","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1841",50);
        AddTask(1,R.string.Task_3_2,"12","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1993",50);
        AddTask(1,R.string.Task_3_3,"27","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1486",50);
        AddTask(1,R.string.Task_3_4,"9","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1769",50);
        AddTask(1,R.string.Task_3_5,"75","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2230",50);
        AddTask(1,R.string.Task_3_6,"23,5","https://www.youtube.com/watch?v=KQUnNOMqJ2w",50);
        AddTask(1,R.string.Task_3_7,"28","https://www.youtube.com/watch?v=RT_alVyhjU8",50);


        resources = null;
    }
}
