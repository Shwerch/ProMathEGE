package com.pro.math.EGE.Products;

import androidx.annotation.NonNull;

public class SubTopic implements Comparable<SubTopic> {
    public int topicId, subTopicId, cost, tasksCount, reward;
    public boolean availability;
    public SubTopic(int topicId, int subTopicId, boolean availability, int cost, int tasksCount, int reward) {
        this.topicId = topicId;
        this.subTopicId = subTopicId;
        this.availability = availability;
        this.cost = cost;
        this.tasksCount = tasksCount;
        this.reward = reward;
    }
    @Override
    public int compareTo(@NonNull SubTopic product) {
        if (this.topicId > product.topicId)
            return 1;
        else if (this.topicId < product.topicId)
            return -1;
        else if (this.subTopicId > product.subTopicId)
            return 1;
        else if (this.subTopicId < product.subTopicId)
            return -1;
        return 0;
    }
}
