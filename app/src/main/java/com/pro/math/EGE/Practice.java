package com.pro.math.EGE;

import java.util.ArrayList;
import java.util.TreeMap;

public class Practice {
    public static final byte TASKS = 19;
    public static ArrayList<Integer>[] TaskText = new ArrayList[TASKS];
    public static ArrayList<String>[] TaskAnswer = new ArrayList[TASKS];
    public static ArrayList<String>[] TaskSolution = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TaskReward = new ArrayList[TASKS];
    public static ArrayList<Integer>[] TaskImage = new ArrayList[TASKS];
    private static void AddTask(int Number,int Text,String Answer,String Solution,int Reward) {
        TaskText[Number].add(Text);
        TaskAnswer[Number].add(Answer);
        TaskSolution[Number].add(Solution);
        TaskReward[Number].add(Reward);
    }
    private static void AddTask(int Number,int Text,String Answer,String Solution,int Reward,int Image) {
        AddTask(Number,Text,Answer,Solution,Reward);
        TaskImage[Number].add(Image);
    }
    static {
        for (int i = 0;i < TASKS;i++) {
            TaskText[i] = new ArrayList<>();
            TaskAnswer[i] = new ArrayList<>();
            TaskSolution[i] = new ArrayList<>();
            TaskReward[i] = new ArrayList<>();
            TaskImage[i] = new ArrayList<>();
        }

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

        AddTask(3,R.string.Task_3_1,"21","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1841",50);
        AddTask(3,R.string.Task_3_2,"12","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1993",50);
        AddTask(3,R.string.Task_3_3,"27","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1486",50);
        AddTask(3,R.string.Task_3_4,"9","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=1769",50);
        AddTask(3,R.string.Task_3_5,"75","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2230",50);
        AddTask(3,R.string.Task_3_6,"23,5","https://www.youtube.com/watch?v=KQUnNOMqJ2w",50);
        AddTask(3,R.string.Task_3_7,"28","https://www.youtube.com/watch?v=RT_alVyhjU8",50);

        AddTask(4,R.string.Task_4_1,"0,2","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2275",50);
        AddTask(4,R.string.Task_4_2,"0,2","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2370",50);
        AddTask(4,R.string.Task_4_3,"0,65","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2455",50);
        AddTask(4,R.string.Task_4_4,"0,98","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2528",50);
        AddTask(4,R.string.Task_4_5,"0,5","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2588",50);
        AddTask(4,R.string.Task_4_6,"0,16","https://www.youtube.com/watch?v=uQipw8zMjgI",50);
        AddTask(4,R.string.Task_4_7,"0,5","https://www.youtube.com/watch?v=50Gva8PsJto",50);

        AddTask(5,R.string.Task_5_1,"0,03","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2953",60);
        AddTask(5,R.string.Task_5_2,"0,059","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3218",60);
        AddTask(5,R.string.Task_5_3,"0,0064","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=2696",60);
        AddTask(5,R.string.Task_5_4,"0,4872","https://www.youtube.com/watch?v=48oj7Mg6bMQ",60);

        AddTask(6,R.string.Task_6_1,"-11","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3627",60);
        AddTask(6,R.string.Task_6_2,"10","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3583",60);
        AddTask(6,R.string.Task_6_3,"-1","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3681",60);
        AddTask(6,R.string.Task_6_4,"4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3862",60);
        AddTask(6,R.string.Task_6_5,"10","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3895",60);
        AddTask(6,R.string.Task_6_6,"0,2","https://www.youtube.com/watch?v=OEz63xGW92k",60);
        AddTask(6,R.string.Task_6_7,"-11","https://www.youtube.com/watch?v=okbFQeLR6Wc",60);
        AddTask(6,R.string.Task_6_8,"-124","https://www.youtube.com/watch?v=9noT5fwEhOo",60);

        AddTask(7,R.string.Task_7_1,"196","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4233",70);
        AddTask(7,R.string.Task_7_2,"-0,5","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=3975",70);
        AddTask(7,R.string.Task_7_3,"4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4095",70);
        AddTask(7,R.string.Task_7_4,"4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4320",70);
        AddTask(7,R.string.Task_7_5,"-16","https://www.youtube.com/watch?v=4gsDCgOfg5Y",70);
        AddTask(7,R.string.Task_7_6,"-136","https://www.youtube.com/watch?v=OOgBbTh_8yU",70);
        AddTask(7,R.string.Task_7_7,"5","https://www.youtube.com/watch?v=taIKNh3qKh0",70);
        AddTask(7,R.string.Task_7_8,"0","https://www.youtube.com/watch?v=JoKtMsO_Itw",70);
        AddTask(7,R.string.Task_7_9,"10","https://www.youtube.com/watch?v=nwglD_YEndA",70);

        AddTask(8,R.string.Task_8_1,"-2","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4858",70,R.drawable.task_8_1);
        AddTask(8,R.string.Task_8_2,"4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4940",70,R.drawable.task_8_2);
        AddTask(8,R.string.Task_8_3,"-1","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=4768",70,R.drawable.task_8_3);
        AddTask(8,R.string.Task_8_4,"-3","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=5037",70,R.drawable.task_8_4);

        AddTask(9,R.string.Task_9_1,"45","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=5368",80);
        AddTask(9,R.string.Task_9_2,"301","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=5533",80);
        AddTask(9,R.string.Task_9_3,"4,4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=5179",80);
        AddTask(9,R.string.Task_9_4,"5500","https://www.youtube.com/watch?v=JaEvg2vQmqw",80);
        AddTask(9,R.string.Task_9_5,"5","https://www.youtube.com/watch?v=p__MIao9qFM",80);

        AddTask(10,R.string.Task_10_1,"120","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=6138",80);
        AddTask(10,R.string.Task_10_2,"10","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=6360",80);
        AddTask(10,R.string.Task_10_3,"15","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=5647",80);
        AddTask(10,R.string.Task_10_4,"70","https://www.youtube.com/watch?v=Jihh3QdpE5E",80);
        AddTask(10,R.string.Task_10_5,"225","https://www.youtube.com/watch?v=5-hwlQAN5no",80);
        AddTask(10,R.string.Task_10_6,"8","https://www.youtube.com/watch?v=HjMhUh-sso8",80);
        AddTask(10,R.string.Task_10_7,"18","https://www.youtube.com/watch?v=k5xGa_0ASTI",80);
        AddTask(10,R.string.Task_10_8,"30","https://www.youtube.com/watch?v=BD2rKkvfzE0",80);
        AddTask(10,R.string.Task_10_9,"33,6","https://www.youtube.com/watch?v=BD2rKkvfzE0&t=379",80);
        AddTask(10,R.string.Task_10_10,"35","https://www.youtube.com/watch?v=TWiqE6YAiP0",80);
        AddTask(10,R.string.Task_10_11,"16","https://www.youtube.com/watch?v=AQZ8MqzDJvM",80);

        AddTask(11,R.string.Task_11_1,"6","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=6710",90,R.drawable.task_11_1);
        AddTask(11,R.string.Task_11_2,"8","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=7002",90,R.drawable.task_11_2);
        AddTask(11,R.string.Task_11_3,"16","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=6632",90,R.drawable.task_11_3);

        AddTask(12,R.string.Task_12_1,"-5","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=7252",90);
        AddTask(12,R.string.Task_12_2,"4","https://www.youtube.com/watch?v=XWRMLZ-c3mY&t=7379",90);
        AddTask(12,R.string.Task_12_3,"11","https://www.youtube.com/watch?v=EMswyNwDClM",90);
        AddTask(12,R.string.Task_12_4,"7","https://www.youtube.com/watch?v=kCgMamOd3kI",90);
    }
    private static int previousTask = -1;
    private static int previousNumber = -1;
    private static int Text;
    private static String Answer;
    private static String Solution;
    private static int Reward;
    private static int Image;
    public static boolean Setup(ArrayList<Integer> Numbers) {
        int Number = Numbers.get((int)(Numbers.size()*Math.random()));
        int RandomTask = (int) (Math.random() * TaskText[Number].size());
        while (previousNumber == Number && RandomTask == previousTask) {
            RandomTask = (int) (Math.random() * TaskText[Number].size());
        }
        previousTask = RandomTask;
        previousNumber = Number;

        Text = TaskText[Number].get(RandomTask);
        Answer = TaskAnswer[Number].get(RandomTask);
        Solution = TaskSolution[Number].get(RandomTask);
        Reward = TaskReward[Number].get(RandomTask);
        boolean image = !TaskImage[Number].isEmpty();
        if (image)
            Image = TaskImage[Number].get(RandomTask);
        return image;
    }
    public static int GetText() {
        return Text;
    }
    public static String GetAnswer() {
        return Answer;
    }
    public static String GetSolution() {
        return Solution;
    }
    public static int GetReward() {
        return Reward;
    }
    public static int GetImage() {
        return Image;
    }
}
