package com.pro.math.EGE;

public class Question {
    String Question;
    String[] Answers;
    boolean[] CorrectAnswers;
    Question() {}
    void Change(String Question, String[] Answers, boolean[] CorrectAnswers) {
        this.Question = Question;
        this.Answers = Answers;
        this.CorrectAnswers = CorrectAnswers;
    }
}