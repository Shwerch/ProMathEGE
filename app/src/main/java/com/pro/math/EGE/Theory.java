package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Context;

import com.pro.math.EGE.Tasks.Question;

import java.util.ArrayList;
import java.util.Collections;

public class Theory {
    private static int LastTopicId = -1;
    private static int LastSubTopicId = -1;
    private static int LastTaskIndex = -1;
    @SuppressLint("DiscouragedApi")
    public static void GetTask(Context context, int topicId, Question question) {
        ArrayList<Integer> AvailableSubTopics = Database.GetAvailableSubTopics(topicId);
        final int subTopicId = AvailableSubTopics.get((int)(AvailableSubTopics.size() * Math.random()));
        int taskIndex;
        if (LastTopicId == topicId && LastSubTopicId == subTopicId) {
            taskIndex = (int) ((Database.theoryAttributes.Get(topicId,subTopicId).tasksCount - 1) * Math.random());
            if (taskIndex >= LastTaskIndex)
                taskIndex++;
        } else
            taskIndex = (int) (Database.theoryAttributes.Get(topicId,subTopicId).tasksCount * Math.random());

        String[][] tasks = Database.theoryTasks.Get(topicId,subTopicId);
        ArrayList<Integer> questionTask = new ArrayList<>(tasks[taskIndex].length - 1);
        final int questionIndex = (int) (tasks[taskIndex].length * Math.random());
        for (int i = 0;i < tasks[taskIndex].length;i++) {
            if (i != questionIndex)
                questionTask.add(i);
        }

        final int correctAnswersCount = (int)(questionTask.size() * Math.random()) + 1;
        ArrayList<String> Answers = new ArrayList<>(Collections.nCopies(6,null));
        for (int i = 0;i < correctAnswersCount;i++) {
            final int index = (int)(questionTask.size() * Math.random());
            Answers.set(i, tasks[taskIndex][questionTask.remove(index)]);
        }
        Collections.shuffle(Answers);

        final int Reward = Database.theoryAttributes.Get(topicId,subTopicId).reward;
        ArrayList<String> allQuestions = new ArrayList<>();
        for (int i = 0;i < tasks.length;i++) {
            if (i != taskIndex)
                Collections.addAll(allQuestions,tasks[i]);
        }
        Collections.shuffle(allQuestions);

        ArrayList<Integer> correctAnswersIndexes = new ArrayList<>(correctAnswersCount);
        for (int i = 0;i < 6;i++) {
            if (Answers.get(i) != null)
                correctAnswersIndexes.add(i);
            else
                Answers.set(i,allQuestions.remove(0));
        }
        String subTopic = Sources.GetStringArray(context.getResources(),Sources.GetLocaleResources(context).getStringArray(R.array.TopicsAttributes)[(topicId - 1) * 2])[Database.theorySubTopics.GetIndex(topicId,subTopicId)];
        question.Change(tasks[taskIndex][questionIndex],subTopic,Answers,correctAnswersIndexes,Reward,correctAnswersCount);
        LastTopicId = topicId;
        LastSubTopicId = subTopicId;
        LastTaskIndex = taskIndex;
    }
}