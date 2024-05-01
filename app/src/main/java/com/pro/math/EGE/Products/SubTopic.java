package com.pro.math.EGE.Products;

public class SubTopic extends AbstractProduct {
    public int topicId;
    public int subTopicId;
    public boolean availability;
    public int cost;
    public SubTopic(int topicId, int subTopicId, boolean availability, int cost) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
        this.availability = availability;
        this.cost = cost;
    }
}
