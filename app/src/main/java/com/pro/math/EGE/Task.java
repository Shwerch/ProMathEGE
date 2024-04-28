package com.pro.math.EGE;


public class Task {
    int Hash, Image;
    String Text, Answer, Solution;
    Task(String Text, String Answer, String Solution, int Image) {
        this.Hash = (Text + Answer + Solution).hashCode();
        this.Text = Text;
        this.Answer = Answer;
        this.Solution = Solution;
        this.Image = Image;
    }
}
