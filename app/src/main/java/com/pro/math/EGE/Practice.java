package com.pro.math.EGE;

import com.pro.math.EGE.Tasks.Task;

public class Practice {
    private static int LastTasksGroupNumber = -1;
    private static int LastTaskIndex = -1;
    public static void GetTask(int tasksGroupNumber,Task task) {
        int taskIndex;
        if (tasksGroupNumber == LastTasksGroupNumber) {
            taskIndex = (int) ((Database.practiceTasksAttributes.Get(tasksGroupNumber)[0] - 1) * Math.random());
            if (taskIndex >= LastTaskIndex)
                taskIndex++;
        } else
            taskIndex = (int) (Database.practiceTasksAttributes.Get(tasksGroupNumber)[0] * Math.random());

        LastTasksGroupNumber = tasksGroupNumber;
        LastTaskIndex = taskIndex;

        String[] taskInfo = Database.practiceTasks.Get(tasksGroupNumber,taskIndex);
        task.Change(taskInfo[0],taskInfo[1],taskInfo[2],taskInfo[3] != null ? Sources.GetImage(taskInfo[3]) : null);
        //Console.L("Text: %s\nAnswer: %s\nSolution: %s\nImage: %s",task.Text,task.Answer,task.Solution,task.Image);
    }
}

/*
    static Task GetTask(int Number,Context context) {
        if (Number != LastTaskNumber) {
            final int index = (int)(Tasks.length * Math.random());
            LastTaskNumber = Number;
            LastTaskIndex = index;
            return TaskSetup(context,Number,index);
        } else {
            final int[] indexes = new int[];
        }

    }*/

        /*double total = 0;
        for (int i = 0;i < Tasks.length;i++) {
            if (Tasks[i].Id == LastTaskIndex && Number == LastTaskNumber) continue;
            final int count = Database.GetPracticeTask(context,Number,Tasks[i].Id);
            total += (count == 0) ? 2d : (1d / count);
        }

        double total = 0;
        final int len = TaskId.get(Number).length;
        final ArrayList<Double> counts = new ArrayList<>(len);
        final ArrayList<Integer> indexes = new ArrayList<>(len);
        int count;
        for (int i = 0;i < len;i++) {
            if (i == previousTask && Number == previousNumber) continue;
            Id = TaskId.get(Number)[i];
            count = Database.GetPracticeTask(context,Number,Id);
            if (count == 0)
                counts.add(2d);
            else
                counts.add(1d / count);
            indexes.add(i);
            total += counts.get(counts.size() - 1);
        }
        double randomNumber = Math.random()*total;
        Console.L("len "+len);
        Console.L("counts "+counts);
        Console.L("indexes "+indexes);
        Console.L("total "+total);
        Console.L("randomNumber "+randomNumber);
        final int lastI = counts.size() - 1;
        for (int i = 0;i < counts.size();i++) {
            randomNumber -= counts.get(i);
            if (randomNumber <= 0 || i == lastI) {
                Console.L("i == lastI "+(i == lastI));
                Console.L("index "+indexes.get(i));
                return indexes.get(i);
            }
        }
        throw new RuntimeException();*/

    /*private static int Number;
    private static int Id;
    private static int previousTask = -1;
    private static int previousNumber = -1;
    private static String Text;
    private static String Answer;
    private static String Solution;
    private static int Reward;
    private static int Image;
    private static int RandomTask(Context context) {
        return (int) (Tasks[Number].length * Math.random());
        /*double total = 0;
        final int len = TaskId.get(Number).length;
        final ArrayList<Double> counts = new ArrayList<>(len);
        final ArrayList<Integer> indexes = new ArrayList<>(len);
        int count;
        for (int i = 0;i < len;i++) {
            if (i == previousTask && Number == previousNumber) continue;
            Id = TaskId.get(Number)[i];
            count = Database.GetPracticeTask(context,Number,Id);
            if (count == 0)
                counts.add(2d);
            else
                counts.add(1d / count);
            indexes.add(i);
            total += counts.get(counts.size() - 1);
        }
        double randomNumber = Math.random()*total;
        Console.L("len "+len);
        Console.L("counts "+counts);
        Console.L("indexes "+indexes);
        Console.L("total "+total);
        Console.L("randomNumber "+randomNumber);
        final int lastI = counts.size() - 1;
        for (int i = 0;i < counts.size();i++) {
            randomNumber -= counts.get(i);
            if (randomNumber <= 0 || i == lastI) {
                Console.L("i == lastI "+(i == lastI));
                Console.L("index "+indexes.get(i));
                return indexes.get(i);
            }
        }
        throw new RuntimeException();
    }
    /*static boolean Setup(ArrayList<Integer> Numbers,Context context) {
        Number = Numbers.get((int)(Numbers.size()*Math.random()));
        int RandomTask = RandomTask(context);
        previousTask = RandomTask;
        previousNumber = Number;

        Text = TaskText.get(Number)[RandomTask];
        Answer = TaskAnswer.get(Number)[RandomTask];
        Solution = TaskSolution.get(Number)[RandomTask];
        Reward = TaskReward.get(Number)[RandomTask];
        Id = TaskId.get(Number)[RandomTask];
        boolean image = TaskImage.get(Number).length != 0;
        Console.L(Number+" "+RandomTask+" "+Text+" "+Answer+" "+Solution+" "+Reward+" "+Id+" "+image);
        if (image)
            Image = TaskImage.get(Number)[RandomTask];
        return image;
    }*/
    /*static int GetNumber() {return Number;}
    static int GetId() {return Id;}
    static String GetText() {return Text;}
    static String GetAnswer() {
        return Answer;
    }
    static String GetSolution() {
        return Solution;
    }
    static int GetReward() {
        return Reward;
    }
    static int GetImage() {
        return Image;
    }*/

    /*static final Task[][] Tasks = new Task[TASKS][];
    /*@SuppressLint("DiscouragedApi")
    static void Setup(@NonNull Context context) {
        Resources res = context.getResources();
        String[][] ResTasks = new String[TASKS][];
        for (int i = 0;i < TASKS;i++) {
            try {
                ResTasks[i] = res.getStringArray((int) R.array.class.getField("Task_"+(i + 1)).get(null));
            } catch (Exception e) {
                ResTasks[i] = new String[] {};
                Console.L(e);
            }
        }
        for (int i = 0;i < ResTasks.length;i++) {
            final int len = ResTasks[i].length;
            final int step;
            if (ResTasks[i][3].startsWith("task"))
                step = 4;
            else
                step = 3;
            Tasks[i] = new Task[len / step];
            for (int k = 0;k < len;k += step) {
                Tasks[i][k] = new Task(ResTasks[i][k],ResTasks[i][k+1],ResTasks[i][k+2],step == 4 ? context.getResources().getIdentifier(ResTasks[i][k+3],"drawable",context.getPackageName()) : -1);
            }
        }
        /*for (int i = 0;i < Tasks.length;i++) {
            final int j = i + 1;
            int step = 3;
            final int len = Tasks[i].length;

            TaskId.put(j,new int[len]);
            TaskText.put(j,new String[len]);
            TaskAnswer.put(j,new String[len]);
            TaskSolution.put(j,new String[len]);
            TaskReward.put(j,new int[len]);
            if (Tasks[i][3].startsWith("task")) {
                step = 4;
                TaskImage.put(j,new int[len]);
            } else
                TaskImage.put(j,new int[] {});
            for (int k = 0;k < len;k += step) {
                TaskId.get(i)[k] = 1;
                TaskText.get(j)[k] = Tasks[i][k];
                TaskAnswer.get(j)[k] = Tasks[i][k + 1];
                TaskSolution.get(j)[k] = Tasks[i][k + 2];
                TaskReward.get(j)[k] = 60;
                if (step == 4)
                    TaskImage.get(j)[k] = context.getResources().getIdentifier(Tasks[i][k + 3], "drawable", context.getPackageName());
            }
        }
        Console.L("TaskId "+TaskId);
    }*/