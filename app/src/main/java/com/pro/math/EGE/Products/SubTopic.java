package com.pro.math.EGE.Products;

public class SubTopic extends AbstractProduct {
    public int topicId, subTopicId, cost, tasksCount;
    public boolean availability;
    public SubTopic(int topicId, int subTopicId, boolean availability, int cost,int tasksCount) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
        this.availability = availability;
        this.cost = cost;
        this.tasksCount = tasksCount;
    }
}
