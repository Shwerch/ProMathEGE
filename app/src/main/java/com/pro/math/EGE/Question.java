package com.pro.math.EGE;

import java.util.ArrayList;

public class Question {
    String Question;
    String[] Answers;
    ArrayList<Integer> CorrectAnswers;
    Question() {}
    void Change(String Question, String[] Answers, ArrayList<Integer> CorrectAnswers) {
        this.Question = Question;
        this.Answers = Answers;
        this.CorrectAnswers = CorrectAnswers;
    }
}