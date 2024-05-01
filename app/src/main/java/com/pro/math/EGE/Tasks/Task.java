package com.pro.math.EGE.Tasks;

public class Task {
    public Integer Image;
    public String Text, Answer, Solution;
    public Task() {}
    public void Change(String Text,String Answer,String Solution,Integer Image) {
        this.Text = Text;
        this.Answer = Answer;
        this.Solution = Solution;
        this.Image = Image;
    }
}