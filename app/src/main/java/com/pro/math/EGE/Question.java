package com.pro.math.EGE;

import java.util.ArrayList;

public class Question {
    String Question;
    String[] Answers;
    ArrayList<Integer> CorrectAnswers;
    int Reward;
    int CorrectAnswersCount;
    Question() {}
    void Change(String Question, String[] Answers, ArrayList<Integer> CorrectAnswers,int Reward,int CorrectAnswersCount) {
        this.Question = Question;
        this.Answers = Answers;
        this.CorrectAnswers = CorrectAnswers;
        this.Reward = Reward;
        this.CorrectAnswersCount = CorrectAnswersCount;
    }
}