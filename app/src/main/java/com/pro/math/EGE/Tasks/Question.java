package com.pro.math.EGE.Tasks;

import java.util.ArrayList;

public class Question {
    public String Question, SubTopic;
    public ArrayList<String> Answers;
    public ArrayList<Integer> CorrectAnswers;
    public int Reward, CorrectAnswersCount;
    public Question() {}
    public void Change(String Question, String SubTopic, ArrayList<String> Answers, ArrayList<Integer> CorrectAnswers,int Reward,int CorrectAnswersCount) {
        this.Question = Question;
        this.Answers = Answers;
        this.CorrectAnswers = CorrectAnswers;
        this.Reward = Reward;
        this.CorrectAnswersCount = CorrectAnswersCount;
        this.SubTopic = SubTopic;
    }
}