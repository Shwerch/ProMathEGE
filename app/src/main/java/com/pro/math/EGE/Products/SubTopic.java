package com.pro.math.EGE.Products;

public class SubTopic extends AbstractProduct {
    public int topicId, subTopicId, cost, tasksCount,reward;
    public boolean availability;
    public SubTopic(int topicId, int subTopicId, boolean availability, int cost, int tasksCount, int reward) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
        this.availability = availability;
        this.cost = cost;
        this.tasksCount = tasksCount;
        this.reward = reward;
    }
}
