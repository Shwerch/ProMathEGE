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
    }
}