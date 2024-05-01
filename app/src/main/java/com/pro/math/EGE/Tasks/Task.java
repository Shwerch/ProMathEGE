package com.pro.math.EGE.Tasks;

public class Task {
    int Hash, Image;
    public String Text, Answer, Solution;
    public Task() {}
    public void Change(String Text, String Answer, String Solution, int Image) {
        this.Hash = (Text + Answer + Solution).hashCode();
        this.Text = Text;
        this.Answer = Answer;
        this.Solution = Solution;
        this.Image = Image;
    }
}